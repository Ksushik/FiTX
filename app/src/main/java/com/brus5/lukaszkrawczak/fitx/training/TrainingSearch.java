package com.brus5.lukaszkrawczak.fitx.training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingSearch
{
    private int id;
    private String name;
    private double calories;

    public TrainingSearch(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public TrainingSearch(int id, String name, double calories)
    {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getCalories()
    {
        return calories;
    }
}
