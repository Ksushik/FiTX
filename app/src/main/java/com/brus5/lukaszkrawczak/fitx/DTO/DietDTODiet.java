package com.brus5.lukaszkrawczak.fitx.DTO;

public class DietDTODiet extends DietStatusDTO implements DtoStatus {
    private static final String TAG = "DietDTODiet";

    public int userID;
    public String userName;
    public String dateToday;
    public String updateKcalResult;
    public String productID;
    public String updateProductWeight;
    public String productTimeStamp;
    public String productWeight;
    public String productName;

    public DietDTODiet(int userID, String userName, String dateToday, String updateKcalResult, String productID, String updateProductWeight, String productTimeStamp, String productWeight, String productName) {
        super(userID, userName, dateToday, updateKcalResult, productID, updateProductWeight, productTimeStamp, productWeight, productName);
    }

    public DietDTODiet() {
    }

    @Override
    public void printStatus() {
        new DietDTODiet(userID,userName,dateToday,updateKcalResult,productID,updateProductWeight,productTimeStamp,productWeight,productName);
    }
}
