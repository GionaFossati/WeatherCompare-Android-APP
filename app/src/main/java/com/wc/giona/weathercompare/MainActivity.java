package com.wc.giona.weathercompare;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public String[] owmInfo;
    public String[] apixuInfo;
    public String[] wuInfo;
    RemoteFetchWu fetchWeatherWu = new RemoteFetchWu();
    RemoteFetchOwm fetchWeatherOwm = new RemoteFetchOwm();
    RemoteFetchApixu fetchWeatherApixu = new RemoteFetchApixu();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CityPreference cityObj = new CityPreference(MainActivity.this);
        String city = cityObj.getCity().toString();

        TextView cityView = (TextView) findViewById(R.id.city_field);
        cityView.setText(city);


        try {
            owmInfo = fetchWeatherOwm.getJSONowm(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            apixuInfo = fetchWeatherApixu.getJSONapixu(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            wuInfo = fetchWeatherWu.getForecastJSONwu(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setViewText(owmInfo, apixuInfo, wuInfo);

        ImageButton bt = (ImageButton) findViewById(R.id.imageButtonRefresh);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    Toast.makeText(getBaseContext(), "Updating weather!", Toast.LENGTH_LONG).show();
                    TextView cityView = (TextView) findViewById(R.id.city_field);
                    CityPreference cityObj = new CityPreference(MainActivity.this);
                    String city = cityObj.getCity().toString();
                    cityView.setText(cityObj.getCity());
                    owmInfo = fetchWeatherOwm.getJSONowm(city);
                    apixuInfo = fetchWeatherApixu.getJSONapixu(city);
                    wuInfo = fetchWeatherWu.getForecastJSONwu(city);
                    setViewText(owmInfo, apixuInfo, wuInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        Button settButton = (Button) findViewById(R.id.settings);
        settButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent settings = new Intent(MainActivity.this, Settings.class);
                startActivity(settings);

            }
        });

    }

    public void setViewText(String[] owmInfo, String[] apixuInfo, String[] wuInfo) {

        TextView actualTemp = (TextView) findViewById(R.id.currentTemp);
        actualTemp.setText(owmInfo[0] + " °C");
        TextView maxOwm = (TextView) findViewById(R.id.max1);
        maxOwm.setText(owmInfo[1] + " °C");
        TextView minOwm = (TextView) findViewById(R.id.min1);
        minOwm.setText(owmInfo[2] + " °C");

        TextView maxApixu = (TextView) findViewById(R.id.max2);
        maxApixu.setText(apixuInfo[1] + " °C");
        TextView minApixu = (TextView) findViewById(R.id.min2);
        minApixu.setText(apixuInfo[2] + " °C");

        TextView maxWu = (TextView) findViewById(R.id.max3);
        maxWu.setText(wuInfo[1] + " °C");
        TextView minWu = (TextView) findViewById(R.id.min3);
        minWu.setText(wuInfo[2] + " °C");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CityPreference cityPreference = new CityPreference(MainActivity.this);
        TextView cityField = (TextView) findViewById(R.id.city_field);
        cityField.setText(cityPreference.getCity());
    }
}






