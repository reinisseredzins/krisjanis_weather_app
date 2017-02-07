package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.pc.weatherapplication.utils.PreferenceUtils;
import com.example.pc.weatherapplication.weather_now.ExampleNow;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NowFragment extends Fragment implements Callback<ExampleNow>, SwipeRefreshLayout.OnRefreshListener, ActivityFragmentInterface {

    private String TAG = NowFragment.class.getSimpleName();
    private TextView mTemp;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    FragmentActivityInterface fragmentActivityInterface;

    public NowFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  FragmentActivityInterface) {
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
    public void onResponse(Call<ExampleNow> call, Response<ExampleNow> response) {
        mSwipeRefreshLayout.setRefreshing(false);
        final ExampleNow forecast = response.body();
        mTemp.setText(Double.toString(forecast.getMain().getTemp()));
    }

    @Override
    public void onFailure(Call<ExampleNow> call, Throwable t) {
        Log.e(TAG, "Received error from NowFragment network call");

        mSwipeRefreshLayout.setRefreshing(false);
        if (fragmentActivityInterface != null) {
            fragmentActivityInterface.showofflinesnackbar();
        }
    }

    public void reloadData() {
        String unitTypes = PreferenceUtils.getUnitTypes(getActivity());
        String city = PreferenceUtils.getCityTypes(getActivity());
        WeatherService.getWeatherForecast(this, city, unitTypes);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        reloadData();
    }
    }

