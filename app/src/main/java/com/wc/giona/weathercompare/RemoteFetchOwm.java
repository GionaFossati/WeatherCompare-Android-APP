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
        Double tempMaxOne, tempMinOne;
        Integer tempMaxOneInt, tempMinOneInt;
        String tempMaxOne_string, tempMinOne_string;
        if ((tempOne.get("max")).getClass().getName() == "java.lang.Double") {
            tempMaxOne = (Double) tempOne.get("max");
            tempMinOne = (Double) tempOne.get("min");
            tempMaxOne_string = Double.valueOf(Math.round(tempMaxOne)).toString();
            tempMinOne_string = Double.valueOf(Math.round(tempMinOne)).toString();
            tempMaxOne_string = tempMaxOne_string.substring(0, tempMaxOne_string.length() - 2);
            tempMinOne_string = tempMinOne_string.substring(0, tempMinOne_string.length() -2);
        } else {
            tempMaxOneInt = (Integer) tempOne.get("max");
            tempMinOneInt = (Integer) tempOne.get("min");
            tempMaxOne_string = (tempMaxOneInt).toString();
            tempMinOne_string = (tempMinOneInt).toString();
        }


        //---------Tomorrow forecast
        JSONObject one = (JSONObject) list.get(Integer.parseInt("1"));
        JSONObject tempTwo = (JSONObject) one.get("temp");
        Double tempMaxTwo, tempMinTwo;
        Integer tempMaxTwoInt, tempMinTwoInt;
        String tempMaxTwo_string, tempMinTwo_string;
        if ((tempTwo.get("max")).getClass().getName() == "java.lang.Double") {
            tempMaxTwo = (Double) tempTwo.get("max");
            tempMinTwo = (Double) tempTwo.get("min");
            tempMaxTwo_string = Double.valueOf(Math.round(tempMaxTwo)).toString();
            tempMinTwo_string = Double.valueOf(Math.round(tempMinTwo)).toString();
            tempMaxTwo_string = tempMaxTwo_string.substring(0, tempMaxTwo_string.length() - 2);
            tempMinTwo_string = tempMinTwo_string.substring(0, tempMinTwo_string.length() -2);
        } else {
            tempMaxTwoInt = (Integer) tempTwo.get("max");
            tempMinTwoInt = (Integer) tempTwo.get("min");
            tempMaxTwo_string = (tempMaxTwoInt).toString();
            tempMinTwo_string = (tempMinTwoInt).toString();
        }

        //---------Day after Tomorrow forecast
        JSONObject two = (JSONObject) list.get(Integer.parseInt("2"));
        JSONObject tempThree = (JSONObject) two.get("temp");
        Double tempMaxThree, tempMinThree;
        Integer tempMaxThreeInt, tempMinThreeInt;
        String tempMaxThree_string, tempMinThree_string;
        if ((tempThree.get("max")).getClass().getName() == "java.lang.Double") {
            tempMaxThree = (Double) tempThree.get("max");
            tempMinThree = (Double) tempThree.get("min");
            tempMaxThree_string = Double.valueOf(Math.round(tempMaxThree)).toString();
            tempMinThree_string = Double.valueOf(Math.round(tempMinThree)).toString();
            tempMaxThree_string = tempMaxThree_string.substring(0, tempMaxThree_string.length() - 2);
            tempMinThree_string = tempMinThree_string.substring(0, tempMinThree_string.length() -2);
        } else {
            tempMaxThreeInt = (Integer) tempThree.get("max");
            tempMinThreeInt = (Integer) tempThree.get("min");
            tempMaxThree_string = (tempMaxThreeInt).toString();
            tempMinThree_string = (tempMinThreeInt).toString();
        }


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