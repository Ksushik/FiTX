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
import com.brus5.lukaszkrawczak.fitx.DTO.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietResetKcalFromGraphDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietSendCountedKcalDTO;

import java.util.HashMap;
import java.util.Map;

public class DietService {

    private static final String TAG = "DietService";



    public void DietSendCountedKcal(final DietSendCountedKcalDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_SEND_COUNTED_KCAL_URL,
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
                params.put(RestApiNames.DB_USER_ID, String.valueOf(dto.userId));
                params.put(RestApiNames.DB_UPDATE_RESULT, dto.updateKcalResult);
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_DATE, dto.dateToday);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void DietResetKcalFromGraph(final DietResetKcalFromGraphDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_RESET_KCAL_URL,
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


}
