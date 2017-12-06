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
    String[] feedValues;
    DatabaseHelper myDB;

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
            wuForecast = fetchWuForecast(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        myDB= new DatabaseHelper(this);
        feedValues = myDB.fetchData();

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

        //----------PASS VALUES TO TABL FRAGMENTS AND SET TEXT--------------

        Bundle bundleInfo = new Bundle();
        Bundle bundleOwm = new Bundle();
        Bundle bundleApixu = new Bundle();
        Bundle bundleWu = new Bundle();
        Bundle bundleFeedValues = new Bundle();
        ServicesTempsTableFragment servicesTemps = new ServicesTempsTableFragment();
        MaxMinFragment maxMinFragment= new MaxMinFragment();


        //-------today bundle
        String[] apixuTodayArray = new String[3];
        apixuTodayArray[0] = null;
        apixuTodayArray[1] =apixuForecast[0];
        apixuTodayArray[2] =apixuForecast[1];
        String[] owmTodayArray = new String[3];
        owmTodayArray[0] = null;
        owmTodayArray[1] =owmForecast[0];
        owmTodayArray[2] =owmForecast[1];
        String[] wuTodayArray = new String[3];
        wuTodayArray[0] = null;
        wuTodayArray[1] = wuForecast[0];
        wuTodayArray[2] = wuForecast[1];

        bundleApixu.putStringArray("apixu", apixuTodayArray);
        bundleOwm.putStringArray("owm", owmTodayArray);
        bundleWu.putStringArray("wu", wuTodayArray);
        bundleFeedValues.putStringArray("feedValues", feedValues);

        bundleInfo.putAll(bundleApixu);
        bundleInfo.putAll(bundleOwm);
        bundleInfo.putAll(bundleWu);
        bundleInfo.putAll(bundleFeedValues);

        servicesTemps.setArguments(bundleInfo);
        maxMinFragment.setArguments(bundleInfo);

        servicesTemps.SetText(findViewById(R.id.fragmentTableToday));
        maxMinFragment.SetText(findViewById(R.id.fragmentMinMaxToday));

        //-------tomorrow bundle
        String[] apixuTomorrowArray = new String[3];
        apixuTomorrowArray[0] = null;
        apixuTomorrowArray[1] =apixuForecast[2];
        apixuTomorrowArray[2] =apixuForecast[3];
        String[] owmTomorrowArray = new String[3];
        owmTomorrowArray[0] = null;
        owmTomorrowArray[1] =owmForecast[2];
        owmTomorrowArray[2] =owmForecast[3];
        String[] wuTomorrowArray = new String[3];
        wuTomorrowArray[0] = null;
        wuTomorrowArray[1] = wuForecast[2];
        wuTomorrowArray[2] = wuForecast[3];

        bundleApixu.putStringArray("apixu", apixuTomorrowArray);
        bundleOwm.putStringArray("owm", owmTomorrowArray);
        bundleWu.putStringArray("wu", wuTomorrowArray);
        bundleFeedValues.putStringArray("feedValues", feedValues);

        bundleInfo.clear();
        bundleInfo.putAll(bundleApixu);
        bundleInfo.putAll(bundleOwm);
        bundleInfo.putAll(bundleWu);
        bundleInfo.putAll(bundleFeedValues);

        servicesTemps.setArguments(bundleInfo);
        maxMinFragment.setArguments(bundleInfo);

        servicesTemps.SetText(findViewById(R.id.fragmentTableTomorrow));
        maxMinFragment.SetText(findViewById(R.id.fragmentMinMaxTomorrow));

        //------day after tomorrow bundle
        String[] apixuThreeArray = new String[3];
        apixuThreeArray[0] = null;
        apixuThreeArray[1] =apixuForecast[4];
        apixuThreeArray[2] =apixuForecast[5];
        String[] owmThreeArray = new String[3];
        owmThreeArray[0] = null;
        owmThreeArray[1] =owmForecast[4];
        owmThreeArray[2] =owmForecast[5];
        String[] wuThreeArray = new String[3];
        wuThreeArray[0] = null;
        wuThreeArray[1] = wuForecast[4];
        wuThreeArray[2] = wuForecast[5];

        bundleApixu.putStringArray("apixu", apixuThreeArray);
        bundleOwm.putStringArray("owm", owmThreeArray);
        bundleWu.putStringArray("wu", wuThreeArray);
        bundleFeedValues.putStringArray("feedValues", feedValues);

        bundleInfo.clear();
        bundleInfo.putAll(bundleApixu);
        bundleInfo.putAll(bundleOwm);
        bundleInfo.putAll(bundleWu);
        bundleInfo.putAll(bundleFeedValues);

        servicesTemps.setArguments(bundleInfo);
        maxMinFragment.setArguments(bundleInfo);

        servicesTemps.SetText(findViewById(R.id.fragmentTableThree));
        maxMinFragment.SetText(findViewById(R.id.fragmentMinMaxThree));
        //----------PASS VALUES TO MIN MAX FRAGMENTS AND SET TEXT--------------


    }
    {

    }
}