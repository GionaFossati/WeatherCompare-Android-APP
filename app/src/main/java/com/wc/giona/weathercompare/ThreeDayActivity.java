package com.wc.giona.weathercompare;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

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

        /*apixuForecast = fetchApixuForecast(city);
        owmForecast = fetchOwmForecast(city);
        owmForecast = fetchWuForecast(city);
        */
    }

    private void fetchApixuForecast(String city) throws Exception {
        apixuForecast = fetchWeatherApixu.getForecast(city);
    }

    private void fetchOwmForecast(String city) {

    }

    private void fetchWuForecast(String city) {

    }

    private void setTextViews(){

    }
}