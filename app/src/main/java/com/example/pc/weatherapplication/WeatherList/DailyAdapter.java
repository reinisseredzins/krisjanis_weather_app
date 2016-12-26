package com.example.pc.weatherapplication.WeatherList;



import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.weatherapplication.JSON_Daily.List;
import com.example.pc.weatherapplication.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Krisjanis on 06/12/2016.
 */

public class DailyAdapter extends RecyclerView.Adapter <DailyAdapter.WeatherListViewHolderDaily> {

    java.util.List<List> mDailySet = new ArrayList<>();


    @Override
    public DailyAdapter.WeatherListViewHolderDaily onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item_layout, parent, false);
        return new DailyAdapter.WeatherListViewHolderDaily(view);

    }

    @Override
    public void onBindViewHolder(DailyAdapter.WeatherListViewHolderDaily holder, int position) {

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(TimeUnit.SECONDS.toMillis(mDailySet.get(position).getDt()));
        //iedotajai dienai
        Integer currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        Integer currentDate = calendar.get(Calendar.DATE);
        //Log.v(currentDay, "cday");
        //holder.mDay.setText(currentDay);
        //Log.v(Integer.toString(calendar.get(Calendar.DAY_OF_WEEK)), "calendar");

        switch (currentDay) {
           case 2:
               //monday
               System.out.println("monday");
               holder.mDay.setText("monday" + currentDay + currentDate);
               break;
           case 3:
               //tuesday
               System.out.println("tuesday");
               holder.mDay.setText("tuesday" + currentDay + currentDate);
               break;
           case 4:
               //wednesday
               System.out.println("wednesday");
               holder.mDay.setText("wednesday" + currentDay + currentDate);
               break;
           case 5:
               //thursday
               System.out.println("thursday");
               holder.mDay.setText("thursday" + currentDay + currentDate);
               break;
           case 6:
               //friday
               System.out.println("friday");
               holder.mDay.setText("friday" + currentDay + currentDate);
               break;
           case 7:
               //saturday
               System.out.println("saturday");
               holder.mDay.setText("saturday" + currentDay + currentDate);
               break;
           case 1:
               //sunday
               System.out.println("sunday");
               holder.mDay.setText("sunday" + currentDay + currentDate);
               break;
       }


        final String icon = (mDailySet.get(position).getWeather().get(0).getIcon());
        final String IMAGE_URL = "http://openweathermap.org/img/w/" + icon + ".png";
        holder.mWeatherDescription.setText(mDailySet.get(position).getWeather().get(0).getDescription());
        Picasso.with(holder.mImage.getContext()).load(IMAGE_URL).into(holder.mImage);
        String unitTypes = PreferenceManager.getDefaultSharedPreferences(holder.mTemp.getContext()).getString("pref_temp_type", "metric");
        String celsius = "°C";
        String farenheight = "°F";
        String textTemp = String.valueOf(mDailySet.get(position).getTemp().getDay());

        if (unitTypes.equals("metric")) {
            holder.mTemp.setText(textTemp + celsius);
        } else {
            holder.mTemp.setText(textTemp + farenheight);
        }

    }

    static class WeatherListViewHolderDaily extends RecyclerView.ViewHolder {
        TextView mWeatherDescription;
        TextView mTemp;
        ImageView mImage;
        TextView mDay;
        public WeatherListViewHolderDaily(View itemView)        {
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

    public void setDailySet(java.util.List<List> newDataSet){
        newDataSet.remove(0);
        mDailySet = newDataSet;
        notifyDataSetChanged();
    }
}
