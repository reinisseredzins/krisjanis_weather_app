package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.weather_now.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<Example> fetchWeatherForecast(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units
    );

    @GET("data/2.5/forecast")
    Call<com.example.pc.weatherapplication.weather_details.Example> fetchDetails(
            @Query("id") String id,
            @Query("appid") String appid,
            @Query("units") String units

    );

    @GET("data/2.5/forecast/daily")
    Call<com.example.pc.weatherapplication.weather_daily.Example> fetchDaily(
            @Query("q") String daily,
            @Query("units") String units,
            @Query("cnt") Integer cnt,
            @Query("APPID") String appid
    );
}
