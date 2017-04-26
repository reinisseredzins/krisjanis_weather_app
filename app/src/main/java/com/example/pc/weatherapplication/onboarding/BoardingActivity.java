package com.example.pc.weatherapplication.onboarding;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.fragments.CityChooserDialogFragment;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boarding);
        ButterKnife.bind(this);
        getFragmentManager().beginTransaction().replace(R.id.boarding_frame, new CityChooserFagment()).commit();
    }
}
