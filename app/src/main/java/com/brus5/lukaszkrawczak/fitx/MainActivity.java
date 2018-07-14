package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.Stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.Training.Training;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingActivity;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingExerciseShow;

import junit.framework.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    Button buttonDietActivity, buttonTrainingActivity, buttonSettingsActivity, buttonStatsActivity;

    HorizontalCalendar horizontalCalendar;
    ListView listViewMainActivity;

    /* Gettings dateToday */
    Configuration cfg = new Configuration();
    String dateInsde, dateInsideTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeStatusBarColor();
        loadInputs(MainActivity.this);

        weekCalendar(cfg.generateEndDay(),cfg.generateNextDay());

    }
    private void weekCalendar(Calendar endDate, Calendar startDate) {
        horizontalCalendar = new HorizontalCalendar.Builder(MainActivity.this, R.id.calendarViewMainActivity)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
//                .textSize(10f, 16f, 14f)
                .showDayName(true)
                .showMonthName(false)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                dateInsde = cfg.getSimpleDateDateInside().format(date.getTime());
                dateInsideTextView = cfg.getSimpleDateTextView().format(date.getTime());

                Log.e(TAG, "onDateSelected: "+dateInsde);
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbar);
    }

    private void loadInputs(Context ctx) {
        buttonDietActivity = findViewById(R.id.buttonDietActivity);
        buttonDietActivity.setOnClickListener(this);
        buttonTrainingActivity = findViewById(R.id.buttonTrainingActivity);
        buttonTrainingActivity.setOnClickListener(this);
        buttonSettingsActivity = findViewById(R.id.buttonSettingsActivity);
        buttonSettingsActivity.setOnClickListener(this);
        buttonStatsActivity = findViewById(R.id.buttonStatsActivity);
        buttonStatsActivity.setOnClickListener(this);

        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserID(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserFirstName(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserName(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserBirthday(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserPassword(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserEmail(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserGender(ctx));
        Log.e(TAG, "SaveSharedPreference: "+SaveSharedPreference.getUserAge(ctx));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDietActivity:
                runNextActivity(MainActivity.this,DietActivity.class);
                break;
            case R.id.buttonTrainingActivity:
                runNextActivity(MainActivity.this, TrainingActivity.class);
                break;
            case R.id.buttonStatsActivity:
                runNextActivity(MainActivity.this,StatsActivity.class);
                break;
            case R.id.buttonSettingsActivity:
                break;
        }
    }

    public void runNextActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        MainActivity.this.startActivity(intent);
    }

    void creatingSimpleMethod(){

    }
}
