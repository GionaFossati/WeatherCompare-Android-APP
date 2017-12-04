package com.wc.giona.weathercompare;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static java.lang.Integer.parseInt;


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
            String[] owmInfo = getArguments().getStringArray("owm");
            String[] apixuInfo = getArguments().getStringArray("apixu");
            String[] wuInfo = getArguments().getStringArray("wu");

            TextView maxTemp = v.findViewById(R.id.maxTemp);
            TextView minTemp = v.findViewById(R.id.minTemp);

            maxTemp.setText((parseInt(wuInfo[1]) + parseInt(owmInfo[1]) + parseInt(apixuInfo[1]))/3 + " °C");
            minTemp.setText((parseInt(wuInfo[2]) + parseInt(owmInfo[2]) + parseInt(apixuInfo[2]))/3 + " °C");
        }
    }


}
