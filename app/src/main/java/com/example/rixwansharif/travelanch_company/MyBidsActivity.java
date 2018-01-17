package com.example.rixwansharif.travelanch_company;

import android.app.Activity;
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

public class MyBidsActivity extends AppCompatActivity {

    private ListView MyBidsListView;
    String  Company_phone_number;

    CheckInternet cd = new CheckInternet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bids);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("My Bids");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //
        MyBidsListView=(ListView) findViewById(R.id.my_bids_listView);

        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Company_phone_number=sharedPreferences.getString(config.Phone_SHARED_PREF, "Not Available").toString();
        Load_Bids();


    }


    private void Load_Bids() {


        if (cd.isConnected()) {

            final ProgressDialog loading = ProgressDialog.show(this, "Please Wait", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {


                    MyBidsActivity.this.runOnUiThread(new Runnable() {
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, config.Company_Bids_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            loading.dismiss();
                            showJSON(response);
                            //Toast.makeText(MyBidsActivity.this,response,Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loading.dismiss();
                            Toast.makeText(MyBidsActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("company_phone", Company_phone_number);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(MyBidsActivity.this);
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
        parse_json_my_bids pj = new parse_json_my_bids(json);
        pj.parseJSON();

        custom_row_for_my_bid cr=new custom_row_for_my_bid(this, parse_json_my_bids.id, parse_json_my_bids.trip_destination, parse_json_my_bids.trip_pickup_location,
                 parse_json_my_bids.trip_vehicle, parse_json_my_bids.trip_start_date, parse_json_my_bids.trip_end_date,
                 parse_json_my_bids.trip_pick_time, parse_json_my_bids.trip_drop_time, parse_json_my_bids.trip_driver, parse_json_my_bids.trip_ac,
                parse_json_my_bids.bid_date,parse_json_my_bids.bid_time, parse_json_my_bids.bid_rate_per_day, parse_json_my_bids.bid_total_fare,
                parse_json_my_bids.bids_on_trip, parse_json_my_bids.bid_vehicle, parse_json_my_accepted_bids.bid_vehicle_img,
                parse_json_my_bids.trip_date,parse_json_my_bids.trip_time);
        MyBidsListView.setAdapter(cr);
    }

    public class custom_row_for_my_bid extends ArrayAdapter<String> {


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
        private String[] trip_date;
        private String[] trip_time;

        //Bid
        private String[] bid_rate_per_day;
        private String[] bid_total_fare;
        private String[] bids_on_trip;
        private String[] bid_vehicle;
        private String[] bid_vehicle_img;
        private String[] bid_date;
        private String[] bid_time;



        private Activity context;

        public custom_row_for_my_bid(Activity context, String[] id, String[] trip_destination, String[] trip_pickup_location, String[] trip_vehicle,
                                           String[] trip_start_date, String[] trip_end_date,String[] trip_pick_time, String[] trip_drop_time,  String[] driver,
                                           String[] ac,String[] bid_date,String[] bid_time ,String[] bid_rate_per_day,String[] bid_total_fare,String[] bids_on_trip,String[] bid_vehicle,
                                           String[] bid_vehicle_img,String[] trip_date,String[] trip_time) {
            super(context, R.layout.custom_row_for_trip_bid,id);
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
            this.trip_date = trip_date;
            this.trip_time = trip_time;
            //
            this.bid_rate_per_day = bid_rate_per_day;
            this.bid_total_fare = bid_total_fare;
            this.bid_vehicle = bid_vehicle;
            this.bid_vehicle_img = bid_vehicle_img;
            this.bids_on_trip = bids_on_trip;
            this.bid_time = bid_time;
            this.bid_date = bid_date;

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final LayoutInflater inflater = context.getLayoutInflater();
          final View listViewItem = inflater.inflate(R.layout.custom_row_for_trip_bid, null, true);


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
            Bid_Date.setText(bid_date[position]);
            Bid_Time.setText(bid_time[position]);

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
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);
                    Intent intent=new Intent(MyBidsActivity.this,ClientTripDetailActivity.class);
                    intent.putExtra("trip_destination",trip_destination[position]);
                    intent.putExtra("trip_pickup_location",trip_pickup_location[position]);
                    intent.putExtra("trip_vehicle",trip_vehicle[position]);
                    intent.putExtra("trip_driver",trip_driver[position]);
                    intent.putExtra("trip_ac",trip_ac[position]);
                    intent.putExtra("trip_start_date",trip_start_date[position]);
                    intent.putExtra("trip_end_date",trip_end_date[position]);
                    intent.putExtra("trip_pick_time","N/A");
                    intent.putExtra("trip_drop_time","N/A");
                    intent.putExtra("bids_on_trip",bids_on_trip[position]);
                    intent.putExtra("trip_date",trip_date[position]);
                    intent.putExtra("trip_time",trip_time[position]);

                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Cheema Sab kuch kr k wkhaao", Toast.LENGTH_SHORT).show();
                }
            });

            Bid_Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);
                    Intent intent=new Intent(MyBidsActivity.this,CompanyBidDetailActivity.class);
                    intent.putExtra("bid_vehicle_img", "vehilce_image.jpg");
                    intent.putExtra("bid_vehicle", bid_vehicle[position]);
                    intent.putExtra("bid_rate_per_day",bid_rate_per_day[position]);
                    intent.putExtra("bid_total_fare",bid_total_fare[position]);
                    intent.putExtra("my_bid_flag","1");
                    intent.putExtra("bid_id",id[position]);
                    intent.putExtra("Company_Phone",Company_phone_number);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Cheema Sab kuch kr k wkhaao", Toast.LENGTH_SHORT).show();
                }
            });




            //Delete & Edit
/*


*/


            //end
            return listViewItem ;

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MyBidsActivity.this,MainActivity.class);
        startActivity(intent);
    }

}
