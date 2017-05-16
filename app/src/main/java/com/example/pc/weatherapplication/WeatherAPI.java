package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.models.weather.WeatherDaily;
import com.example.pc.weatherapplication.models.weather.WeatherDetails;
import com.example.pc.weatherapplication.models.weather.WeatherNow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<WeatherNow> fetchWeatherForecast(
            @Query("q") String city,
            @Query("APPID") String appid,
            @Query("units") String units
    );

    @GET("data/2.5/forecast")
    Call<WeatherDetails> fetchDetails(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units

    );

    @GET("data/2.5/forecast/daily")
    Call<WeatherDaily> fetchDaily(
            @Query("q") String city,
            @Query("units") String units,
            @Query("cnt") Integer cnt,
            @Query("APPID") String appid
    );
}
