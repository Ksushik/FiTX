package com.brus5.lukaszkrawczak.fitx.training.addons;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Timer
{
    private static final String TAG = "Timer";
    public static long START_TIME_IN_MILLIS;
    public boolean isTimerRunning;
    public SeekBar seekBar;
    private Context context;
    private CountDownTimer timer;
    private long timeLeftInMillis;
    private ProgressBar circleProgressBar;
    private Button btnReset;
    private TextView tvTime;
    private FloatingActionButton btnStartPause;

    /**
     * Construcor of Timer
     *
     * @param context  Context is for updating TextView's
     */
    public Timer(Context context)
    {
        this.context = context;

        seekBar = ((Activity)context).findViewById(R.id.seekBarTimer);
        circleProgressBar = ((Activity)context).findViewById(R.id.progressBarCircle);
        btnReset = ((Activity)context).findViewById(R.id.buttonResetTimer);
        tvTime = ((Activity)context).findViewById(R.id.textViewTime);
        btnStartPause = ((Activity)context).findViewById(R.id.floatingButtonStartPause);

        // automatically after loading constructor of Timer buttons are loading.
        buttons();
        Log.d(TAG, "Timer() " + isTimerRunning);
    }

    /**
     * This method is loading Timer
     */
    public void startTimer()
    {
        /**
         * Creating new Timer object with milliseconds left to the end,
         * and countDownInterval in milliseconds
         */
        timer = new CountDownTimer(timeLeftInMillis, 1000)
        {
            /**
             *  When Timer Ticking
             * @param millisUntilFinished long
             */
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMillis = millisUntilFinished;

                setCountDownTextView();

                circleProgressBar.setProgress((int) (millisUntilFinished / 1000));
            }

            /**
             * When Timer is finished
             */
            @Override
            public void onFinish()
            {
                isTimerRunning = false;
                btnStartPause.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                setProgressbar();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();
            }
        }.start();

        isTimerRunning = true;
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setImageResource(R.drawable.ic_pause_white_24dp);
    }

    /**
     * Pausing Timer and updating View
     */
    public void pauseTimer()
    {
        timer.cancel();
        isTimerRunning = false;
        btnStartPause.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        btnReset.setVisibility(View.VISIBLE);
    }

    /**
     * Reset Timer and updating View
     */
    public void resetTimer()
    {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        btnStartPause.setVisibility(View.VISIBLE);
        setProgressbar();
        setCountDownTextView();
    }

    /**
     * Sets TextView with converted value of time
     */
    protected void setCountDownTextView()
    {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        tvTime.setText(timeLeftFormatted);

        Log.d(TAG, "setCountDownTextView: " + "| tvTime: " + tvTime.getText().toString() + "| isTimerRunning: " + isTimerRunning + "| timerLeftInMillis: " + timeLeftInMillis + "| START_TIME_IN_MILLIS: " + START_TIME_IN_MILLIS);
    }

    public void seekbar() {}

    public void setSeekbarProgress(int milliseconds) {}

    private void setProgressbar()
    {
        circleProgressBar.setMax((int) START_TIME_IN_MILLIS / 1000);
        circleProgressBar.setProgress((int) START_TIME_IN_MILLIS / 1000);
    }

    public void buttons()
    {
        btnStartPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isTimerRunning)
                {
                    pauseTimer();
                }
                else
                {
                    startTimer();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetTimer();
            }
        });
    }
}