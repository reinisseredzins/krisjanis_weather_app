package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.ActivityFragmentInterface;
import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.weather_daily.Example;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.adapters.DailyAdapter;
import com.example.pc.weatherapplication.WeatherService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyFragment extends Fragment implements Callback<com.example.pc.weatherapplication.weather_daily.Example>, FragmentInterface, ActivityFragmentInterface {

    private DailyAdapter mDaily;

    public static final String TAG = DailyFragment.class.getSimpleName();

    public DailyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_daily, container, false);

        mDaily = new DailyAdapter();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weather_daily);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mDaily);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
    }

    @Override
    public void onResponse(Call<com.example.pc.weatherapplication.weather_daily.Example> call, Response<com.example.pc.weatherapplication.weather_daily.Example> response) {
        Log.v("VVV", "onResponse");
        final Example forecast = response.body();
        mDaily.setDailySet(forecast.getList());
    }

    @Override
    public void onFailure(Call<com.example.pc.weatherapplication.weather_daily.Example> call, Throwable t) {
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void reloadData() {
        Log.v("VVV", "reloadData");
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        WeatherService.getDaily(this, "Riga", unitTypes);
    }
}
