package com.example.pc.weatherapplication.details;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pc.weatherapplication.JSON.Forecast;
import com.example.pc.weatherapplication.JSON_Details.Example;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherList.DetailsAdapter;
import com.example.pc.weatherapplication.WeatherList.WeatherAdapter;
import com.example.pc.weatherapplication.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements Callback<Example>, SwipeRefreshLayout.OnRefreshListener {

    private DetailsAdapter mDetails;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;


    public static final String TAG = DetailsFragment.class.getSimpleName();

    TextView mText;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();
        mDetails = new DetailsAdapter();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weather_details);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(mDetails);
        recyclerView.setLayoutManager(linearLayoutManager);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_details);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_details);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void fetchNewDetails() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        int cityidint = getArguments().getInt("key");
        String cityid = String.valueOf(cityidint);
        Log.v("omg", unitTypes);

        WeatherService.getDetails(this, cityid, unitTypes);
    }

    private void showOrHideProgressBar(Boolean isVisible) {
        mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchNewDetails();
        showOrHideProgressBar(true);

    }

    @Override
    public void onResponse(Call<Example> call, Response<Example> response) {
        final Example forecast = response.body();
        Log.v("vvv", response.toString());
        mDetails.setDetailsSet(forecast.getList());
        mSwipeRefreshLayout.setRefreshing(false);
        showOrHideProgressBar(false);
    }

    @Override
    public void onFailure(Call<Example> call, Throwable t) {

        Log.v("vvv", t.toString());

    }

    @Override
    public void onRefresh() {
        fetchNewDetails();
        showOrHideProgressBar(true);
    }
}
