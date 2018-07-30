package com.brus5.lukaszkrawczak.fitx;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Main extends ArrayList
{
    private int id;
    private int done;
    private String name;
    private int restTime;
    private String weight;
    private String reps;
    private String timeStamp;
    private String target;
    private int kcal;
    private int kcalLimit;



    public Main(int kcal, int kcalLimit)
    {
        this.kcal = kcal;
        this.kcalLimit = kcalLimit;
    }

    public int getKcal() {
        return kcal;
    }

    public int getKcalLimit() {
        return kcalLimit;
    }

    public int getId()
    {
        return id;
    }

    public int getDone()
    {
        return done;
    }

    public String getName()
    {
        return name;
    }

    public int getRestTime()
    {
        return restTime;
    }

    public String getWeight()
    {
        return weight;
    }

    public String getReps()
    {
        return reps;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }

    public String getTarget()
    {
        return target;
    }
}
