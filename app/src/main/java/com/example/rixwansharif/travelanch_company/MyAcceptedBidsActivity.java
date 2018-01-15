package com.example.rixwansharif.travelanch_company;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

public class MyAcceptedBidsActivity extends AppCompatActivity {

    private ListView MyAcceptedBidsListView;
    String  Company_phone_number;

    CheckInternet cd = new CheckInternet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accepted_bids);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //
        MyAcceptedBidsListView=(ListView) findViewById(R.id.my_accepted_bids_listView);

        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Company_phone_number=sharedPreferences.getString(config.Phone_SHARED_PREF, "Not Available").toString();
        Load_Accepted_Bids();
    }


    private void Load_Accepted_Bids() {


        if (cd.isConnected()) {

            final ProgressDialog loading = ProgressDialog.show(this, "Please Wait", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {

                    MyAcceptedBidsActivity.this.runOnUiThread(new Runnable() {
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.Accepted_Company_Bids_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            //Toast.makeText(MyAcceptedBidsActivity.this,response,Toast.LENGTH_LONG).show();
                            showJSON(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(MyAcceptedBidsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("company_phone", Company_phone_number);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MyAcceptedBidsActivity.this);
            requestQueue.add(stringRequest);
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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



    //



    private void showJSON(String json){
        parse_json_my_accepted_bids pj = new parse_json_my_accepted_bids(json);
        pj.parseJSON();

        custom_row_for_my_accepted_bid cr=new custom_row_for_my_accepted_bid(this, parse_json_my_accepted_bids.id, parse_json_my_accepted_bids.trip_destination, parse_json_my_accepted_bids.trip_pickup_location,
                parse_json_my_accepted_bids.trip_vehicle, parse_json_my_accepted_bids.trip_start_date, parse_json_my_accepted_bids.trip_end_date,
                parse_json_my_accepted_bids.trip_pick_time, parse_json_my_accepted_bids.trip_drop_time, parse_json_my_accepted_bids.trip_driver, parse_json_my_accepted_bids.trip_ac,
                parse_json_my_accepted_bids.trip_date_time,parse_json_my_accepted_bids.bid_rate_per_day,
                parse_json_my_accepted_bids.bid_total_fare, parse_json_my_accepted_bids.bids_on_trip, parse_json_my_accepted_bids.bid_vehicle,parse_json_my_accepted_bids.bid_vehicle_img);
        MyAcceptedBidsListView.setAdapter(cr);
    }

    public class custom_row_for_my_accepted_bid extends ArrayAdapter<String> {


        private String[] id;

        // Trip
        private String[] trip_destination;
        private String[] trip_pick_time;
        private String[] trip_drop_time;
        private String[] trip_pickup_location;
        private String[] trip_vehicle;
        private String[] trip_start_date;
        private String[] trip_end_date;
        private String[] trip_driver;
        private String[] trip_ac;
        private String[] trip_date_time;



        //Bid
        private String[] bid_rate_per_day;
        private String[] bid_total_fare;
        private String[] bids_on_trip;
        private String[] bid_vehicle;
        private String[] bid_vehicle_img;



        private Activity context;

        public custom_row_for_my_accepted_bid(Activity context, String[] id, String[] trip_destination, String[] trip_pickup_location, String[] trip_vehicle,
                                     String[] trip_start_date, String[] trip_end_date,String[] trip_pick_time, String[] trip_drop_time,  String[] driver,
                                     String[] ac,String[] trip_date_time, String[] bid_rate_per_day,String[] bid_total_fare,String[] bids_on_trip,String[] bid_vehicle,
                                     String[] bid_vehicle_img) {
            super(context, R.layout.custom_row_for_accepted_bid,id);
            this.context = context;

            this.id = id;
            this.trip_destination = trip_destination;
            this.trip_pickup_location = trip_pickup_location;
            this.trip_pick_time = trip_pick_time;
            this.trip_drop_time = trip_drop_time;
            this.trip_vehicle = trip_vehicle;
            this.trip_start_date = trip_start_date;
            this.trip_end_date = trip_end_date;
            this.trip_driver = driver;
            this.trip_ac = ac;
            this.trip_date_time = trip_date_time;
            //
            this.bid_rate_per_day = bid_rate_per_day;
            this.bid_total_fare = bid_total_fare;
            this.bid_vehicle = bid_vehicle;
            this.bid_vehicle_img = bid_vehicle_img;
            this.bids_on_trip = bids_on_trip;


        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater = context.getLayoutInflater();
            final View listViewItem = inflater.inflate(R.layout.custom_row_for_accepted_bid, null, true);


            Button Trip_Button = (Button) listViewItem.findViewById(R.id.accepted_bid_trip_btn);
            Button Bid_Button = (Button) listViewItem.findViewById(R.id.accepted_bid_bid_btn);
            TextView Trip_Name = (TextView) listViewItem.findViewById(R.id.accepted_bid_trip_name);
            TextView Vehicle_Name = (TextView) listViewItem.findViewById(R.id.accepted_bid_vehilce_name);
            TextView Bid_Date = (TextView) listViewItem.findViewById(R.id.accepted_bid_date_textview);
            TextView Bid_Time = (TextView) listViewItem.findViewById(R.id.accepted_bid_time_textview);
            final CircleImageView Client_Pic=(CircleImageView) listViewItem.findViewById(R.id.accepted_bid_client_pic);
            final CircleImageView Vehicle_Pic=(CircleImageView) listViewItem.findViewById(R.id.accepted_bid_vehicle_pic);

            Trip_Name.setText(trip_destination[position]);
            Vehicle_Name.setText(bid_vehicle[position]);
            Bid_Date.setText(trip_date_time[position]);
            Bid_Time.setText(trip_date_time[position]);

            Picasso.with(getApplicationContext())
                    .load("http://rixwanxharif.000webhostapp.com/uploads/" + "IMG_20180110_013436_362.jpg")
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(Client_Pic, new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError() {
                            Picasso.with(getApplicationContext())
                                    .load("http://rixwanxharif.000webhostapp.com/uploads/" + "IMG_20180110_013436_362.jpg")
                                    .into(Client_Pic);}});
            Picasso.with(getApplicationContext())
                    .load("http://rixwanxharif.000webhostapp.com/uploads/" + "vehilce_image.jpg")
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(Vehicle_Pic, new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError() {
                            Picasso.with(getApplicationContext())
                                    .load("http://rixwanxharif.000webhostapp.com/uploads/" + "vehilce_image.jpg")
                                    .into(Vehicle_Pic);}});

            Trip_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Cheema Sab kuch kr k wkhaao", Toast.LENGTH_SHORT).show();
                }
            });

            Bid_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Cheema Sab kuch kr k wkhaao", Toast.LENGTH_SHORT).show();
                }
            });





/*
            //Trip details
            TextView destination_text = (TextView) listViewItem.findViewById(R.id.tt_dd_destination_textview);
            TextView trip_vehicle_text = (TextView) listViewItem.findViewById(R.id.tt_dd_vehcle_textview);
            TextView pick_loc_text = (TextView) listViewItem.findViewById(R.id.tt_dd_pickup_loc_textview);
            TextView driver_ac_text = (TextView) listViewItem.findViewById(R.id.tt_dd_ac_driver_textview);
            TextView date_from_text = (TextView) listViewItem.findViewById(R.id.tt_dd_from_textview);
            TextView date_to_text = (TextView) listViewItem.findViewById(R.id.tt_dd_to_textview);
            TextView pick_time_text = (TextView) listViewItem.findViewById(R.id.tt_dd_pick_time_textview);
            TextView drop_time_text = (TextView) listViewItem.findViewById(R.id.tt_dd_drop_time_textview);
            TextView date_time_text = (TextView) listViewItem.findViewById(R.id.tt_dd_date_time_textview);
            //Bid

            //TextView rate_per_day_text = (TextView) listViewItem.findViewById(R.id.accepted_bid_rate_text);
            //TextView total_fare_text = (TextView) listViewItem.findViewById(R.id.accepted_bid_total_fare_txt);
            //TextView no_bid_text = (TextView) listViewItem.findViewById(R.id.accepted_bid_bids_text);

            //TextView vehicle_detail_text = (TextView) listViewItem.findViewById(R.id.accepted_bid_vehicle_detail_text);
            //TextView vehiclee_detail_text = (TextView) listViewItem.findViewById(R.id.accepted_bid_vehiclee_detail_text);
            //ImageView vehicle_img = (ImageView) listViewItem.findViewById(R.id.accepted_bid_vehicle_image);




            //Trip

            destination_text.setText(trip_destination[position]);
            trip_vehicle_text.setText(trip_vehicle[position]);
            pick_loc_text.setText(trip_pickup_location[position]);
            date_from_text.setText(trip_start_date[position]);
            date_to_text.setText(trip_end_date[position]);
            pick_time_text.setText(trip_pick_time[position]);
            drop_time_text.setText(trip_drop_time[position]);
            date_time_text.setText(trip_date_time[position]);

            if (trip_driver[position].equals("1") && trip_ac[position].equals("1")) {
                driver_ac_text.setText("Yes / Yes");
            }
            else if (trip_driver[position].equals("1") && trip_ac[position].equals("0"))
            {
                driver_ac_text.setText("Yes / No");
            }
            else if (trip_driver[position].equals("0") && trip_ac[position].equals("1"))
            {
                driver_ac_text.setText("No / Yes");
            }
            else
            {
                driver_ac_text.setText("No / No");
            }


            //Image Code

            //no_bid_text.setText(bids_on_trip[position]);
/*
            if (bid_rate_per_day[position].equals("0")) {
                rate_per_day_text.setText("N/A");
                vehicle_detail_text.setText("N/A");

                vehiclee_detail_text.setText(bid_vehicle[position]);
                total_fare_text.setText(bid_total_fare[position]);
            } else if (bid_total_fare[position].equals("0")) {
                total_fare_text.setText("N/A");
                vehiclee_detail_text.setText("N/A");

                vehicle_detail_text.setText(bid_vehicle[position]);
                rate_per_day_text.setText(bid_rate_per_day[position]);
            } else {
                vehiclee_detail_text.setText(bid_vehicle[position]);
                total_fare_text.setText(bid_total_fare[position]);
                vehicle_detail_text.setText(bid_vehicle[position]);
                rate_per_day_text.setText(bid_rate_per_day[position]);
            }

            // Animation
            Button trip_btn = (Button) listViewItem.findViewById(R.id.accepted_bid_trip_btn);
            Button bid_btn = (Button) listViewItem.findViewById(R.id.accepted_bid_bid_btn);

            final RelativeLayout trip_tray=(RelativeLayout) listViewItem.findViewById(R.id.accepted_bid_trip_details_tray);
            final RelativeLayout bid_tray=(RelativeLayout) listViewItem.findViewById(R.id.accepted_bid_bid_details_tray);

            trip_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);

                    if (trip_tray.getVisibility() == View.GONE) {

                        bid_tray.setVisibility(View.GONE);
                        trip_tray.setVisibility(View.VISIBLE);
                    }

                }
            });

            bid_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);

                    if (bid_tray.getVisibility() == View.GONE) {

                        trip_tray.setVisibility(View.GONE);
                        bid_tray.setVisibility(View.VISIBLE);
                    }

                }
            });

*/
            return listViewItem ;

        }
    }


}
