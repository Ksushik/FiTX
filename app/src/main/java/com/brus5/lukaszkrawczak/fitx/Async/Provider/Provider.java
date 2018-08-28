package com.brus5.lukaszkrawczak.fitx.Async.Provider;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Async.HTTPService;

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

    protected void startHTTPService(String link, String params)
    {
        HTTPService HTTPService = new HTTPService(activity, context, listView);
        HTTPService.execute(link, params);
    }


}



