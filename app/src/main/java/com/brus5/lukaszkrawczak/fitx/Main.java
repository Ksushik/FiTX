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
    private String weight;
    private String timeStamp;
    private int reps;
    private int restTime;
    private int kcal;
    private int kcalLimit;
    private int lifted;

    public Main(){}


    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcalLimit(int kcalLimit) {
        this.kcalLimit = kcalLimit;
    }

    public int getKcalLimit()
    {
        return kcalLimit;
    }

    public void setReps(int reps)
    {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public void setRestTime(int restTime)
    {
        this.restTime = restTime;
    }

    public int getRestTime()
    {
        return restTime;
    }

    public void setLifted(int lifted)
    {
        this.lifted = lifted;
    }

    public int getLifted() {
        return lifted;
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

    public String getWeight()
    {
        return weight;
    }

    public String getTimeStamp()
    {
        return timeStamp;
    }


}
