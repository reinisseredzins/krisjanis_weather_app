package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.ActivityFragmentInterface;
import com.example.pc.weatherapplication.FragmentActivityInterface;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherService;
import com.example.pc.weatherapplication.custom_views.TemperatureGraph;
import com.example.pc.weatherapplication.models.weather.CurrentWeather;
import com.example.pc.weatherapplication.models.weather.EveryDayForecast;
import com.example.pc.weatherapplication.models.weather.ForecastEveryThreeHours;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TomorrowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ActivityFragmentInterface {

    private TextView mTemp;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String TAG = TomorrowFragment.class.getSimpleName();

    FragmentActivityInterface fragmentActivityInterface;

    @BindView(R.id.temperature_graph_now)
    TemperatureGraph mTemperatureGraph;

    public TomorrowFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityInterface) {
            fragmentActivityInterface = (FragmentActivityInterface) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityInterface = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        ButterKnife.bind(this, view);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout_tomorrow);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mTemp = (TextView) view.findViewById(R.id.tomorrow_temp);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    private class EveryDayForecastCallback implements Callback<EveryDayForecast> {

        @Override
        public void onResponse(Call<EveryDayForecast> call, Response<EveryDayForecast> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            final EveryDayForecast forecast = response.body();
            //mTemp.setText(String.valueOf(forecast.));
        }

        @Override
        public void onFailure(Call<EveryDayForecast> call, Throwable t) {
            Log.e(TAG, "Received error from NowFragment network call");

            mSwipeRefreshLayout.setRefreshing(false);
            if (fragmentActivityInterface != null) {
                fragmentActivityInterface.displayOfflineSnackBar();
            }
        }
    }

    private class ForecastEveryThreeHoursCallback implements Callback<ForecastEveryThreeHours> {

        @Override
        public void onResponse(Call<ForecastEveryThreeHours> call, Response<ForecastEveryThreeHours> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            final ForecastEveryThreeHours forecast = response.body();
            mTemperatureGraph.addButtonViews(forecast.getWeatherMetadata(), TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) + 1);
        }

        @Override
        public void onFailure(Call<ForecastEveryThreeHours> call, Throwable t) {
            Log.e(TAG, "Received error from NowFragment network call");

            mSwipeRefreshLayout.setRefreshing(false);
            if (fragmentActivityInterface != null) {
                fragmentActivityInterface.displayOfflineSnackBar();
            }
        }
    }



    public void reloadData() {
        String unitTypes = PreferenceUtils.getUnitTypes(getActivity());
        String city = PreferenceUtils.getSelectedCity(getActivity());
        WeatherService.getEveryDayForecast(new EveryDayForecastCallback(), city, unitTypes);
        WeatherService.getForecastEveryThreeHours(new ForecastEveryThreeHoursCallback(), city, unitTypes);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        reloadData();
    }

}
