package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class EmptyRow extends MyRow
{
    public EmptyRow(Context context, MySettingsList mySettingsList)
    {
        super(mySettingsList);

        String name = "";
        String descriptionShort = "";
        String dbName = "";
        String descriptionLong = "";

        super.name = name;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 2; // as empty row
        super.descriptionLong = descriptionLong;

        setData();
    }
}
