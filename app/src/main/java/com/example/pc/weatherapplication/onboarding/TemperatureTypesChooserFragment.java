package com.example.pc.weatherapplication.onboarding;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.MainActivity;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TemperatureTypesChooserFragment extends android.app.Fragment {

    @BindView(R.id.celsius_choose)
    TextView mCelsius;
    @BindView(R.id.fahrenheit_choose)
    TextView mFahrenheit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_temperature_types_chooser, container, false);
        ButterKnife.bind(this, view);

        final Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        mCelsius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setUnitTypes(v.getContext(),"metric");
                PreferenceUtils.isBoardingCompleted(v.getContext(), true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mFahrenheit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setUnitTypes(v.getContext(),"imperial");
                PreferenceUtils.isBoardingCompleted(v.getContext(), true);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

}
