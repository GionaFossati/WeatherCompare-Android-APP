package com.wc.giona.weathercompare;

/**
 * Created by Giona on 16/11/2017.
 */

public class FetchResultsApixu {
    private final String actualTempApixu;
    private final String maxApixu;
    private final String minApixu;


    public FetchResultsApixu(String actualTempApixu, String maxApixu, String minApixu) {
        this.actualTempApixu = actualTempApixu;
        this.maxApixu = maxApixu;
        this.minApixu = minApixu;
    }

    public String getTempApixu() {
        return actualTempApixu;
    }

    public String getMaxApixu() {
        return maxApixu;
    }

    public String getMinApixu() {
        return minApixu;
    }

}
