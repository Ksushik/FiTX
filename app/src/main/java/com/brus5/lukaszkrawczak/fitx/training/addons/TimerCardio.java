package com.brus5.lukaszkrawczak.fitx.training.addons;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

public class TimerCardio extends Timer
{
    private static final String TAG = "TimerCardio";
    private TextView tvBurned;
    private double burned; // burning calories per 1 min

    public TimerCardio(Context context)
    {
        super(context);

        tvBurned = ((Activity)context).findViewById(R.id.textViewBurned);
    }

    /**
     * loading seekbar
     */
    @Override
    public void seekbar()
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                setTimer(progress);
                resetTimer();
                setBurnedTextView(burned);
                Log.d(TAG, "onProgressChanged() called with: seekBar \n" + "burned: " + burned + " START_TIME_IN_MILLIS: " + START_TIME_IN_MILLIS);
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
            case 300_000:
                seekBar.setProgress(0);
                break;
            case 600_000:
                seekBar.setProgress(1);
                break;
            case 900_000:
                seekBar.setProgress(2);
                break;
            case 1_200_000:
                seekBar.setProgress(3);
                break;
            case 1_500_000:
                seekBar.setProgress(4);
                break;
            case 1_800_000:
                seekBar.setProgress(5);
                break;
            case 2_100_000:
                seekBar.setProgress(6);
                break;
            case 2_400_000:
                seekBar.setProgress(7);
                break;
            case 2_700_000:
                seekBar.setProgress(8);
                break;
            case 3_000_000:
                seekBar.setProgress(9);
                break;
            case 3_300_000:
                seekBar.setProgress(10);
                break;
        }
    }

    /**
     * setBurnedTextView is responsible for updating TextView while SeekBar
     * is changing.
     *
     * @param v Calories burned per minute.
     */
    public void setBurnedTextView(double v)
    {
        this.burned = v;

        // This variable shows how many calories can user burn per value entered in SeekBar
        double iBurned = v * (START_TIME_IN_MILLIS / 1000 / 60);
        tvBurned.setText(String.valueOf((int) iBurned));

        Log.d(TAG, "setBurnedTextView() called with: value = [" + v + "]" + " iBurned: " + iBurned + " this.burned: " + this.burned);
    }
}
