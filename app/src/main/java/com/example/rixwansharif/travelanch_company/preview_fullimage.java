package com.example.rixwansharif.travelanch_company;

/**
 * Created by salman on 1/11/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class preview_fullimage  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_fullimage);
  /*  SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);



    final String pic_path=sharedPreferences.getString(config.Image_SHARED_PREF, "Not Available");
   ImageView profile_imageView=(CircleImageView) findViewById(R.id.imageView);
    @Override

        Picasso.with(getApplicationContext())
                .load("http://rixwanxharif.000webhostapp.com/" + pic_path)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(profile_imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getApplicationContext())
                                .load("http://rixwanxharif.000webhostapp.com/" + pic_path)
                                .into(profile_imageView);
                    }
                });
                */

    }
}