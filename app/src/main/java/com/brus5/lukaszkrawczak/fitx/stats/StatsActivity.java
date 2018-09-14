package com.brus5.lukaszkrawczak.fitx.stats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.jjoe64.graphview.GraphView;

public class StatsActivity extends AppCompatActivity implements IDefaultView
{

    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        loadInput();
        loadDefaultView();

        loadGraphData();

    }

    private void loadGraphData()
    {
        new Provider(StatsActivity.this,StatsActivity.this).load();
    }

    @Override
    public void loadInput()
    {
        graphView = findViewById(R.id.grapViewStatistics);
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
