package com.brus5.lukaszkrawczak.fitx.Login.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class GetUserInfoDTO implements DtoStatus{
    private static final String TAG = "GetUserInfoDTO";
    public String userName;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * userName: " + userName +
                " *");
    }
}
