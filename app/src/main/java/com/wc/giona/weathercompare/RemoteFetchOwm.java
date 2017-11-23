package com.wc.giona.weathercompare;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteFetchOwm {

    public String[] getJSONowm(String city) throws Exception {
        JSONObject data = null;
        try {
                URL url = new URL(String.format("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=79f1dbf6ed137706eb49daeb2fe46355&cnt=3&units=metric&cnt=7&lang=en"));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Log.d("pdpd", "getJSONowm: connessione aperta");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();

                data = new JSONObject(json.toString());
                //Log.d("pdpd2", "getJSONowm: creato data");

        } catch (Exception e){
                e.printStackTrace();
            }


        String[] owm = extractInfo(data);

        return owm;
    }

    public String temp;
    public String tempMin;
    public String tempMax;

    public String[] extractInfo(JSONObject data) throws JSONException {
        JSONObject main = (JSONObject) data.get("main");
        Log.d("pdpd3", "getJSONowm: preso main");

        if ((main.get("temp").getClass().getName()).equals("Integer")) {
                temp = (main.get("temp")).toString();
                     }
            else { temp = (main.get("temp")).toString();
                        }

        if ((main.get("temp_min").getClass().getName()).equals("Integer")) {
                tempMin = (String) (main.get("temp_min")).toString();
                    }
            else { tempMin = (main.get("temp_min")).toString();
                        }

        if ((main.get("temp_max").getClass().getName()).equals("Integer")) {
                tempMax =  (main.get("temp_max").toString());
                    }
            else { tempMax = (main.get("temp_max")).toString();
                       }

        String extractedInfo[] = new String[3];
        extractedInfo[0] =  temp;
        extractedInfo[1] =  tempMax;
        extractedInfo[2] =  tempMin;

        return extractedInfo;
    }

}






