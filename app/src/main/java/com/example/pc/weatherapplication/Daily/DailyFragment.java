package com.example.pc.weatherapplication.Daily;


import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.JSON_Daily.Example;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.WeatherList.DailyAdapter;
import com.example.pc.weatherapplication.WeatherList.DetailsAdapter;
import com.example.pc.weatherapplication.WeatherService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment implements Callback<com.example.pc.weatherapplication.JSON_Daily.Example>, FragmentInterface {

    private DailyAdapter mDaily;

    public static final String TAG = DailyFragment.class.getSimpleName();

    TextView mText;


    public DailyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        Bundle bundle = getArguments();
        mDaily = new DailyAdapter();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.weather_daily);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(mDaily);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void fetchNewDaily() {
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_temp_type", "metric");
        /**int cityidint = getArguments().getInt("key");
        String cityid = String.valueOf(cityidint);**/

        Log.v("omg", unitTypes);
        WeatherService.getDaily(this, "94043", unitTypes);
    }


    @Override
    public void onStart() {
        super.onStart();
        fetchNewDaily();

    }


    @Override
    public void onResponse(Call<com.example.pc.weatherapplication.JSON_Daily.Example> call, Response<com.example.pc.weatherapplication.JSON_Daily.Example> response) {
        final Example forecast = response.body();
        Log.v("vvv", response.toString());
        mDaily.setDailySet(forecast.getList());
    }

    @Override
    public void onFailure(Call<com.example.pc.weatherapplication.JSON_Daily.Example> call, Throwable t) {
        Log.v("vvv", t.toString());
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
