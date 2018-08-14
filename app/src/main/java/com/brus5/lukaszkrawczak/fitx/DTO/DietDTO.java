package com.brus5.lukaszkrawczak.fitx.DTO;

public class DietDTO extends DietStatusDTO implements DtoStatus
{
    public int userID;
    public String userName;
    public String dateToday;
    public String updateKcalResult;
    public String productID;
    public String updateProductWeight;
    public String productTimeStamp;
    public String productWeight;
    public String productName;

    public DietDTO(int userID, String userName, String dateToday, String updateKcalResult, String productID, String updateProductWeight, String productTimeStamp, String productWeight, String productName)
    {
        super(userID, userName, dateToday, updateKcalResult, productID, updateProductWeight, productTimeStamp, productWeight, productName);
    }

    public DietDTO()
    {
    }

    @Override
    public void printStatus()
    {
        new DietDTO(userID, userName, dateToday, updateKcalResult, productID, updateProductWeight, productTimeStamp, productWeight, productName);
    }
}
