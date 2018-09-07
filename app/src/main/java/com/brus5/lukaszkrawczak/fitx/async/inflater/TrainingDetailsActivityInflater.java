package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.diet.adapter.DietSearchListAdapter;
import com.brus5.lukaszkrawczak.fitx.training.CardioDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.training.Training;
import com.brus5.lukaszkrawczak.fitx.training.TrainingDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.StringConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_DONE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_NOTEPAD;

@SuppressLint({"LongLogTag", "Registered"})

public class TrainingDetailsActivityInflater extends TrainingDetailsActivity
{
    private static final String TAG = "TrainingDetailsActivityInflater";

    private Activity activity;
    private Context context;

    public TrainingDetailsActivityInflater(Activity activity, Context context, String response)
    {
        this.activity = activity;
        this.context = context;

        dataInflater(response);
    }

    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");

        try
        {
            JSONObject jsonObject = new JSONObject(s);
            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            int done = 0;
            int time = 0;
            String reps = "";
            String weight = "";
            String notepad = "";

            String exerciseName = "";
            String[] mReps_table = new String[]{};
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                exerciseName = object.getString(RestAPI.DB_EXERCISE_NAME);
            }


            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

            for (int i = 0; i < trainings_info.length(); i++)
            {
                JSONObject tr_info = trainings_info.getJSONObject(i);
                done = tr_info.getInt(RestAPI.DB_EXERCISE_DONE);
                time = tr_info.getInt(RestAPI.DB_EXERCISE_REST_TIME);

                // reps and weight variables contains RAW data from MySQL.
                // Each dot in this String represents 1 serie.
                // Example 12.10.8. <- last dot also represents serie
                reps = tr_info.getString(RestAPI.DB_EXERCISE_REPS);

                weight = tr_info.getString(RestAPI.DB_EXERCISE_WEIGHT);
                notepad = tr_info.getString(RestAPI.DB_EXERCISE_NOTEPAD);

                // This String removes dots from RAW String and preparing it
                // to being inserted to String[]
                String mReps = reps.replaceAll("\\p{Punct}", " ");

                // This table is separating each number
                mReps_table = mReps.split("\\s+");



//                inflater.setReps(reps);
//                inflater.setWeight(weight);

//                TrainingDetailsActivity.this.seriesGenerator(mReps_table.length);
            }


//            tvName.setText(StringConverter.toUpperFirstLetter(exerciseName));
//            TrainingDetailsActivity.this.onTrainingChangerListener(done);
//            etNotepad.setText(notepad);
//            timer.setSeekbarProgress(Integer.valueOf(rest));

            Training t = new Training.Builder()
                    .name(exerciseName)
                    .done(done)
                    .reps(reps)
                    .weight(weight)
                    .notepad(notepad)
                    .time(time)
                    .sets(mReps_table.length)
                    .build();

            load(activity,context, t);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

}
