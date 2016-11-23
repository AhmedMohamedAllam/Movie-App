package com.allam.android.movies.Fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.allam.android.movies.R;


/**
 * Created by Allam on 02/08/2016.
 */
public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        bindPrenferenceSummarytoValue(findPreference(getString(R.string.pref_category_key)));
    }

    public void bindPrenferenceSummarytoValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i("AAA", PreferenceManager.getDefaultSharedPreferences(preference.getContext())
            .getString(preference.getKey(), "") );
        String stringValue = newValue.toString();

        //Change the title
        if (stringValue.equals(getString(R.string.pref_category_popular))) {
            stringValue = "Popular";
        } else if(stringValue.equals(getString(R.string.pref_category_top_rated))){
            stringValue = "Top Rated";
        }else {
            stringValue = "Favourite";
        }
        preference.setSummary(stringValue);
        return true;
    }
}
