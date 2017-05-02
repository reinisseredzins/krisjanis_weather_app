package com.example.pc.weatherapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.weatherapplication.database.CityListDbHelper;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.CityViewHolder> {

    CityListDbHelper helper;
    private MainActivity mContext;

    java.util.List<CityList> mCityList = new ArrayList<>();

    int currentlySelectedCity;

    public DrawerAdapter(MainActivity mainActivity) {
        helper = new CityListDbHelper(mainActivity);
        mContext = mainActivity;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cities_drawer_layout, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, final int position) {
        final CityList city = mCityList.get(position);
        holder.mCity.setText(city.getNm());

        holder.mRootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mCityList.size() > 1) {
                    showRemoveCityDialog(holder.mRootView.getContext(), position);
                } else {
                    showRemoveCityErrorDialog(holder.mRootView.getContext(), position);
                }
                return false;
            }
        });
        if (position == currentlySelectedCity)   {
            holder.mRootView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
        } else {

            holder.mRootView.setBackground(resetBackground());
        }
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.setSelectedCity(holder.mRootView.getContext(), city.getNm());
                currentlySelectedCity = holder.getAdapterPosition();
                mContext.reloadData(city.getNm());
                notifyDataSetChanged();
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

    String selectedCity;

    public void setCitySet(java.util.List<CityList> cityList) {
        selectedCity = PreferenceUtils.getSelectedCity(mContext);
        for(int i=0; i<cityList.size(); i++ )   {
            if(selectedCity.equalsIgnoreCase(cityList.get(i).getNm()))  {
                currentlySelectedCity = i;
                break;
            }
        }
        mCityList = cityList;
        notifyDataSetChanged();
    }


    public void removeCity(int position) {
        helper.removeFromFavorites(String.valueOf(mCityList.get(position).getId()));
        mCityList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    public void showRemoveCityErrorDialog(Context context, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_warning)
                .setMessage(R.string.city_remove_dialog_text_error)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(android.R.string.ok, null).show();
    }

    public void showRemoveCityDialog(Context context, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_warning)
                .setMessage(R.string.city_remove_dialog_text)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        removeCity(position);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();
    }
    public Drawable resetBackground() {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(R.style.AppTheme, new int[] {R.attr.selectableItemBackground});
        int attributeResourceId = a.getResourceId(0, 0);
        Drawable drawable = mContext.getResources().getDrawable(attributeResourceId);
        return drawable;
    }
}