package com.brus5.lukaszkrawczak.fitx.Converter;

import android.util.Log;

import java.util.Locale;

abstract class Converter
{
    private static final String TAG = "Converter";
    private String dateToday;
    private String oldTimeStamp;
    private String covertedWeight;
    private double weight;
    private double productWeight;

    Converter(String dateToday, String oldTimeStamp)
    {
        this.dateToday = dateToday;
        this.oldTimeStamp = oldTimeStamp;
        Log.i(TAG, "Converter: has been activated " + dateToday + " oldTimeStamp: " + oldTimeStamp);
    }

    public Converter() {}

    private String getDayAndMonth()
    {
        return this.dateToday.substring(5,10);
    }

    private String getDayAndMonthFromTimeStamp()
    {
        return this.oldTimeStamp.substring(5,10);
    }

    public String getNewTimeStamp()
    {
        return this.oldTimeStamp.replace(getDayAndMonthFromTimeStamp(),getDayAndMonth());
    }

    Converter(double weight, double productWeight)
    {
        this.weight = weight;
        this.productWeight = productWeight;
        setProductGrams(productWeight);
    }

    private void setProductGrams(double productWeight)
    {
        double mProductProteins = productWeight*(this.weight*0.01);
        this.covertedWeight = String.format(Locale.getDefault(),"%.1f",mProductProteins).replace(",",".");
    }

    public String getConvertedWeight()
    {
        return this.covertedWeight;
    }

}
