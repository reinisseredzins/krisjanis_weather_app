package com.example.pc.weatherapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.pc.weatherapplication.CityList;
import com.example.pc.weatherapplication.FragmentInterface;
import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.database.CityListDbHelper;

import java.util.List;

public class CityChooserDialogFragment extends android.app.DialogFragment   {

    FragmentInterface onCityChosenListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_city_chooser_dialog, container, false);
        final EditText citySearch = (EditText) view.findViewById(R.id.add_city_search);
        final LinearLayout scrollView = (LinearLayout) view.findViewById(R.id.scrollView_linear_layout);
        final CityListDbHelper helper = new CityListDbHelper(getActivity());

        citySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                scrollView.removeAllViews();
                if (s.length() > 1) {
                    List<String> cityLists = helper.searchForCity(s.toString());
                    Log.v("VVV", ""+ cityLists);
                    int citySize = cityLists.size();
                    int size;
                    if (citySize > 9)   {
                        size = 10;
                    }   else    {
                        size = citySize;
                    }
                    for (int b = 0; b  < size; b++)    {
                        TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.search_dropdown_element, scrollView, false);
                        textView.setText(cityLists.get(b));
                        scrollView.addView(textView);
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
