package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.training.Training;
import com.brus5.lukaszkrawczak.fitx.training.TrainingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrainingActivityInflater
{
    //      http://justfitx.xyz/Training/ShowByUserShort.php
    private static final String TAG = "MainActivityInflater";

    private Activity activity;
    private Context context;
    private ListView listView;
    private Training training;
    private TrainingAdapter adapter;
    private ArrayList<Training> list = new ArrayList<>();

    public TrainingActivityInflater(Activity activity, Context context, ListView listView, String response)
    {
        this.activity = activity;
        this.context = context;
        this.listView = listView;

        dataInflater(response);

        training = new Training();
    }

    private void dataInflater(String s)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            JSONArray training_types = jsonObject.getJSONArray("training_types");

            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

            int id;
            String name;
            String target;

            int restTime;
            int done;
            String reps;
            String weight;
            String date;

            if (trainings_info.length() > 0)
            {
                for (int i = 0; i < trainings_info.length(); i++)
                {
                    JSONObject trainings_infoObj = trainings_info.getJSONObject(i);

                    restTime = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_REST_TIME);
                    done = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_DONE);
                    reps = trainings_infoObj.getString(RestAPI.DB_EXERCISE_REPS);
                    weight = trainings_infoObj.getString(RestAPI.DB_EXERCISE_WEIGHT);
                    date = trainings_infoObj.getString(RestAPI.DB_EXERCISE_DATE);


                    JSONObject server_responseObj = training_types.getJSONObject(i);

                    id = server_responseObj.getInt(RestAPI.DB_EXERCISE_ID);
                    name = server_responseObj.getString(RestAPI.DB_EXERCISE_NAME);
                    target = server_responseObj.getString(RestAPI.DB_EXERCISE_TARGET);

                    training = new Training(id, done, name, restTime, weight, reps, date, target, 1);
                    list.add(training);
                    adapter = new TrainingAdapter(context, R.layout.row_training_excercise, list);

                }


            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        try
        {
            JSONObject jsonObject = new JSONObject(s);
            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            JSONArray cardio_types = jsonObject.getJSONArray("cardio_types");

            JSONArray cardio_info = jsonObject.getJSONArray("cardio_info");

            int id;
            String name;
            String kcalPerMin;

            int done;
            String time;
            String date;

            if (cardio_info.length() > 0)
            {
                for (int i = 0; i < cardio_info.length(); i++)
                {
                    JSONObject cardio = cardio_info.getJSONObject(i);

                    done = cardio.getInt(RestAPI.DB_CARDIO_DONE);
                    time = cardio.getString(RestAPI.DB_CARDIO_TIME);
                    date = cardio.getString(RestAPI.DB_DATE);


                    JSONObject cardioType = cardio_types.getJSONObject(i);

                    id = cardioType.getInt(RestAPI.DB_CARDIO_ID);
                    name = cardioType.getString(RestAPI.DB_CARDIO_NAME);
                    kcalPerMin = cardioType.getString(RestAPI.DB_CARDIO_CALORIES);


                    training = new Training(id, done, name, time, date, kcalPerMin, 2);
                    list.add(training);
                    adapter = new TrainingAdapter(context, R.layout.row_training_excercise, list);
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        listView.setAdapter(adapter);
        listView.invalidate();
    }

}
