package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;
import com.jjoe64.graphview.GraphView;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_GRAPH_KCAL;

@SuppressLint("LongLogTag")
class StatsActivityProvider extends Provider
{
    private static final String TAG = "StatsActivityProvider";

    // Link from server to HTTP connection
    private static final String URL = URL_GRAPH_KCAL;

    /**
     * This constructor preparing SERVER_URL which should be sended to
     * startHTTPService.
     * Example SERVER_URL:
     * http://justfitx.xyz/Graph/GetKcalGraph.php?id=5
     *
     * @param activity activity from current Activity
     * @param context  context from current Activity
     * @param graphView graphView from current Activity
     */

    StatsActivityProvider(Activity activity, Context context, GraphView graphView)
    {
        super(activity, context, graphView);

        // Attributing proper information to variables
        int userID = SaveSharedPreference.getUserID(context);

        // Glueing SERVER_URL with variables
        String params = "?id=" + userID;

        Log.d(TAG, URL+params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing SERVER_URL + params
     * This method is in Provider.class
     *
     * @param link   it should be whole SERVER_URL for example: http://justfitx.xyz/Graph/GetKcalGraph.php
     * @param params it should named: ?id=5
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}
