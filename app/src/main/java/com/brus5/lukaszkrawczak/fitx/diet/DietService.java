package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DietService
{

    private static final String TAG = "DietService";

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String WEIGHT = "weight";
    public static final String DATE = "date";

    public static final String UPDATE_WEIGHT = "updateweight";

    private Context context;

    public DietService(Context context)
    {
        this.context = context;
    }

    public void post(final HashMap params, final String LINK)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LINK, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
