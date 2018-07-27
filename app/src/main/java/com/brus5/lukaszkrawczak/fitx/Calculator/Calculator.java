package com.brus5.lukaszkrawczak.fitx.Calculator;

import android.util.Log;

public abstract class Calculator
{

    private static final String TAG = "Calculator";
    private double proteins;
    private double fats;
    private double carbs;
    private double kcal;

    Calculator() {}

    public int proteinsGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult * valueRatio * 0.01) / 4;
        Log.i(TAG, "proteinsGoal() called " + (int) result + " of proteins");
        return (int) result;
    }

    public int fatsGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult * valueRatio * 0.01) / 9;
        Log.i(TAG, "fatsGoal() called " + (int) result + " of fats");
        return (int) result;
    }

    public int carbsGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult * valueRatio * 0.01) / 4;
        Log.i(TAG, "carbsGoal() called " + (int) result + " of carbs");
        return (int) result;
    }

    public double setProteins(double value, double weight)
    {
        double result = value * (weight / 100);
        Log.i(TAG, "setProteins() called " + (int) result + " of proteins");
        this.proteins = result;
        return result;
    }

    public double setFats(double value, double weight)
    {
        double result = value * (weight / 100);
        Log.i(TAG, "setFats() called " + (int) result + " of fats");
        this.fats = result;
        return result;
    }

    public double setCarbs(double value, double weight)
    {
        double result = value * (weight / 100);
        Log.i(TAG, "setCarbs() called " + (int) result + " of carbs");
        this.carbs = result;
        return result;
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

    public double setCalories(double value, double weight)
    {
        double result = value * weight * 0.01;
        Log.i(TAG, "setCalories() called " + (int) result + " of kcal");
        this.kcal = result;
        return result;
    }

    public double getKcal()
    {
        return kcal;
    }
}