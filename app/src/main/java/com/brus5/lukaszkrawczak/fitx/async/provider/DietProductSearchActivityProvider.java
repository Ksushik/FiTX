package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

public class DietProductSearchActivityProvider extends Provider
{
    private static final String TAG = "DietProductSearchActivityProvider";

    // Link from server to HTTP connection
    private final String link = "http://justfitx.xyz/Diet/ProductsSearch.php";

    /**
     * This constructor preparing link which should be sended to
     * startHTTPService.
     * Example link:
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

        // Glueing link with variables
        final String params = "?name=" + searchName;

        Log.d(TAG, "DietActivityProdiver: " + params);

        // Starting AsyncTask after completing up link+params
        // This method is in Provider.class
        startHTTPService(link, params);
    }


    /**
     * This method start AsyncTask after completing link + params
     * This method is in Provider.class
     *
     * @param link   it should be whole link for example: http://justfitx.xyz/Diet/ProductsSearch.php
     * @param params it should named: ?name=ziemniaki
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}