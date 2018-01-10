package com.example.rixwansharif.travelanch_company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {

    private static Button log_in;
    private static Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        getSupportActionBar().hide();

        //Define widgets

        log_in=(Button) findViewById(R.id.set_new_pass_btn);
        sign_up=(Button) findViewById(R.id.sign_up_btn);

        //Listener for sign up and

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                v.startAnimation(buttonClick);

                log_in_click();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
                v.startAnimation(buttonClick);
                sign_up_click();
            }
        });

    }


    private void log_in_click()
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void sign_up_click()
    {

        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
