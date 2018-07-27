package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.brus5.lukaszkrawczak.fitx.Diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.Stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingActivity;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btnDiet, btnTraining, btnSettings, btnStats;
    HorizontalCalendar calendar;
    Configuration cfg = new Configuration();
    String dateFormat, dateFormatView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        loadInputs();
        weekCalendar(cfg.oldestDay(), cfg.newestDay());
    }

    private void weekCalendar(Calendar endDate, Calendar startDate)
    {
        calendar = new HorizontalCalendar.Builder(MainActivity.this, R.id.calendarViewMainActivity)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
                .showDayName(true)
                .showMonthName(false)
                .build();

        calendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Date date, int position)
            {
                dateFormat = cfg.getDateFormat().format(date.getTime());
                dateFormatView = cfg.getDateFormatView().format(date.getTime());
            }
        });
    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbar);
    }

    private void loadInputs()
    {
        btnDiet = findViewById(R.id.buttonDiet);
        btnDiet.setOnClickListener(this);
        btnTraining = findViewById(R.id.buttonTraining);
        btnTraining.setOnClickListener(this);
        btnSettings = findViewById(R.id.buttonSettings);
        btnSettings.setOnClickListener(this);
        btnStats = findViewById(R.id.buttonStats);
        btnStats.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonDiet:
                runNextActivity(MainActivity.this, DietActivity.class);
                break;
            case R.id.buttonTraining:
                runNextActivity(MainActivity.this, TrainingActivity.class);
                break;
            case R.id.buttonStats:
                runNextActivity(MainActivity.this, StatsActivity.class);
                break;
            case R.id.buttonSettings:
                break;
        }
    }

    public void runNextActivity(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext, cls);
        MainActivity.this.startActivity(intent);

    }
}
