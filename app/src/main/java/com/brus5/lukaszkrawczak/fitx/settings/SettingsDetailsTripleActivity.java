package com.brus5.lukaszkrawczak.fitx.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.IPreviousActivity;
import com.brus5.lukaszkrawczak.fitx.MainService;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS_INSERT;

public class SettingsDetailsTripleActivity extends AppCompatActivity implements IDefaultView, IPreviousActivity
{
    private static final String TAG = "SettingsDetailsTripleAc";

    private String db;

    private EditText firstEditText;
    private EditText secondEditText;
    private EditText thirdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_2_details_triple);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        loadInfoContext();

    }

    @Override
    public void loadInput()
    {
        // Creating workaround
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutSettingsTripleDetails);
        // Request focus for non focusing on EditText
        constraintLayout.requestFocus();

        firstEditText = findViewById(R.id.firstSettingsTripleEditText);
        secondEditText = findViewById(R.id.secondSettingsTripleEditText);
        thirdEditText = findViewById(R.id.thirdSettingsTripleEditText);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(SettingsDetailsTripleActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbaSettingsDetailsTripleActivity);
    }


    @Override
    public void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
//        name = intent.getStringExtra("name");
//        descriptionLong = intent.getStringExtra("descriptionLong");
        db = intent.getStringExtra("db");
        Log.d(TAG, "getIntentFromPreviousActiity() called db: " + db);
    }

    private void loadInfoContext()
    {
        switch (db)
        {
            case "user_diet_ratio":
                String s1 = getResources().getString(R.string.proteins);
                String s2 = getResources().getString(R.string.fats);
                String s3 = getResources().getString(R.string.carbs);
                new TypeInfo(s1,s2,s3);
                break;
        }
    }

    /**
     * This method is responsible for showing OptionsMenu
     *
     * @param menu item on ActionBar
     * @return created item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_details, menu);

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is responsible for accepting result entered by user
     *
     * @param item is item on ActionBar menu
     * @return clicked item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.menu_save_setting:
                Log.d(TAG, "onOptionsItemSelected() called with: getRatioResult() = [" + getRatioResult() + "]");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is responsible for converting values from EditText's to proper format
     * @return String proper value for example: "30:40:30"
     */
    private String getRatioResult()
    {
        String first = firstEditText.getText().toString();
        String second = secondEditText.getText().toString();
        String third = thirdEditText.getText().toString();
        return first + ":" + second + ":" + third;
    }

    /**
     * This class is responsible for creating text depends on String db value
     */
    private class TypeInfo
    {
        private TextView firstTV;
        private TextView secondTV;
        private TextView thirdTV;

        TypeInfo(String firstRow, String secondRow, String thirdRow)
        {
            firstTV = findViewById(R.id.firstSettingsTripleTextView);
            secondTV = findViewById(R.id.secondSettingsTripleTextView);
            thirdTV = findViewById(R.id.thirdSettingsTripleTextView);

            firstTV.setText(firstRow);
            secondTV.setText(secondRow);
            thirdTV.setText(thirdRow);
        }

    }
// TODO: 30/10/2018 zrobić sending info do mysql
}