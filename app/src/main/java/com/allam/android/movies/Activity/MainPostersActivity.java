package com.allam.android.movies.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.allam.android.movies.Fragment.MainPostersFragment;
import com.allam.android.movies.Fragment.MovieDetailFragment;
import com.allam.android.movies.Model.Movie;
import com.allam.android.movies.R;

public class MainPostersActivity extends AppCompatActivity implements MainPostersFragment.Callback {

    private String mCategory;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = getCategory();

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

    }

    protected Fragment createFragment() {
        return MainPostersFragment.newInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String sort = getCategory();
        if (sort != null && !sort.equals(mCategory)) {
            MainPostersFragment pmf = (MainPostersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pop);
            if (null != pmf) {
                pmf.updateView();
            }
            mCategory = sort;
        }
    }



    @Override
    public void onItemSelected(Movie.ResultsBean movie) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, MovieDetailFragment.newInstance(movie), DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = DetailActivity.newIntent(this, movie);
            startActivity(intent);
        }
    }

    public String getCategory() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String category = prefs.getString(getBaseContext().getString(R.string.pref_category_key),
                getBaseContext().getString(R.string.pref_category_popular));
        return category;
    }


}
