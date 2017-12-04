package com.wc.giona.weathercompare;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        //owmForecast = fetchWuForecast(city);

    }

    private String[] fetchApixuForecast(String city) throws Exception {
        apixuForecast = fetchWeatherApixu.getForecast(city);
        return apixuForecast;
    }

    private String[] fetchOwmForecast(String city) throws Exception {
        owmForecast = fetchWeatherOwm.getForecast(city);
        return owmForecast;
    }

    private void fetchWuForecast(String city) {

    }

    private void setTextViews(){

    }
}