package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_GET_INFORMATIONS;

public class DietProductDetailsActivityProvider extends Provider
{
    private static final String TAG = "DietProductSearchActivityProvider";

    // Link from server to HTTP connection
    private static final String URL = URL_DIET_PRODUCT_GET_INFORMATIONS;

    /**
     * This constructor preparing link which should be sended to
     * startHTTPService.
     * Example link:
     * http://justfitx.xyz/Diet/GetProductInformations.php?product_id=4
     * <p>
     * RestAPI.SERVER_URL SERVER_URL is private static final in RestApi.class
     *
     * @param context  context from current Activity
     * @param listView listView from current Activity
     */
    @SuppressLint("LongLogTag")
    public DietProductDetailsActivityProvider(Context context, ListView listView, String s)
    {
        super(context, listView);

        // Glueing link with variables
        final String params = "?product_id=" + s;

        Log.d(TAG, "DietProductDetailsActivityProvider: " + params);

        // Starting AsyncTask after completing up link+params
        // This method is in Provider.class
        startHTTPService(URL, params);
    }


    /**
     * This method start AsyncTask after completing link + params
     * This method is in Provider.class
     *
     * @param link   it should be whole link for example: http://justfitx.xyz/Diet/GetProductInformations.php
     * @param params it should named: ?product_id=4
     */
    @Override
    protected void startHTTPService(String link, String params)
    {
        super.startHTTPService(link, params);
    }
}