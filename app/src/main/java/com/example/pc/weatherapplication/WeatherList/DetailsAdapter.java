package com.example.pc.weatherapplication.WeatherList;

import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.weatherapplication.JSON.ForecastListItem;
import com.example.pc.weatherapplication.JSON_Details.Example;
import com.example.pc.weatherapplication.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Krisjanis on 29/11/2016.
 */

public class DetailsAdapter extends RecyclerView.Adapter <DetailsAdapter.WeatherListViewHolderDetails> {

    List<com.example.pc.weatherapplication.JSON_Details.List> mDetailsSet = new ArrayList<>();

    @Override
    public WeatherListViewHolderDetails onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_item_layout, parent, false);
        return new WeatherListViewHolderDetails(view);

    }

    static class WeatherListViewHolderDetails extends RecyclerView.ViewHolder {

        TextView mDate;
        TextView mTemp;
        public WeatherListViewHolderDetails(View itemView)        {
            super(itemView);
            mTemp = (TextView) itemView.findViewById(R.id.weather_temp_details);
            mDate = (TextView) itemView.findViewById(R.id.weather_date_details);
        }


    }

    @Override
    public void onBindViewHolder(final DetailsAdapter.WeatherListViewHolderDetails holder, int position) {

        holder.mDate.setText(mDetailsSet.get(position).getDtTxt());

        String unitTypes = PreferenceManager.getDefaultSharedPreferences(holder.mTemp.getContext()).getString("pref_temp_type", "metric");
        String celsius = "°C";
        String farenheight = "°F";
        String textTemp = String.valueOf(mDetailsSet.get(position).getMain().getTemp());
        if (unitTypes.equals("metric")) {
            holder.mTemp.setText(textTemp + celsius);
        } else {
            holder.mTemp.setText(textTemp + farenheight);
        }
    }

    @Override
    public int getItemCount() {
        return mDetailsSet.size();
    }

    public void setDetailsSet(List<com.example.pc.weatherapplication.JSON_Details.List> newDataSet){
        mDetailsSet = newDataSet;
        notifyDataSetChanged();
    }

}
