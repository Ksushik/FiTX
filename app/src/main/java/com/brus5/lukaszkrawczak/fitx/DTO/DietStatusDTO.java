package com.brus5.lukaszkrawczak.fitx.DTO;

import android.util.Log;

public abstract class DietStatusDTO
{
    private static final String TAG = "DietStatusDTO";
    private int userID;
    private String userName;
    private String dateToday;
    private String updateKcalResult;
    private String productID;
    private String updateProductWeight;
    private String productTimeStamp;
    private String productWeight;
    private String productName;

    public DietStatusDTO()
    {
    }

    public DietStatusDTO(int userID, String userName, String dateToday, String updateKcalResult, String productID, String updateProductWeight, String productTimeStamp, String productWeight, String productName)
    {
        this.userID = userID;
        this.userName = userName;
        this.dateToday = dateToday;
        this.updateKcalResult = updateKcalResult;
        this.productID = productID;
        this.updateProductWeight = updateProductWeight;
        this.productTimeStamp = productTimeStamp;
        this.productWeight = productWeight;
        this.productName = productName;

        if (this.userID != 0)
        {
            Log.i(TAG, "userID: " + this.userID);
        }
        if (this.userName != null)
        {
            Log.i(TAG, "userName: " + this.userName);
        }
        if (this.dateToday != null)
        {
            Log.i(TAG, "dateToday: " + this.dateToday);
        }
        if (this.updateKcalResult != null)
        {
            Log.i(TAG, "updateKcalResult: " + this.updateKcalResult);
        }
        if (this.productID != null)
        {
            Log.i(TAG, "productID: " + this.productID);
        }
        if (this.updateProductWeight != null)
        {
            Log.i(TAG, "updateProductWeight: " + this.updateProductWeight);
        }
        if (this.productTimeStamp != null)
        {
            Log.i(TAG, "productTimeStamp: " + this.productTimeStamp);
        }
        if (this.productWeight != null)
        {
            Log.i(TAG, "productWeight: " + this.productWeight);
        }
        if (this.productName != null)
        {
            Log.i(TAG, "productName: " + this.productName);
        }


    }


}
