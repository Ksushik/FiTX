package com.brus5.lukaszkrawczak.fitx.settings.list.row;

import android.content.Context;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.settings.list.MySettingsList;

public class DietGoal extends MyRow
{
    public DietGoal(Context context, MySettingsList mySettingsList, String value)
    {
        super(mySettingsList);

        String name = context.getString(R.string.settings_user_goal);
        String descriptionShort = context.getString(R.string.settings_user_goal_description_short);
        String dbName = "diet_goal";

        String s0 = "Zwiększanie masy";
        String s1 = "Balans";
        String s2 = "Redukcja";
        int valNum = 0;

        super.name = name;

        switch (value)
        {
            case "0":
                super.value = s0;
                valNum = 0;
                break;
            case "1":
                super.value = s1;
                valNum = 1;
                break;
            case "2":
                super.value = s2;
                valNum = 2;
                break;
        }

        super.descriptionShort = descriptionShort;
        super.dbName = dbName;
        super.viewType = 3;
        super.items = new String[]{s0,s1,s2};
        super.valNum = valNum;

        setItemsData();
    }
}
