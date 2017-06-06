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
import com.example.pc.weatherapplication.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;

public class TemperatureGraph extends LinearLayout {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("H:mm");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");

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

    public void addButtonViews(List<WeatherMetadata> weatherMetadata, long dayCompareTo) {
        removeAllViews();
        int sizeOfMetadata = weatherMetadata.size();
        /*long currentDate = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis());
        long tommorowDate = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()) + 1;*/
        for (int i = 0; i < sizeOfMetadata; i++) {
            long receivedDate = TimeUnit.SECONDS.toDays(weatherMetadata.get(i).getDt());
            if (dayCompareTo == receivedDate) {
                WeatherMetadata specificWeather = weatherMetadata.get(i);
                View buttonLayout = LayoutInflater.from(getContext()).inflate(R.layout.temperature_button, this, false);
                TextView temp = (TextView) buttonLayout.findViewById(R.id.temp_button_temp);
                TextView time = (TextView) buttonLayout.findViewById(R.id.temp_button_clock);
                ImageView icon = (ImageView) buttonLayout.findViewById(R.id.temp_button_image);
                temp.setText(String.valueOf(specificWeather.getMain().getTemp()));
                time.setText(dateFormat.format(TimeUnit.SECONDS.toMillis(weatherMetadata.get(i).getDt())));
                String img = String.valueOf(specificWeather.getWeather().get(0).getIcon());
                icon.setImageResource(Constants.mIconToDrawable.get(img));
                addView(buttonLayout);
            }
        }
    }
}
