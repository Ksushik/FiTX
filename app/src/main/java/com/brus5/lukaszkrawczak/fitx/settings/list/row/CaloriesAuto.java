package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class CaloriesAuto extends MyRow
{
    public CaloriesAuto(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getString(R.string.calories_automatic);
        String descriptionShort = context.getString(R.string.calories_automatic_long);
        String dbName = "user_calories_limit_auto";

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 2;

        setData();
    }
}
