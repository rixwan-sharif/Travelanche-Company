package com.example.rixwansharif.travelanch_company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rixwan Sharif on 12/9/2017.
 */
public class parse_json_my_accepted_bids {

    public static String[] id;

    // Trip
    public static String[] trip_destination;
    public static String[] trip_pick_time;
    public static String[] trip_drop_time;
    public static String[] trip_pickup_location;
    public static String[] trip_vehicle;
    public static String[] trip_start_date;
    public static String[] trip_end_date;
    public static String[] trip_driver;
    public static String[] trip_ac;

    //Bid
    public static String[] bid_rate_per_day;
    public static String[] bid_total_fare;
    public static String[] bids_on_trip;
    public static String[] bid_vehicle;
    public static String[] bid_vehicle_img;

    private JSONArray my_accepted_bids = null;

    private String json;

    public parse_json_my_accepted_bids(String json)
    {
        this.json = json;
    }



    protected void parseJSON()

    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(json);
            my_accepted_bids = jsonObject.getJSONArray("my_accepted_bids");

            id = new String[my_accepted_bids.length()];
            trip_destination =new String[my_accepted_bids.length()];
            trip_pickup_location = new String[my_accepted_bids.length()];
            trip_vehicle=new String[my_accepted_bids.length()];
            trip_start_date=new String[my_accepted_bids.length()];
            trip_end_date = new String[my_accepted_bids.length()];
            trip_pick_time =new String[my_accepted_bids.length()];
            trip_drop_time =new String[my_accepted_bids.length()];
            trip_driver = new String[my_accepted_bids.length()];
            trip_ac=new String[my_accepted_bids.length()];

            bid_rate_per_day = new String[my_accepted_bids.length()];
            bid_total_fare =new String[my_accepted_bids.length()];
            bid_vehicle =new String[my_accepted_bids.length()];
            bid_vehicle_img = new String[my_accepted_bids.length()];
            bids_on_trip=new String[my_accepted_bids.length()];

            for (int i = 0; i < my_accepted_bids.length(); i++)
            {
                JSONObject jo = my_accepted_bids.getJSONObject(i);


                id[i] = jo.getString("id");
                trip_destination[i] = jo.getString("dest");
                trip_pickup_location[i] = jo.getString("pick_loc");
                trip_vehicle[i]=jo.getString("trip_vehicle");
                trip_start_date[i]=jo.getString("st_date");
                trip_end_date[i] = jo.getString("end_date");
                trip_pick_time[i] = jo.getString("pick_time");
                trip_drop_time[i]=jo.getString("drop_time");
                trip_driver[i] = jo.getString("driver");
                trip_ac[i] = jo.getString("ac");

                bid_rate_per_day[i]=jo.getString("rate_per_day");
                bid_total_fare[i] = jo.getString("total_fare");
                bid_vehicle[i] = jo.getString("bid_vehicle");
                bid_vehicle_img[i]=jo.getString("vehicle_img");
                bids_on_trip[i] = jo.getString("no_bids");

            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
