package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;
import com.brus5.lukaszkrawczak.fitx.settings.list.Settings;

abstract class MyRow
{
    protected MySettingsList mySettingsList;

    protected String name;
    protected String value;
    protected String descriptionShort;
    protected String dbName;
    protected int viewType;
    protected String descriptionLong;


    MyRow(MySettingsList mySettingsList)
    {
        this.mySettingsList = mySettingsList;
    }

    void setData()
    {
        Settings settings = new Settings(name, value, descriptionShort, dbName, viewType, descriptionLong);
        mySettingsList.add(settings);
        mySettingsList.load();
    }
}