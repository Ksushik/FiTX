package com.brus5.lukaszkrawczak.fitx.training;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.dto.TrainingDTO;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

public class TrainingService
{
    public void CardioInsert(final TrainingDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_INSERT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

            }
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
                params.put(RestAPI.DB_CARDIO_ID, String.valueOf(dto.getTrainingID()));
                params.put(RestAPI.DB_CARDIO_DONE, String.valueOf(dto.getTrainingDone()));
                params.put(RestAPI.DB_CARDIO_TIME, String.valueOf(dto.getTrainingTime()));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_CARDIO_NOTEPAD, dto.getTrainingNotepad());
                params.put(RestAPI.DB_CARDIO_DATE, dto.getTrainingTimeStamp());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void CardioUpdate(final TrainingDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_UPDATE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

            }
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
                params.put(RestAPI.DB_CARDIO_ID, String.valueOf(dto.getTrainingID()));
                params.put(RestAPI.DB_CARDIO_DONE, String.valueOf(dto.getTrainingDone()));
                params.put(RestAPI.DB_CARDIO_TIME, String.valueOf(dto.getTrainingTime()));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_CARDIO_NOTEPAD, dto.getTrainingNotepad());
                params.put(RestAPI.DB_CARDIO_DATE, dto.getTrainingTimeStamp());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void CaardioDelete(final TrainingDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_DELETE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

            }
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
                params.put(RestAPI.DB_CARDIO_ID, String.valueOf(dto.getTrainingID()));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_CARDIO_DATE, dto.getTrainingTimeStamp());
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
}


