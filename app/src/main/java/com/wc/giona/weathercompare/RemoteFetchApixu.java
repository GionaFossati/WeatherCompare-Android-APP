package com.wc.giona.weathercompare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class RemoteFetchApixu {

    public String[] getCurrent(String city) throws JSONException {
        JSONObject apixuJson = getJSONapixu(city);
        String[] apixu = extractCurrentInfo(apixuJson);
        return apixu;
    }

    public String[] getForecast(String city) throws JSONException {
        JSONObject apixuJson = getJSONapixu(city);
        String[] apixu = extractForecastInfo(apixuJson);
        return apixu;
    }

    private JSONObject getJSONapixu(String city) throws JSONException {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.apixu.com/v1/forecast.json?key=e459170e51ef4e22a42192213170604&q=" + city + "&days=3"));
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

        JSONObject current = (JSONObject) data.get("current");
        Double temp = (Double) current.get("temp_c");

        JSONObject forecast = (JSONObject) data.get("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");
        JSONObject zero = (JSONObject) forecastDay.get(Integer.parseInt("0"));
        JSONObject day = (JSONObject) zero.get("day");
        Double tempMax = (Double) day.get("maxtemp_c");
        Double tempMin = (Double) day.get("mintemp_c");

        String temp_string = Double.valueOf(Math.round(temp)).toString();
        String tempMax_string = Double.valueOf(Math.round(tempMax)).toString();
        String tempMin_string = Double.valueOf(Math.round(tempMin)).toString();

        temp_string = temp_string.substring(0, temp_string.length() - 2);
        tempMax_string = tempMax_string.substring(0, tempMax_string.length() - 2);
        tempMin_string = tempMin_string.substring(0, tempMin_string.length() - 2);

        String extractedInfo[] = new String[3];
        extractedInfo[0] =  temp_string;
        extractedInfo[1] =  tempMax_string;
        extractedInfo[2] =  tempMin_string;

        return extractedInfo;
    }

    private String[] extractForecastInfo(JSONObject data) throws JSONException {

        JSONObject current = (JSONObject) data.get("current");
        Double temp = (Double) current.get("temp_c");

        JSONObject forecast = (JSONObject) data.get("forecast");
        JSONArray forecastDay = forecast.getJSONArray("forecastday");

        //------------TODAY FORECAST-------------------
        JSONObject zero = (JSONObject) forecastDay.get(Integer.parseInt("0"));
        JSONObject dayOne = (JSONObject) zero.get("day");
        Double tempMaxOne = (Double) dayOne.get("maxtemp_c");
        Double tempMinOne = (Double) dayOne.get("mintemp_c");

        String tempMaxOne_string = Double.valueOf(Math.round(tempMaxOne)).toString();
        String tempMinOne_string = Double.valueOf(Math.round(tempMinOne)).toString();

        //------------TOMORROW FORECAST-------------------
        JSONObject one = (JSONObject) forecastDay.get(Integer.parseInt("1"));
        JSONObject dayTwo = (JSONObject) one.get("day");
        Double tempMaxTwo = (Double) dayTwo.get("maxtemp_c");
        Double tempMinTwo = (Double) dayTwo.get("mintemp_c");

        String tempMaxTwo_string = Double.valueOf(Math.round(tempMaxTwo)).toString();
        String tempMinTwo_string = Double.valueOf(Math.round(tempMinTwo)).toString();


        //------------DAY AFTER TOMORROW FORECAST-------------------
        JSONObject two = (JSONObject) forecastDay.get(Integer.parseInt("2"));
        JSONObject dayThree = (JSONObject) two.get("day");
        Double tempMaxThree = (Double) dayThree.get("maxtemp_c");
        Double tempMinThree = (Double) dayThree.get("mintemp_c");

        String tempMaxThree_string = Double.valueOf(Math.round(tempMaxThree)).toString();
        String tempMinThree_string = Double.valueOf(Math.round(tempMinThree)).toString();


        String extractedInfo[] = new String[6];
        extractedInfo[0] =  tempMaxOne_string;
        extractedInfo[1] =  tempMinOne_string;
        extractedInfo[2] =  tempMaxTwo_string;
        extractedInfo[3] =  tempMinTwo_string;
        extractedInfo[4] =  tempMaxThree_string;
        extractedInfo[5] =  tempMinThree_string;

        return extractedInfo;
    }

}