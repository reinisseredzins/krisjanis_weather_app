package com.example.pc.weatherapplication.onboarding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.weatherapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureTypesChooserFragment extends android.app.Fragment {


    public TemperatureTypesChooserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_temperature_types_chooser, container, false);
    }

}
