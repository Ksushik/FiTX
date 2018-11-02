package com.brus5.lukaszkrawczak.fitx.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.ConnectionFailedActivity;

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
            ((Activity)context).finish();

            Intent intent = new Intent(context,ConnectionFailedActivity.class);
            context.startActivity(intent);

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
