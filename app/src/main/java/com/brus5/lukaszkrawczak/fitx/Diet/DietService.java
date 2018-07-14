package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietProductDeleteDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietProductInsertDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietProductWeightUpdateDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietSearchProductDTO;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietDeleteCountedKcalDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietUpdateKcalResultDTO;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

public class DietService {

    private static final String TAG = "DietService";

    public void DietUpdateCountedKcal(final DietUpdateKcalResultDTO dto, final Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_UPDATE_COUNTED_KCAL_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener()
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
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestApiNames.DB_UPDATE_RESULT, dto.updateKcalResult);
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_DATE, dto.dateToday);
                Log.e(TAG, "getParams: "+params );
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void DietDeleteCountedKcal(final DietDeleteCountedKcalDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_DELETE_COUNTED_KCAL_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response );
                    }
                },
                new Response.ErrorListener()
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
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_DATE, dto.dateToday);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void DietProductWeightUpdate(final DietProductWeightUpdateDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_UPDATE_WEIGHT_PRODUCT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response );
                    }
                },
                new Response.ErrorListener()
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
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USER_DIET_ID,                    dto.productId);
                params.put(RestApiNames.DB_USER_DIET_USERNAME,              dto.userName);
                params.put(RestApiNames.DB_PRODUCT_DIET_WEIGHT_UPDATE,      dto.updateProductWeight);
                params.put(RestApiNames.DB_USER_DIET_DATE,                  dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void DietProductInsert(final DietProductInsertDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_INSERT_PRODUCT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response );
                    }
                },
                new Response.ErrorListener()
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
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USER_DIET_ID,            dto.productId);
                params.put(RestApiNames.DB_USER_DIET_USERNAME,      dto.userName);
                params.put(RestApiNames.DB_PRODUCT_WEIGHT,          dto.productWeight);
                params.put(RestApiNames.DB_USER_DIET_DATE,          dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void DietDeleteProduct(final DietProductDeleteDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_DELETE_PRODUCT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response );
                    }
                },
                new Response.ErrorListener()
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
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USER_DIET_ID,            dto.productId);
                params.put(RestApiNames.DB_USER_DIET_USERNAME,      dto.userName);
                params.put(RestApiNames.DB_USER_WEIGHT,             dto.updateProductWeight);
                params.put(RestApiNames.DB_USER_DIET_DATE,          dto.productTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }


}
