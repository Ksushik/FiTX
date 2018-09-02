package com.brus5.lukaszkrawczak.fitx.training.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

public class TimerCardio extends Timer
{
    private static final String TAG = "TimerCardio";
    private Activity activity;
    private TextView tvBurned;
    private double burned; // burning calories per 1 min

    public TimerCardio(Activity activity, Context context)
    {
        super(activity, context);

        this.activity = activity;

        tvBurned = this.activity.findViewById(R.id.textViewBurned);
    }

    public void seekBarTimer()
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                setTimer(progress);
                setBurned(burned);
                updateCountDownText();
                resetTimer();
                Log.d(TAG, "onProgressChanged() called with: seekBar \n" + "burned: " + burned + " START_TIME_IN_MILLIS: " + START_TIME_IN_MILLIS);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void setBurned(double value)
    {
        this.burned = value;
        double iBurned = value * (START_TIME_IN_MILLIS / 1000 / 60);
        tvBurned.setText(String.valueOf((int) iBurned));
        Log.d(TAG, "setBurned() called with: value = [" + value + "]" + " iBurned: " + iBurned + " this.burned: " + this.burned);
    }


    private void setTimer(int progress)
    {
        switch (progress)
        {
            case 0:
                START_TIME_IN_MILLIS = 300_000;
                break;
            case 1:
                START_TIME_IN_MILLIS = 600_000;
                break;
            case 2:
                START_TIME_IN_MILLIS = 900_000;
                break;
            case 3:
                START_TIME_IN_MILLIS = 1_200_000;
                break;
            case 4:
                START_TIME_IN_MILLIS = 1_500_000;
                break;
            case 5:
                START_TIME_IN_MILLIS = 1_800_000;
                break;
            case 6:
                START_TIME_IN_MILLIS = 2_100_000;
                break;
            case 7:
                START_TIME_IN_MILLIS = 2_400_000;
                break;
            case 8:
                START_TIME_IN_MILLIS = 2_700_000;
                break;
            case 9:
                START_TIME_IN_MILLIS = 3_000_000;
                break;
            case 10:
                START_TIME_IN_MILLIS = 3_300_000;
                break;
        }
    }

}
