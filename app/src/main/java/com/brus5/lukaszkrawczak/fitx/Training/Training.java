package com.brus5.lukaszkrawczak.fitx.Training;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training extends ArrayList
{
    private int viewType; /* 1: for Gym; 2: for Cardio */
    private int id;
    private int done;
    private String name;
    private int restTime;
    private String weight;
    private String reps;
    private String timeStamp;
    private String target;
    private String time;
    private String kcalPerMin;

    public Training(int id, int done, String name, int restTime, String weight, String reps, String timeStamp, String target, int viewType)
    {
        this.id = id;
        this.done = done;
        this.name = name;
        this.restTime = restTime;
        this.weight = weight;
        this.reps = reps;
        this.timeStamp = timeStamp;
        this.target = target;
        this.viewType = viewType;
    }

    public Training(){}


    public Training(int id, int done, String name, String time, String timeStamp, String kcalPerMin, int viewType)
    {
        this.id = id;
        this.done = done;
        this.name = name;
        this.time = time;
        this.timeStamp = timeStamp;
        this.kcalPerMin = kcalPerMin;
        this.viewType = viewType;
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

    public int getViewType()
    {
        return viewType;
    }

    public String getTime()
    {
        return time;
    }

    public String getKcalPerMin()
    {
        return kcalPerMin;
    }
}
