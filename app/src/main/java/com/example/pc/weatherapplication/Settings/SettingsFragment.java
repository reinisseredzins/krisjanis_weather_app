package com.example.pc.weatherapplication.Settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.R;


public class SettingsFragment extends PreferenceFragment implements FragmentInterface{

    public static final String TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_layout);
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
