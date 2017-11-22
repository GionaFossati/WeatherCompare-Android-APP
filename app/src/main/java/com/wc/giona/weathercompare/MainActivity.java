package com.wc.giona.weathercompare;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

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

        String city = "Verona";
        TextView cityView = (TextView)findViewById(R.id.city_field);
        cityView.setText(city.toUpperCase());


        try {
            owmInfo = fetchWeatherOwm.getJSONowm("Verona");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            apixuInfo = fetchWeatherApixu.getJSONapixu("Verona");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            wuInfo = fetchWeatherWu.getForecastJSONwu("verona");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setViewText(owmInfo,apixuInfo,wuInfo);

        Button bt = (Button) findViewById(R.id.refreshButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    owmInfo = fetchWeatherOwm.getJSONowm("Verona");
                    apixuInfo = fetchWeatherApixu.getJSONapixu("Verona");
                    wuInfo = fetchWeatherWu.getForecastJSONwu("verona");
                    setViewText(owmInfo,apixuInfo,wuInfo);
                } catch (Exception e)  { e.printStackTrace();}

            }
        });

        //TODO per salvare le prenferenze (città, unità di misura) creare un file di testo da leggere ogni volta che l'app si avvia
        //TODO Menù dal quale accedere alle impostazioni
        //TODO DB connection

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


}






