package com.example.pc.weatherapplication.WeatherList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.weatherapplication.JSON.Forecast;
import com.example.pc.weatherapplication.JSON.ForecastListItem;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.details.DetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krisjanis on 04/11/2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter <WeatherAdapter.WeatherListViewHolder> {

    List<ForecastListItem> mDataSet = new ArrayList<>();
    private FragmentManager fragmentManager;

    public  WeatherAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    static class WeatherListViewHolder extends RecyclerView.ViewHolder {
        TextView mWeatherDescription;
        TextView mCity;
        ImageView mImage;
        TextView mTemp;


        public WeatherListViewHolder(View itemView)        {
            super(itemView);
            mTemp = (TextView) itemView.findViewById(R.id.weather_temp);
            mWeatherDescription = (TextView) itemView.findViewById(R.id.weather_description);
            mCity = (TextView) itemView.findViewById(R.id.city);
            mImage = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public WeatherListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item_layout, parent, false);
        return new WeatherListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherListViewHolder holder,int position) {
        final String icon = (mDataSet.get(position).getWeather().get(0).getIcon());
        final String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";
        holder.mWeatherDescription.setText(mDataSet.get(position).getWeather().get(0).getDescription());
        holder.mCity.setText(mDataSet.get(position).getName());
        Picasso.with(holder.mImage.getContext()).load(IMAGE_URL).into(holder.mImage);
        String textTemp = Long.toString(Math.round(mDataSet.get(position).getMain().getTemp()));
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(holder.mTemp.getContext()).getString("pref_temp_type", "metric");
        String celsius = "°C";
        String farenheight = "°F";
        if (unitTypes.equals("metric")) {
            holder.mTemp.setText(textTemp + celsius);
        } else {
            holder.mTemp.setText(textTemp + farenheight);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cityId = mDataSet.get(holder.getAdapterPosition()).getId();
                final Bundle bundle = new  Bundle();
                bundle.putInt("key", cityId);
                final Fragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_conrainer, detailsFragment).addToBackStack(DetailsFragment.TAG).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setDataSet(List<ForecastListItem> newDataSet){
        mDataSet = newDataSet;
        notifyDataSetChanged();
    }
}
