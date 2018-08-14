package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

public class DietService
{

    private static final String TAG = "DietService";

    public void DietUpdateCountedKcal(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_UPDATE_COUNTED_KCAL, response -> Log.e(TAG, "onResponse: " + response), error -> {

        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID,          String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_UPDATE_RESULT,    dto.updateKcalResult);
                params.put(RestAPI.DB_DATE,             dto.dateToday);
                Log.e(TAG, "getParams: " + params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void DietDeleteCountedKcal(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_DELETE_COUNTED_KCAL, response -> Log.e(TAG, "onResponse: " + response), error -> {

        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID,  String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE,     dto.dateToday);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void DietProductWeightUpdate(final DietDTO dto, Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_UPDATE_WEIGHT_PRODUCT, response -> Log.e(TAG, "onResponse: " + response), error -> {

        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID,                 dto.productID);
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY,       String.valueOf(dto.userID));
                params.put(RestAPI.DB_PRODUCT_DIET_WEIGHT_UPDATE,   dto.updateProductWeight);
                params.put(RestAPI.DB_USER_DIET_DATE,               dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void DietProductInsert(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_INSERT_PRODUCT, response -> Log.e(TAG, "onResponse: " + response), error -> {

        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID,             dto.productID);
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY,   String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_PRODUCT_WEIGHT,           dto.productWeight);
                params.put(RestAPI.DB_USER_DIET_DATE,           dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void DietDeleteProduct(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_DELETE_PRODUCT, response -> Log.e(TAG, "onResponse: " + response), error -> {

        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID,             dto.productID);
                params.put(RestAPI.DB_USER_WEIGHT,              dto.updateProductWeight);
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY,   String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_USER_DIET_DATE,           dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
}
