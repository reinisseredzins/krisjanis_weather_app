package com.example.pc.weatherapplication.adapters;


import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.utils.Constants;
import com.example.pc.weatherapplication.models.weather.List;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.WeatherListViewHolderDaily> {

    static HashMap<String, Integer> mIconToDrawable = new HashMap<>();
    static {
        mIconToDrawable.put("01d", R.drawable.sun);  // clear sky day
        mIconToDrawable.put("02d", R.drawable.cloud_sun);  // few clouds day
        mIconToDrawable.put("03d", R.drawable.cloud);  // scattered clouds day
        mIconToDrawable.put("04d", R.drawable.cloud_wind);  // broken clouds day
        mIconToDrawable.put("09d", R.drawable.cloud_rain_sun_alt);  // shower rain day
        mIconToDrawable.put("10d", R.drawable.cloud_rain_sun);  // rain day
        mIconToDrawable.put("11d", R.drawable.cloud_lightning);  // thunderstorm day
        mIconToDrawable.put("13d", R.drawable.cloud_snow_alt);  // snow day
        mIconToDrawable.put("50d", R.drawable.cloud_fog);  // mist day

        mIconToDrawable.put("01n", R.drawable.moon);  // clear sky night
        mIconToDrawable.put("02n", R.drawable.cloud_moon);  // few clouds night
        mIconToDrawable.put("03n", R.drawable.cloud);  // scattered clouds night
        mIconToDrawable.put("04n", R.drawable.cloud_wind);  // broken clouds night
        mIconToDrawable.put("09n", R.drawable.cloud_rain_moon_alt);  // shower rain night
        mIconToDrawable.put("10n", R.drawable.cloud_rain_moon);  // rain night
        mIconToDrawable.put("11n", R.drawable.cloud_lightning);  // thunderstorm night
        mIconToDrawable.put("13n", R.drawable.cloud_snow_alt);  // snow night
        mIconToDrawable.put("50n", R.drawable.cloud_fog);  // mist night
    }

    java.util.List<List> mDailyList = new ArrayList<>();
    SimpleDateFormat inFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");


    @Override
    public DailyAdapter.WeatherListViewHolderDaily onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item_layout, parent, false);
        return new DailyAdapter.WeatherListViewHolderDaily(view);
    }

    @Override
    public void onBindViewHolder(DailyAdapter.WeatherListViewHolderDaily holder, int position) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(mDailyList.get(position).getDt()));


        String date = inFormat.format(calendar.getTime());
        holder.mDay.setText(date);


        String unitTypes = PreferenceManager.getDefaultSharedPreferences(holder.mTemp.getContext()).getString("pref_temp_type", "metric");

        final String icon = (mDailyList.get(position).getWeather().get(0).getIcon());
        holder.mWeatherDescription.setText(mDailyList.get(position).getWeather().get(0).getDescription());
        holder.mImage.setBackgroundResource(mIconToDrawable.get(icon));

        String celsius = "°C";
        String fahrenheit = "°F";
        String textTemp = Long.toString(Math.round(mDailyList.get(position).getTemp().getDay()));

        String units = unitTypes.equals("metric") ? celsius : fahrenheit;
        holder.mTemp.setText(textTemp + units);
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
        return mDailyList.size();
    }

    public void setDailySet(java.util.List<List> newDataSet) {
        removeTodayAndTomorrow(newDataSet);
        mDailyList = newDataSet;
        notifyDataSetChanged();
    }



    public void removeTodayAndTomorrow(java.util.List<List> newDataSet) {
        newDataSet.remove(0);
        newDataSet.remove(0);
    }
}
