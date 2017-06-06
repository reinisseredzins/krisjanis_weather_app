package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.models.weather.EveryDayForecast;
import com.example.pc.weatherapplication.models.weather.ForecastEveryThreeHours;
import com.example.pc.weatherapplication.models.weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {

    @GET("data/2.5/weather")
    Call<CurrentWeather> fetchWeatherNow(
            @Query("q") String city,
            @Query("APPID") String appid,
            @Query("units") String units
    );

    @GET("data/2.5/forecast")
    Call<ForecastEveryThreeHours> fetchForecast(
            @Query("q") String city,
            @Query("appid") String appid,
            @Query("units") String units

    );

    @GET("data/2.5/forecast/daily")
    Call<EveryDayForecast> fetchDaily(
            @Query("q") String city,
            @Query("units") String units,
            @Query("cnt") Integer cnt,
            @Query("APPID") String appid
    );
}
