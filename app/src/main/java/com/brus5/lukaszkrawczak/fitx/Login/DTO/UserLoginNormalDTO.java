package com.brus5.lukaszkrawczak.fitx.Login.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DTO.DtoStatus;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class UserLoginNormalDTO implements DtoStatus{
    private static final String TAG = "UserLoginNormalDTO";
    public String userName;
    public String userPassword;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * userName: " + userName +
                " * userPassword: "+ userPassword +
                " *");
    }
}
