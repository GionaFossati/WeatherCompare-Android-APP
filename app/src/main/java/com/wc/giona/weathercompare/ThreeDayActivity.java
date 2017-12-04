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

        Button bt = findViewById(R.id.btbt);
        bt.setOnClickListener(new View.OnClickListener() {
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

    private void setTextViews(){

    }
}