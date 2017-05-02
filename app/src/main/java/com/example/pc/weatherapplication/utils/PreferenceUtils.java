package com.example.pc.weatherapplication.utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class PreferenceUtils {

    private static final String PREFERRED_CITY_KEY = "pref_city";
    private static final String DEFAULT_CITY = "riga";

    private static final String PREFERRED_TEMPERATURE_TYPE_KEY = "pref_temp_type";
    private static final String DEFAULT_TYPE = "metric";

    private static final String IS_DATABASE_POPULATED_KEY = "pref_boolean_key_database";
    private static final String IS_ONBOARDING_COMPLETED_KEY = "pref_boolean_key_onboarding";



    public static String getSelectedCity(Context context) {
        if (context != null && context.getPackageName() != null) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(PREFERRED_CITY_KEY, DEFAULT_CITY);
        }
        return DEFAULT_TYPE;
    }

    public static void setSelectedCity(Context context, String city)     {
        if (context != null && context.getPackageName() != null) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREFERRED_CITY_KEY, city).apply();
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
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREFERRED_TEMPERATURE_TYPE_KEY, type).apply();
        }
    }

    public static Boolean isDatabasePopulated(Context context)  {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_DATABASE_POPULATED_KEY, false);
    }

    public static void setDatabasePopulated(Context context, boolean isPopulated)  {
       PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(IS_DATABASE_POPULATED_KEY, isPopulated).apply();
    }

    public static Boolean isBoardingCompleted(Context context)  {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_ONBOARDING_COMPLETED_KEY, false);
    }

    public static void isBoardingCompleted(Context context, boolean isCompleted)  {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(IS_ONBOARDING_COMPLETED_KEY, isCompleted).apply();
    }

}