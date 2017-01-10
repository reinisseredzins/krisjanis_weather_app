package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherService;
import com.example.pc.weatherapplication.weather_now.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowFragment extends Fragment implements Callback<Example>, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTemp;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NowFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_now, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout_now);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTemp = (TextView) view.findViewById(R.id.now_temp);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {
        mSwipeRefreshLayout.setRefreshing(false);
        final Example forecast = response.body();
        mTemp.setText(Double.toString(forecast.getMain().getTemp()));
    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {}


    public void reloadData() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        WeatherService.getWeatherForecast(this, unitTypes);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        reloadData();
    }
}
