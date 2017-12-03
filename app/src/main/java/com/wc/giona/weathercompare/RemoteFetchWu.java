package com.wc.giona.weathercompare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetchWu {

    public String[] getCurrent(String city) throws Exception {
        String wu[];
        JSONObject ForecastData = getForecastJSONwu(city);
        wu = extractCurrentInfo(ForecastData);
        wu[0] = getCurrentJSONwu(city);
        return wu;
    }

    public String[] getForecast(String city) throws Exception {
        JSONObject owmJson = getForecastJSONwu(city);
        String[] owm = extractForecastInfo(owmJson);
        return owm;
    }

    private String getCurrentJSONwu(String city) throws Exception {
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
            if ((data2.endsWith("."))) {data2 = data2.substring(0,1);}

        } catch (Exception e){
            e.printStackTrace();
        }

        return data2;
    }

    private JSONObject getForecastJSONwu(String city) throws Exception {
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

        return data;

    }


    private String[] extractCurrentInfo(JSONObject data) throws JSONException {

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

    private String[] extractForecastInfo(JSONObject data) throws JSONException {

        JSONObject forecast = (JSONObject) data.get("forecast");
        JSONObject simpleForecast = (JSONObject) forecast.get("simpleforecast");
        JSONArray forecastDay = simpleForecast.getJSONArray("forecastday");

        //------today forecast-------
        JSONObject zero = (JSONObject) forecastDay.get(0);
        JSONObject highOne = (JSONObject) zero.get("high");
        JSONObject lowOne = (JSONObject) zero.get("low");
        String tempMaxOne = (String) highOne.get("celsius");
        String tempMinOne = (String) lowOne.get("celsius");

        //------tomorrow forecast-------
        JSONObject one = (JSONObject) forecastDay.get(1);
        JSONObject highTwo = (JSONObject) one.get("high");
        JSONObject lowTwo = (JSONObject) one.get("low");
        String tempMaxTwo = (String) highTwo.get("celsius");
        String tempMinTwo = (String) lowTwo.get("celsius");

        //------day after tomorrow forecast-------
        JSONObject two = (JSONObject) forecastDay.get(2);
        JSONObject highThree = (JSONObject) two.get("high");
        JSONObject lowThree = (JSONObject) two.get("low");
        String tempMaxThree = (String) highThree.get("celsius");
        String tempMinThree = (String) lowThree.get("celsius");


        String[] extractedInfo = new String[6];
        extractedInfo[0] = tempMaxOne;
        extractedInfo[1] = tempMinOne;
        extractedInfo[2] = tempMaxThree;
        extractedInfo[3] = tempMinThree;
        extractedInfo[4] = tempMaxThree;
        extractedInfo[5] = tempMinThree;

        return extractedInfo;
    }
}