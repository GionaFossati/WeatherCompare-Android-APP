package com.wc.giona.weathercompare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Giona on 14/11/2017.
 */

public class ResultsUI {
    TextView city_field;
    TextView currentTemp;
    TextView max1;
    TextView min1;
    TextView max2;
    TextView min2;
    TextView max3;
    TextView min3;
     Handler handler;

    public ResultsUI() {
        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        city_field = (TextView)rootView.findViewById(R.id.city_field);
        currentTemp = (TextView)rootView.findViewById(R.id.currentTemp);
        max1 = (TextView)rootView.findViewById(R.id.max1);
        min1 = (TextView)rootView.findViewById(R.id.min1);
        max2 = (TextView)rootView.findViewById(R.id.max2);
        min2 = (TextView)rootView.findViewById(R.id.min2);
        max3 = (TextView)rootView.findViewById(R.id.max3);
        min3 = (TextView)rootView.findViewById(R.id.min3);

        return rootView;
    }
    */
}
