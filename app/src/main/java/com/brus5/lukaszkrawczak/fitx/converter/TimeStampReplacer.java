package com.brus5.lukaszkrawczak.fitx.converter;

public class TimeStampReplacer extends Converter
{

    public TimeStampReplacer(String dateToday, String oldTimeStamp)
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
