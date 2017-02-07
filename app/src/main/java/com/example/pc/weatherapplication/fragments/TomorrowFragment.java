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
import com.example.pc.weatherapplication.weather_daily.ExampleDaily;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TomorrowFragment extends Fragment implements Callback<ExampleDaily>, SwipeRefreshLayout.OnRefreshListener, ActivityFragmentInterface {

    private TextView mTemp;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String TAG = TomorrowFragment.class.getSimpleName();

    FragmentActivityInterface fragmentActivityInterface;

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

    @Override
    public void onResponse(Call<ExampleDaily> call, Response<ExampleDaily> response) {
        mSwipeRefreshLayout.setRefreshing(false);
        final ExampleDaily forecast = response.body();
        mTemp.setText(Double.toString(forecast.getList().get(1).getTemp().getDay()));
    }


    @Override
    public void onFailure(Call<ExampleDaily> call, Throwable t) {
        Log.e(TAG, "Received error from NowFragment network call");

        mSwipeRefreshLayout.setRefreshing(false);
        if (fragmentActivityInterface != null) {
            fragmentActivityInterface.showofflinesnackbar();

        }
    }

    public void reloadData() {
        String unitTypes = PreferenceUtils.getUnitTypes(getActivity());
        String city = PreferenceUtils.getCityTypes(getActivity());
        WeatherService.getDaily(this, city, unitTypes);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        reloadData();
    }

}
