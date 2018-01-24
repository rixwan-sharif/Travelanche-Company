package com.example.rixwansharif.travelanch_company;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientTripDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_trip_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ad7ab5")));
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


        //Trip details
        TextView destination_text = (TextView) findViewById(R.id.trip_details_client_destination);
        TextView trip_vehicle_text = (TextView) findViewById(R.id.trip_details_client_vehicle_to_rental);
        TextView pick_loc_text = (TextView) findViewById(R.id.trip_details_client_pickup_location_to_rental);
        TextView driver_text = (TextView) findViewById(R.id.trip_details_client_driver_to_rental);
        TextView ac_text = (TextView) findViewById(R.id.trip_details_client_AC_to_rental);
        TextView date_from_text = (TextView)findViewById(R.id.trip_details_client_start_date_to_rental);
        TextView date_to_text = (TextView) findViewById(R.id.trip_details_client_end_date_to_rental);
        TextView pick_time_text = (TextView) findViewById(R.id.trip_details_client_pick_time_to_rental);
        TextView drop_time_text = (TextView) findViewById(R.id.trip_details_client_drop_time_to_rental);
        TextView no_of_bids = (TextView) findViewById(R.id.no_bids_on_client_trip);
        TextView date_text = (TextView) findViewById(R.id.trip_details_date);
        TextView time_text = (TextView) findViewById(R.id.trip_details_time);

        //getting intent data
        Bundle intent_data=getIntent().getExtras();
        destination_text.setText(intent_data.getString("trip_destination"));
        trip_vehicle_text.setText(intent_data.getString("trip_vehicle"));
        pick_loc_text.setText(intent_data.getString("trip_pickup_location"));
        driver_text.setText(intent_data.getString("trip_driver"));
        ac_text.setText(intent_data.getString("trip_ac"));
        date_from_text.setText(intent_data.getString("trip_start_date"));
        date_to_text.setText(intent_data.getString("trip_end_date"));
        pick_time_text.setText(intent_data.getString("trip_pick_time"));
        drop_time_text.setText(intent_data.getString("trip_drop_time"));
        no_of_bids.setText(intent_data.getString("bids_on_trip"));
        date_text.setText(compareDates(getDateTime(),intent_data.getString("trip_date"))?"Today":intent_data.getString("trip_date"));
        time_text.setText(intent_data.getString("trip_time"));

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private boolean compareDates(String date_1,String date_2) {
      if(date_1.equals(date_2))
          return true;
        else
          return false;
    }

}
