package com.example.rixwansharif.travelanch_company;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LambSambActivity extends AppCompatActivity {


    private EditText vehicle_text,fare_text,rate_text;
    private Button Bid_Button;
    String UserPhone,Trip_id,Vehicle,Company_Name,Company_Phone;
    Long Total_Fare,Rate_Per_Day;
    CheckInternet cd=new CheckInternet(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamb_samb);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //widgets

        vehicle_text=(EditText) findViewById(R.id.lambsamb_vehicle_text);
        fare_text=(EditText) findViewById(R.id.lambsamb_fare_text);
        rate_text=(EditText) findViewById(R.id.lambsamb_rate_txt);
        Bid_Button=(Button) findViewById(R.id.lamb_samb_bid_btn);



        // Setting Previous data

        Set_Data();

        //Funcs


        final CheckBox checkbox=(CheckBox)findViewById(R.id.lambsamb_checkBox);
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox.isChecked()) {
                    rate_text.setVisibility(View.VISIBLE);
                } else {
                    rate_text.setVisibility(View.GONE);
                }
            }
        });

        vehicle_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Laod_Vehicles();
            }
        });

        Bid_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bid();
            }
        });
    }

    private void Bid()
    {

        if(Validate())
        {
            Intialize();
            Bid_Process();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    //validation

    private boolean Validate()
    {


        boolean valid=true;

        if(  vehicle_text.length() == 0)
        {

            vehicle_text.setError("Choose Vehicle");
            vehicle_text.requestFocus();
            valid=false;
        }
        if( fare_text.length() == 0 )
        {
            fare_text.setError("Total Fare is required");
            fare_text.requestFocus();
            valid=false;
        }
        if(rate_text.getVisibility()==View.VISIBLE)
        {
            if( rate_text.length() == 0 )
            {
                rate_text.setError("Rate/Day is required");
                rate_text.requestFocus();
                valid=false;
            }
        }


        return valid;
    }


    //Intializing values

    private void Intialize()
    {
        Vehicle=vehicle_text.getText().toString().trim();
        Total_Fare=Long.decode(fare_text.getText().toString().trim());

        if(rate_text.getVisibility()==View.VISIBLE)
        {
            Rate_Per_Day=Long.decode(rate_text.getText().toString().trim());
        }
        else
        {
            Rate_Per_Day=Long.decode("0");
        }
    }

    private void Set_Data()
    {
        // Setting Previous data

        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Bundle  intent_data=getIntent().getExtras();

        Company_Phone= sharedPreferences.getString(config.Phone_SHARED_PREF,"Not Available");
        Company_Name=sharedPreferences.getString(config.Company_SHARED_PREF, "Not Available");
        Trip_id =intent_data.getString("trip_id");
        UserPhone =intent_data.getString("user_phone");
    }

    private void Bid_Process()
    {

        if (cd.isConnected())
        {

            final ProgressDialog loading = ProgressDialog.show(LambSambActivity.this, "Please wait", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {


                    LambSambActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (loading.isShowing()) {
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(), "Something has gone wrong, Try again !", Toast.LENGTH_SHORT).show();
                            }


                        }

                    });
                }
            }, 15000);

            //Creating an string request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.LambSamb_Bid_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            if (response.equalsIgnoreCase("success"))
                            {

                              AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LambSambActivity.this);
                                // set dialog message
                                alertDialogBuilder
                                        .setMessage("Bid Completed")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // current activity
                                                Intent intent = new Intent(LambSambActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        });
                                //
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                // show it
                                alertDialog.show();

                            }
                            else
                            {
                                //Displaying an error message on toast
                                Toast.makeText(LambSambActivity.this, "Failed to set new password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(LambSambActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("trip_id", Trip_id);
                    params.put("vehicle", Vehicle);
                    params.put("company_name", Company_Name);
                    params.put("company_phone", Company_Phone);
                    params.put("total_fare", Total_Fare+"");
                    params.put("rate_per_day", Rate_Per_Day+"");
                    params.put("user_phone", UserPhone);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LambSambActivity.this);
            requestQueue.add(stringRequest);


        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
            alertDialogBuilder
                    .setMessage("Check Internet Connection !")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }

    private void Laod_Vehicles()
    {

    }

}
