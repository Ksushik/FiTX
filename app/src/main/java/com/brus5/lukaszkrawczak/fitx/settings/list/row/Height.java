package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class Height extends MyRow
{
    public Height(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getString(R.string.body_height);
        String descriptionShort = context.getString(R.string.body_height_description_short);
        String dbName = "user_height";
        String descriptionLong = context.getString(R.string.body_height_description_long);

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 1;
        super.descriptionLong = descriptionLong;

        setData();
    }
}
