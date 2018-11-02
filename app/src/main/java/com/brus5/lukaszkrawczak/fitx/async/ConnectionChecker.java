package com.brus5.lukaszkrawczak.fitx.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.ConnectionFailedActivity;
import com.brus5.lukaszkrawczak.fitx.R;

public class ConnectionChecker extends AsyncTask<Boolean, Boolean, Boolean>
{
    private static final String TAG = "ConnectionChecker";

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public ConnectionChecker(Context context)
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
            Log.i(TAG, "Connected to MySQL: " + aBoolean);
        }
        else
        {
            ((Activity)context).finish();

            Intent intent = new Intent(context,ConnectionFailedActivity.class);
            context.startActivity(intent);

            Toast.makeText(context, context.getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
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
