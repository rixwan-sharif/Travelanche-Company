package com.example.rixwansharif.travelanch_company;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.RelativeLayout;
=======
import android.widget.ImageView;
>>>>>>> fb35918c8913d42907aa3abfe47610f76d242e57
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mylayout;
    private ActionBarDrawerToggle mytoggle;
    private Toolbar mtoolbar;
    private NavigationView navigationView;

    private Button client_trips,my_bids,my_accepted_bids;
    private CircleImageView profile_imageView;
    private TextView username,phone_number;



    String phone,name,city;


    CheckInternet cd=new CheckInternet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //widgets

        mtoolbar=(Toolbar) findViewById(R.id.nav_action);
        mylayout=(DrawerLayout) findViewById(R.id.mylayout);
        navigationView=(NavigationView) findViewById(R.id.mynavigationview);
        profile_imageView=(CircleImageView) findViewById(R.id.profile_image);
        username=(TextView) findViewById(R.id.profile_user_name);
        phone_number=(TextView) findViewById(R.id.profile_user_phone);




        client_trips=(Button) findViewById(R.id.client_trips_btn);
        my_bids=(Button) findViewById(R.id.my_bids_btn);
        my_accepted_bids=(Button) findViewById(R.id.accepted_bis_btn);
        //Set action bar
        setSupportActionBar(mtoolbar);


        // NavigationView

        mytoggle=new ActionBarDrawerToggle(this,mylayout,R.string.open,R.string.close);
        mylayout.setDrawerListener(mytoggle);
        mytoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);


        // Load trips of the clients of Rental's city
        Set_profile();

<<<<<<< HEAD
=======
        profile_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImageView img_full = (ImageView) findViewById(R.id.profile_image);
                Intent i=new Intent(MainActivity.this,preview_fullimage.class);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, img_full, "profileimage");
                startActivity(i, options.toBundle());
            }
        });


>>>>>>> fb35918c8913d42907aa3abfe47610f76d242e57
        //
        client_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ClientTripsActivity.class);
                startActivity(intent);
            }
        });

        //My bids
        my_bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyBidsActivity.class);
                startActivity(intent);
            }
        });


        //Accepted bids
        my_accepted_bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyAcceptedBidsActivity.class);
                startActivity(intent);
            }
        });
    }

    /*   @Override
       public boolean onCreateOptionsMenu(Menu menu) {
           // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.menu_main, menu);
           return true;
       }
   */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (mytoggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {


        int id = menuItem.getItemId();

        if(id==R.id.my_trips)
        {

        }
        if(id==R.id.settings)
        {

        }
        if(id==R.id.logout)
        {
            logout();
        }
        if(id==R.id.address)
        {

        }
        if(id==R.id.privacy_policy)
        {

        }

        mylayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout()
    {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Drawer_menu_Theme));
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(config.Phone_SHARED_PREF, "");
                        editor.putString(config.Company_SHARED_PREF, "");
                        editor.putString(config.Contact_Person_SHARED_PREF, "");
                        editor.putString(config.City_SHARED_PREF, "");
                        editor.putString(config.Image_SHARED_PREF, "");
                        editor.putString(config.Rating_SHARED_PREF, "");
                        editor.putString(config.Address_SHARED_PREF, "");
                        editor.putString(config.Company_RegNo_SHARED_PREF, "");


                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }


    // End load trip
    private void Set_profile()
    {



        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        phone= sharedPreferences.getString(config.Phone_SHARED_PREF,"Not Available");
        name=sharedPreferences.getString(config.Contact_Person_SHARED_PREF, "Not Available");


        username.setText(name);
        phone_number.setText(phone);

        final String pic_path=sharedPreferences.getString(config.Image_SHARED_PREF, "Not Available");


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
    }

    //back key
    @Override
    public void onBackPressed()
    {
        if(mylayout.isDrawerOpen(GravityCompat.START))
        {
            mylayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            moveTaskToBack(true);
        }
    }

}
