package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class ManualCalories extends MyRow
{
    public ManualCalories(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getResources().getString(R.string.calories_manual);
        String descriptionShort = context.getResources().getString(R.string.calories_manual_long);
        String dbName = "user_calories_limit";
        String descriptionLong = "Samodzielnie ustal limit kalorii";

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 1;
        super.descriptionLong = descriptionLong;

        setData();
    }
}
