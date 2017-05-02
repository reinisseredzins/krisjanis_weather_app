package com.example.pc.weatherapplication.onboarding;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.weatherapplication.CityList;
import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.database.CityListDbHelper;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import java.util.List;


public class CityChooserFagment extends android.app.Fragment {


    FragmentInterface onCityChosenListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_chooser_fragment, container, false);
        final EditText citySearch = (EditText) view.findViewById(R.id.add_city_search_fragment);
        final LinearLayout scrollView = (LinearLayout) view.findViewById(R.id.scrollView_linear_layout_fragment);
        final CityListDbHelper helper = new CityListDbHelper(getActivity());

        citySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scrollView.removeAllViews();
                if (s.length() > 1) {
                    final List<CityList> cityLists = helper.searchForCity(s.toString());
                    Log.v("VVV", ""+ cityLists);
                    int citySize = cityLists.size();
                    int size;
                    if (citySize > 9)   {
                        size = 10;
                    }   else    {
                        size = citySize;
                    }
                    for (int b = 0; b  < size; b++)    {
                        final View dropdownView = LayoutInflater.from(getActivity()).inflate(R.layout.search_dropdown_element, scrollView, false);
                        final TextView city = (TextView) dropdownView.findViewById(R.id.search_dropdown);
                        final TextView country = (TextView) dropdownView.findViewById(R.id.search_dropdown_country);
                        city.setText(cityLists.get(b).getNm());
                        country.setText(cityLists.get(b).getCountryCode());
                        scrollView.addView(dropdownView);

                        dropdownView.setTag(cityLists.get(b).getId());
                        final int i = b;
                        dropdownView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                helper.addToFavorites(String.valueOf(v.getTag()));
                                PreferenceUtils.setCity(v.getContext(), cityLists.get(i).getNm());
                                getFragmentManager().beginTransaction().replace(R.id.boarding_frame, new TemperatureTypesChooserFragment()).commit();
                            }
                        });
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

}
