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
                    //check user already login or not
                    if (App.getAppPreferences().getToken(activity).equalsIgnoreCase("1")) {
                        //if login then move to home Page
                        startActivity(new Intent(activity, HomeActivity.class));
                        finishAffinity();
                    } else {
                        //if not then move to login Screen
                        Intent intent = new Intent(activity, ChooseLanguageActivity.class);
                        intent.putExtra("Move", "0");
                        startActivity(intent);
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
