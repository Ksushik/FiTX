package com.brus5.lukaszkrawczak.fitx;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class MainTraining extends ArrayList
{

    private int reps;

    private int lifted;

    public MainTraining(int reps, int lifted)
    {
        this.reps = reps;
        this.lifted = lifted;
    }

    public MainTraining(){}

    public int getReps()
    {
        return reps;
    }

    public int getLifted()
    {
        return lifted;
    }

    public void setReps(int reps)
    {
        this.reps = reps;
    }

    public void setLifted(int lifted)
    {
        this.lifted = lifted;
    }
}
