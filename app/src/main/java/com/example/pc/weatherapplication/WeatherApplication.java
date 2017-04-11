package com.example.pc.weatherapplication;


import android.app.Application;
import android.util.Log;

import com.example.pc.weatherapplication.database.CityListDbHelper;
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

        String aa = Utils.readStringFromAssets(this, "cities.json");

        Boolean isDatabasePopulated = PreferenceUtils.isDatabasePopulated(this);

        if (!isDatabasePopulated) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CityList>>() {
            }.getType();
            List<CityList> posts = (List<CityList>) gson.fromJson(aa, listType);
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

    }
}
