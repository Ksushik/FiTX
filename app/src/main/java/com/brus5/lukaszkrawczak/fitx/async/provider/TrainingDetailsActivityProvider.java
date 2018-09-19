package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_SHOW;

class TrainingDetailsActivityProvider extends Provider
{
    private static final String TAG = "TrainingDetailsActivityProvider";

    // Link from server to HTTP connection
    private static final String URL = URL_TRAINING_SHOW;

    /**
     * This constructor preparing SERVER_URL which should be sended to
     * startHTTPService.
     * Example SERVER_URL:
     * http://justfitx.xyz/Training/ShowByUser.php?user_id=5&date=2018-09-07&id=1
     * @param activity activity from current Activity
     * @param context  context from current Activity
     */

    @SuppressLint("LongLogTag")
    TrainingDetailsActivityProvider(Context context, String trainingID)
    {
        super(context);

        // Attributing proper information to variables
        int userID = SaveSharedPreference.getUserID(context);
        String date = DateGenerator.getSelectedDate();

        // Glueing SERVER_URL with variables
        String params = "?user_id=" + userID + "&date=" + date + "&id=" + trainingID;

        Log.d(TAG, "TrainingActivityProvider: " + params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    @SuppressLint("LongLogTag")
    TrainingDetailsActivityProvider(Context context, String trainingID, boolean isNew)
    {
        super(context);

        // Glueing SERVER_URL with variables
        String params = "?id=" + trainingID;

        Log.d(TAG, "TrainingActivityProvider: " + params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing SERVER_URL + params
     * This method is in Provider.class
     *
     * @param link   it should be whole SERVER_URL for example: hhttp://justfitx.xyz/Training/ShowByUser.php
     * @param params it should named: ?user_id=5&date=2018-09-07&id=1
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}
