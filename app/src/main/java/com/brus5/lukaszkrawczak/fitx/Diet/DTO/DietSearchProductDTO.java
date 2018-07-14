package com.brus5.lukaszkrawczak.fitx.Diet.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class DietSearchProductDTO implements DtoStatus{
    private static final String TAG = "DietSearchProductDTO";
    public String name;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * name: " + name +
                " *");
    }
}