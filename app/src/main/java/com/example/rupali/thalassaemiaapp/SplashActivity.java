package com.example.rupali.thalassaemiaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.squareup.picasso.Picasso;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    SharedPreferences sharedPreferences=getSharedPreferences(Constants.LoginSharedPref.SHARED_PREF_NAME,MODE_PRIVATE);
                    Boolean ispreviouslyStarted =sharedPreferences.getBoolean(Constants.LoginSharedPref.PREVIOUSLY_STARTED, false);
                    if (!ispreviouslyStarted) {
                        //show start activity
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
//                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(i);
                }
            }
        };
        splashThread.start();

    }
}
