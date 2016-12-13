package com.example.pc.weatherapplication;

import android.support.v7.view.ActionMode;
import android.util.Log;

import com.example.pc.weatherapplication.JSON.Forecast;
import com.example.pc.weatherapplication.details.DetailsFragment;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Krisjanis on 04/11/2016.
 */

public class WeatherService {

    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static WeatherAPI mWeatherAPI;

    public static WeatherAPI getRetrofitClient() {
        if (mWeatherAPI == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient okHttpClient = new  OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            WeatherService.mWeatherAPI = retrofit.create(WeatherAPI.class);
        }
        return mWeatherAPI;
    }

    public static Call<Forecast> getWeatherForecast(Callback<Forecast> callback, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<Forecast> forecastCall = weatherAPI.fetchWeatherForecast("-12,59,31,43", "yes", "4c056cebab6673b2a6f683da3777698f", units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<com.example.pc.weatherapplication.JSON_Details.Example> getDetails(Callback<com.example.pc.weatherapplication.JSON_Details.Example> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<com.example.pc.weatherapplication.JSON_Details.Example> forecastCall = weatherAPI.fetchDetails(cityid, "4c056cebab6673b2a6f683da3777698f", units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<com.example.pc.weatherapplication.JSON_Daily.Example> getDaily(Callback<com.example.pc.weatherapplication.JSON_Daily.Example> callback, String cityid, String units ) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<com.example.pc.weatherapplication.JSON_Daily.Example> forecasrtCall = weatherAPI.fetchDaily(cityid, units, 14, "4c056cebab6673b2a6f683da3777698f");
        forecasrtCall.enqueue(callback);
        return forecasrtCall;
    }


}
