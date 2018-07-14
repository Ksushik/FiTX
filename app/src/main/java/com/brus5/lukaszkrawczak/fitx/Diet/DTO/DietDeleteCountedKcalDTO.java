package com.brus5.lukaszkrawczak.fitx.Diet.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class DietDeleteCountedKcalDTO implements DtoStatus{
    private static final String TAG = "DietDeleteCountedKcalDT";
    public String userName;
    public String dateToday;


    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * userName: " + userName +
                " * dateToday: " + dateToday +
                " *");
    }
}