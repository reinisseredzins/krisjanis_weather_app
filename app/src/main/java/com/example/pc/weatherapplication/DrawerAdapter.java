package com.example.pc.weatherapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.database.CityListDbHelper;

import java.util.ArrayList;
import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.CityViewHolder> {

    CityListDbHelper helper;

    java.util.List<CityList> mCityList = new ArrayList<>();

    public DrawerAdapter(Context context){
        helper = new CityListDbHelper(context);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_drawer_layout, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder,final int position) {
        CityList city = mCityList.get(position);
        holder.mCity.setText(city.getNm());

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

    public void setCitySet(java.util.List<CityList> cityList) {
        mCityList = cityList;
        notifyDataSetChanged();
    }


    public void removeCity(int position)    {
        mCityList.remove(position);
        notifyDataSetChanged();
        helper.removeFromFavorites(String.valueOf(mCityList.get(position).getId()));
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