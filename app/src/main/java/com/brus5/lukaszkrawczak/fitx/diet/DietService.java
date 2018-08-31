package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.dto.DietDTO;

import java.util.HashMap;
import java.util.Map;

public class DietService
{

    private static final String TAG = "DietService";

    public void updateCalories(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_UPDATE_COUNTED_KCAL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_UPDATE_RESULT, String.valueOf(dto.updateKcalResult));
                params.put(RestAPI.DB_DATE, dto.dateToday);
                Log.e(TAG, "getParams: " + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void deleteCalories(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_DELETE_COUNTED_KCAL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE, dto.dateToday);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void updateProductWeight(final DietDTO dto, Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_UPDATE_WEIGHT_PRODUCT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID, String.valueOf(dto.productID));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(dto.userID));
                params.put(RestAPI.DB_PRODUCT_DIET_WEIGHT_UPDATE, String.valueOf(dto.updateProductWeight));
                params.put(RestAPI.DB_USER_DIET_DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void insert(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_INSERT_PRODUCT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID, String.valueOf(dto.productID));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_PRODUCT_WEIGHT, String.valueOf(dto.productWeight));
                params.put(RestAPI.DB_USER_DIET_DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void delete(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_DELETE_PRODUCT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID, String.valueOf(dto.productID));
                params.put(RestAPI.DB_USER_WEIGHT, String.valueOf(dto.updateProductWeight));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_USER_DIET_DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
}
