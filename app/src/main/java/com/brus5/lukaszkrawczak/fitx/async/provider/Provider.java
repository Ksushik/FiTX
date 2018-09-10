package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.async.HTTPService;

public class Provider
{
    private static final String TAG = "Provider";
    private ListView listView;
    private Activity activity;
    private Context context;

    public Provider(Activity activity, Context context, ListView listView)
    {
        this.activity = activity;
        this.context = context;
        this.listView = listView;
    }

    public Provider(Activity activity, Context context)
    {
        this.activity = activity;
        this.context = context;
    }


    /**
     * This method loads proper class. It's must have to run out AsyncTask
     */
    public void load()
    {
        switch (context.getClass().getSimpleName())
        {
            case "MainActivity":
                new MainActivityProvider(activity, context, listView);
                break;
            case "DietActivity":
                new DietActivityProdiver(activity, context, listView);
                break;
            case "TrainingActivity":
                new TrainingActivityProvider(activity, context, listView);
                break;
            case "SettingsActivity":
                new SettingsActivityProvider(activity, context, listView);
                break;
        }
    }


    /**
     * This method load proper class with String param as one of communicators with HTTPService
     *
     * @param s param with will be passed to Rest Service
     */
    public void load(final String s)
    {
        switch (context.getClass().getSimpleName())
        {
            case "DietProductSearchActivity":
                new DietProductSearchActivityProvider(activity, context, listView, s);
                break;
            case "DietProductDetailsActivity":
                new DietProductDetailsActivityProvider(activity, context, listView, s);
                break;
            case "CardioDetailsActivity":
                new CardioDetailsActivityProvider(activity, context, listView, s);
                break;
            case "TrainingDetailsActivity":
                new TrainingDetailsActivityProvider(activity, context, s);
                break;
        }
    }

    public void load(final String s, final boolean isNew)
    {
        switch (context.getClass().getSimpleName())
        {
            case "TrainingDetailsActivity":
                new TrainingDetailsActivityProvider(activity, context, s, isNew);
                break;
        }
    }

    /**
     * This method load proper class with String param as one of communicators with HTTPService
     *
     * @param id        is id which you want to get informations of
     * @param timeStamp is trainingTimeStamp to get informations
     */
    public void load(final String id, final String timeStamp)
    {
        switch (context.getClass().getSimpleName())
        {
            case "CardioDetailsActivity":
                new CardioDetailsActivityProvider(activity, context, listView, timeStamp, id);
                break;
        }
    }

    /**
     * This method starts HTTP Service which witch AsyncTask
     *
     * @param link   is an private final static field in RestApi.class this is main link to server
     * @param params is string of parameters it's for example: "?product_id=4"
     */
    protected void startHTTPService(String link, String params)
    {
        HTTPService HTTPService = new HTTPService(activity, context, listView);
        HTTPService.execute(link, params);
    }


}



