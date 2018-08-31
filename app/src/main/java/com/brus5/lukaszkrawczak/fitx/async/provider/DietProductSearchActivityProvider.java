package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;

public class DietProductSearchActivityProvider extends Provider
{
    private static final String TAG = "DietProductSearchActivityProvider";

    // Link from server to HTTP connection
    private static final String URL = RestAPI.SERVER_URL + "Diet/ProductsSearch.php";

    /**
     * This constructor preparing SERVER_URL which should be sended to
     * startHTTPService.
     * Example SERVER_URL:
     * http://justfitx.xyz/Diet/ProductsSearch.php?name=ziemniaki
     *
     * @param activity activity from current Activity
     * @param context  context from current Activity
     * @param listView listView from current Activity
     */
    @SuppressLint("LongLogTag")
    public DietProductSearchActivityProvider(Activity activity, Context context, ListView listView, String searchName)
    {
        super(activity, context, listView);

        // Glueing SERVER_URL with variables
        final String params = "?name=" + searchName;

        Log.d(TAG, "DietActivityProdiver: " + params);

        // Starting AsyncTask after completing up SERVER_URL+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing SERVER_URL + params
     * This method is in Provider.class
     *
     * @param link   it should be whole SERVER_URL for example: http://justfitx.xyz/Diet/ProductsSearch.php
     * @param params it should named: ?name=ziemniaki
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}