package com.brus5.lukaszkrawczak.fitx.converter;

import android.util.Log;

import java.util.Locale;

abstract class Converter
{
    private static final String TAG = "Converter";

    private static final int KG_ONE_TONE = 1000;

    private String dateToday;
    private String oldTimeStamp;
    private String covertedWeight;
    private double weight;
    private double productWeight;

    Converter(String dateToday, String oldTimeStamp)
    {
        this.dateToday = dateToday;
        this.oldTimeStamp = oldTimeStamp;
        Log.i(TAG, "Converter, dateToday: " + dateToday + " oldTimeStamp: " + oldTimeStamp);

        StringBuilder builder = new StringBuilder(TAG);
        builder.append("\n");
        builder.append("dateToday: ");
        builder.append(dateToday);
        builder.append("\n");
        builder.append("oldTimeStamp: ");
        builder.append(oldTimeStamp);
        Log.i(TAG, "Converter: " + builder);
    }

    public double convertWeight(double weight)
    {

        double toneConverter;

        if (weight < KG_ONE_TONE)
        {
            return weight;
        }
        else
        {
            toneConverter = weight / KG_ONE_TONE;
            return toneConverter;
        }
    }



    private void setProductGrams(double productWeight)
    {
        double mProductProteins = productWeight * (this.weight * 0.01);
        this.covertedWeight = String.format(Locale.getDefault(), "%.1f", mProductProteins).replace(",", ".");
    }

    public String getConvertedWeight()
    {
        return this.covertedWeight;
    }

}
