package com.example.pc.weatherapplication.utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class PreferenceUtils {

    private static final String PREFERRED_CITY_KEY =  "pref_city";
    private static final String DEFAULT_CITY = "riga";

    private static final String PREFERRED_TEMPERATURE_TYPE_KEY = "pref_temp_type";
    private static final String DEFAULT_TYPE = "metric";

    public static String getCityTypes(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_CITY_KEY, DEFAULT_CITY);
    }

    public static String getUnitTypes(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_TEMPERATURE_TYPE_KEY, DEFAULT_TYPE);
    }
}