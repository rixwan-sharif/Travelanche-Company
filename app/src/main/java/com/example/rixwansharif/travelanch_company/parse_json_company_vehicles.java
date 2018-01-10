package com.example.rixwansharif.travelanch_company;

/**
 * Created by Rixwan Sharif on 9/23/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class parse_json_company_vehicles {

    public static String[] vehicle_name;

    private JSONArray company_vehicles = null;

    private String json;

    public parse_json_company_vehicles(String json)
    {
        this.json = json;
    }

    protected void parseJSON()

    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(json);
            company_vehicles = jsonObject.getJSONArray("company_vehicles");

            vehicle_name = new String[company_vehicles.length()];

            for (int i = 0; i < company_vehicles.length(); i++)
            {
                JSONObject jo = company_vehicles.getJSONObject(i);

                vehicle_name[i] = jo.getString("vehicle_name");
            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


}
