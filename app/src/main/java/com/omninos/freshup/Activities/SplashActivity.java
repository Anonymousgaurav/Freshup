package com.omninos.freshup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.omninos.freshup.R;
import com.omninos.freshup.Utils.App;

public class SplashActivity extends AppCompatActivity {

    private SplashActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = SplashActivity.this;

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    if (App.getAppPreferences().getToken(activity).equalsIgnoreCase("1")) {
                        startActivity(new Intent(activity, HomeActivity.class));
                        finishAffinity();
                    } else {
                        startActivity(new Intent(activity, WellcomeScreenActivity.class));
                        finishAffinity();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
