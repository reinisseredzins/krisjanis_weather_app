package com.example.pc.weatherapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        final List<String> listOfCities =  new CityListDbHelper(getActivity()).getCity();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, listOfCities);
        final AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.add_city_search);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("VVV", textView.getText().toString());
                onCityChosenListener.onCityChosen(textView.getText().toString());
                getDialog().dismiss();
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
