package com.brus5.lukaszkrawczak.fitx.converter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
     */
    public String getNewTimeStamp()
    {
        if (oldTimeStamp == null || dateToday == null)
        {
            dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            oldTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        return this.oldTimeStamp.replace(oldTimeStamp.substring(5, 10), dateToday.substring(5, 10));
    }
}
