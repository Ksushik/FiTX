package com.brus5.lukaszkrawczak.fitx.training.addons;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;

public class TimerGym extends Timer
{
    private static final String TAG = "TimerCardio";

    public TimerGym(Context context)
    {
        super(context);

    }

    /**
     * loading seekbar
     */
    @Override
    public void seekbar()
    {
        // need to set those methods because without this
        // after adding new training user wont be able to
        // turn on timer
        seekBar.setProgress(3);
        setTimer(3);
        resetTimer();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                setTimer(progress);
                resetTimer();
                Log.d(TAG, "onProgressChanged() called with: seekBar \n" + " START_TIME_IN_MILLIS: " + START_TIME_IN_MILLIS);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * This method is responsible for setting milliseconds of
     * the Timer. The START_TIME_IN_MILLIS is variable of Timer.class
     *
     * @param progress progress of actual seekBar placement.
     */
    private void setTimer(int progress)
    {
        switch (progress)
        {
            case 0:
                START_TIME_IN_MILLIS = 15_000;
                break;
            case 1:
                START_TIME_IN_MILLIS = 30_000;
                break;
            case 2:
                START_TIME_IN_MILLIS = 45_000;
                break;
            case 3:
                START_TIME_IN_MILLIS = 60_000;
                break;
            case 4:
                START_TIME_IN_MILLIS = 75_000;
                break;
            case 5:
                START_TIME_IN_MILLIS = 90_000;
                break;
            case 6:
                START_TIME_IN_MILLIS = 105_000;
                break;
            case 7:
                START_TIME_IN_MILLIS = 120_000;
                break;
            case 8:
                START_TIME_IN_MILLIS = 135_000;
                break;
            case 9:
                START_TIME_IN_MILLIS = 150_000;
                break;
            case 10:
                START_TIME_IN_MILLIS = 165_000;
                break;
        }
    }

    /**
     * This method automatically sets progress on seekBar
     *
     * @param milliseconds time in milliseconds
     */
    @Override
    public void setSeekbarProgress(int milliseconds)
    {
        switch (milliseconds)
        {
            case 15_000:
                seekBar.setProgress(0);
                break;
            case 30_000:
                seekBar.setProgress(1);
                break;
            case 45_000:
                seekBar.setProgress(2);
                break;
            case 60_000:
                seekBar.setProgress(3);
                break;
            case 75_000:
                seekBar.setProgress(4);
                break;
            case 90_000:
                seekBar.setProgress(5);
                break;
            case 105_000:
                seekBar.setProgress(6);
                break;
            case 120_000:
                seekBar.setProgress(7);
                break;
            case 135_000:
                seekBar.setProgress(8);
                break;
            case 150_000:
                seekBar.setProgress(9);
                break;
            case 165_000:
                seekBar.setProgress(10);
                break;
        }
    }

}
