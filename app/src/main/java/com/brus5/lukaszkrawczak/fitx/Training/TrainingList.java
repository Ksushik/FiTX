package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.brus5.lukaszkrawczak.fitx.R;

public class TrainingList extends View
{
    private static final String TAG = "TrainingList";

    private int resId;
    Context mContext;
    @SuppressLint("ResourceType")
    private String chest;

    public TrainingList(Context context)
    {
        super(context);
        this.mContext = context;
        chest = this.mContext.getString(R.string.body_chest);
    }

    public void setResId(int resId)
    {
        this.resId = resId;
    }

    String getResourceName()
    {
        Log.i(TAG, "getResourceName: " + chest);
        String excerciseName = "";
        switch (resId)
        {
            case R.id.textViewButtonChest:
                excerciseName = "chest";
                break;
            case R.id.textViewButtonAbs:
                excerciseName = "abs";
                break;
            case R.id.textViewButtonQuads:
                excerciseName = "quads";
                break;
            case R.id.textViewButtonShoulders:
                excerciseName = "shoulders";
                break;
            case R.id.textViewButtonTraps:
                excerciseName = "traps";
                break;
            case R.id.textViewButtonLats:
                excerciseName = "lats";
                break;
            case R.id.textViewButtonGlutes:
                excerciseName = "glutes";
                break;
            case R.id.textViewButtonTriceps:
                excerciseName = "triceps";
                break;
            case R.id.textViewButtonHamstrings:
                excerciseName = "hamstrings";
                break;
            case R.id.textViewButtonCalves:
                excerciseName = "calves";
                break;
            case R.id.textViewButtonBiceps:
                excerciseName = "biceps";
                break;
            case R.id.textViewButtonForearms:
                excerciseName = "forearms";
                break;
        }
        return excerciseName;
    }


}
