package com.brus5.lukaszkrawczak.fitx.Training;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.zip.Inflater;

public class Timer {
    Activity activity;


    private SeekBar seekBarTimer;
    private CountDownTimer countDownTimer;
    private long START_TIME_IN_MILLIS;
    private long timeLeftInMillis;
    private boolean timerRunning;
    ProgressBar progressBarCircle;

    public Timer(Activity activity) {
        this.activity = activity;
    }

    public void loadTrainingDetailsActivityInputs(){
        progressBarCircle = this.activity.findViewById(R.id.progressBarCircle);

    }


    // TODO: 24.07.2018 pass all timer methods here




}
