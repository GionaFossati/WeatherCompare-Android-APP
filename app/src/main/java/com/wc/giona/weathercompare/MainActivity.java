package com.wc.giona.weathercompare;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public String[] owmInfo;
    public String[] apixuInfo;
    public String[] wuInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RemoteFetchOwm fetchWeatherOwm = new RemoteFetchOwm();

        try {
            owmInfo = fetchWeatherOwm.getJSONowm("Verona");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RemoteFetchApixu fetchWeatherApixu = new RemoteFetchApixu();

        try {
            apixuInfo = fetchWeatherApixu.getJSONapixu("Verona");
        } catch (Exception e) {
            e.printStackTrace();
        }

        RemoteFetchWu fetchWeatherWu = new RemoteFetchWu();
        try {
            wuInfo = fetchWeatherWu.getForecastJSONwu("verona");
        } catch (Exception e) {
            e.printStackTrace();
        }



        setViewText(owmInfo,apixuInfo,wuInfo);

        //TODO per salvare le prenferenze (città, unità di misura) creare un file di testo da leggere ogni volta che l'app si avvia
        //TODO bottone refresh: chiamare un nuovo OnCreate (oppure sempre lo stesso?)
        //TODO Menù dal quale accedere alle impostazioni
        //TODO DB connection


        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                } catch (Exception e) {}

            }
        });*/

    }

    public void setViewText(String[] owmInfo, String[] apixuInfo, String[] wuInfo) {

        TextView actualTemp = (TextView) findViewById(R.id.currentTemp);
        actualTemp.setText(owmInfo[0]);
        TextView maxOwm = (TextView) findViewById(R.id.max1);
        maxOwm.setText(owmInfo[1]);
        TextView minOwm = (TextView) findViewById(R.id.min1);
        minOwm.setText(owmInfo[2]);

        TextView maxApixu = (TextView) findViewById(R.id.max2);
        maxApixu.setText(apixuInfo[1]);
        TextView minApixu = (TextView) findViewById(R.id.min2);
        minApixu.setText(apixuInfo[2]);

        TextView maxWu = (TextView) findViewById(R.id.max3);
        maxOwm.setText(wuInfo[1]);
        TextView minWu = (TextView) findViewById(R.id.min3);
        minOwm.setText(wuInfo[2]);
    }


}






