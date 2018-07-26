package com.brus5.lukaszkrawczak.fitx.Calculator;

import android.util.Log;

public abstract class Calculator
{

    private static final String TAG = "Calculator";

    Calculator() {}

    public int countProteinGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult*valueRatio*0.01)/4;
        Log.i(TAG, "countProteinGoal() called " + (int) result + " of proteins");
        return (int) result;
    }

    public int countFatsGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult*valueRatio*0.01)/9;
        Log.i(TAG, "countFatsGoal() called " + (int) result + " of fats");
        return (int) result;
    }

    public int countCarbsGoal(double kcalResult, double valueRatio)
    {
        double result = (kcalResult*valueRatio*0.01)/4;
        Log.i(TAG, "countCarbsGoal() called " + (int) result + " of carbs");
        return (int) result;
    }

}
