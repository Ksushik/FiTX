package com.brus5.lukaszkrawczak.fitx;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class Main extends ArrayList
{

    private int textViewBig;
    private int textViewSmall;

    private int viewType; /* 1: for Diet; 2: for Training */

    public Main(){}


    public Main(int textViewBig, int textViewSmall, int viewType)
    {
        this.textViewBig = textViewBig;
        this.textViewSmall = textViewSmall;
        this.viewType = viewType;
    }

    public int getTextViewBig()
    {
        return textViewBig;
    }

    public void setTextViewBig(int textViewBig)
    {
        this.textViewBig = textViewBig;
    }

    public int getTextViewSmall()
    {
        return textViewSmall;
    }

    public void setTextViewSmall(int textViewSmall)
    {
        this.textViewSmall = textViewSmall;
    }

    public void setViewType(int viewType)
    {
        this.viewType = viewType;
    }

    public int getViewType()
    {
        return viewType;
    }
}
