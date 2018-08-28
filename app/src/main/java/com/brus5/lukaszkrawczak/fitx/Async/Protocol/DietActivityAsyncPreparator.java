package com.brus5.lukaszkrawczak.fitx.Async.Protocol;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.DTO.MainDTO;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.Utils.DateGenerator;

class DietActivityAsyncPreparator extends AsyncPreparator
{
    private static final String TAG = "AsyncPreparatorDietActi";

    // Creating new object of MainDTO
    private MainDTO dto = new MainDTO();

    // Link from server to HTTP connection
    private String link = "http://justfitx.xyz/Diet/ShowByUser.php";

    /**
     * This constructor preparing link which should be sended to
     * startAsyncTask.
     * Example link:
     * http://justfitx.xyz/Diet/ShowByUser.php?username=brus5&date=2018-08-27&user_id=5
     *
     * @param activity activity from current Activity
     * @param context  context from current Activity
     * @param listView listView from current Activity
     */
    DietActivityAsyncPreparator(Activity activity, Context context, ListView listView)
    {
        super(activity, context, listView);

        // Attributing proper information to variables
        dto.userName = SaveSharedPreference.getUserName(context);
        dto.userID = SaveSharedPreference.getUserID(context);
        dto.date = DateGenerator.getDate();

        // Glueing link with variables
        String params = "?username=" + dto.userName + "&date=" + dto.date + "&user_id=" + dto.userID;

        Log.d(TAG, "DietActivityAsyncPreparator: " + params);

        // Starting AsyncTask after completing up link+params
        // This method is in AsyncPreparator.class
        startAsyncTask(link, params);
    }


    /**
     * This method start AsyncTask after completing link + params
     * This method is in AsyncPreparator.class
     *
     * @param link   it should be whole link for example: http://justfitx.xyz/Diet/ShowByUser.php
     * @param params it should named: ?username=brus5&date=2018-08-27&user_id=5
     */
    @Override
    protected void startAsyncTask(String link, String params)
    {
        super.startAsyncTask(link, params);
    }
}