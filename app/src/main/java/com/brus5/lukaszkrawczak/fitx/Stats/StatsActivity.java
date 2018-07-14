package com.brus5.lukaszkrawczak.fitx.Stats;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.brus5.lukaszkrawczak.fitx.R;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        changeStatusBarColor();
        onBackButtonPressed();
    }

    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(StatsActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarStatsActivity);
        setSupportActionBar(toolbar);
    }

    public void runNextActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        StatsActivity.this.startActivity(intent);
        finish();
    }
}
