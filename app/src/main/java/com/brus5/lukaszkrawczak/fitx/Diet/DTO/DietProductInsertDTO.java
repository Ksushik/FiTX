package com.brus5.lukaszkrawczak.fitx.Diet.DTO;


import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class DietProductInsertDTO implements DtoStatus {
    private static final String TAG = "DietProductInsertDTO";
    public String userName;
    public String productId;
    public String productTimeStamp;
    public String productWeight;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * productId: " + productId +
                " * userName: " + userName +
                " * productTimeStamp: "+ productTimeStamp +
                " * productWeight: " + productWeight+
                " *");
    }
}
