package com.wc.giona.weathercompare;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
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

        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        CityPreference cityObj = new CityPreference(MainActivity.this);
        String city = cityObj.getCity().toString();

        TextView cityView = findViewById(R.id.city_field);
        cityView.setText(city);


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

        TextView actualTemp = findViewById(R.id.currentTemp);
        long actualTempLong = (long) ((Double.parseDouble(wuInfo[0]) + Double.parseDouble(owmInfo[0]) + Double.parseDouble(apixuInfo[0]))/3);
        actualTempLong = Math.round(actualTempLong);
        actualTemp.setText(actualTempLong + " °C");

        TextView maxTemp = findViewById(R.id.maxTemp);
        TextView minTemp = findViewById(R.id.minTemp);

        maxTemp.setText((parseInt(wuInfo[1]) + parseInt(owmInfo[1]) + parseInt(apixuInfo[1]))/3 + " °C");
        minTemp.setText((parseInt(wuInfo[2]) + parseInt(owmInfo[2]) + parseInt(apixuInfo[2]))/3 + " °C");


        //----------PASS VALUES TO FRAGMENT AND SET TEXT--------------
        ServicesTempsTableFragment a = new ServicesTempsTableFragment();

        Bundle bundleInfo = new Bundle();
        Bundle bundleOwm = new Bundle();
        Bundle bundleApixu = new Bundle();
        Bundle bundleWu = new Bundle();

        bundleApixu.putStringArray("apixu", apixuInfo);
        bundleOwm.putStringArray("owm", owmInfo);
        bundleWu.putStringArray("wu", wuInfo);
        bundleInfo.putAll(bundleApixu);
        bundleInfo.putAll(bundleOwm);
        bundleInfo.putAll(bundleWu);

        a.setArguments(bundleInfo);
        a.SetText(findViewById(R.id.fragmentMain));


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CityPreference cityPreference = new CityPreference(MainActivity.this);
        TextView cityField = findViewById(R.id.city_field);
        cityField.setText(cityPreference.getCity());
    }

    public void buttonsClick(){
        ImageButton bt = findViewById(R.id.imageButtonRefresh);
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
                    wuInfo = fetchWeatherWu.getCurrent(city);
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





