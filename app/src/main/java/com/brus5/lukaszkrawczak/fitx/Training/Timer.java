package com.brus5.lukaszkrawczak.fitx.Training;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.Locale;
import java.util.zip.Inflater;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Timer {
    Activity activity;

    public boolean timerRunning;
    public long START_TIME_IN_MILLIS;

    public SeekBar seekBarTimer;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private ProgressBar progressBarCircle;
    private Button buttonStartStopTimer, buttonResetTimer;
    private TextView textViewTime;

    public Timer(Activity activity) {
        this.activity = activity;
        seekBarTimer = this.activity.findViewById(R.id.seekBarTimer);
        progressBarCircle = this.activity.findViewById(R.id.progressBarCircle);
        buttonStartStopTimer = this.activity.findViewById(R.id.buttonStartStopTimer);
        buttonResetTimer = this.activity.findViewById(R.id.buttonResetTimer);
        textViewTime = this.activity.findViewById(R.id.textViewTime);
    }

    public void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        buttonStartStopTimer.setVisibility(View.VISIBLE);
        setProgressBarValues();
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartStopTimer.setText(R.string.start);
                buttonStartStopTimer.setVisibility(View.INVISIBLE);
                buttonResetTimer.setVisibility(View.VISIBLE);
                setProgressBarValues();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();

            }
        }.start();
        timerRunning = true;
        buttonResetTimer.setVisibility(View.INVISIBLE);
        buttonStartStopTimer.setText(R.string.pause);
    }

    private void updateCountDownText() {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewTime.setText(timeLeftFormatted);
    }

    public void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartStopTimer.setText(R.string.start);
        buttonResetTimer.setVisibility(View.VISIBLE);
    }

    public void seekBarTimer() {
        seekBarTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onProgressSetTime(progress);
                updateCountDownText();
                resetTimer();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void onProgressSetTime(int progress) {
        switch (progress){
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

    public void convertSetTime(int timeInMillis){
        switch (timeInMillis){
            case 15000:
                seekBarTimer.setProgress(0);
                break;
            case 30000:
                seekBarTimer.setProgress(1);
                break;
            case 45000:
                seekBarTimer.setProgress(2);
                break;
            case 60000:
                seekBarTimer.setProgress(3);
                break;
            case 75000:
                seekBarTimer.setProgress(4);
                break;
            case 90000:
                seekBarTimer.setProgress(5);
                break;
            case 105000:
                seekBarTimer.setProgress(6);
                break;
            case 120000:
                seekBarTimer.setProgress(7);
                break;
            case 135000:
                seekBarTimer.setProgress(8);
                break;
            case 150000:
                seekBarTimer.setProgress(9);
                break;
        }
    }

    private void setProgressBarValues() {
        progressBarCircle.setMax((int) START_TIME_IN_MILLIS / 1000);
        progressBarCircle.setProgress((int) START_TIME_IN_MILLIS / 1000);
    }

}
