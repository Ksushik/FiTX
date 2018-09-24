package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

/**
 * This class is responsible for creating Triple Choose AlertDialog
 * one selected option.
 */
public class TripleAlertDialog
{
    private final String[] items = new String[]{"Zwiększanie masy", "Balans", "Redukcja"};
    private String goal;

    TripleAlertDialog(final Context context, Settings itemClicked)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(itemClicked.getName())
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(context, "which: " + which, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "pos: " + getGoal() + " accepted. Send data to DB", Toast.LENGTH_SHORT).show();
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
}

