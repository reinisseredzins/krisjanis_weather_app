package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.weather_daily.ExampleDaily;
import com.example.pc.weatherapplication.weather_details.ExampleDetails;
import com.example.pc.weatherapplication.weather_now.ExampleNow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<ExampleNow> fetchWeatherForecast(
            @Query("q") String city,
            @Query("APPID") String appid,
            @Query("units") String units
    );

    @GET("data/2.5/forecast")
    Call<ExampleDetails> fetchDetails(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units

    );

    @GET("data/2.5/forecast/daily")
    Call<ExampleDaily> fetchDaily(
            @Query("q") String city,
            @Query("units") String units,
            @Query("cnt") Integer cnt,
            @Query("APPID") String appid
    );
}
