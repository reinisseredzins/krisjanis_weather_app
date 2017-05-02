package com.example.pc.weatherapplication.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.pc.weatherapplication.MainActivity;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import butterknife.ButterKnife;

public class BoardingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean isBoardingCompleted = PreferenceUtils.isBoardingCompleted(this);


        Intent intent = null;
        if (isBoardingCompleted) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_boarding);
            ButterKnife.bind(this);
            getFragmentManager().beginTransaction().replace(R.id.boarding_frame, new CityChooserFragment()).commit();
        }

    }
}
