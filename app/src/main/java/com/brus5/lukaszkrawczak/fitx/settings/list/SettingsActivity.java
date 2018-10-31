package com.brus5.lukaszkrawczak.fitx.settings.list;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.HTTPService;
import com.brus5.lukaszkrawczak.fitx.login.LoginActivity;
import com.brus5.lukaszkrawczak.fitx.settings.SettingsDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.settings.SettingsDetailsTripleActivity;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.CaloriesAuto;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.CaloriesGoal;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.DietRatio;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.EmptyRow;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.Height;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.ManualCalories;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.Somatotype;
import com.brus5.lukaszkrawczak.fitx.settings.list.row.Weight;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.MyFloatingMenu;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_SETTINGS;

public class SettingsActivity extends AppCompatActivity implements IDefaultView
{

    private static final String TAG = "SettingsActivity";
    private ListView listView;
    private MySettingsList mySettingsList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_1);
        loadInput();
        loadDefaultView();

        mySettingsList = new MySettingsList(SettingsActivity.this, listView);

        new MyFloatingMenu(this);
    }


    @Override
    public void loadInput()
    {
        listView = findViewById(R.id.listViewSettings);
    }

    public static void start(Context context)
    {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_logoff, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.menu_settings_logoff:
                Toast.makeText(this, "You'll be logged off", Toast.LENGTH_SHORT).show();

                int[] items = new int[]{R.string.no, R.string.yes};

                Log.d(TAG, "onOptionsItemSelected() called with: item = [" + String.valueOf(getResources().getString(items[0])) + "]");


                AlertDialog dialog = new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle(R.string.quit_message_title)
                        .setMessage(R.string.quit_message_long)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                SaveSharedPreference.logOff(SettingsActivity.this);
                                start(SettingsActivity.this);
                                finishActivityFromChild(SettingsActivity.this, 0);
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.no, null)
                        .create();
                dialog.show();


                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Reload info
     */
    @Override
    protected void onResume()
    {
        super.onResume();

        mySettingsList.clear();
        listView.invalidate();

        int userID = SaveSharedPreference.getUserID(SettingsActivity.this);
        String params = "?id=" + userID;

        MySettings mySettings = new MySettings(SettingsActivity.this);
        mySettings.execute(URL_SETTINGS, params);

    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(SettingsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarSettingsActivity);
    }

    @SuppressLint("StaticFieldLeak")
    class MySettings extends HTTPService
    {
        private Context context;

        MySettings(Context context)
        {
            super(context);
            this.context = context;
        }

        @Override
        public void onPostExecute(String s)
        {
            super.onPostExecute(s);


            Log.d(TAG, "dataInflater() called with: s = [" + s + "]");

            try
            {
                // Creating JSON Object with value fetched from "s" paramtere
                JSONObject json = new JSONObject(s);

                // Getting array from JSONObject named "server_response"
                JSONArray array = json.getJSONArray("server_response");

                // Getting JSONObject of array with index and then getting specific String name
                String weight = array.getJSONObject(0).getString("weight");
                String height = array.getJSONObject(1).getString("height");
                String somatotype = array.getJSONObject(2).getString("somatotype");
                String auto_calories = array.getJSONObject(3).getString("auto_calories");
                String calories_limit = array.getJSONObject(4).getString("calories_limit");
                String diet_ratio = array.getJSONObject(5).getString("diet_ratio");
                String goal = array.getJSONObject(6).getString("diet_goal");
                SaveSharedPreference.setLimitCalories(context, calories_limit);

                new Weight(SettingsActivity.this, mySettingsList, weight);

                new Height(SettingsActivity.this, mySettingsList, height);

                new Somatotype(SettingsActivity.this, mySettingsList, somatotype);

                new CaloriesAuto(SettingsActivity.this, mySettingsList, auto_calories);

                // If Automatic calories are turned OFF then be able to add manual calories row
                if (SaveSharedPreference.getAutoCalories(context) == 0)
                {
                    new ManualCalories(SettingsActivity.this, mySettingsList, calories_limit);
                }

                new DietRatio(SettingsActivity.this, mySettingsList, diet_ratio);

                new CaloriesGoal(SettingsActivity.this, mySettingsList, goal);

                new EmptyRow(SettingsActivity.this,mySettingsList);

                new OnItemClicked(mySettingsList);


            }
            catch (JSONException e)
            {
                Log.e(TAG, "dataInflater: ", e);
            }
        }
    }

    /**
     * What happens when you click on specific item of ListView
     */
    private class OnItemClicked
    {
        OnItemClicked(final MySettingsList mySettingsList)
        {
            ListView listView = findViewById(R.id.listViewSettings);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // Getting position of clicked item
                    Settings itemClicked = mySettingsList.get(position);

                    String name = itemClicked.getName();
                    String descriptionLong = itemClicked.getDescriptionLong();
                    String db = itemClicked.getDb();
                    int viewType = itemClicked.getViewType();

                    // If viewType is standard view with value
                    if (viewType == 1)
                    {
                        Intent intent = new Intent(SettingsActivity.this, SettingsDetailsActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("descriptionLong", descriptionLong);
                        intent.putExtra("db", db);
                        startActivity(intent);
                    }

                    // If viewType is with switch NO ACITON after click
                    if (viewType == 2)
                    {
                        Toast.makeText(SettingsActivity.this, "name: " + itemClicked.getName() + " pos: " + position, Toast.LENGTH_SHORT).show();
                    }

                    // If view Type is with TripleChooser
                    if (viewType == 3)
                    {
                        Toast.makeText(SettingsActivity.this, "name: " + itemClicked.getName() + " pos: " + position, Toast.LENGTH_SHORT).show();

                        new TripleAlertDialog(SettingsActivity.this, itemClicked);
                    }

                    // If viewType is triple EditText Activity
                    if (viewType == 4)
                    {
                        Intent intent = new Intent(SettingsActivity.this, SettingsDetailsTripleActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("descriptionLong", descriptionLong);
                        intent.putExtra("value", itemClicked.getValue());
                        intent.putExtra("db", db);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}
