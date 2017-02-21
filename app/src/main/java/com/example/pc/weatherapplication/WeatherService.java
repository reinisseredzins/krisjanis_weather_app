package com.example.pc.weatherapplication;

import com.example.pc.weatherapplication.utils.Constants;
import com.example.pc.weatherapplication.weather_daily.WeatherDaily;
import com.example.pc.weatherapplication.weather_details.WeatherDetails;
import com.example.pc.weatherapplication.weather_now.WeatherNow;

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

    public static Call<WeatherNow> getWeatherForecast(Callback<WeatherNow> callback, String city, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<WeatherNow> forecastCall = weatherAPI.fetchWeatherForecast(city, Constants.KEY, units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<WeatherDetails> getDetails(Callback<WeatherDetails> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<WeatherDetails> forecastCall = weatherAPI.fetchDetails(cityid, Constants.KEY, units);
        forecastCall.enqueue(callback);
        return forecastCall;
    }

    public static Call<WeatherDaily> getDaily(Callback<WeatherDaily> callback, String cityid, String units) {
        final WeatherAPI weatherAPI = getRetrofitClient();
        Call<WeatherDaily> forecastCall = weatherAPI.fetchDaily(cityid, units, Constants.DAY_COUNT, Constants.KEY);
        forecastCall.enqueue(callback);
        return forecastCall;
    }
}
