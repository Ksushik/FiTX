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
    private int which;
    private String db;

    private OnDialogChangeListener onDialogChangeListener;
    private OnDialogSelectedListener onDialogSelectedListener;

    TripleAlertDialog(final Context context, Settings itemClicked, final String[] items)
    {
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
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SaveSharedPreference.setDietGoal(context,getWhich());
                        onDialogChangeListener.onItemSelected(getWhich(),db);
                        onDialogSelectedListener.onChanged(String.valueOf(getWhich()));
                    }
                })
                .create();
        alertDialog.show();
    }

    void setOnDialogChangeListener(OnDialogChangeListener onDialogChangeListener)
    {
        this.onDialogChangeListener = onDialogChangeListener;
    }

    void setOnDialogSelectedListener(OnDialogSelectedListener onDialogSelectedListener)
    {
        this.onDialogSelectedListener = onDialogSelectedListener;
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

