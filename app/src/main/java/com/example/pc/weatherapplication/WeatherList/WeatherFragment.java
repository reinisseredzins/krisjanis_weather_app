package com.example.pc.weatherapplication.WeatherList;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.JSON.Forecast;
import com.example.pc.weatherapplication.JSON.Weather;
import com.example.pc.weatherapplication.JSON_Daily.Example;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment implements Callback<Forecast>, SwipeRefreshLayout.OnRefreshListener, FragmentInterface {
    private WeatherAdapter mAdapter;
    private ProgressBar mProgressBar;
    private View mErrorScreen;
    private boolean hasFetchedData = false;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static final String TAG = WeatherFragment.class.getSimpleName();

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_weather, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weather_list);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mErrorScreen = view.findViewById(R.id.error_screen);

        Button retryButton = (Button) mErrorScreen.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchNewData();
                showOrHideErrorScreen(false);
                showOrHideProgressBar(true);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new WeatherAdapter(getFragmentManager());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void showOrHideProgressBar(Boolean isVisible) {
        mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void showOrHideErrorScreen(Boolean isVisible) {
        mErrorScreen.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchNewData();

    }

    private void fetchNewData() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        Log.v("omg", unitTypes);

        WeatherService.getWeatherForecast(this, unitTypes);
    }

    @Override
    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
        final Forecast forecast = response.body();
        if (forecast != null) {
            mAdapter.setDataSet(forecast.getList());
        }
        hasFetchedData = true;
        showOrHideProgressBar(false);
        Log.v("SSS", response.toString());
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<Forecast> call, Throwable t) {
        TextView errorText = (TextView) mErrorScreen.findViewById(R.id.error_text);
        showOrHideErrorScreen(true);
        showOrHideProgressBar(false);
        if (java.io.IOException.class.isInstance(t)) {
            errorText.setText("Error! No internet connection!");
        } else {
            errorText.setText("Error! Problem occured, try again or contact developers!");
        }

        Log.v("SSS", t.toString());
    }

    @Override
    public void onRefresh() {
        fetchNewData();
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
