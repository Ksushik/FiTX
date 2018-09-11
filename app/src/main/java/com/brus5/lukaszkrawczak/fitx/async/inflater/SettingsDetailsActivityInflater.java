package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SettingsDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.training.Training;
import com.brus5.lukaszkrawczak.fitx.training.TrainingDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"LongLogTag", "Registered"})

public class SettingsDetailsActivityInflater extends SettingsDetailsActivity
{
    private static final String TAG = "SettingsDetailsActivityInflater";
    private String val;
    private Activity activity;
    private Context context;

    public SettingsDetailsActivityInflater(Activity activity, Context context, String response)
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
            JSONObject obj = new JSONObject(s);
            JSONArray arr = obj.getJSONArray("server_response");
            val = arr.getJSONObject(0).getString("RESULT");
            load(activity,context,arr.getJSONObject(0).getString("RESULT"));
        }
        catch(JSONException e)
        {
            Log.e(TAG, "dataInflater: ",e);
        }
    }


}
/*
*
*
{
    "server_response": [
        {
            "RESULT": "181"
        }
    ]
}
*
*
* */