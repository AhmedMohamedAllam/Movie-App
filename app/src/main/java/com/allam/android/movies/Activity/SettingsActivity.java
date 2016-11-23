package com.allam.android.movies.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.allam.android.movies.Fragment.SettingsFragment;


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
