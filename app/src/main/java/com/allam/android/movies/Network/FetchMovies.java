package com.allam.android.movies.Network;

import android.net.Uri;
import android.util.Log;

import com.allam.android.movies.Model.Movie;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Allam on 07/08/2016.
 */
public class FetchMovies {
    private static final String API = "3e384d99b5dbbc117909fd8288616749";
    private static final String TAG = "MoviesLogTag";

    private String buildUrl(String searchType, String page) {
        String url = Uri.parse("https://api.themoviedb.org/3/movie/").buildUpon()
                .appendEncodedPath(searchType)
                .appendQueryParameter("api_key", API)
                .appendQueryParameter("page", page)
                .build().toString();
        Log.i(TAG, url);
        return url;
    }


    private String getUrlBytes(String searchType, String page) throws IOException {
        String urlSpec = buildUrl(searchType, page);
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
        } finally {
            connection.disconnect();
        }

    }

    public Movie fetchMovieData(String searchType, String page) throws IOException {
        try {
            String parsedData = getUrlBytes(searchType, page);
            Gson gson = new Gson();
            Movie movie = gson.fromJson(parsedData, Movie.class);
            return movie;
        } catch (Exception e) {
            return null;
        }
    }
}
