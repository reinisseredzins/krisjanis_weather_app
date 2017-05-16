package com.example.pc.weatherapplication.fragments;


import android.content.Context;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityChooserDialogFragment extends android.app.DialogFragment   {

    FragmentInterface onCityChosenListener;

    @BindView(R.id.add_city_search_fragment)
    TextView mCitySearch;
    @BindView(R.id.scrollView_linear_layout_fragment)
    LinearLayout mScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_chooser_fragment, container, false);
        ButterKnife.bind(this, view);
        final CityListDbHelper helper = new CityListDbHelper(getActivity());

        mCitySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mScrollView.removeAllViews();
                if (s.length() > 1) {
                    final List<CityList> cityLists = helper.searchForCity(s.toString());
                    int citySize = cityLists.size();
                    int size;
                    if (citySize > 9)   {
                        size = 10;
                    }   else    {
                        size = citySize;
                    }
                    for (int b = 0; b  < size; b++)    {
                        final View dropdownView = LayoutInflater.from(getActivity()).inflate(R.layout.search_dropdown_element, mScrollView, false);
                        final TextView city = (TextView) dropdownView.findViewById(R.id.search_dropdown);
                        final TextView country = (TextView) dropdownView.findViewById(R.id.search_dropdown_country);
                        city.setText(cityLists.get(b).getNm());
                        country.setText(cityLists.get(b).getCountryCode());
                        mScrollView.addView(dropdownView);

                        dropdownView.setTag(cityLists.get(b).getId());
                        dropdownView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                helper.addToFavorites(String.valueOf(v.getTag()));
                                onCityChosenListener.onCityChosen();
                                getFragmentManager().popBackStack();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onCityChosenListener = (FragmentInterface) context;
        } catch (ClassCastException e)  {
            throw new ClassCastException(context.toString() + "must implement onAttach");
        }
    }
}
