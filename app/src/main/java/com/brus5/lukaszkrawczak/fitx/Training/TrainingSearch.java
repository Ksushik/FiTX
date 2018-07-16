package com.brus5.lukaszkrawczak.fitx.Training;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingSearch {
    private int id;
    private String name;
    private double kcal;
    private int verified;

    public TrainingSearch(int id, String name, double kcal, int verified) {
        this.id = id;
        this.name = name;
        this.kcal = kcal;
        this.verified = verified;
    }
    // FIXME: 16.07.2018 need to change everything
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }
}
