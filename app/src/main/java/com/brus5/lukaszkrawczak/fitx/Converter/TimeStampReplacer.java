package com.brus5.lukaszkrawczak.fitx.Converter;

public class TimeStampReplacer extends Converter {

    String oldTimeStamp;
    String newTimeStamp;
    String dateToday;



    public TimeStampReplacer(String dateToday, String oldTimeStamp) {
        super(dateToday);
        this.dateToday = dateToday;
        this.oldTimeStamp = oldTimeStamp;
    }



    private String getDayAndMonth(){
        return this.dateToday.substring(5,10);
    }

    private String getDayAndMonthFromTimeStamp(){
        return this.oldTimeStamp.substring(5,10);
    }

    public String getNewTimeStamp(){
        return this.oldTimeStamp.replace(getDayAndMonthFromTimeStamp(),getDayAndMonth());
    }





}
