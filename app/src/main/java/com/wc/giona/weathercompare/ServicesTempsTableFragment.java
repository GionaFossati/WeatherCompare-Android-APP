package com.wc.giona.weathercompare;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static com.wc.giona.weathercompare.R.*;


public class ServicesTempsTableFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_services_temps_table, container, false);
    }


    public void SetText(View v) {
        Bundle bundleInfo = this.getArguments();
        if(getArguments()!=null)
        {
            String[] owmInfo = getArguments().getStringArray("owm");
            String[] apixuInfo = getArguments().getStringArray("apixu");
            String[] wuInfo = getArguments().getStringArray("wu");

            TextView maxOwm = v.findViewById(R.id.max1);
            TextView minOwm = v.findViewById(R.id.min1);
            TextView maxApixu = v.findViewById(R.id.max2);
            TextView minApixu = v.findViewById(R.id.min2);
            TextView maxWu = v.findViewById(R.id.max3);
            TextView minWu = v.findViewById(R.id.min3);

            maxOwm.setText(owmInfo[1] + " °C");
            minOwm.setText(owmInfo[2] + " °C");


            maxApixu.setText(apixuInfo[1] + " °C");
            minApixu.setText(apixuInfo[2] + " °C");


            maxWu.setText(wuInfo[1] + " °C");
            minWu.setText(wuInfo[2] + " °C");
        }
    }

    public void logoPressed(View view) {
        showFeebackDialog();
    }

    private void showFeebackDialog() {
        String[] feedValues= getArguments().getStringArray("feedValues");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Weather Service's Reliability:");
        final TextView apixu = new TextView(this.getContext());
        final TextView owm = new TextView(this.getContext());
        final TextView wu = new TextView(this.getContext());
        builder.setView(apixu);
        builder.setView(wu);
        builder.setView(owm);
        //feedValues: apixu= [0] | owm=[1] | Wu= [2]
        apixu.setText(feedValues[0]);
        owm.setText(feedValues[1]);
        wu.setText(feedValues[2]);
        builder.show();
    }




}
