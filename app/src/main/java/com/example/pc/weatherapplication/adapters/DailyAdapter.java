package com.example.pc.weatherapplication.adapters;


import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.weatherapplication.weather_daily.List;
import com.example.pc.weatherapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;


public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.WeatherListViewHolderDaily> {

    java.util.List<List> mDailySet = new ArrayList<>();
    SimpleDateFormat inFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");


    @Override
    public DailyAdapter.WeatherListViewHolderDaily onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item_layout, parent, false);
        return new DailyAdapter.WeatherListViewHolderDaily(view);
    }

    @Override
    public void onBindViewHolder(DailyAdapter.WeatherListViewHolderDaily holder, int position) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(mDailySet.get(position).getDt()));


        String date = inFormat.format(calendar.getTime());
        holder.mDay.setText(date);


        String unitTypes = PreferenceManager.getDefaultSharedPreferences(holder.mTemp.getContext()).getString("pref_temp_type", "metric");

        final String icon = (mDailySet.get(position).getWeather().get(0).getIcon());
        final String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";
        holder.mWeatherDescription.setText(mDailySet.get(position).getWeather().get(0).getDescription());
        Picasso.with(holder.mImage.getContext()).load(IMAGE_URL).into(holder.mImage);
        String celsius = "°C";
        String fahrenheit = "°F";
        String textTemp = Long.toString(Math.round(mDailySet.get(position).getTemp().getDay()));

        if (unitTypes.equals("metric")) {
            holder.mTemp.setText(textTemp + celsius);
        } else {
            holder.mTemp.setText(textTemp + fahrenheit);
        }


    }

    static class WeatherListViewHolderDaily extends RecyclerView.ViewHolder {
        TextView mWeatherDescription;
        TextView mTemp;
        ImageView mImage;
        TextView mDay;

        public WeatherListViewHolderDaily(View itemView) {
            super(itemView);
            mTemp = (TextView) itemView.findViewById(R.id.weather_temp_daily);
            mWeatherDescription = (TextView) itemView.findViewById(R.id.weather_description_daily);
            mImage = (ImageView) itemView.findViewById(R.id.image_daily);
            mDay = (TextView) itemView.findViewById(R.id.weather_day);
        }
    }

    @Override
    public int getItemCount() {
        return mDailySet.size();
    }

    public void setDailySet(java.util.List<List> newDataSet) {
        removeTodayAndTomorrow(newDataSet);
        mDailySet = newDataSet;
        notifyDataSetChanged();
    }

    public void removeTodayAndTomorrow(java.util.List<List> newDataSet) {
        newDataSet.remove(0);
        newDataSet.remove(0);
    }
}
