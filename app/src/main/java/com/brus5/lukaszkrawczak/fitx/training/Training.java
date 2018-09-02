package com.brus5.lukaszkrawczak.fitx.training;

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
    private int time;
    private String kcalPerMin;
    private double kcal;


    private Training(Builder builder)
    {
        this.viewType = builder.viewType;
        this.id = builder.id;
        this.done = builder.done;
        this.name = builder.name;
        this.restTime = builder.restTime;
        this.weight = builder.weight;
        this.reps = builder.reps;
        this.timeStamp = builder.timeStamp;
        this.target = builder.target;
        this.time = builder.time;
        this.kcalPerMin = builder.kcalPerMin;
        this.kcal = builder.kcal;
    }

    @Override
    public String toString()
    {
        return "Training{" + "viewType=" + viewType + ", id=" + id + ", done=" + done + ", name='" + name + '\'' + ", restTime=" + restTime + ", weight='" + weight + '\'' + ", reps='" + reps + '\'' + ", timeStamp='" + timeStamp + '\'' + ", target='" + target + '\'' + ", time=" + time + ", kcalPerMin='" + kcalPerMin + '\'' + ", kcal=" + kcal + '}';
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

    public int getTime()
    {
        return time;
    }

    public String getKcalPerMin()
    {
        return kcalPerMin;
    }

    public double getKcal()
    {
        return kcal;
    }

    public static class Builder
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
        private int time;
        private String kcalPerMin;
        private double kcal;

        public Builder viewType(int viewType)
        {
            this.viewType = viewType;
            return this;
        }

        public Builder id(int id)
        {
            this.id = id;
            return this;
        }

        public Builder done(int done)
        {
            this.done = done;
            return this;
        }

        public Builder name(String name)
        {
            this.name = name;
            return this;
        }

        public Builder weight(String weight)
        {
            this.weight = weight;
            return this;
        }

        public Builder reps(String reps)
        {
            this.reps = reps;
            return this;
        }

        public Builder timeStamp(String timeStamp)
        {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder target(String target)
        {
            this.target = target;
            return this;
        }

        public Builder time(int time)
        {
            this.time = time;
            return this;
        }

        public Builder kcalPerMin(String kcalPerMin)
        {
            this.kcalPerMin = kcalPerMin;
            return this;
        }

        public Builder kcal(double kcal)
        {
            this.kcal = kcal;
            return this;
        }

        public Training build()
        {
            return new Training(this);
        }

    }
}
