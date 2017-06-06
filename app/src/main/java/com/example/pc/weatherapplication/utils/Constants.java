package com.example.pc.weatherapplication.utils;


import com.example.pc.weatherapplication.R;

import java.util.HashMap;

public class Constants {

    public static final HashMap<String, Integer> mIconToDrawable = new HashMap<>();
    static {
        mIconToDrawable.put("01d", R.drawable.sun);  // clear sky day
        mIconToDrawable.put("02d", R.drawable.cloud_sun);  // few clouds day
        mIconToDrawable.put("03d", R.drawable.cloud);  // scattered clouds day
        mIconToDrawable.put("04d", R.drawable.cloud_wind);  // broken clouds day
        mIconToDrawable.put("09d", R.drawable.cloud_rain_sun_alt);  // shower rain day
        mIconToDrawable.put("10d", R.drawable.cloud_rain_sun);  // rain day
        mIconToDrawable.put("11d", R.drawable.cloud_lightning);  // thunderstorm day
        mIconToDrawable.put("13d", R.drawable.cloud_snow_alt);  // snow day
        mIconToDrawable.put("50d", R.drawable.cloud_fog);  // mist day

        mIconToDrawable.put("01n", R.drawable.moon);  // clear sky night
        mIconToDrawable.put("02n", R.drawable.cloud_moon);  // few clouds night
        mIconToDrawable.put("03n", R.drawable.cloud);  // scattered clouds night
        mIconToDrawable.put("04n", R.drawable.cloud_wind);  // broken clouds night
        mIconToDrawable.put("09n", R.drawable.cloud_rain_moon_alt);  // shower rain night
        mIconToDrawable.put("10n", R.drawable.cloud_rain_moon);  // rain night
        mIconToDrawable.put("11n", R.drawable.cloud_lightning);  // thunderstorm night
        mIconToDrawable.put("13n", R.drawable.cloud_snow_alt);  // snow night
        mIconToDrawable.put("50n", R.drawable.cloud_fog);  // mist night
    }

    public static final String BASE_URL = "http://api.openweathermap.org/";

    public static final String KEY = "4c056cebab6673b2a6f683da3777698f";

    public static final Integer DAY_COUNT = 12;

    public static final String IMAGE_URL = "http://openweathermap.org/img/w/%1$s.png";

    public static final Integer MAX_CITY_LIMIT = 6;

    public static String getImageUrl(String iconId) {
       return String.format(IMAGE_URL, iconId);
    }


}
