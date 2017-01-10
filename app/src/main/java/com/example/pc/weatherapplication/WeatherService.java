package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.weather_now.Example;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherService {

    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static WeatherAPI mWeatherAPI;

    public static WeatherAPI getRetrofitClient() {
        if (mWeatherAPI == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
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

    public static Call<Example> getWeatherForecast(Callback<Example> callback, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<Example> forecastCall = weatherAPI.fetchWeatherForecast("Riga", "4c056cebab6673b2a6f683da3777698f", units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<com.example.pc.weatherapplication.weather_details.Example> getDetails(Callback<com.example.pc.weatherapplication.weather_details.Example> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<com.example.pc.weatherapplication.weather_details.Example> forecastCall = weatherAPI.fetchDetails(cityid, "4c056cebab6673b2a6f683da3777698f", units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<com.example.pc.weatherapplication.weather_daily.Example> getDaily(Callback<com.example.pc.weatherapplication.weather_daily.Example> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<com.example.pc.weatherapplication.weather_daily.Example> forecastCall = weatherAPI.fetchDaily(cityid, units, 8, "4c056cebab6673b2a6f683da3777698f");
        forecastCall.enqueue(callback);
        return forecastCall;
    }
}
