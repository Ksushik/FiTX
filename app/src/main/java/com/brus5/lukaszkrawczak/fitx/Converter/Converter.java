package com.brus5.lukaszkrawczak.fitx.Converter;

import android.util.Log;

abstract class Converter {

    private static final String TAG = "Converter";

    Converter(String dateToday){
        Log.i(TAG, "Converter: has been activated " + dateToday);
    }

}
