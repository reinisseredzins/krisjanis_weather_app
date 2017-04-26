package com.example.pc.weatherapplication.utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class PreferenceUtils {

    private static final String PREFERRED_CITY_KEY = "pref_city";
    private static final String DEFAULT_CITY = "riga";
    private static final String USED_CITY = "";

    private static final String PREFERRED_TEMPERATURE_TYPE_KEY = "pref_temp_type";
    private static final String DEFAULT_TYPE = "metric";

    private static final String PREFERRED_BOOLEAN_KEY = "pref_boolean_key";


    public static String getCityTypes(Context context) {
        if (context != null && context.getPackageName() != null) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_CITY_KEY, DEFAULT_CITY);
        }
        return DEFAULT_TYPE;
    }

    public static void setCity(Context context, String city)     {
        if (context != null && context.getPackageName() != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREFERRED_CITY_KEY, city).commit();
        }
    }

    public static String getUnitTypes(Context context) {
        if (context != null && context.getPackageName() != null)  {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_TEMPERATURE_TYPE_KEY, DEFAULT_TYPE);
        }
        return DEFAULT_TYPE;
    }

    public static void setUnitTypes(Context context, String type) {
        if (context != null && context.getPackageName() != null)  {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREFERRED_TEMPERATURE_TYPE_KEY, type);
        }
    }

    public static Boolean isDatabasePopulated(Context context)  {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERRED_BOOLEAN_KEY, false);
    }

    public static void setDatabasePopulated(Context context, boolean isPopulated)  {
       PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERRED_BOOLEAN_KEY, isPopulated).apply();
    }

    public static Boolean isBoardingCompleted(Context context)  {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERRED_BOOLEAN_KEY, false);
    }

    public static void isBoardingCompleted(Context context, boolean isCompleted)  {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERRED_BOOLEAN_KEY, isCompleted).apply();
    }

}