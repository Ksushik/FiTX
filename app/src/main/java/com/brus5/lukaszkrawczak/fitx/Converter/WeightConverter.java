package com.brus5.lukaszkrawczak.fitx.Converter;

import android.annotation.SuppressLint;

public class WeightConverter extends Converter
{
    public WeightConverter(double weight, double productWeight)
    {
        super(weight,productWeight);
    }

    @Override
    public double convertWeight(double weight)
    {
        return super.convertWeight(weight);
    }

    public WeightConverter() {}

    @Override
    public String getConvertedWeight()
    {
        return super.getConvertedWeight();
    }

    @SuppressLint("DefaultLocale")
    public String countCalories(double proteins, double fats, double carbs)
    {
        double calories = (proteins * 4) + (fats * 9) + (carbs * 4);
        // Need to add this kind of conversion because Samsung devices got problems and shows double value with comma (2,9) not with dot (2.9).
        return String.format("%.1f",calories).replace(",",".");
    }
}
