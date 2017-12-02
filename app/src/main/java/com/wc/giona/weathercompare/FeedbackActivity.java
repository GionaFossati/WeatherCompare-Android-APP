package com.wc.giona.weathercompare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FeedbackActivity extends AppCompatActivity {

    public Integer[] newFeedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    private Integer[] getViewsValues() {

        return null;
    }

    public void postFeedValues(Integer[] newFeedValues) {

    }
}
