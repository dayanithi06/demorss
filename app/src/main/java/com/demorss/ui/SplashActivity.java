package com.demorss.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.demorss.R;
import com.demorss.ui.main.MainActivity;
import com.demorss.utils.PrefConnect;

/**
 * Created by DAYANITHI on. 04-07-2017.
 */

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadPrefs();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (isLoggedIn) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void loadPrefs() {
        isLoggedIn = PrefConnect.readBoolean(this, PrefConnect.isLoggedIn, false);
    }
}

