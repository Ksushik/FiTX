package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS_GET;

@SuppressLint("LongLogTag")
class SettingsDetailsActivityProvider extends Provider
{
    private static final String TAG = "SettingsDetailsActivityProvider";

    // Link from server to HTTP connection
    private static final String URL = URL_SETTINGS_GET;

    /**
     * This constructor preparing SERVER_URL which should be sended to
     * startHTTPService.
     * Example SERVER_URL:
     * http://justfitx.xyz/Settings/GetLast.php?id=5&table=user_height
     *
     * @param context  context from current Activity
     */

    SettingsDetailsActivityProvider(Context context, String db_table)
    {
        super(context);

        // Attributing proper information to variables
        int userID = SaveSharedPreference.getUserID(context);

        // Glueing SERVER_URL with variables
        String params = "?id=" + userID + "&table=" + db_table;

        Log.d(TAG, "params: " + params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing SERVER_URL + params
     * This method is in Provider.class
     *
     * @param link   it should be whole SERVER_URL for example: http://justfitx.xyz/Settings/GetLast.php
     * @param params it should named: ?id=5&table=user_height
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}
