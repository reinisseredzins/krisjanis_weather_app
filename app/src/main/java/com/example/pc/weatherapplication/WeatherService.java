package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.utils.Constants;
import com.example.pc.weatherapplication.weather_daily.ExampleDaily;
import com.example.pc.weatherapplication.weather_details.ExampleDetails;
import com.example.pc.weatherapplication.weather_now.ExampleNow;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WeatherService {

    private static WeatherAPI mWeatherAPI;

    public static WeatherAPI getRetrofitClient() {
        if (mWeatherAPI == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            WeatherService.mWeatherAPI = retrofit.create(WeatherAPI.class);
        }
        return mWeatherAPI;
    }

    public static Call<ExampleNow> getWeatherForecast(Callback<ExampleNow> callback, String city, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<ExampleNow> forecastCall = weatherAPI.fetchWeatherForecast(city, Constants.KEY, units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<ExampleDetails> getDetails(Callback<ExampleDetails> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<ExampleDetails> forecastCall = weatherAPI.fetchDetails(cityid, Constants.KEY, units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<ExampleDaily> getDaily(Callback<ExampleDaily> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<ExampleDaily> forecastCall = weatherAPI.fetchDaily(cityid, units, Constants.DAY_COUNT, Constants.KEY);
        forecastCall.enqueue(callback);
        return forecastCall;
    }
}
