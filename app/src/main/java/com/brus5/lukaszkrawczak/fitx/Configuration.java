package com.brus5.lukaszkrawczak.fitx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class Configuration {

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormatView = new SimpleDateFormat("dd MMMM yyyy");

    public SimpleDateFormat getDateFormatView() {
        return dateFormatView;
    }

    public Calendar oldestDay() {
        Calendar oldest = Calendar.getInstance();
        oldest.add(Calendar.MONTH, 1);
        return oldest;
    }

    public Calendar newestDay() {
        Calendar newest = Calendar.getInstance();
        newest.add(Calendar.MONTH, -1);
        return newest;
    }

    public void showError(Context context) {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
    }
}
