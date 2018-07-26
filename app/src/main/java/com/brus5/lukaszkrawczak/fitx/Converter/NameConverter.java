package com.brus5.lukaszkrawczak.fitx.Converter;

public class NameConverter
{
    private String name;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
