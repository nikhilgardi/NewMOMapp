package com.mom.app.retail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;


public class splash extends Activity {

    private static int SPLASH_TIME_OUT = 2000;

    private static final String PREFS_NAME = "MyApp_Settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(String_url.ApplicationStarted,"yes");
        editor.putString(String_url.NotificationCount, "yes");
        editor.putString(String_url.notificationClass, "yes");
        editor.commit();

        Intent i = new Intent(splash.this, Background.class);
        startService(i);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                Intent i = new Intent(splash.this, LoginActivity.class);
                startActivity(i);overridePendingTransition(0, 0);
                finish();


            }
        }, SPLASH_TIME_OUT);
    }

}