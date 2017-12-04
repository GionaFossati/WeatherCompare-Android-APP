package com.wc.giona.weathercompare;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ThreeDayActivity extends AppCompatActivity {
    String[] wuForecast;
    String[] owmForecast;
    String[] apixuForecast;
    RemoteFetchWu fetchWeatherWu = new RemoteFetchWu();
    RemoteFetchOwm fetchWeatherOwm = new RemoteFetchOwm();
    RemoteFetchApixu fetchWeatherApixu = new RemoteFetchApixu();
    Double[] feedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_day);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CityPreference cityObj = new CityPreference(ThreeDayActivity.this);
        String city = cityObj.getCity().toString();

        try {
            apixuForecast = fetchApixuForecast(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            owmForecast = fetchOwmForecast(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            owmForecast = fetchWuForecast(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        buttonsClick();
        setTextViews();
    }

    private String[] fetchApixuForecast(String city) throws Exception {
        return fetchWeatherApixu.getForecast(city);
    }

    private String[] fetchOwmForecast(String city) throws Exception {
        return fetchWeatherOwm.getForecast(city);
    }

    private String[] fetchWuForecast(String city) throws Exception {
        return fetchWeatherWu.getForecast(city);

    }

    public void buttonsClick(){
        Button buttonToday = findViewById(R.id.buttonToday);
        buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout layout = findViewById(R.id.todayFragment); //---begindelayedtransition?
                if (layout.getVisibility() == View.VISIBLE) {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = 1;
                    layout.setVisibility(View.INVISIBLE);
                    layout.requestLayout();
                }
                else {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layout.setVisibility(View.VISIBLE);
                    layout.requestLayout();

                }
            }
        });

        Button buttonTomorrow = findViewById(R.id.buttonTomorrow);
        buttonTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout layout = findViewById(R.id.tomorrowFragment); //---begindelayedtransition?
                if (layout.getVisibility() == View.VISIBLE) {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = 1;
                    layout.setVisibility(View.INVISIBLE);
                    layout.requestLayout();
                }
                else {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layout.setVisibility(View.VISIBLE);
                    layout.requestLayout();

                }
            }
        });

        Button buttonDayAfterTomorrow = findViewById(R.id.buttonDayAfterTomorrow);
        buttonDayAfterTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout layout = findViewById(R.id.dayAfterTomorrowFragment); //---begindelayedtransition?
                if (layout.getVisibility() == View.VISIBLE) {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = 1;
                    layout.setVisibility(View.INVISIBLE);
                    layout.requestLayout();
                }
                else {
                    ViewGroup.LayoutParams params =  layout.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    layout.setVisibility(View.VISIBLE);
                    layout.requestLayout();

                }
            }
        });

    }

    private void setTextViews(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dayafter = calendar.getTime();

        TextView todayTextView = findViewById(R.id.todayField);
        TextView tomorrowTextView = findViewById(R.id.tomorrowField);
        TextView dayAfterTextView = findViewById(R.id.dayAfterTomorrowField);
        todayTextView.setText(dateFormat.format(today));
        tomorrowTextView.setText(dateFormat.format(tomorrow));
        dayAfterTextView.setText(dateFormat.format(dayafter));
    }
}