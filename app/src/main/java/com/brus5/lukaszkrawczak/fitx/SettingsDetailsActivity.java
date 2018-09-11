package com.brus5.lukaszkrawczak.fitx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

public class SettingsDetailsActivity extends AppCompatActivity implements IDefaultView, IPreviousActivity
{
    private static final String TAG = "SettingsDetailsActivity";
    private String db;
    private String name;
    private String descriptionLong;
    private TextView tvName;
    private TextView tvDescription;
    private ConstraintLayout constraintLayout;
    private static double VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_details);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        tvName.setText(name);
        tvDescription.setText(descriptionLong);

        new Provider(SettingsDetailsActivity.this,SettingsDetailsActivity.this).load(db);

    }

    @Override
    public void loadInput()
    {
        constraintLayout = findViewById(R.id.constraintLayoutSettingsDetails);
        constraintLayout.requestFocus();
        tvName = findViewById(R.id.textViewSettings);
        tvDescription = findViewById(R.id.textViewDescriptionLong);
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
                Toast.makeText(this, String.valueOf(VALUE), Toast.LENGTH_SHORT).show();

                String id = String.valueOf(SaveSharedPreference.getUserID(SettingsDetailsActivity.this));
                String RESULT = String.valueOf(VALUE);

                new Provider(SettingsDetailsActivity.this, SettingsDetailsActivity.this).postSettings(RESULT, db);

//                Calendar cal = Calendar.getInstance();
//                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                String date = sdf.format(cal.getTime());


//                HashMap<String,String> params = new HashMap<>();
//                params.put("id",id);
//                params.put("date",date);
//                params.put("RESULT",RESULT);
//                params.put("table",db);
//                http://justfitx.xyz/Settings/Insert.php?id=5&RESULT=92.1&date=2018-09-11&table=user_weight
//                String l = URL_SETTINGS_INSERT + "?id=" + id + "&date=" + date + "&RESULT=" + RESULT + "&table=" + db;
//                Log.i(TAG, "onOptionsItemSelected: "+l);
//                MainService s = new MainService(SettingsDetailsActivity.this);
//                s.post(l);
//                new StringRequest(Request.Method.HEAD,l,null,null);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    @Override
    public void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        descriptionLong = intent.getStringExtra("descriptionLong");
        db = intent.getStringExtra("db");
    }

    public void load(Activity activity, Context context, String value)
    {
        EditText et = activity.findViewById(R.id.editTextSettings);
        et.setText(value);
        VALUE = Double.valueOf(value);
    }
}

