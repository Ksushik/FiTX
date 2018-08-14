package com.brus5.lukaszkrawczak.fitx;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Main extends ArrayList
{

    private double textViewBig;
    private int textViewSmall;
    private int viewType; /* 1: for Diet; 2: for Training; 3: for Cardio; 4: for UserWeight */

    private String weight;
    private String reps;
    private String rest;

    public Main(){}

    public Main(double textViewBig, int textViewSmall, int viewType)
    {
        this.textViewBig = textViewBig;
        this.textViewSmall = textViewSmall;
        this.viewType = viewType;
    }

    public Main(String rest, String reps, String weight, int viewType)
    {
        this.rest = rest;
        this.reps = reps;
        this.weight = weight;
        this.viewType = viewType;
    }

    public double getTextViewBig()
    {
        return textViewBig;
    }

    public int getTextViewSmall()
    {
        return textViewSmall;
    }

    public int getViewType()
    {
        return viewType;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

    public String getReps()
    {
        return reps;
    }

    public String getRest()
    {
        return rest;
    }
}
