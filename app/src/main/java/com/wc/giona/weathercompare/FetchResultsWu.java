package com.wc.giona.weathercompare;

/**
 * Created by Giona on 16/11/2017.
 */

public class FetchResultsWu {
    private final String actualTempWu;
    private final String maxWu;
    private final String minWu;

    public FetchResultsWu(String actualTempWu, String maxWu, String minWu) {
        this.actualTempWu = actualTempWu;
        this.maxWu = maxWu;
        this.minWu = minWu;
    }

    public String getTempWu() {
        return actualTempWu;
    }

    public String getMaxWu() {
        return maxWu;
    }

    public String getMinWu() {
        return minWu;
    }


}
