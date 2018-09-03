package com.brus5.lukaszkrawczak.fitx.diet;

import com.brus5.lukaszkrawczak.fitx.converter.StringConverter;

import java.util.Locale;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Product
{
    private int id;
    private String name;
    private double weight;
    private double proteins;
    private double fats;
    private double carbs;
    private double kcal;
    private int verified;
    private String dateTimeStamp;

    private double saturatedFats;
    private double unSaturatedFats;
    private double carbsFiber;
    private double carbsSugar;
    private double multiplier;

    public Product(String name, double proteins, double fats, double carbs, double saturatedFats, double unSaturatedFats, double carbsFiber, double carbsSugar, double multiplier, int verified)
    {
        this.name = StringConverter.toUpperFirstLetter(name);
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.saturatedFats = saturatedFats;
        this.unSaturatedFats = unSaturatedFats;
        this.carbsFiber = carbsFiber;
        this.carbsSugar = carbsSugar;
        this.multiplier = multiplier;
        this.verified = verified;
    }

    public Product(int id, String name, double weight, double proteins, double fats, double carbs, double kcal, int verified, String dateTimeStamp)
    {
        this.id = id;
        this.name = StringConverter.toUpperFirstLetter(name);
        this.weight = weight;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.kcal = kcal;
        this.verified = verified;
        this.dateTimeStamp = dateTimeStamp;
    }

    public Product(int id, String name, double kcal, int verified)
    {
        this.id = id;
        this.name = name;
        this.kcal = kcal;
        this.verified = verified;
    }

    public Product() {}

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getWeight()
    {
        return weight;
    }

    public double getProteins()
    {
        return proteins;
    }

    public double getFats()
    {
        return fats;
    }

    public double getCarbs()
    {
        return carbs;
    }

    public double getKcal()
    {
        return kcal;
    }

    public int getVerified()
    {
        return verified;
    }

    public String getDateTimeStamp()
    {
        return dateTimeStamp;
    }

    public double getSaturatedFats()
    {
        return saturatedFats;
    }

    public double getUnSaturatedFats()
    {
        return unSaturatedFats;
    }

    public double getCarbsFiber()
    {
        return carbsFiber;
    }

    public double getCarbsSugar()
    {
        return carbsSugar;
    }

    public double getMultiplier()
    {
        return multiplier;
    }

    public double countCalories(double proteins, double fats, double carbs)
    {
        return (proteins * 4) + (fats * 9) + (carbs * 4);
    }
}

class Calories
{
    private double enteredWeight;
    private double weight;
    private double convertedWeight = -1; // this variable must have -1 as default value

    public Calories(double enteredWeight, double weight)
    {
        this.enteredWeight = enteredWeight;
        this.weight = weight;
    }

    /**
     * This method counting weight of calories of specific macronutrient
     *
     * @param calories is Calories method which contains 2 variables:
     *                 enteredWeight: which should be entered weight by user
     *                 weight: actual procut weight
     * @return convertedWeight as double
     */
    public double countCalories(Calories calories)
    {
        convertedWeight = calories.enteredWeight * (calories.weight * 0.01);
        return convertedWeight;
    }

    /**
     * This method converts convertedWeight to String.
     *
     * @return String value with 1 digit after decimal. For example: 12.4
     */
    @Override
    public String toString()
    {
        return String.format(Locale.getDefault(), "%.1f", convertedWeight);
    }


}