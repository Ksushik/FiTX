package com.brus5.lukaszkrawczak.fitx.dto;

public class DietDTO
{
    public int userID;
    public String userName;

    public String dateToday;
    public String productTimeStamp;

    public String productName;
    public int updateKcalResult;
    public int productID;
    public int updateProductWeight;
    public int productWeight;

    public DietDTO()
    {
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("DietDTO");
        builder.append("\n");
        builder.append("{");
        builder.append("\n");
        if (userID != 0)
        {
            builder.append("userID='");
            builder.append(userID);
            builder.append('\'');
            builder.append("\n");
        }
        if (userName != null)
        {
            builder.append("userName='");
            builder.append(userName);
            builder.append('\'');
            builder.append("\n");
        }
        if (dateToday != null)
        {
            builder.append("dateToday='");
            builder.append(dateToday);
            builder.append('\'');
            builder.append("\n");
        }
        if (updateKcalResult != 0)
        {
            builder.append("updateKcalResult='");
            builder.append(updateKcalResult);
            builder.append('\'');
            builder.append("\n");
        }
        if (updateProductWeight != 0)
        {
            builder.append("updateProductWeight='");
            builder.append(updateProductWeight);
            builder.append('\'');
            builder.append("\n");
        }
        if (productTimeStamp != null)
        {
            builder.append("productTimeStamp='");
            builder.append(productTimeStamp);
            builder.append('\'');
            builder.append("\n");
        }
        if (productWeight != 0)
        {
            builder.append("productWeight='");
            builder.append(productWeight);
            builder.append('\'');
            builder.append("\n");
        }
        if (productName != null)
        {
            builder.append("productName='");
            builder.append(productName);
            builder.append('\'');
            builder.append("\n");
        }
        builder.append("}");

        return builder.toString();
    }

}
