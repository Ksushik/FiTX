package com.brus5.lukaszkrawczak.fitx.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.brus5.lukaszkrawczak.fitx.R;

public class ActivityView
{
    private Activity activity;
    private Context context;
    private AppCompatActivity appCompatActivity;

    public ActivityView(Activity activity, Context context, AppCompatActivity appCompatActivity)
    {
        this.activity = activity;
        this.context = context;
        this.appCompatActivity = appCompatActivity;
    }

    /**
     * This method chaning statusbar color on whole application
     *
     * @param resID we need to pass Resources ID from status bar
     */
    public void statusBarColor(int resID)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark/*colorYellow*/));
        }
        Toolbar toolbar = activity.findViewById(resID);
        appCompatActivity.setSupportActionBar(toolbar);
    }

    /**
     * Chaning statusbar color with specific color
     *
     * @param resID pass ResID from status bar
     * @param color pass color from R.color.
     */
    public void statusBarColor(int resID, int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, color));
        }
        Toolbar toolbar = activity.findViewById(resID);
        appCompatActivity.setSupportActionBar(toolbar);
    }

    /**
     * This method showing up back button on the left side of status bar
     * we can set parent Activity name with following code in Manifest.xml:
     * android:parentActivityName=".YourActivity"
     * After button pressed it will pause actual Activity
     * and go back to parent Activity
     */
    public void showBackButton()
    {
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
