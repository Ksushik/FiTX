package com.brus5.lukaszkrawczak.fitx.Diet.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class DietUpdateKcalResultDTO implements DtoStatus {
    private static final String TAG = "DietUpdateKcalResultDTO";
    public int userId;
    public String userName;
    public String updateKcalResult;
    public String dateToday;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * userId: " + userId +
                " * userName: " + userName +
                " * updateKcalResult: " + updateKcalResult +
                " * dateToday: "+ dateToday +
                " *");
    }
}
