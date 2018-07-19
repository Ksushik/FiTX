package com.brus5.lukaszkrawczak.fitx.DTO;

import android.util.Log;

public class DietDTO implements DtoStatus {
    private static final String TAG = "DietDTO";

    public int userID;
    public String userName;
    public String dateToday;
    public String updateKcalResult;


    public String productID;
    public String updateProductWeight;
    public String productTimeStamp;


    public String productWeight;




    public String productName;


    @Override
    public void printStatus() {
        Log.i(TAG, "\nprintStatus: " +
                "\n userID: " + userID +
                "\n productId: " + productID +
                "\n userName: " + userName +
                "\n updateProductWeight: " + updateProductWeight+
                "\n updateKcalResult: "+ updateKcalResult +
                "\n productTimeStamp: "+ productTimeStamp +
                "\n productName: "+ productName +
                "\n"


        );
    }
}
