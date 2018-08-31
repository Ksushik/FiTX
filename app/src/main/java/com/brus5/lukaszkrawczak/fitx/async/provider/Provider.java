package com.brus5.lukaszkrawczak.fitx.async.provider;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.async.HTTPService;

public class Provider
{
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



