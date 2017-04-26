package com.example.pc.weatherapplication.utils;


public class Constants {

    public static final String BASE_URL = "http://api.openweathermap.org/";

    public static final String KEY = "4c056cebab6673b2a6f683da3777698f";

    public static final Integer DAY_COUNT = 12;

    public static final String IMAGE_URL = "http://openweathermap.org/img/w/%1$s.png";

    public static final Integer MAX_CITY_LIMIT = 6;

    public static String getImageUrl(String iconId) {
       return String.format(IMAGE_URL, iconId);
    }


}
