package com.wc.giona.weathercompare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteFetchOwm {

    public String[] getCurrent(String city) throws Exception {
        JSONObject owmJson = getJSONowm(city);
        String[] owm = extractCurrentInfo(owmJson);
        return owm;
    }

    public String[] getForecast(String city) throws Exception {
        JSONObject owmJson = getForecastJSONowm(city);
        String[] owm = extractForecastInfo(owmJson);
        return owm;
    }

    private JSONObject getJSONowm(String city) throws Exception {
        JSONObject data = null;
        try {
                URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=79f1dbf6ed137706eb49daeb2fe46355&cnt=3&units=metric&cnt=7&lang=en"));
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

    private JSONObject getForecastJSONowm(String city) throws Exception {
        JSONObject data = null;
        try {
                URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=" + city + "&mode=json&units=metric&cnt=3&APPID=79f1dbf6ed137706eb49daeb2fe46355"));
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
        JSONObject main = (JSONObject) data.get("main");
        String temp = (main.get("temp")).toString();
        String tempMin =  (main.get("temp_min")).toString();
        String tempMax =  (main.get("temp_max").toString());


        String extractedInfo[] = new String[3];
        extractedInfo[0] =  temp;
        extractedInfo[1] =  tempMax;
        extractedInfo[2] =  tempMin;

        return extractedInfo;
    }

    private String[] extractForecastInfo(JSONObject data) throws JSONException {
        JSONArray list = (JSONArray) data.get("list");

        //---------Today forecast
        JSONObject zero = (JSONObject) list.get(Integer.parseInt("0"));
        JSONObject tempOne = (JSONObject) zero.get("temp");
        Double tempMaxOne = (Double) tempOne.get("max");
        Double tempMinOne = (Double) tempOne.get("min");

        String tempMaxOne_string = Double.valueOf(Math.round(tempMaxOne)).toString();
        String tempMinOne_string = Double.valueOf(Math.round(tempMinOne)).toString();


        //---------Tomorrow forecast
        JSONObject one = (JSONObject) list.get(Integer.parseInt("1"));
        JSONObject tempTwo = (JSONObject) one.get("temp");
        Double tempMaxTwo = (Double) tempTwo.get("max");
        Double tempMinTwo = (Double) tempTwo.get("min");

        String tempMaxTwo_string = Double.valueOf(Math.round(tempMaxTwo)).toString();
        String tempMinTwo_string = Double.valueOf(Math.round(tempMinTwo)).toString();

        //---------Day after Tomorrow forecast
        JSONObject two = (JSONObject) list.get(Integer.parseInt("2"));
        JSONObject tempThree = (JSONObject) two.get("temp");
        Double tempMaxThree = (Double) tempThree.get("max");
        Double tempMinThree = (Double) tempThree.get("min");

        String tempMaxThree_string = Double.valueOf(Math.round(tempMaxThree)).toString();
        String tempMinThree_string = Double.valueOf(Math.round(tempMinThree)).toString();


        String extractedInfo[] = new String[6];
        extractedInfo[0] = tempMaxOne_string;
        extractedInfo[1] = tempMinOne_string;
        extractedInfo[2] = tempMaxTwo_string ;
        extractedInfo[3] = tempMinTwo_string ;
        extractedInfo[4] = tempMaxThree_string ;
        extractedInfo[5] = tempMinThree_string ;

        return extractedInfo;
    }

 }