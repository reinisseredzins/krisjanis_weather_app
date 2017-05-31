package com.example.pc.weatherapplication.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pc.weatherapplication.R;
import com.example.pc.weatherapplication.models.weather.Weather;
import com.example.pc.weatherapplication.models.weather.WeatherDaily;
import com.jjoe64.graphview.GraphView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TemperatureGraph extends RelativeLayout {

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
        inflate(getContext(), R.layout.graph_view, this);
    }

    void addButtonViews(WeatherDaily weatherDaily)  {
        for (com.example.pc.weatherapplication.models.weather.List specificWeather : weatherDaily.getList()) {
            View buttonLayout = LayoutInflater.from(getContext()).inflate(R.layout.temperature_button, this, false);
            TextView temp = (TextView) buttonLayout.findViewById(R.id.temp_button_temp);
            TextView time = (TextView) buttonLayout.findViewById(R.id.temp_button_clock);
            ImageView icon = (ImageView) buttonLayout.findViewById(R.id.temp_button_image);
        }
    }
}
