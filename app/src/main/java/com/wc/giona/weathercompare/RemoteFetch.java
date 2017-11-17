package com.wc.giona.weathercompare;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteFetch {

    public JSONObject getJSONwu(String city) throws JSONException {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.wunderground.com/api/02b568e5758b4ca8/conditions/q/IT/=" + city + ".json"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());

            /* This value will be 404 if the request was not
             successful
            if (data.getInt("cod") != 200) {
                return null;
            }
            */

        } catch (Exception e){};

        return data;
    }

    public JSONObject getJSONapixu(String city) throws JSONException {
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

            /* This value will be 404 if the request was not
             successful
            if (data.getInt("cod") != 200) {
                return null;
            }
            */
        } catch (Exception e){};

        return data;
    }

    /*public String temp_string;
    public String tempMax_string;
    public String tempMin_string;
*/
    public String[] getJSONowm(String city) throws Exception {
        JSONObject data = null;
        try {
            URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=79f1dbf6ed137706eb49daeb2fe46355&cnt=3&units=metric&cnt=7&lang=en"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Log.d("pdpd", "getJSONowm: connessione aperta");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());
            Log.d("pdpd2", "getJSONowm: creato data");

        } catch (Exception e){
            e.printStackTrace();
        }

        JSONObject main = (JSONObject) data.get("main");
        Log.d("pdpd3", "getJSONowm: preso main");

        Double temp = (Double) main.get("temp");
        Integer tempMin = (Integer) main.get("temp_min");
        Integer tempMax = (Integer) main.get("temp_max");

        String temp_string = temp.toString();
        String tempMax_string = tempMax.toString();
        String tempMin_string = tempMin.toString();

        String owm[] = new String[3];
        owm[0] =  temp_string;
        owm[1] =  tempMax_string;
        owm[2] =  tempMin_string;
        return owm;

        //FetchResultsOwm resultsowm = new FetchResultsOwm();
        //resultsowm.fetchResultsOwm(temp_string,tempMax_string,tempMin_string);
        /*getTempOwm(temp_string);
        getMaxOwm(tempMax_string);
        getMinOwm(tempMin_string);
        */



        //TODO chiedere se per fare il fetch dei dati inseriti qui sopra devo fare un return dell'oggetto "resultsowm" oppure se mi basta crearne uno nuovo

    }


    }

    /*public String getTempOwm(String temp_string) {
        return temp_string;
    }


    public String getMaxOwm(String tempMax_string) {
        return tempMax_string;
    }


    public String getMinOwm(String tempMin_string) {
        return tempMin_string;
    }



    public int dataOfInterest(JSONObject data) throws JSONException {
        JSONObject dataOfInterest = new JSONObject(JSONObject.quote("main"));
        double temperature = Double.parseDouble(dataOfInterest.getString("temp"));
        int temperatureInt = (int) temperature;
        return temperatureInt;

    }*/



