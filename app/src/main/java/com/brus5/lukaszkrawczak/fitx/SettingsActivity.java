package com.brus5.lukaszkrawczak.fitx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;

public class SettingsActivity extends AppCompatActivity implements IDefaultView
{
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loadInput();
        loadDefaultView();

        new Provider(SettingsActivity.this,SettingsActivity.this,listView).load();
    }

    @Override
    public void loadInput()
    {
        listView = findViewById(R.id.listViewSettings);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(SettingsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarSettingsActivity);
        activityView.showBackButton();
    }
}
