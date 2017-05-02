package com.example.pc.weatherapplication.onboarding;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.weatherapplication.CityList;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.database.CityListDbHelper;
import com.example.pc.weatherapplication.utils.PreferenceUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityChooserFragment extends android.app.Fragment {

    @BindView(R.id.add_city_search_fragment)
    TextView mCitySearch;
    @BindView(R.id.scrollView_linear_layout_fragment)
    LinearLayout mScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_chooser_fragment, container, false);
        ButterKnife.bind(this, view);
        mCitySearch.addTextChangedListener(new EnteredCityTextWatcher());
        return view;
    }

    private class EnteredCityTextWatcher implements TextWatcher {

        final CityListDbHelper helper = new CityListDbHelper(getActivity());

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            mScrollView.removeAllViews();
            if (charSequence.length() > 1) {
                final List<CityList> cityLists = helper.searchForCity(charSequence.toString());
                int citySize = cityLists.size();
                int size;
                if (citySize > 9) {
                    size = 10;
                } else {
                    size = citySize;
                }
                for (int b = 0; b < size; b++) {
                    final View dropdownView = LayoutInflater.from(getActivity()).inflate(R.layout.search_dropdown_element, mScrollView, false);
                    final TextView city = ButterKnife.findById(dropdownView, R.id.search_dropdown);
                    final TextView country = ButterKnife.findById(dropdownView, R.id.search_dropdown_country);
                    city.setText(cityLists.get(b).getNm());
                    country.setText(cityLists.get(b).getCountryCode());
                    mScrollView.addView(dropdownView);

                    dropdownView.setTag(cityLists.get(b).getId());
                    final int i = b;
                    dropdownView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            helper.addToFavorites(String.valueOf(v.getTag()));
                            PreferenceUtils.setSelectedCity(v.getContext(), cityLists.get(i).getNm());
                            getFragmentManager().beginTransaction().replace(R.id.boarding_frame, new TemperatureTypesChooserFragment()).commit();
                        }
                    });
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}