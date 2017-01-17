package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pc.weatherapplication.ActivityFragmentInterface;
import com.example.pc.weatherapplication.FragmentActivityInterface;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherService;
import com.example.pc.weatherapplication.adapters.DailyAdapter;
import com.example.pc.weatherapplication.weather_daily.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyFragment extends Fragment implements Callback<com.example.pc.weatherapplication.weather_daily.Example>, SwipeRefreshLayout.OnRefreshListener, ActivityFragmentInterface {

    private DailyAdapter mDaily;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String TAG = DailyFragment.class.getSimpleName();

    FragmentActivityInterface fragmentActivityInterface;

    public DailyFragment() {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_daily, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout_daily);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
        mSwipeRefreshLayout.setRefreshing(false);
        final Example forecast = response.body();
        mDaily.setDailySet(forecast.getList());
    }

    @Override
    public void onFailure(Call<com.example.pc.weatherapplication.weather_daily.Example> call, Throwable t) {
        Log.e(TAG, "Received error from NowFragment network call");
        mSwipeRefreshLayout.setRefreshing(false);
        if (fragmentActivityInterface != null) {
            fragmentActivityInterface.showofflinesnackbar();

        }

    }

    public void reloadData() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        WeatherService.getDaily(this, "Riga",  unitTypes);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        reloadData();
    }
}
