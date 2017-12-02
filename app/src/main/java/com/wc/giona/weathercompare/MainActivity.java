package com.wc.giona.weathercompare;

import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.SQLException;

import static java.lang.Integer.parseInt;

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

        Database aa = new Database();
        try {
            aa.getFeed();

        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CityPreference cityObj = new CityPreference(MainActivity.this);
        String city = cityObj.getCity().toString();

        TextView cityView = (TextView) findViewById(R.id.city_field);
        cityView.setText(city);

        try {
            String[] ddd = fetchWeatherOwm.getForecast(city);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            wuInfo = fetchWeatherWu.getForecastJSONwu(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wuInfo;
    }

    public void setViewText(String[] owmInfo, String[] apixuInfo, String[] wuInfo) {

        TextView actualTemp = (TextView) findViewById(R.id.currentTemp);
        long actualTempLong = (long) ((Double.parseDouble(wuInfo[0]) + Double.parseDouble(owmInfo[0]) + Double.parseDouble(apixuInfo[0]))/3);
        actualTempLong = Math.round(actualTempLong);
        actualTemp.setText(actualTempLong + " °C");


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


        TextView maxTemp = (TextView) findViewById(R.id.maxTemp);
        maxTemp.setText((parseInt(wuInfo[1]) + parseInt(owmInfo[1]) + parseInt(apixuInfo[1]))/3 + " °C");

        TextView minTemp = (TextView) findViewById(R.id.minTemp);
        minTemp.setText((parseInt(wuInfo[2]) + parseInt(owmInfo[2]) + parseInt(apixuInfo[2]))/3 + " °C");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CityPreference cityPreference = new CityPreference(MainActivity.this);
        TextView cityField = (TextView) findViewById(R.id.city_field);
        cityField.setText(cityPreference.getCity());
    }

    public void buttonsClick(){
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
                    owmInfo = fetchWeatherOwm.getCurrent(city);
                    apixuInfo = fetchWeatherApixu.getCurrent(city);
                    wuInfo = fetchWeatherWu.getForecastJSONwu(city);
                    setViewText(owmInfo, apixuInfo, wuInfo);
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
            }
        });
        builder.show();
        TextView cityView = (TextView) findViewById(R.id.city_field);
        CityPreference cityObj = new CityPreference(MainActivity.this);
        cityView.setText(cityObj.getCity());
    }

    public void changeCity(String city) {
        new CityPreference(this).setCity(city);
        String newCity = new CityPreference(this).getCity();
        TextView cityView = (TextView) findViewById(R.id.city_field);
        cityView.setText(newCity);

        apixuInfo = fetchApixu(newCity);
        owmInfo = fetchOwm(newCity);
        wuInfo = fetchWu(newCity);

        setViewText(owmInfo, apixuInfo, wuInfo);
        Toast.makeText(getBaseContext(), "City updated!" , Toast.LENGTH_LONG ).show();

    }

}





