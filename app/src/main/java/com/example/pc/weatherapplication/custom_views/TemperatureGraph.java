package com.example.pc.weatherapplication.custom_views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.models.weather.EveryDayForecast;
import com.example.pc.weatherapplication.models.weather.ForecastEveryThreeHours;
import com.example.pc.weatherapplication.models.weather.WeatherMetadata;

import butterknife.ButterKnife;

public class TemperatureGraph extends LinearLayout {


    public TemperatureGraph(Context context) {
        super(context);
        inflateGraphView();
    }

    public TemperatureGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateGraphView();
    }

    public TemperatureGraph(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateGraphView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TemperatureGraph(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateGraphView();

    }

    void inflateGraphView() {
        ButterKnife.bind(this);
    }

    public void addButtonViews(ForecastEveryThreeHours forecastEveryThreeHours)  {
        for (WeatherMetadata specificWeather : forecastEveryThreeHours.getWeatherMetadata()) {
            View buttonLayout = LayoutInflater.from(getContext()).inflate(R.layout.temperature_button, this, false);
            TextView temp = (TextView) buttonLayout.findViewById(R.id.temp_button_temp);
            TextView time = (TextView) buttonLayout.findViewById(R.id.temp_button_clock);
            ImageView icon = (ImageView) buttonLayout.findViewById(R.id.temp_button_image);
            temp.setText(String.valueOf(specificWeather.getMain().getTemp()));
            time.setText(String.valueOf(specificWeather.getDt()));
            icon.setImageResource(R.drawable.cloud_fog);
            addView(buttonLayout);
        }
    }
}
