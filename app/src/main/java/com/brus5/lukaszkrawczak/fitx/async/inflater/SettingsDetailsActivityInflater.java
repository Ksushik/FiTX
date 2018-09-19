package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.SettingsDetailsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"LongLogTag", "Registered"})

public class SettingsDetailsActivityInflater extends SettingsDetailsActivity
{
    private static final String TAG = "SettingsDetailsActivityInflater";

    private Context context;

    public SettingsDetailsActivityInflater(Context context, String response)
    {
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
            String val = arr.getJSONObject(0).getString("RESULT");

            load(context, val);

            Log.d(TAG, "dataInflater() called with: s = [" + val + "]");
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