package com.wc.giona.weathercompare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ThreeDayLoading extends AppCompatActivity {
    private ImageView imageSplash;
    private ImageView imageLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_day_loading);

        final Animation animation = new RotateAnimation(0.0f, 720.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
        animation.setRepeatCount(1);
        animation.setDuration(2000);

        imageSplash = findViewById(R.id.imageSplash);
        imageLoad = findViewById(R.id.imageLoad);

        Animation loadAnimation = AnimationUtils.loadAnimation(this,R.anim.transition);
        Animation rotation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        imageSplash.startAnimation(loadAnimation);
        imageLoad.startAnimation(rotation);

        final Intent i = new Intent(this,ThreeDayActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {e.printStackTrace();}
                finally {
                    startActivity(i);
                    finish();
                }

            }

        };
        timer.start();
    }
}