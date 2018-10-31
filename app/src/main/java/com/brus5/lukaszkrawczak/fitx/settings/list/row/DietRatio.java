package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class DietRatio extends MyRow
{
    public DietRatio(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);
        String name = context.getString(R.string.diet_ratio);
        String descriptionShort = context.getString(R.string.diet_ratio_description_short);
        String dbName = "user_diet_ratio";
        String descriptionLong = "Ustal stosunek makroskładników aby precyzyjniej określić zapotrzebowanie. Trafne oszacowanie pozwoli na szybsze osiągnięcie zamierzonych efektów. \n" +
                "\n" +
                "Pamiętaj do prawidłowego obliczenia, suma musi wynosić 100. Nie mniej, nie więcej.";

        super.name = name;
        super.value = value;
        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 4;
        super.descriptionLong = descriptionLong;

        setData();
    }
}
