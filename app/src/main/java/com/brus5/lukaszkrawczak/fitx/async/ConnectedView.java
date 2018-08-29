package com.brus5.lukaszkrawczak.fitx.async;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ConnectedView extends AsyncTask<Boolean, Boolean, Boolean>
{
    private static final String TAG = "ConnectedView";

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public ConnectedView(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        if (aBoolean)
        {
            Toast.makeText(context, "Connected: " + aBoolean, Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Connected to MySQL: " + aBoolean);
        }
        else
        {
            Toast.makeText(context, "Connected: " + aBoolean, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Connected to MySQL: " + aBoolean);
        }
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans)
    {
        boolean isConnected;
        isConnected = new Connected().doInBackground();
        return isConnected;
    }
}
