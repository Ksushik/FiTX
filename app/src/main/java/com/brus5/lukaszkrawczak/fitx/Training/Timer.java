package com.brus5.lukaszkrawczak.fitx.Training;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
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
    Activity activity;
    public boolean timerRunning;
    public long START_TIME_IN_MILLIS;
    public SeekBar seekBar;
    private CountDownTimer timer;
    private long timeLeftInMillis;
    private ProgressBar progressBar;
    private Button btnReset;
    private TextView tvTime;
    private FloatingActionButton btnStartPause;

    public Timer(Activity activity)
    {
        this.activity = activity;
        seekBar = this.activity.findViewById(R.id.seekBarTimer);
        progressBar = this.activity.findViewById(R.id.progressBarCircle);
        btnReset = this.activity.findViewById(R.id.buttonResetTimer);
        tvTime = this.activity.findViewById(R.id.textViewTime);
        btnStartPause = this.activity.findViewById(R.id.floatingButtonStartPause);
    }

    public void resetTimer()
    {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnStartPause.setVisibility(View.VISIBLE);
        setProgressBarValues();
    }

    public void startTimer()
    {
        timer = new CountDownTimer(timeLeftInMillis, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                progressBar.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish()
            {
                timerRunning = false;
                btnStartPause.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
                setProgressBarValues();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();

            }
        }.start();

        timerRunning = true;
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setImageResource(R.drawable.ic_pause_white_24dp);
    }

    private void updateCountDownText()
    {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        tvTime.setText(timeLeftFormatted);
    }

    public void pauseTimer()
    {
        timer.cancel();
        timerRunning = false;
        btnStartPause.setImageResource(R.drawable.ic_play_arrow_white_24dp);
        btnReset.setVisibility(View.VISIBLE);
    }

    public void seekBarTimer()
    {
        seekBar.setOnSeekBarChangeListener(
            new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                {
                    onProgressSetTime(progress);
                    updateCountDownText();
                    resetTimer();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            }
                                          );
    }

    private void onProgressSetTime(int progress)
    {
        switch (progress)
        {
            case 0:
                START_TIME_IN_MILLIS = 15000;
                break;
            case 1:
                START_TIME_IN_MILLIS = 30000;
                break;
            case 2:
                START_TIME_IN_MILLIS = 45000;
                break;
            case 3:
                START_TIME_IN_MILLIS = 60000;
                break;
            case 4:
                START_TIME_IN_MILLIS = 75000;
                break;
            case 5:
                START_TIME_IN_MILLIS = 90000;
                break;
            case 6:
                START_TIME_IN_MILLIS = 105000;
                break;
            case 7:
                START_TIME_IN_MILLIS = 120000;
                break;
            case 8:
                START_TIME_IN_MILLIS = 135000;
                break;
            case 9:
                START_TIME_IN_MILLIS = 150000;
                break;
        }
    }

    public void convertSetTime(int timeInMillis)
    {
        switch (timeInMillis)
        {
            case 15000:
                seekBar.setProgress(0);
                break;
            case 30000:
                seekBar.setProgress(1);
                break;
            case 45000:
                seekBar.setProgress(2);
                break;
            case 60000:
                seekBar.setProgress(3);
                break;
            case 75000:
                seekBar.setProgress(4);
                break;
            case 90000:
                seekBar.setProgress(5);
                break;
            case 105000:
                seekBar.setProgress(6);
                break;
            case 120000:
                seekBar.setProgress(7);
                break;
            case 135000:
                seekBar.setProgress(8);
                break;
            case 150000:
                seekBar.setProgress(9);
                break;
        }
    }

    private void setProgressBarValues()
    {
        progressBar.setMax((int) START_TIME_IN_MILLIS / 1000);
        progressBar.setProgress((int) START_TIME_IN_MILLIS / 1000);
    }

}
