package com.example.pc.weatherapplication.utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class PreferenceUtils {

    private static final String PREFERRED_CITY_KEY = "pref_city";
    private static final String DEFAULT_CITY = "riga";

    private static final String PREFERRED_TEMPERATURE_TYPE_KEY = "pref_temp_type";
    private static final String DEFAULT_TYPE = "metric";

    private static final String PREFERRED_BOOLEAN_KEY = "pref_boolean_key";


    public static String getCityTypes(Context context) {
        if (context != null && context.getPackageName() != null) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_CITY_KEY, DEFAULT_CITY);
        }
        return DEFAULT_TYPE;
    }

    public static String getUnitTypes(Context context) {
        if (context != null && context.getPackageName() != null)  {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_TEMPERATURE_TYPE_KEY, DEFAULT_TYPE);
        }
        return DEFAULT_TYPE;
    }

    public static Boolean isDatabasePopulated(Context context)  {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERRED_BOOLEAN_KEY, false);
    }
    public static void setDatabasePopulated(Context context, boolean isPopulated)  {
       PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERRED_BOOLEAN_KEY, isPopulated).apply();
    }


}