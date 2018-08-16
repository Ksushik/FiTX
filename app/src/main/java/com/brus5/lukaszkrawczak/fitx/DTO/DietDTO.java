package com.brus5.lukaszkrawczak.fitx.DTO;

public class DietDTO
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
        if (updateKcalResult != null)
        {
            builder.append("updateKcalResult='");
            builder.append(updateKcalResult);
            builder.append('\'');
            builder.append("\n");
        }
        if (updateProductWeight != null)
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
        if (productWeight != null)
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
