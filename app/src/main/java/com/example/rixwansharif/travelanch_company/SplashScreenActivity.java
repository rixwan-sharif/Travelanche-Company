package com.example.rixwansharif.travelanch_company;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        getSupportActionBar().hide();

        //Check if user alredy login

        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);
        //If we will get true


        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {

                    sleep(2300);

                    if (loggedIn)
                    {
                        //We will start the Profile Activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                    else {

                        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                        startActivity(intent);
                    }
                    finish();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        //Starting Thread
        myThread.start();

    }

}
