package com.example.pc.weatherapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.pc.weatherapplication.JSON.Forecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Krisjanis on 04/11/2016.
 */

public interface WeatherAPI {

    @GET("data/2.5/box/city")
    Call<Forecast> fetchWeatherForecast(
            @Query("bbox") String coordinates,
            @Query("cluster") String shouldUseCluster,
            @Query("appid") String appid,
            @Query("units") String units
            );

    @GET("data/2.5/forecast")
    Call<com.example.pc.weatherapplication.JSON_Details.Example> fetchDetails(
            @Query("id") String id,
            @Query("appid") String appid,
            @Query("units") String units

    );

    @GET("data/2.5/forecast/daily")
    Call<com.example.pc.weatherapplication.JSON_Daily.Example> fetchDaily(
            @Query("q") String daily,
            @Query("units") String units,
            @Query("cnt") Integer cnt,
            @Query("APPID") String appid
    );
}
