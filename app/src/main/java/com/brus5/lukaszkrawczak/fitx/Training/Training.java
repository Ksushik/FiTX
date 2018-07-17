package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training {
    private int id;
    private String name;
    private int restTime;
    private String weight;
    private String reps;
    private String dateTimeStamp;

    public Training(int id, String name, int restTime, String weight, String reps, String dateTimeStamp) {
        this.id = id;
        this.name = name;
        this.restTime = restTime;
        this.weight = weight;
        this.reps = reps;
        this.dateTimeStamp = dateTimeStamp;
    }

    public int getId() {
        return id;
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

    public String getDateTimeStamp() {
        return dateTimeStamp;
    }
}
