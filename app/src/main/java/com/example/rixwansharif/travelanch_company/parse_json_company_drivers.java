package com.example.rixwansharif.travelanch_company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rixwan Sharif on 1/8/2018.
 */
public class parse_json_company_drivers {

    public static String[] driver_name;

    private JSONArray company_drivers = null;

    private String json;

    public parse_json_company_drivers(String json)
    {
        this.json = json;
    }

    protected void parseJSON()

    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(json);
            company_drivers = jsonObject.getJSONArray("company_drivers");

            driver_name = new String[company_drivers.length()];

            for (int i = 0; i < company_drivers.length(); i++)
            {
                JSONObject jo = company_drivers.getJSONObject(i);

                driver_name[i] = jo.getString("driver_name");
            }
        }

        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
