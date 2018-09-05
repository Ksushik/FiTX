package com.brus5.lukaszkrawczak.fitx.diet;

import com.brus5.lukaszkrawczak.fitx.utils.StringConverter;

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

    private Product(Builder builder)
    {
        this.id = builder.id;
        this.name = builder.name;
        this.weight = builder.weight;
        this.proteins = builder.proteins;
        this.fats = builder.fats;
        this.carbs = builder.carbs;
        this.kcal = builder.kcal;
        this.verified = builder.verified;
        this.dateTimeStamp = builder.dateTimeStamp;
        this.saturatedFats = builder.saturatedFats;
        this.saturatedFats = builder.saturatedFats;
        this.unSaturatedFats = builder.unSaturatedFats;
        this.carbsFiber = builder.carbsFiber;
        this.carbsSugar = builder.carbsSugar;
        this.multiplier = builder.multiplier;
    }

    public static class Builder
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

        public Builder id(int id)
        {
            this.id = id;
            return this;
        }

        public Builder name(String name)
        {
            this.name = StringConverter.toUpperFirstLetter(name);
            return this;
        }

        public Builder weight(double weight)
        {
            this.weight = weight;
            return this;
        }

        public Builder proteins(double proteins)
        {
            this.proteins = proteins;
            return this;
        }

        public Builder fats(double fats)
        {
            this.fats = fats;
            return this;
        }

        public Builder carbs(double carbs)
        {
            this.carbs = carbs;
            return this;
        }

        public Builder kcal(double kcal)
        {
            this.kcal = kcal;
            return this;
        }

        public Builder verified(int verified)
        {
            this.verified = verified;
            return this;
        }

        public Builder dateTimeStamp(String dateTimeStamp)
        {
            this.dateTimeStamp = dateTimeStamp;
            return this;
        }

        public Builder saturatedFats(double saturatedFats)
        {
            this.saturatedFats = saturatedFats;
            return this;
        }

        public Builder unSaturatedFats(double unSaturatedFats)
        {
            this.unSaturatedFats = unSaturatedFats;
            return this;
        }

        public Builder carbsFiber(double carbsFiber)
        {
            this.carbsFiber = carbsFiber;
            return this;
        }

        public Builder carbsSugar(double carbsSugar)
        {
            this.carbsSugar = carbsSugar;
            return this;
        }

        public Builder multiplier(double multiplier)
        {
            this.multiplier = multiplier;
            return this;
        }

        public Product build()
        {
            return new Product(this);
        }
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