package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.MainService;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS_SET_AUTO_CALORIES;

public class MySwitch
{
    private static final String TAG = "MySwitch";

    MySwitch(final Context context, final Switch aSwitch, final MySettingsList mySettingsList, final String db, final String calories_limit)
    {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                String id = String.valueOf(SaveSharedPreference.getUserID(context));

                switch (db)
                {
                    case "user_calories_limit_auto":
                    {
                        String auto_calories = b ? "1" : "0";

                        if (b) SaveSharedPreference.setAutoCalories(context, 1);
                        else SaveSharedPreference.setAutoCalories(context, 0);

                        Toast.makeText(context, String.valueOf(b), Toast.LENGTH_SHORT).show();

                        if (!b)
                        {
                            String mc1 = context.getResources().getString(R.string.calories_manual);
                            String mc3 = context.getResources().getString(R.string.calories_manual_long);
                            String mc4 = "user_calories_limit";
                            String mc6 = "Long text";
                            Settings mManual = new Settings(mc1, SaveSharedPreference.getLimitCalories(context), mc3, mc4, 1, mc6);

                            Log.d(TAG, "onCheckedChanged() called with: calories_limit = [" + calories_limit + "], b = [" + b + "]");

                            mySettingsList.add(4, mManual);
                            mySettingsList.load();
                        }
                        else
                        {
                            Log.d(TAG, "onCheckedChanged() called with: calories_limit = [" + calories_limit + "], b = [" + b + "]");

                            mySettingsList.remove(4);
                            mySettingsList.load();
                        }

                        final String link = URL_SETTINGS_SET_AUTO_CALORIES + "?id=" + id + "&auto_calories=" + auto_calories;

                        // Set Automatic Calories
                        new MainService(context).post(link);
                    }
                }

                if (b)
                {
                    Toast.makeText(context, db + " ON", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, db + " OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
