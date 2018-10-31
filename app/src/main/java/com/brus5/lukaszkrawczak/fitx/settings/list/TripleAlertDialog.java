package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

/**
 * This class is responsible for creating Triple Choose AlertDialog
 * one selected option.
 */
public class TripleAlertDialog
{
    private final String[] items;
    private String goal;
    private int which;
    private String db;

    TripleAlertDialog(final Context context, Settings itemClicked, final String[] items, final OnDialogChangeListener onDialogChangeListener)
    {
        this.items = items;
        this.db = itemClicked.getDb();

        this.which = itemClicked.getValNum();

        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(itemClicked.getName())
                .setSingleChoiceItems(items, which, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        setWhich(which);
                        if (which == 0) setGoal(items[0]);
                        if (which == 1) setGoal(items[1]);
                        if (which == 2) setGoal(items[2]);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SaveSharedPreference.setDietGoal(context,getWhich());
                        onDialogChangeListener.onItemSelected(getWhich(),db);
                    }
                })
                .create();
        alertDialog.show();
    }

    private String getGoal()
    {
        if (goal == null || goal.equals("null")) return items[1];
        else return goal;
    }

    private void setGoal(String goal)
    {
        this.goal = goal;
    }

    public int getWhich()
    {
        return which;
    }

    public void setWhich(int which)
    {
        this.which = which;
    }
}

