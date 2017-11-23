package com.wc.giona.weathercompare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetchWu {

    public String[] getForecastJSONwu(String city) throws Exception {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.wunderground.com/api/02b568e5758b4ca8/forecast/q/IT/" + city + ".json"));
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
        String wu[];
        wu = extractInfo(data);
        wu[0] = getCurrentJSONwu(city);
        String[] check = wu;
        return wu;

    }

    public String[] a;

    public String getCurrentJSONwu(String city) throws Exception {
        JSONObject data = null;
        String data2 = null;
        try {
            URL url = new URL(String.format("http://api.wunderground.com/api/02b568e5758b4ca8/conditions/q/IT/" + city + ".json"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String temp = "";
            while (!(temp = reader.readLine()).contains("temp_c")) {
                json.append(temp);
            }
            reader.close();

            data2 = temp.substring(11,14);
            if ((data2.length()) == 3) {data2 = data2.substring(0,2);}

        } catch (Exception e){
            e.printStackTrace();
        }

        return data2;
    }

    /*public String extractCurrentInfo(JSONObject data) throws JSONException {
        //-------------current temp
        JSONObject currentObs = (JSONObject) data.get("current_observation");
        Double currentTemp = (Double) currentObs.get("temp_c");
        String currentTemp_string = currentTemp.toString();

        return currentTemp_string;
    }*/



    private String[] extractInfo(JSONObject data) throws JSONException {

        //----------------today max and min
        //JSONObject jj = (JSONObject) data.get("response");
        JSONObject forecast = (JSONObject) data.get("forecast");
        JSONObject simpleForecast = (JSONObject) forecast.get("simpleforecast");
        JSONArray forecastDay = simpleForecast.getJSONArray("forecastday");
        JSONObject zero = (JSONObject) forecastDay.get(0);
        JSONObject high = (JSONObject) zero.get("high");
        JSONObject low = (JSONObject) zero.get("low");
        String tempMax = (String) high.get("celsius");
        String tempMin = (String) low.get("celsius");

        String[] extractedInfo = new String[3];
        extractedInfo[1] = tempMax;
        extractedInfo[2] = tempMin;

        return extractedInfo;
    }
}
