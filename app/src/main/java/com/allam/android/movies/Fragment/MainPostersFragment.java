package com.allam.android.movies.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.allam.android.movies.Activity.SettingsActivity;
import com.allam.android.movies.Model.Movie;
import com.allam.android.movies.MovieAdapter;
import com.allam.android.movies.Network.NetworkTasks;
import com.allam.android.movies.R;
import com.allam.android.movies.data.MovieDBSchema;
import com.allam.android.movies.data.MovieDBSchema.MovieTable;

import java.io.IOException;


/**
 * Created by Allam on 07/08/2016.
 */
public class MainPostersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private boolean isTablet;
    private final static String mPosterSize = "w185";
    private static final int MOVIE_LOADER = 0;
    private int mCurrentPage = 1, mTotalPages = 1;
    private MovieAdapter mMovieAdapter;
    private GridView mGridView;
    private int mPosition = 0;
    private boolean isFirstExcuteBackgroundTask = true;
    public final static String Cols[] = {
            MovieTable.TABLE_NAME + "." + MovieTable.Columns._ID,
            MovieTable.TABLE_NAME + "." + MovieTable.Columns.MOVIE_ID,
            MovieTable.Columns.PATH,
            MovieTable.Columns.TITLE,
            MovieTable.Columns.OVERVIEW,
            MovieTable.Columns.DATE,
            MovieTable.Columns.RATE,

    };

    public static int _ID = 0;
    public static int MOVIE_ID = 1;
    public static int POSTER_PATH = 2;
    public static int ORIGINAL_TITLE = 3;
    public static int OVERVIEW = 4;
    public static int RELEASE_DATE = 5;
    public static int VOTE_AVERAGE = 6;

    public static MainPostersFragment newInstance() {
        return new MainPostersFragment();
    }

    public interface Callback {
        void onItemSelected(Movie.ResultsBean movie);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_posters_fragment, container, false);
        // mRecyclerView = (RecyclerView) view.findViewById(R.id.main_posters_recyclerview);
        mMovieAdapter = new MovieAdapter(getActivity(), null, 0);
        mGridView = (GridView) view.findViewById(R.id.movies_grid_view);

        //Change the title dynamically
        String category = getCategory();
        if (category.equals(getString(R.string.pref_category_popular))) {
            getActivity().setTitle("Popular");
        } else if (category.equals(getString(R.string.pref_category_top_rated))) {
            getActivity().setTitle("Top Rated");
        } else {
            getActivity().setTitle("Favourite");
        }


        // Check if in landscape mode
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Check if it is tablet or not
        isTablet = getActivity().getResources().getBoolean(R.bool.isTablet);
        if (isTablet || width > height)
            mGridView.setNumColumns(3);
        else
            mGridView.setNumColumns(2);

        mGridView.setAdapter(mMovieAdapter);


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    Movie.ResultsBean movie = getMovieFromCursor(cursor);
                    ((Callback) getActivity())
                            .onItemSelected(movie);
                }
            }
        });


        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && totalItemCount != 0 && mCurrentPage != mTotalPages) {
                    new FetchMovieTask().execute(getCategory(), mCurrentPage++ + "");
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
        return view;
    }


    private Movie.ResultsBean getMovieFromCursor(Cursor cursor) {
        Movie.ResultsBean movie = new Movie.ResultsBean();
        movie.setId(cursor.getInt(MOVIE_ID));
        movie.setPoster_path(cursor.getString(POSTER_PATH));
        movie.setOriginal_title(cursor.getString(ORIGINAL_TITLE));
        movie.setOverview(cursor.getString(OVERVIEW));
        movie.setRelease_date(cursor.getString(RELEASE_DATE));
        movie.setVote_average(cursor.getDouble(VOTE_AVERAGE));
        return movie;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt("position");
        }
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", mPosition);
    }

    //-------------------------------------------------------------------------------------------

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sortOrder = null;
        Uri tableUri = MovieDBSchema.PopularTable.CONTENT_URI;

        if (getCategory().equals(getString(R.string.pref_category_popular))) {
            tableUri = MovieDBSchema.PopularTable.CONTENT_URI;
        } else if (getCategory().equals(getString(R.string.pref_category_top_rated))) {
            tableUri = MovieDBSchema.TopRatedTable.CONTENT_URI;
            sortOrder = MovieTable.Columns.RATE + " DESC";
        } else if (getCategory().equals(getString(R.string.pref_category_favourite))) {
            tableUri = MovieDBSchema.FavouriteTable.CONTENT_URI;
        }

        return new CursorLoader(getActivity(),
                tableUri,
                Cols,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieAdapter.swapCursor(null);
    }
    //-------------------------------------------------------------------------------------------

    @Override
    public void onStart() {
        super.onStart();
        updateView();
    }

    public void updateView() {

        if (!getCategory().equals(getString(R.string.pref_category_favourite))) {
            if(isNetworkAvailable(getActivity())){
                new FetchMovieTask().execute(getCategory(), mCurrentPage + "");
            }else {
                Toast.makeText(getActivity(), "Error, Check network connection!", Toast.LENGTH_SHORT).show();
            }
        }
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }


    //Build poster's url after fetching it's path
    public static String buildPosterUrl(String photoPath) {
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/").buildUpon().appendEncodedPath(mPosterSize)
                .appendEncodedPath(photoPath).build();
        return uri.toString();
    }

    //inflate menu option xml
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().finish();
                startActivity(getActivity().getIntent());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //background task for fetching movies data
    public class FetchMovieTask extends AsyncTask<String, Void, Movie> {

        @Override
        protected Movie doInBackground(String... params) {
            try {
                return new NetworkTasks(getActivity()).fetchMovieData(params[0], params[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            if (movie != null) {
                mTotalPages = movie.getTotal_pages();
            }
        }
    }

    public String getCategory() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String category = prefs.getString(getActivity().getString(R.string.pref_category_key),
                getActivity().getString(R.string.pref_category_popular));
        return category;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
