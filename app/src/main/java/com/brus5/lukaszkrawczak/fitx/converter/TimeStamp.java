package com.brus5.lukaszkrawczak.fitx.converter;

public class TimeStamp extends Converter
{

    public TimeStamp(String dateToday, String oldTimeStamp)
    {
        super(dateToday, oldTimeStamp);
    }

    @Override
    public String getNewTimeStamp()
    {
        return super.getNewTimeStamp();
    }

    public String toString()
    {
        return "timeStamp replaced with value: " + super.getNewTimeStamp();
    }

}
