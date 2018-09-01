package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.dto.MainDTO;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_SHOW_SHORT;

class TrainingActivityProvider extends Provider
{
    private static final String TAG = "TrainingActivityProvide";

    // Creating new object of MainDTO
    private MainDTO dto = new MainDTO();

    // Link from server to HTTP connection
    private static final String URL = URL_TRAINING_SHOW_SHORT;

    /**
     * This constructor preparing SERVER_URL which should be sended to
     * startHTTPService.
     * Example SERVER_URL:
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

        // Glueing SERVER_URL with variables
        String params = "?user_id=" + dto.userID + "&date=" + dto.date;

        Log.d(TAG, "TrainingActivityProvider: " + params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing SERVER_URL + params
     * This method is in Provider.class
     *
     * @param link   it should be whole SERVER_URL for example: http://justfitx.xyz/Training/ShowByUserShort.php
     * @param params it should named: ?user_id=5&date=2018-08-28
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}
