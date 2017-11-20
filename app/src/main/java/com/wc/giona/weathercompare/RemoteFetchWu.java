package com.wc.giona.weathercompare;

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
                //TODO trovare il modo di fare leggere tutto il JSON dal ciclo
                json.append(tmp + "\n");
            reader.close();

            data = new JSONObject(json.toString());

        } catch (Exception e){
            e.printStackTrace();
        }
        String[] wu = new String[3];
        wu = extractInfo(data);
        wu[0] = getCurrentJSONwu("verona");
        return wu;



    }

    public String getCurrentJSONwu(String city) throws Exception {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.wunderground.com/api/02b568e5758b4ca8/conditions/q/IT/" + city + ".json"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null) {
                    if ((tmp = reader.readLine()).contains("temp_c")) { json.append(tmp).append("\n"); }
                }
            reader.close();

            data = new JSONObject(json.toString());

        } catch (Exception e){
            e.printStackTrace();
        }

        String actualTemp = extractCurrentInfo(data);
        return actualTemp;


    }

    public String extractCurrentInfo(JSONObject data) throws JSONException {
        //-------------current temp
        JSONObject currentObs = (JSONObject) data.get("current_observation");
        Double currentTemp = (Double) currentObs.get("temp_c");
        String currentTemp_string = currentTemp.toString();

        return currentTemp_string;
    }



    public String[] extractInfo(JSONObject data) throws JSONException {

        //----------------today max and min
        JSONObject forecast = (JSONObject) data.get("current_observation");
        JSONObject simpleForecast = (JSONObject) forecast.get("simpleforecast");
        JSONArray forecastDay = (JSONArray) simpleForecast.getJSONArray("forecastday");
        JSONObject zero = (JSONObject) forecastDay.get(Integer.parseInt("0"));
        JSONObject high = (JSONObject) zero.get("high");
        JSONObject low = (JSONObject) zero.get("low");
        Double tempMax = (Double) high.get("celsius");
        Double tempMin = (Double) low.get("celsius");

        String tempMax_string = tempMax.toString();
        String tempMin_string = tempMin.toString();

        String[] extractedInfo = new String[3];
        extractedInfo[1] = tempMax_string;
        extractedInfo[2] = tempMin_string;

        return extractedInfo;
    }
}
