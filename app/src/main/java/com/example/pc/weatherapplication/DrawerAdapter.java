package com.example.pc.weatherapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.CityViewHolder> {

    java.util.List<String> mCityList = new ArrayList<>();

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_drawer_layout, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder,final int position) {
        String city = mCityList.get(position);
        holder.mCity.setText(city);

        holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    showRemoveCityDialog(holder.mRootView.getContext(), position);
                return false;
            }
        });
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        View mRootView;
        TextView mCity;

        public CityViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView;
            mCity = (TextView) itemView.findViewById(R.id.city_option);
        }
    }

    public void setCitySet(java.util.List<String> cityList) {
        mCityList = cityList;
        notifyDataSetChanged();
    }

    public void addCity(String specificCity) {
        mCityList.add(specificCity);
        notifyDataSetChanged();
    }

    public void removeCity(int position)    {
        mCityList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public void showRemoveCityDialog( Context context, final int position)    {
        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_warning)
                .setMessage(R.string.city_remove_dialog_text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        removeCity(position);
                    }})
                .setNegativeButton(android.R.string.cancel, null).show();
    }
}