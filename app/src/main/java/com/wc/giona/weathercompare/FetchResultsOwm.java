package com.wc.giona.weathercompare;

/**
 * Created by Giona on 16/11/2017.
 */

final class FetchResultsOwm {
    String actualTempOwm;
    String maxOwm;
    String minOwm;


    public void fetchResultsOwm(String actualTempOwm, String maxOwm, String minOwm) {
            this.actualTempOwm = actualTempOwm;
            this.maxOwm = maxOwm;
            this.minOwm = minOwm;
    }

    public String getTempOwm() {
        return actualTempOwm;
    }


    public String getMaxOwm() {
        return maxOwm;
    }


    public String getMinOwm() {
        return minOwm;
    }

}
