package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SecondService
{

    private static final String TAG = "SecondService";


    private Context context;

    public SecondService(Context context)
    {
        this.context = context;
    }

    public void post(final String LINK, final OnDataLoaded onDataLoaded)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, LINK, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
                onDataLoaded.onSuccess(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }
}
