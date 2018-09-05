package com.brus5.lukaszkrawczak.fitx.converter;

public class TimeStamp
{
    private String dateToday;
    private String oldTimeStamp;

    public TimeStamp(String dateToday, String oldTimeStamp)
    {
        this.dateToday = dateToday;
        this.oldTimeStamp = oldTimeStamp;
    }

    /**
     * This method is responsible for changing TimeStamp.
     * If for example user choosed not today date, this method must
     *
     * @return
     * @throws NullPointerException
     */
    public String getNewTimeStamp() throws NullPointerException
    {
        return this.oldTimeStamp.replace(oldTimeStamp.substring(5, 10), dateToday.substring(5, 10));
    }
}
