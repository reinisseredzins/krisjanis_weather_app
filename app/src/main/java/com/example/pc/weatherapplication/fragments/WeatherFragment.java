package com.example.pc.weatherapplication.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.weatherapplication.ActivityFragmentInterface;
import com.example.pc.weatherapplication.FragmentActivityInterface;
import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.weather_now.Forecast;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.adapters.WeatherAdapter;
import com.example.pc.weatherapplication.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherFragment extends Fragment implements Callback<Forecast>, FragmentInterface, ActivityFragmentInterface {
    private WeatherAdapter mAdapter;
    private View mErrorScreen;
    private boolean hasFetchedData = false;
    private FragmentActivityInterface mMainActivity;

    public static final String TAG = WeatherFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityInterface) {
            mMainActivity = (FragmentActivityInterface) context;
        } else {
            throw new IllegalArgumentException("Activity must implement FragmentActivity interface.");
        }
    }

    public WeatherFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_weather, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weather_list);
        mErrorScreen = view.findViewById(R.id.error_screen);


        Button retryButton = (Button) mErrorScreen.findViewById(R.id.retry_button);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchNewData();
                showOrHideErrorScreen(false);
            }
        });

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new WeatherAdapter(getFragmentManager());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
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
        WeatherService.getWeatherForecast(this, unitTypes);
    }

    @Override
    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
        final Forecast forecast = response.body();
        if (forecast != null) {
            mAdapter.setDataSet(forecast.getList());
        }
        hasFetchedData = true;
        mMainActivity.hideSwipeLayout();
    }

    @Override
    public void onFailure(Call<Forecast> call, Throwable t) {
        TextView errorText = (TextView) mErrorScreen.findViewById(R.id.error_text);
        showOrHideErrorScreen(true);
        if (java.io.IOException.class.isInstance(t)) {
            errorText.setText("Error! No internet connection!");
        } else {
            errorText.setText("Error! Problem occured, try again or contact developers!");
        }
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void reloadData() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        WeatherService.getWeatherForecast(this, unitTypes);
    }
}
