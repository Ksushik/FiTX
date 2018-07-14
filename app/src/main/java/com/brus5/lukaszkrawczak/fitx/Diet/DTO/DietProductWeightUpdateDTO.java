package com.brus5.lukaszkrawczak.fitx.Diet.DTO;

import android.util.Log;
import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class DietProductWeightUpdateDTO implements DtoStatus{
    private static final String TAG = "DietProductWeightUpdate";
    public String productId;
    public String userName;
    public String updateProductWeight;
    public String productTimeStamp;

    @Override
    public void printStatus() {
        Log.i(TAG, "printStatus: " +
                " * productId: " + productId +
                " * userName: " + userName +
                " * updateProductWeight: " + updateProductWeight +
                " * productTimeStamp: "+ productTimeStamp +
                " *");
    }
}
