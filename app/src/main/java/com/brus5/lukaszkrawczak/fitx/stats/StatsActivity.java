package com.brus5.lukaszkrawczak.fitx.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;

public class StatsActivity extends AppCompatActivity implements DefaultView
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        loadInput();
        loadDefaultView();

    }

    @Override
    public void loadInput()
    {

    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(StatsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarStatsActivity);
        activityView.showBackButton();
    }

    public void runNextActivity(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext, cls);
        StatsActivity.this.startActivity(intent);
        finish();
    }
}
