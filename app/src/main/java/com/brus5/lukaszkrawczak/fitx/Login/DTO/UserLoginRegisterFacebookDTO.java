package com.brus5.lukaszkrawczak.fitx.Login.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class UserLoginRegisterFacebookDTO implements DtoStatus {
    private static final String TAG = "UserLoginRegisterFacebo";
    public String userFirstName;
    public String userName;
    public String userBirthday;
    public String userPassword;
    public String userGender;
    public String userEmail;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * userFirstName: " + userFirstName +
                " * userName: "+ userName +
                " * userBirthday: "+ userBirthday +
                " * userPassword: "+ userPassword +
                " * userGender: "+ userGender +
                " * userEmail: "+ userEmail +
                " *");
    }
}
