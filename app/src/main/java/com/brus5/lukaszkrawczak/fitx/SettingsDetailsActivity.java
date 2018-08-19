package com.brus5.lukaszkrawczak.fitx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsDetailsActivity extends AppCompatActivity {

    private Configuration cfg = new Configuration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_details);
        cfg.changeStatusBarColor(this, getApplicationContext(),R.id.toolbaSettingsDetailsActivity,this);
        onBackButtonPressed();
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
