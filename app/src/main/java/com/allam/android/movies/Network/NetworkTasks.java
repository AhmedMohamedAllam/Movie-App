package com.allam.android.movies.Network;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.allam.android.movies.BuildConfig;
import com.allam.android.movies.Model.Movie;
import com.allam.android.movies.Model.Review;
import com.allam.android.movies.Model.Trailer;
import com.allam.android.movies.R;
import com.allam.android.movies.data.MovieDBSchema;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.DATE;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.MOVIE_ID;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.OVERVIEW;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.PATH;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.RATE;
import static com.allam.android.movies.data.MovieDBSchema.MovieTable.Columns.TITLE;

/**
 * Created by Allam on 07/08/2016.
 */
public class NetworkTasks {
    private Context mContext;
    public NetworkTasks(Context context) {
        mContext = context;
    }

    private static final String API = "api_key";

    private String buildUrl(String searchType, String page) {
        if (searchType.equals("favourite")) {
            return null;
        }
        String url = Uri.parse("https://api.themoviedb.org/3/movie/").buildUpon()
                .appendEncodedPath(searchType)
                .appendQueryParameter(API, BuildConfig.POP_MOVIES_API_KEY)
                .appendQueryParameter("page", page)
                .build().toString();
        return url;
    }

    private String buildTrailerAndReviewsUrl(String type, int MovieId, String page) {
        String url = Uri.parse("https://api.themoviedb.org/3/movie/").buildUpon()
                .appendEncodedPath(Integer.toString(MovieId))
                .appendEncodedPath(type)
                .appendQueryParameter(API, BuildConfig.POP_MOVIES_API_KEY)
                .appendQueryParameter("page", page)
                .build().toString();
        return url;
    }


    private String getUrlBytes(String searchType, String page, Integer MovieId) throws IOException {

        String urlSpec;
        if (MovieId == null) {
            urlSpec = buildUrl(searchType, page);
        } else {
            urlSpec = buildTrailerAndReviewsUrl(searchType, MovieId, page);
        }

        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toString();
        }
        finally {
            connection.disconnect();
        }

    }

    public Movie fetchMovieData(String searchType, String page) throws IOException {
        String parsedData  = getUrlBytes(searchType, page, null);
        Gson gson = new Gson();
        Movie movie = gson.fromJson(parsedData, Movie.class);

        Vector<ContentValues> lcv = new Vector<>();
        Vector<ContentValues> lid = new Vector<>();

        List<Movie.ResultsBean> movies = movie.getResults();

        for (int i = 0; i < movies.size(); i++) {
            ContentValues cv = getMovieContentValues(movies.get(i));
            ContentValues cid = new ContentValues();
            cid.put(MovieDBSchema.MovieTable.Columns.MOVIE_ID, movies.get(i).getId());
            lcv.add(cv);
            lid.add(cid);
        }

        if (lcv.size() > 0) {
            ContentValues[] cvArray = new ContentValues[lcv.size()];
            lcv.toArray(cvArray);

            ContentValues[] cvids = new ContentValues[lid.size()];
            lid.toArray(cvids);

            mContext.getContentResolver().bulkInsert(MovieDBSchema.MovieTable.CONTENT_URI, cvArray);

            int ins;
            if (getCategory().equals(mContext.getResources().getString(R.string.pref_category_popular))){
                ins = mContext.getContentResolver().bulkInsert(MovieDBSchema.PopularTable.CONTENT_URI, cvids);
            }
            else
                ins = mContext.getContentResolver().bulkInsert(MovieDBSchema.TopRatedTable.CONTENT_URI, cvids);
        }

        return movie;

    }

    public Trailer fetchTrailers(String page, Integer MovieId) throws IOException {
        if (MovieId != null) {
            String jsonString = getUrlBytes("videos", page, MovieId);

            Gson gson = new Gson();
            Trailer trailer = gson.fromJson(jsonString, Trailer.class);
            return trailer;
        } else
            return null;
    }

    public Review fetchreviews(String page, Integer MovieId) throws IOException {
        if (MovieId != null) {
            String jsonString = getUrlBytes("reviews", page, MovieId);
            Gson gson = new Gson();
            Review review = gson.fromJson(jsonString, Review.class);
            return review;
        } else
            return null;
    }


    private ContentValues getMovieContentValues(Movie.ResultsBean item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID, item.getId());
        contentValues.put(TITLE, item.getOriginal_title());
        contentValues.put(OVERVIEW, item.getOverview());
        contentValues.put(RATE, item.getVote_average());
        contentValues.put(DATE, item.getRelease_date());
        contentValues.put(PATH, item.getPoster_path());
        return contentValues;
    }

    public String getCategory() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String category = prefs.getString(mContext.getString(R.string.pref_category_key),
                mContext.getString(R.string.pref_category_popular));
        return category;
    }

}
