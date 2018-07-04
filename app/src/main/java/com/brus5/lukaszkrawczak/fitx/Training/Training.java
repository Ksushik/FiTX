package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Training {
    private int task_id;
    private String task_name;
    private int task_rest;
    private String task_weight;
    private String task_reps;

    public Training(int task_id, String task_name, int task_rest, String task_weight, String task_reps) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_rest = task_rest;
        this.task_weight = task_weight;
        this.task_reps = task_reps;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public int getTask_rest() {
        return task_rest;
    }

    public void setTask_rest(int task_rest) {
        this.task_rest = task_rest;
    }

    public String getTask_weight() {
        return task_weight;
    }

    public void setTask_weight(String task_weight) {
        this.task_weight = task_weight;
    }

    public String getTask_reps() {
        return task_reps;
    }

    public void setTask_reps(String task_reps) {
        this.task_reps = task_reps;
    }
}
