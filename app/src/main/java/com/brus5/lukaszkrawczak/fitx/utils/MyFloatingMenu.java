package com.brus5.lukaszkrawczak.fitx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.settings.list.SettingsActivity;
import com.brus5.lukaszkrawczak.fitx.stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.training.TrainingActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MyFloatingMenu
{
    private Context context;
    private FloatingActionMenu fem;

    public MyFloatingMenu(Context context)
    {
        this.context = context;

        fem = ((Activity) context).findViewById(R.id.menu);
        fem.addMenuButton(button("Dieta", R.drawable.ic_local_dining_white_24dp, DietActivity.class));
        fem.addMenuButton(button("Trening", R.drawable.ic_fitness_center_white_24dp, TrainingActivity.class));
        fem.addMenuButton(button("Ustawienia", R.drawable.ic_settings_white_24dp, SettingsActivity.class));
        fem.addMenuButton(button("Statystyki", R.drawable.ic_timeline_white_24dp, StatsActivity.class));
        fem.setAnimated(true);

    }

    private FloatingActionButton button(String name, int resID, final Class<?> classDestination)
    {
        FloatingActionButton b = new FloatingActionButton(context);
        b.setLabelText(name);
        b.setImageResource(resID);
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                runNextActivity(classDestination);
            }
        });
        return b;
    }

    public void remove()
    {

    }

    private void runNextActivity(Class<?> cls)
    {
        Intent intent = new Intent(this.context, cls);
        this.context.startActivity(intent);
    }

}
