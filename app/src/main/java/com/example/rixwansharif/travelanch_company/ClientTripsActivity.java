package com.example.rixwansharif.travelanch_company;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientTripsActivity extends AppCompatActivity {

    private ListView TripListView;

    CheckInternet cd=new CheckInternet(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_trips);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        //
        TripListView=(ListView) findViewById(R.id.trips_listView);

        Load_Trip();
    }

    private void Load_Trip()
    {



        if (cd.isConnected()) {

            final ProgressDialog loading = ProgressDialog.show(this, "Please Wait", "Please wait...", false, true);

            Timer timer_a = new Timer();
            timer_a.schedule(new TimerTask() {
                @Override
                public void run() {


                    ClientTripsActivity.this.runOnUiThread(new Runnable() {
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


            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response);
                }

            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    loading.dismiss();
                    Toast.makeText(ClientTripsActivity.this,volleyError.getMessage(),Toast.LENGTH_LONG).show();

                }
            };

            fetch_trip_company fetchTripRequest = new fetch_trip_company(responseListener, errorListener);
            RequestQueue queue = Volley.newRequestQueue(ClientTripsActivity.this);
            queue.add(fetchTripRequest);



        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
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
        parse_json_trip_company pj = new parse_json_trip_company(json);
        pj.parseJSON();

        custom_row_for_trip_company cr = new custom_row_for_trip_company(this, parse_json_trip_company.trip_id,parse_json_trip_company.trip_destination,
                parse_json_trip_company.trip_pickup_location,parse_json_trip_company.trip_pick_time,parse_json_trip_company.trip_drop_time,
                parse_json_trip_company.trip_vehicle,parse_json_trip_company.trip_start_date,parse_json_trip_company.trip_end_date,
                parse_json_trip_company.trip_driver, parse_json_trip_company.trip_ac,parse_json_trip_company.user_f_name,parse_json_trip_company.user_l_name
                ,parse_json_trip_company.user_image,parse_json_trip_company.user_phone);

        TripListView.setAdapter(cr);
    }


    public class custom_row_for_trip_company extends ArrayAdapter<String> {

        private String[] trip_id;
        private String[] trip_destination;
        private String[] trip_pick_time;
        private String[] trip_drop_time;
        private String[] trip_pickup_location;
        private String[] trip_vehicle;
        private String[] trip_start_date;
        private String[] trip_end_date;
        private String[] trip_driver;
        private String[] trip_ac;
        private String[] user_f_name;
        private String[] user_l_name;
        private String[] user_image;
        private String[] user_phone;



        String trip_ID;


        private Activity context;

        public custom_row_for_trip_company(Activity context,String[] trip_id, String[] trip_destination, String[] trip_pickup_location,String[] trip_pick_time,
                                           String[] trip_drop_time,String[] trip_vehicle, String[] trip_start_date,String[] trip_end_date,String[] driver,
                                           String[] ac,String[] user_f_name,String[] user_l_name,String[] user_image,String[] user_phone)
        {
            super(context, R.layout.custom_row_for_trip,trip_id);
            this.context = context;

            this.trip_id=trip_id;
            this.trip_destination = trip_destination;
            this.trip_pickup_location = trip_pickup_location;
            this.trip_pick_time=trip_pick_time;
            this.trip_drop_time=trip_drop_time;
            this.trip_vehicle = trip_vehicle;
            this.trip_start_date = trip_start_date;
            this.trip_end_date = trip_end_date;
            this.trip_driver = driver;
            this.trip_ac = ac;
            this.user_f_name=user_f_name;
            this.user_l_name=user_l_name;
            this.user_image=user_image;
            this.user_phone=user_phone;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final LayoutInflater inflater = context.getLayoutInflater();
            final View listViewItem = inflater.inflate(R.layout.custom_row_for_trip, null, true);


            TextView Name = (TextView) listViewItem.findViewById(R.id.customer_name_to_rental);
            TextView Destination = (TextView) listViewItem.findViewById(R.id.customer_destination);
            TextView Pickup_Loc = (TextView) listViewItem.findViewById(R.id.customer_pickup_location_to_rental);
            TextView Vehicle = (TextView) listViewItem.findViewById(R.id.customer_vehicle_to_rental);
            TextView from_date = (TextView) listViewItem.findViewById(R.id.customer_start_date_to_rental);
            TextView to_date = (TextView) listViewItem.findViewById(R.id.customer_end_date_to_rental);
            TextView driver = (TextView) listViewItem.findViewById(R.id.customer_driver_to_rental);
            TextView ac = (TextView) listViewItem.findViewById(R.id.customer_AC_to_rental);
            TextView pick_time = (TextView) listViewItem.findViewById(R.id.customer_start_time_to_rental);
            TextView drop_time = (TextView) listViewItem.findViewById(R.id.customer_end_time_to_rental);
            TextView no_of_bids = (TextView) listViewItem.findViewById(R.id.no_of_bids);
            final CircleImageView imageView  = (CircleImageView) listViewItem.findViewById(R.id.customer_pic_to_rental);


            final LinearLayout bottom_tab=(LinearLayout) listViewItem.findViewById(R.id.company_trip_bottom_tab);

            // tab functanionailty
            LinearLayout rental_previous_bids_tab=(LinearLayout) listViewItem.findViewById(R.id.rental_previous_bids_tab);
            Button bid_now_btn=(Button) listViewItem.findViewById(R.id.rental_bid_now_btn);







            Name.setText(user_f_name[position]+""+user_l_name[position]);
            Destination.setText(trip_destination[position]);
            Pickup_Loc.setText(trip_pickup_location[position]);
            Vehicle.setText(trip_vehicle[position]);
            from_date.setText(trip_start_date[position]);
            to_date.setText(trip_end_date[position]);
            pick_time.setText(trip_pick_time[position]);
            drop_time.setText(trip_drop_time[position]);
            no_of_bids.setText("5");
            trip_ID=trip_id[position];


            if(trip_driver[position].equals("1"))
            {
                driver.setText("Yes");
            }
            else
            {
                driver.setText("No");
            }

            if(trip_ac[position].equals("1"))
            {
                ac.setText("Yes");
            }
            else
            {
                ac.setText("No");
            }


            Picasso.with(getApplicationContext())
                    .load("http://rixwanxharif.000webhostapp.com/" + user_image[position])
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(getApplicationContext())
                                    .load("http://rixwanxharif.000webhostapp.com/"+user_image[position])
                                    .into(imageView);
                        }
                    });






            rental_previous_bids_tab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);

                    //
                }
            });


            bid_now_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.2F);
                    buttonClick.setDuration(175);
                    v.startAnimation(buttonClick);

                    //show
                    LayoutInflater li = LayoutInflater.from(ClientTripsActivity.this);

                    //Creating a view to get the dialog box

                    View confirmDialog = li.inflate(R.layout.bid_now_dialogue, null);

                    //Initizliaing confirm button fo dialog box and edittext of dialog box

                    Button bachat_pack_buuton = (AppCompatButton) confirmDialog.findViewById(R.id.bachat_btn);
                    Button lamb_samb_pack_buuton = (AppCompatButton) confirmDialog.findViewById(R.id.lamb_samb_btn);

                    //Creating an alertdialog builder
                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ClientTripsActivity.this);

                    //Adding our dialog box to the view of alert dialog
                    alertDialogBuilder.setView(confirmDialog);
                    //Creating an alert dialog
                    final android.support.v7.app.AlertDialog buttonsDialog= alertDialogBuilder.create();

                    // show it
                    buttonsDialog.show();
                    buttonsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    buttonsDialog.setCanceledOnTouchOutside(false);
                    //

                    bachat_pack_buuton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            buttonsDialog.dismiss();
                            Intent intent = new Intent(ClientTripsActivity.this, BachatBidActivity.class);
                            intent.putExtra("trip_id", trip_id[position]);
                            intent.putExtra("user_phone",user_phone[position]);
                            startActivity(intent);

                        }
                    });

                    lamb_samb_pack_buuton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            buttonsDialog.dismiss();
                            Intent intent = new Intent(ClientTripsActivity.this, LambSambActivity.class);
                            intent.putExtra("trip_id", trip_id[position]);
                            intent.putExtra("user_phone",user_phone[position]);
                            startActivity(intent);

                        }
                    });

                }
            });



            return listViewItem ;
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Load_Trip();
    }

}



