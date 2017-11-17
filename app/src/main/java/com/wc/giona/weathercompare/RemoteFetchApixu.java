package com.wc.giona.weathercompare;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Giona on 17/11/2017.
 */

public class RemoteFetchApixu {

    public String[] getJSONapixu(String city) throws JSONException {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.apixu.com/v1/forecast.json?key=e459170e51ef4e22a42192213170604&q=" + city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());


        } catch (Exception e){
            e.printStackTrace();
            }

        String[] apixu = extractInfo(data);

        return apixu;
    }

    public String[] extractInfo(JSONObject data) throws JSONException {

        JSONObject current = (JSONObject) data.get("current");
        Double temp = (Double) current.get("temp_c");

        JSONObject forecast = (JSONObject) data.get("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");
        JSONObject zero = (JSONObject) forecastDay.get(Integer.parseInt("0"));
        JSONObject day = (JSONObject) zero.get("day");
        Double tempMax = (Double) day.get("maxtemp_c");
        Double tempMin = (Double) day.get("mintemp_c");

        String temp_string = temp.toString();
        String tempMax_string = tempMax.toString();
        String tempMin_string = tempMin.toString();

        String extractedInfo[] = new String[3];
        extractedInfo[0] =  temp_string;
        extractedInfo[1] =  tempMax_string;
        extractedInfo[2] =  tempMin_string;

        return extractedInfo;
    }

}

