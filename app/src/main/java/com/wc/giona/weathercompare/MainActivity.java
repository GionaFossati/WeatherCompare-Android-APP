package com.wc.giona.weathercompare;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public String[] owmInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
        RemoteFetch fetchWeather = new RemoteFetch();

        try {
            owmInfo = fetchWeather.getJSONowm("Verona");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //todo FetchResultsOwm owm = new FetchResultsOwm();
        //todo posso creare un nuovo oggetto e chiamare le funzioni get...() per chiedere i valori inseriti nel cliclo try..catch??

        TextView actualTemp = (TextView) findViewById(R.id.currentTemp);
        actualTemp.setText(owmInfo[0]);
        TextView maxOwm = (TextView) findViewById(R.id.max1);
        maxOwm.setText(owmInfo[1]);
        TextView minOwm = (TextView) findViewById(R.id.min1);
        minOwm.setText(owmInfo[2]);


        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                } catch (Exception e) {}

            }
        });*/

    }


}






