package com.wc.giona.weathercompare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MaxMinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_max_min, container, false);
    }


    public void SetText(View v) {
        Bundle bundleInfo = this.getArguments();
        if(getArguments()!=null)
        {
            String[] owmInfoString = getArguments().getStringArray("owm");
            String[] apixuInfoString = getArguments().getStringArray("apixu");
            String[] wuInfoString = getArguments().getStringArray("wu");
            String[] feedValuesString = getArguments().getStringArray("feedValues");

            Double[] feedValues = new Double[3];
            Double[] apixuInfo = new Double[3];
            Double[] wuInfo = new Double[3];
            Double[] owmInfo = new Double[3];

            for (int i=0;i<3;i++) {feedValues[i] = Double.parseDouble(feedValuesString[i]);}
            if (owmInfoString[0]==null) {
                for (int i=1;i<3;i++) {apixuInfo[i] = Double.parseDouble(apixuInfoString[i]);}
                for (int i=1;i<3;i++) {owmInfo[i] = Double.parseDouble(owmInfoString[i]);}
                for (int i=1;i<3;i++) {wuInfo[i] = Double.parseDouble(wuInfoString[i]);}
                } else {
                for (int i=0;i<3;i++) {apixuInfo[i] = Double.parseDouble(apixuInfoString[i]);}
                for (int i=0;i<3;i++) {owmInfo[i] = Double.parseDouble(owmInfoString[i]);}
                for (int i=0;i<3;i++) {wuInfo[i] = Double.parseDouble(wuInfoString[i]);}
            }

            TextView maxTemp = v.findViewById(R.id.maxTemp);
            TextView minTemp = v.findViewById(R.id.minTemp);

            Double feedValuesSum = feedValues[0] + feedValues[1] + feedValues[2];
            //feedValues: apixu= [0] | owm=[1] | Wu= [2]
            long maxTempLong = (long) ((wuInfo[1] * feedValues[2] + owmInfo[1]* feedValues[1] + apixuInfo[1] * feedValues[0])/ feedValuesSum);
            maxTempLong = Math.round(maxTempLong);
            maxTemp.setText(maxTempLong + " °C");

            long minTempLong = (long) ((wuInfo[2] * feedValues[2] + owmInfo[2]* feedValues[1] + apixuInfo[2] * feedValues[0])/ feedValuesSum);
            minTempLong = Math.round(minTempLong);
            minTemp.setText(minTempLong + " °C");
        }
    }


}
