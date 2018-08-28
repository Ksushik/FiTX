package com.brus5.lukaszkrawczak.fitx.Async.Protocol;

import android.app.Activity;
import android.content.Context;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Async.LoadData;

public class AsyncPreparator
{
    private ListView listView;
    private Activity activity;
    private Context context;

    public AsyncPreparator(Activity activity, Context context, ListView listView)
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
                new MainActivityAsyncPreparator(activity, context, listView);
                break;
            case "DietActivity":
                new DietActivityAsyncPreparator(activity, context, listView);
                break;
            case "TrainingActivity":
                new TrainingActivityAsyncPreparator(activity, context, listView);
                break;
        }
    }

    protected void startAsyncTask(String link, String params)
    {
        LoadData loadData = new LoadData(activity, context, listView);
        loadData.execute(link, params);
    }


}



