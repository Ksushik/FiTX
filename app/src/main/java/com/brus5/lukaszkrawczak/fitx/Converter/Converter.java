package com.brus5.lukaszkrawczak.fitx.Converter;

import android.util.Log;

abstract class Converter {

    private String dateToday;
    private String oldTimeStamp;
    private static final String TAG = "Converter";

    Converter(String dateToday, String oldTimeStamp){
        this.dateToday = dateToday;
        this.oldTimeStamp = oldTimeStamp;
        Log.i(TAG, "Converter: has been activated " + dateToday + " oldTimeStamp: " + oldTimeStamp);
    }

    private String getDayAndMonth(){
        return this.dateToday.substring(5,10);
    }

    private String getDayAndMonthFromTimeStamp(){
        return this.oldTimeStamp.substring(5,10);
    }

    public String getNewTimeStamp(){
        return this.oldTimeStamp.replace(getDayAndMonthFromTimeStamp(),getDayAndMonth());
    }

}
