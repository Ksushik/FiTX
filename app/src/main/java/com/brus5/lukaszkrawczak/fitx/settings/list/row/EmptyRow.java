package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class Weight extends MyRow
{
    public Weight(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getResources().getString(R.string.body_mass);
        String descriptionShort = context.getResources().getString(R.string.body_mass_descripition_short);
        String dbName = "user_weight";
        String descriptionLong = context.getResources().getString(R.string.body_mass_descripition_long);

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 1;
        super.descriptionLong = descriptionLong;

        setData();
    }
}
