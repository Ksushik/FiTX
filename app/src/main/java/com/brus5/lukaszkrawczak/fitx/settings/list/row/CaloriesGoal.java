package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class CaloriesGoal extends MyRow
{
    public CaloriesGoal(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getString(R.string.settings_user_goal);
        String descriptionShort = context.getString(R.string.settings_user_goal_description_short);
        String dbName = "user_goals";

        super.name = name;

        switch (value)
        {
            case "0":
                super.value = "Zwiększanie masy";
                break;
            case "1":
                super.value = "Balans";
                break;
            case "2":
                super.value = "Redukcja";
                break;
        }

        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 3;

        setData();
    }
}
