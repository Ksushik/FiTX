package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.DTO.TrainingDTO;

import java.util.HashMap;
import java.util.Map;

public class TrainingService {
    public void TrainingInsert(final TrainingDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.TRAINING_INSERT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

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
                params.put(RestApiNames.DB_EXERCISE_ID,             dto.trainingID);
                params.put(RestApiNames.DB_EXERCISE_DONE,           dto.trainingDone);
                params.put(RestApiNames.DB_EXERCISE_REST_TIME,      dto.trainingRestTime);
                params.put(RestApiNames.DB_EXERCISE_REPS,           dto.trainingReps);
                params.put(RestApiNames.DB_EXERCISE_WEIGHT,         dto.trainingWeight);
                params.put(RestApiNames.DB_USERNAME,                dto.userName);
                params.put(RestApiNames.DB_EXERCISE_DATE,           dto.trainingTimeStamp);
                params.put(RestApiNames.DB_EXERCISE_NOTEPAD,        dto.trainingNotepad);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void TrainingDelete(final TrainingDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.TRAINING_DELETE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

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
                params.put(RestApiNames.DB_EXERCISE_ID,             dto.trainingID);
                params.put(RestApiNames.DB_USERNAME,                dto.userName);
                params.put(RestApiNames.DB_EXERCISE_DATE,           dto.trainingTimeStamp);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
    public void TrainingUpdate(final TrainingDTO dto, Context context){

        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.TRAINING_UPDATE,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

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
                params.put(RestApiNames.DB_EXERCISE_ID,             dto.trainingID);
                params.put(RestApiNames.DB_EXERCISE_DONE,           dto.trainingDone);
                params.put(RestApiNames.DB_EXERCISE_REST_TIME,      dto.trainingRestTime);
                params.put(RestApiNames.DB_EXERCISE_REPS,           dto.trainingReps);
                params.put(RestApiNames.DB_EXERCISE_WEIGHT,         dto.trainingWeight);
                params.put(RestApiNames.DB_USERNAME,                dto.userName);
                params.put(RestApiNames.DB_EXERCISE_DATE,           dto.trainingTimeStamp);
                params.put(RestApiNames.DB_EXERCISE_NOTEPAD,        dto.trainingNotepad);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
}


