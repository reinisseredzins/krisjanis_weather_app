package com.example.pc.weatherapplication;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.pc.weatherapplication.database.CityListDbHelper;
import com.example.pc.weatherapplication.onboarding.BoardingActivity;
import com.example.pc.weatherapplication.utils.PreferenceUtils;
import com.example.pc.weatherapplication.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String citiesJson = Utils.readStringFromAssets(this, "cities.json");

        Boolean isDatabasePopulated = PreferenceUtils.isDatabasePopulated(this);
        Boolean isBoardingCompleted = PreferenceUtils.isBoardingCompleted(this);

        if (!isDatabasePopulated) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CityList>>() {
            }.getType();
            List<CityList> posts = (List<CityList>) gson.fromJson(citiesJson, listType);
            CityListDbHelper cityListDbHelper = new CityListDbHelper(this);
            cityListDbHelper.getWritableDatabase().beginTransaction();
            for (CityList specificCity : posts) {
                cityListDbHelper.insertNewRow(specificCity);
            }
            cityListDbHelper.getWritableDatabase().setTransactionSuccessful();
            cityListDbHelper.getWritableDatabase().endTransaction();
            PreferenceUtils.setDatabasePopulated(this, true);
        }
        Log.v("aa", "VVV");

        if (!isBoardingCompleted) {

        }

        Intent intent = null;
        if (isBoardingCompleted) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, BoardingActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}
