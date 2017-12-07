package com.wc.giona.weathercompare;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String[] owmInfo;
    public String[] apixuInfo;
    public String[] wuInfo;
    public String[] feedValues;
    RemoteFetchWu fetchWeatherWu = new RemoteFetchWu();
    RemoteFetchOwm fetchWeatherOwm = new RemoteFetchOwm();
    RemoteFetchApixu fetchWeatherApixu = new RemoteFetchApixu();
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CityPreference cityObj = new CityPreference(MainActivity.this);
        String city = cityObj.getCity().toString();

        TextView cityView = findViewById(R.id.cityField);
        cityView.setText(city);


        myDB= new DatabaseHelper(this);
        feedValues = myDB.fetchData();
        if (feedValues[0].equals("null")) {
            Integer[] firstValues = new Integer[3];
            firstValues[0] =  6;
            firstValues[1] =  6;
            firstValues[2] =  6;
            myDB.insertData(firstValues);
        }
        feedValues = myDB.fetchData();


        apixuInfo = fetchApixu(city);
        owmInfo = fetchOwm(city);
        wuInfo = fetchWu(city);

        setViewText(owmInfo, apixuInfo, wuInfo);

        buttonsClick();

    }

    public String[] fetchOwm(String city) {
        try {
            owmInfo = fetchWeatherOwm.getCurrent(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return owmInfo;
    }

    public String[] fetchApixu(String city) {
        try {
            apixuInfo = fetchWeatherApixu.getCurrent(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apixuInfo;
    }

    public String[] fetchWu(String city) {
        try {
            wuInfo = fetchWeatherWu.getCurrent(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wuInfo;
    }

    public void setViewText(String[] owmInfo, String[] apixuInfo, String[] wuInfo) {
        //feedValues: apixu= [0] | owm=[1] | Wu= [2]
        TextView actualTemp = findViewById(R.id.currentTemp);
        Double[] feedDouble = new Double[3];

        for (int i=0;i<3;i++) {feedDouble[i] = Double.valueOf(feedValues[i]);}
        Double feedValuesSum = feedDouble[0] + feedDouble[1] + feedDouble[2];
        long actualTempLong = (long) ((((Double.parseDouble(wuInfo[0]) * feedDouble[2])+ (Double.parseDouble(owmInfo[0])* feedDouble[1])) + (Double.parseDouble(apixuInfo[0])* feedDouble[0]))/ feedValuesSum);
        actualTempLong = Math.round(actualTempLong);
        actualTemp.setText(actualTempLong + " °C");


        //----------PASS VALUES TO FRAGMENTS AND SET TEXT--------------

        Bundle bundleInfo = new Bundle();
        Bundle bundleOwm = new Bundle();
        Bundle bundleApixu = new Bundle();
        Bundle bundleWu = new Bundle();
        Bundle bundleFeedValues = new Bundle();

        bundleApixu.putStringArray("apixu", apixuInfo);
        bundleOwm.putStringArray("owm", owmInfo);
        bundleWu.putStringArray("wu", wuInfo);
        bundleFeedValues.putStringArray("feedValues", feedValues);

        bundleInfo.putAll(bundleApixu);
        bundleInfo.putAll(bundleOwm);
        bundleInfo.putAll(bundleWu);
        bundleInfo.putAll(bundleFeedValues);

        ServicesTempsTableFragment servicesTemps = new ServicesTempsTableFragment();
        MaxMinFragment maxMinFragment= new MaxMinFragment();

        servicesTemps.setArguments(bundleInfo);
        maxMinFragment.setArguments(bundleInfo);

        servicesTemps.SetText(findViewById(R.id.fragmentServicesMain));
        maxMinFragment.SetText(findViewById(R.id.fragmentMinMaxMain));


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CityPreference cityPreference = new CityPreference(MainActivity.this);
        TextView cityField = findViewById(R.id.cityField);
        cityField.setText(cityPreference.getCity());
    }

    public void buttonsClick(){

        final ImageButton bt = findViewById(R.id.imageButtonRefresh);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    Animation rotation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
                    bt.setAnimation(rotation);
                    TextView cityView = (TextView) findViewById(R.id.cityField);
                    CityPreference cityObj = new CityPreference(MainActivity.this);
                    String city = cityObj.getCity().toString();
                    cityView.setText(cityObj.getCity());
                    owmInfo = fetchWeatherOwm.getCurrent(city);
                    apixuInfo = fetchWeatherApixu.getCurrent(city);
                    wuInfo = fetchWeatherWu.getCurrent(city);
                    setViewText(owmInfo, apixuInfo, wuInfo);
                    Toast.makeText(getBaseContext(), "Weather Updated!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        ImageButton settButton = (ImageButton) findViewById(R.id.editCity);
        settButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();

            }
        });

        ImageButton threeDayButton = findViewById(R.id.threeDayButton);
        threeDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThreeDayLoading.class);
                startActivity(intent);
            }
        });

        ImageButton feedButton = findViewById(R.id.feedButton);
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
                Toast.makeText(getBaseContext(), "City Updated!" , Toast.LENGTH_LONG ).show();
                ImageButton bt = findViewById(R.id.imageButtonRefresh);
                Animation rotation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.rotate);
                bt.setAnimation(rotation);
            }
        });
        builder.show();
        TextView cityView = (TextView) findViewById(R.id.cityField);
        CityPreference cityObj = new CityPreference(MainActivity.this);
        cityView.setText(cityObj.getCity());
    }

    public void changeCity(String city) {
        new CityPreference(this).setCity(city);
        String newCity = new CityPreference(this).getCity();
        TextView cityView = (TextView) findViewById(R.id.cityField);
        cityView.setText(newCity);

        apixuInfo = fetchApixu(newCity);
        owmInfo = fetchOwm(newCity);
        wuInfo = fetchWu(newCity);

        setViewText(owmInfo, apixuInfo, wuInfo);


    }

}





