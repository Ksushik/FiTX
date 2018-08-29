package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.dto.MainDTO;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;

class TrainingActivityProvider extends Provider
{
    private static final String TAG = "TrainingActivityProvide";

    // Creating new object of MainDTO
    private MainDTO dto = new MainDTO();

    // Link from server to HTTP connection
    private String link = "http://justfitx.xyz/Training/ShowByUserShort.php";

    /**
     * This constructor preparing link which should be sended to
     * startHTTPService.
     * Example link:
     * http://justfitx.xyz/Diet/ShowByUser.php?user_id=5&date=2018-08-28
     *
     * @param activity activity from current Activity
     * @param context  context from current Activity
     * @param listView listView from current Activity
     */
    TrainingActivityProvider(Activity activity, Context context, ListView listView)
    {
        super(activity, context, listView);

        // Attributing proper information to variables
        dto.userID = SaveSharedPreference.getUserID(context);
        dto.date = DateGenerator.getSelectedDate();

        // Glueing link with variables
        String params = "?user_id=" + dto.userID + "&date=" + dto.date;

        Log.d(TAG, "TrainingActivityProvider: " + params);

        // Starting AsyncTask after completing up link+params
        // This method is in Provider.class
        startHTTPService(link, params);
    }


    /**
     * This method start AsyncTask after completing link + params
     * This method is in Provider.class
     *
     * @param link   it should be whole link for example: http://justfitx.xyz/Training/ShowByUserShort.php
     * @param params it should named: ?user_id=5&date=2018-08-28
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}
