package com.brus5.lukaszkrawczak.fitx.Diet;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Diet {
    private int id;
    private String name;
    private double weight;
    private double proteins;
    private double fats;
    private double carbs;
    private double kcal;
    private String image;

    public Diet(int id, String name, double weight, double proteins, double fats, double carbs, double kcal, String image) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.proteins = proteins;
        this.fats = fats;
        this.carbs = carbs;
        this.kcal = kcal;
        this.image = image;
    }

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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
