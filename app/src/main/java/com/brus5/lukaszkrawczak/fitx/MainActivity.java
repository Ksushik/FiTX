package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Async.ConnectedView;
import com.brus5.lukaszkrawczak.fitx.Diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.Stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingActivity;
import com.brus5.lukaszkrawczak.fitx.Utils.MyCalendar;

/**
 * This is MainActivity class which shows Main class after LoginActivity.class
 * This Activity provides informations about:
 * - Daily Diet result
 * - Daily Training result
 * - Daily Cardio result
 * <p>
 * XML FILE: <link>activity_main.xml</link>
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DefaultView
{
    private static final String TAG = "MainActivity";
    private Button btnDiet, btnTraining, btnSettings, btnStats;
    private Configuration cfg = new Configuration();
    private ListView listView;
    public String response;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        cfg.changeStatusBarColor(this, getApplicationContext(), R.id.toolbarMainActivity,this);
        loadInput();
        ConnectedView connectedView = new ConnectedView(this);
        connectedView.execute();
        new MyCalendar(this, this, R.id.calendarViewMainActivity, listView);
    }



    public void loadInput()

    {
        listView = findViewById(R.id.listViewMain);
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
                runNextActivity(MainActivity.this, SettingsActiviy.class);
                break;
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    public void runNextActivity(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext, cls);
        MainActivity.this.startActivity(intent);
    }

    public void onClickDiet(View view)
    {
        runNextActivity(MainActivity.this,DietActivity.class);
    }

    public void onClickTraining(View view)
    {
        runNextActivity(MainActivity.this,TrainingActivity.class);
    }

    public void onClickCardio(View view)
    {
        runNextActivity(MainActivity.this,TrainingActivity.class);
    }
}


