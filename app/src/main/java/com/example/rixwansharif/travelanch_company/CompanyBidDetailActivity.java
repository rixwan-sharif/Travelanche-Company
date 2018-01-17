package com.example.rixwansharif.travelanch_company;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyBidDetailActivity extends AppCompatActivity {

    CheckInternet cd = new CheckInternet(this);
    String Bid_id,Company_phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_bid_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //Bid
        final CircleImageView vehicle_img = (CircleImageView) findViewById(R.id.bid_vehicle_image);
        TextView rate_per_day_text = (TextView) findViewById(R.id.bid_rate_per_day);
        TextView total_fare_text = (TextView) findViewById(R.id.bid_total_fare);
        TextView vehicle_detail_text = (TextView) findViewById(R.id.bid_vehicle);
        TextView vehiclee_detail_text = (TextView) findViewById(R.id.bid_vehiclee);

        Button delete_bid = (Button) findViewById(R.id.delete_bid);
        Button edit_bid = (Button) findViewById(R.id.edit_bid);

        Bundle intent_data = getIntent().getExtras();
        if (intent_data == null) {
            return;
        }

        final String pic_path = intent_data.getString("bid_vehicle_img");

        if (intent_data.getString("my_bid_flag").equals("1")) {
            Company_phone_number=intent_data.getString("Company_Phone");
            Bid_id=intent_data.getString("bid_id");
        }else
        {
            delete_bid.setVisibility(View.GONE);
            edit_bid.setVisibility(View.GONE);
        }

        if (intent_data.getString("bid_rate_per_day").equals("0")) {
            rate_per_day_text.setText("N/A");
            vehicle_detail_text.setText("N/A");

            vehiclee_detail_text.setText(intent_data.getString("bid_vehicle"));
            total_fare_text.setText(intent_data.getString("bid_total_fare"));
        } else if (intent_data.getString("bid_total_fare").equals("0")) {
            total_fare_text.setText("N/A");
            vehiclee_detail_text.setText("N/A");

            vehicle_detail_text.setText(intent_data.getString("bid_vehicle"));
            rate_per_day_text.setText(intent_data.getString("bid_rate_per_day"));
        } else {
            vehiclee_detail_text.setText(intent_data.getString("bid_vehicle"));
            total_fare_text.setText(intent_data.getString("bid_total_fare"));
            vehicle_detail_text.setText(intent_data.getString("bid_vehicle"));
            rate_per_day_text.setText(intent_data.getString("bid_rate_per_day"));
        }

        Picasso.with(getApplicationContext())
                .load("http://rixwanxharif.000webhostapp.com/uploads/" + pic_path)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(vehicle_img, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext())
                                .load("http://rixwanxharif.000webhostapp.com/uploads/" + pic_path)
                                .into(vehicle_img);
                    }
                });

        //
        delete_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Delete_Bid();
            }
        });

    }


    private void Delete_Bid() {

        if (cd.isConnected()) {

            final ProgressDialog loading = ProgressDialog.show(CompanyBidDetailActivity.this, "Please Wait", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {


                    CompanyBidDetailActivity.this.runOnUiThread(new Runnable() {
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

            //fetch
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.Delete_Company_Bid_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            Toast.makeText(CompanyBidDetailActivity.this, response, Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(CompanyBidDetailActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("company_phone", Company_phone_number);
                    params.put("bid_id", Bid_id);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(CompanyBidDetailActivity.this);
            requestQueue.add(stringRequest);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CompanyBidDetailActivity.this);
            //alertDialogBuilder.setTitle("Your Title");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Check Internet Connection !")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            //
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }
}
