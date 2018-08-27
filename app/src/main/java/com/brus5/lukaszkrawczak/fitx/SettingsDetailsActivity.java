package com.brus5.lukaszkrawczak.fitx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.Utils.ActivityView;

public class SettingsDetailsActivity extends AppCompatActivity implements DefaultView
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_details);
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
        ActivityView activityView = new ActivityView(SettingsDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbaSettingsDetailsActivity);
        activityView.showBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_details, menu);

        MenuItem item = menu.findItem(R.id.menu_save_setting);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_setting:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
