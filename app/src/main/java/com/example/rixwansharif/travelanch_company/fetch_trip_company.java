package com.example.rixwansharif.travelanch_company;

/**
 * Created by Rixwan Sharif on 10/24/2017.
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rixwan Sharif on 7/14/2017.
 */
public class fetch_trip_company extends StringRequest {

    private Map<String,String> params;



    public fetch_trip_company(Response.Listener<String> responseListener, Response.ErrorListener errorListener)
    {
        super(Method.POST, config.Fetch_Trip_URL,responseListener,errorListener);
        params=new HashMap<>();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


}