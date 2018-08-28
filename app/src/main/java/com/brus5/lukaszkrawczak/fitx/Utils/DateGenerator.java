package com.brus5.lukaszkrawczak.fitx.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class DateGenerator
{
    private static final String TAG = "DateGenerator";
    private static String date;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private SimpleDateFormat dateFormatView = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());



    public static String getDate()
    {
        Log.i(TAG, "getDate: " + DateGenerator.date);
        return date;
    }

    public static void setDate(String date)
    {
        DateGenerator.date = date;
        Log.i(TAG, "setDate: " + DateGenerator.date);
    }


    public SimpleDateFormat getDateFormat()
    {
        return dateFormat;
    }

    public SimpleDateFormat getDateFormatView()
    {
        return dateFormatView;
    }

    public Calendar calendarPast()
    {
        Calendar oldest = Calendar.getInstance();
        oldest.add(Calendar.MONTH, 1);
        return oldest;
    }

    public Calendar calendarFuture()
    {
        Calendar newest = Calendar.getInstance();
        newest.add(Calendar.MONTH, -1);
        return newest;
    }

    public void showError(Context context)
    {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
    }

    public Date selectedDate(String date)
    {
        Calendar calendar = Calendar.getInstance();
        try
        {
            return getDateFormat().parse(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public void changeStatusBarColor(Activity activity, Context context, int resID, AppCompatActivity appCompatActivity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }

        Toolbar toolbar = activity.findViewById(resID);
        appCompatActivity.setSupportActionBar(toolbar);
    }

}