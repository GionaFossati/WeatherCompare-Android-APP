package com.wc.giona.weathercompare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    public Integer[] newFeedValues = new Integer[3];
    public String[] feedValues =  new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getFeedValues();
        setViews();
        }


    public void sendPressed(View view) {
        RatingBar ratingBarApixu = findViewById(R.id.ratingBarApixu);
        RatingBar ratingBarOwm = findViewById(R.id.ratingBarOwm);
        RatingBar ratingBarWu = findViewById(R.id.ratingBarWu);

        if ((!(ratingBarApixu.getRating() == 0) && !(ratingBarOwm.getRating() == 0) && !(ratingBarWu.getRating() == 0))) {

            // APIXU: [0] / OWM: [1] / WU: [2]
            newFeedValues[0] = Math.round(ratingBarApixu.getRating());
            newFeedValues[1] = Math.round(ratingBarOwm.getRating());
            newFeedValues[2] = Math.round(ratingBarWu.getRating());
            DatabaseHelper db = new DatabaseHelper(this);
            Boolean result = db.insertData(newFeedValues);
            if (result==false)
                Toast.makeText(getBaseContext(), "There is a problem" , Toast.LENGTH_LONG ).show();
            else Toast.makeText(getBaseContext(), "Feeback Sent, Thank you!!" , Toast.LENGTH_LONG ).show();
            db.close();
            finish();
        }
            else
            Toast.makeText(getBaseContext(), "Please set all feedback stars!!" , Toast.LENGTH_LONG ).show();
    }

    public void getFeedValues() {
        DatabaseHelper db = new DatabaseHelper(this);
        feedValues = db.fetchData();

    }

    private void setViews() {
        //apixu= [0] | owm=[1] | Wu= [2]

        TextView currentApixu = findViewById(R.id.currentApixu);
        TextView currentOwm = findViewById(R.id.currentOwm);
        TextView currentWu = findViewById(R.id.currentWu);

        currentApixu.setText(feedValues[0]);
        currentOwm.setText(feedValues[1]);
        currentWu.setText(feedValues[2]);
    }


}
