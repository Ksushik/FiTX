package com.brus5.lukaszkrawczak.fitx.Training;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training extends ArrayList{
    private int id;
    private int done;
    private String name;
    private int restTime;
    private String weight;
    private String reps;
    private String timeStamp;
    private String target;

    public Training(int id, int done, String name, int restTime, String weight, String reps, String timeStamp, String target) {
        this.id = id;
        this.done = done;
        this.name = name;
        this.restTime = restTime;
        this.weight = weight;
        this.reps = reps;
        this.timeStamp = timeStamp;
        this.target = target;
    }

    public int getId() {
        return id;
    }

    public int getDone() {
        return done;
    }

    public String getName() {
        return name;
    }

    public int getRestTime() {
        return restTime;
    }

    public String getWeight() {
        return weight;
    }

    public String getReps() {
        return reps;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTarget() {
        return target;
    }
}
