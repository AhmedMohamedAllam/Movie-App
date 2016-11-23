package com.allam.android.movies.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.allam.android.movies.R;
import com.allam.android.movies.Fragment.MovieDetailFragment;
import com.allam.android.movies.Model.Movie;

public class DetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movie.ResultsBean movie = (Movie.ResultsBean) getIntent().getSerializableExtra(MovieDetailFragment.EXTRA_MOVIE);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, MovieDetailFragment.newInstance(movie))
                    .commit();
        }
    }

    public static Intent newIntent(Context c, Movie.ResultsBean movie) {
        Intent i = new Intent(c, DetailActivity.class);
        i.putExtra(MovieDetailFragment.EXTRA_MOVIE, movie);
        return i;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
