package com.example.shan.onlineorderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shan.onlineorderapp.MainActivity;
import com.example.shan.onlineorderapp.R;

public class Splash extends MainActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Activity. */
                Intent mainIntent = new Intent(Splash.this,LoginActivity1.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}