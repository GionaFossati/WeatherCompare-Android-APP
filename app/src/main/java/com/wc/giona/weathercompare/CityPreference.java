package com.wc.giona.weathercompare;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Giona on 03/11/2017.
 */

public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    String getCity(){
        return prefs.getString("city", "Trento");
    }

    void setCity(String city) {
        prefs.edit().putString("city",city).commit();
    }
}
