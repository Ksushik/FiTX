package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.IPreviousActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.HTTPService;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStamp;
import com.brus5.lukaszkrawczak.fitx.diet.DietService;
import com.brus5.lukaszkrawczak.fitx.training.addons.TimerCardio;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.ImageLoader;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.brus5.lukaszkrawczak.fitx.training.addons.Timer.START_TIME_IN_MILLIS;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_DATE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_DONE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_ID;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_NOTEPAD;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_CARDIO_TIME;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_USER_ID_NO_PRIMARY_KEY;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.SERVER_URL;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_DELETE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_INSERT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_SHOW;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_UPDATE;

/**
 * This class is viewing Cardio Details where user can set the timer,
 * mark as "trained" or "not trained yet" training and simple notepad to
 * store some thoughts. Also user can see how many calories can burn while
 * moving SeekBar.
 */
@SuppressLint("SimpleDateFormat")
public class CardioDetailsActivity extends AppCompatActivity implements IDefaultView, IPreviousActivity
{
    private static final String TAG = "CardioDetailsActivity";
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private static String previousActivity;
    private String trainingTimeStamp, newTimeStamp;
    private int trainingID, trainingTime;
    private EditText editText;
    private TextView tvName, textViewBurned;
    private CheckBox checkBox;
    private double kcalPerMin;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_4_details);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        startProvider(previousActivity);

        final String url = SERVER_URL + "images/cardio/" + trainingID + ".jpg";
        Log.d(TAG, "url: " + url);
        new ImageLoader(CardioDetailsActivity.this, R.id.imageViewCardio, R.id.progressBarCardioDetails, url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_cardio:
                if (previousActivity.equals( TrainingActivity.class.getSimpleName() ))
                {
                    Toast.makeText(this, R.string.training_updated, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(CardioDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(DB_CARDIO_ID, String.valueOf(trainingID));
                    params.put(DB_CARDIO_DONE, String.valueOf(setOnCheckedChangeListener()));
                    params.put(DB_CARDIO_TIME, String.valueOf(START_TIME_IN_MILLIS / 1000 / 60));
                    params.put(DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(CardioDetailsActivity.this)));
                    params.put(DB_CARDIO_NOTEPAD, editText.getText().toString());
                    params.put(DB_CARDIO_DATE, newTimeStamp);

                    service.post(params, URL_CARDIO_UPDATE);

                    finish();
                }

                else if (previousActivity.equals( CardioListActivity.class.getSimpleName() ))
                {
                    Toast.makeText(this, R.string.training_inserted, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(CardioDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(DB_CARDIO_ID, String.valueOf(trainingID));
                    params.put(DB_CARDIO_DONE, String.valueOf(setOnCheckedChangeListener()));
                    params.put(DB_CARDIO_TIME, String.valueOf(START_TIME_IN_MILLIS / 1000 / 60));
                    params.put(DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(CardioDetailsActivity.this)));
                    params.put(DB_CARDIO_NOTEPAD, editText.getText().toString());
                    params.put(DB_CARDIO_DATE, newTimeStamp);

                    service.post(params, URL_CARDIO_INSERT);

                    finish();
                }
                else
                {
                    DateGenerator cfg = new DateGenerator();
                    cfg.showError(CardioDetailsActivity.this);
                }
                break;
            case R.id.menu_delete_cardio:
                Toast.makeText(this, R.string.training_deleted, Toast.LENGTH_SHORT).show();

                DietService service = new DietService(CardioDetailsActivity.this);

                HashMap<String, String> params = new HashMap<>();
                params.put(DB_CARDIO_ID, String.valueOf(trainingID));
                params.put(DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(CardioDetailsActivity.this)));
                params.put(DB_CARDIO_DATE, newTimeStamp);

                service.post(params, URL_CARDIO_DELETE);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cardio_3_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_cardio);
        if (previousActivity.equals( CardioListActivity.class.getSimpleName() ))
        {
            item.setVisible(false);
        }
        else
        {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void loadInput()
    {
        tvName = findViewById(R.id.textViewCardioName);
        textViewBurned = findViewById(R.id.textViewBurned);
        editText = findViewById(R.id.editTextNotepadCardio);

        checkBox = findViewById(R.id.checkBox);

        constraintLayout = findViewById(R.id.constraingLayoutCardioDetails);
        constraintLayout.requestFocus();
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(CardioDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarCardio);
        activityView.showBackButton();
    }

    @Override
    public void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID", -1);
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        trainingTimeStamp = timeStampChanger(trainingTimeStamp);
        trainingTime = intent.getIntExtra("trainingTime", -1);
        kcalPerMin = intent.getDoubleExtra("kcalPerMin", -1);
        previousActivity = intent.getStringExtra("previousActivity");

        TimeStamp time = new TimeStamp(DateGenerator.getSelectedDate(), trainingTimeStamp);
        newTimeStamp = time.getNewTimeStamp();

        Log.i(TAG, "getIntentFromPreviousActiity: \n" + "trainingID: " + trainingID + "\n" + "trainingTimeStamp: " + trainingTimeStamp + "\n" + "trainingTime: " + trainingTime + "\n" + "kcalPerMin: " + kcalPerMin + "\n" + "previousActivity: " + previousActivity + "\n" + "DateGenerator.getSelectedDate(): " + DateGenerator.getSelectedDate() + "\n" + "newTimeStamp: " + newTimeStamp);
    }

    private String timeStampChanger(String trainingTimeStamp)
    {
        if (trainingTimeStamp == null) return timeStamp;
        else return this.trainingTimeStamp;
    }

    public void startProvider(String previousActivity)
    {
        String trainingID = String.valueOf(this.trainingID);
        int userID = SaveSharedPreference.getUserID(this);


        if (previousActivity.equals(TrainingActivity.class.getSimpleName()))
        {
            String params = "?user_id=" + userID + "&date=" + newTimeStamp + "&id=" + trainingID;

            new MyCardio(CardioDetailsActivity.this).execute(URL_CARDIO_SHOW, params);
        }
        if (previousActivity.equals(CardioListActivity.class.getSimpleName()))
        {
            String params = "?id=" + trainingID;

            new MyCardio(CardioDetailsActivity.this).execute(URL_CARDIO_SHOW, params);
        }
    }

    private int setOnCheckedChangeListener()
    {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    checkBox.setText(R.string.done);
                }
                else
                {
                    checkBox.setText(R.string.not_done);
                }
            }
        });

        if (checkBox.isChecked())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public void onTrainingChangerListener(int i)
    {
        setOnCheckedChangeListener();
        if (i == 0)
        {
            checkBox.setChecked(false);
            checkBox.setText(R.string.not_done);
        }
        else
        {
            checkBox.setChecked(true);
            checkBox.setText(R.string.done);
        }
    }

    /**
     * Those are informations gathered from another application thread
     *
     * @param context  actual context
     * @param training training object
     */
    public void load(Context context, Training training)
    {

        // need to findView once again because in loadInput() are loaded in the Main Thread
        tvName = ((Activity) context).findViewById(R.id.textViewCardioName);
        checkBox = ((Activity) context).findViewById(R.id.checkBox);
        editText = ((Activity) context).findViewById(R.id.editTextNotepadCardio);

        if (previousActivity.equals(TrainingActivity.class.getSimpleName()))
        {
            TimerCardio timer = new TimerCardio(context);
            timer.seekbar();
            timer.setSeekbarProgress(training.getTime() * 60 * 1000);
            timer.setBurnedTextView(training.getKcal());
        }
        else if (previousActivity.equals(CardioListActivity.class.getSimpleName()))
        {
            TimerCardio timer = new TimerCardio(context);
            timer.seekbar();
            timer.setSeekbarProgress(900_000);
            timer.setBurnedTextView(training.getKcal());
        }

        tvName.setText(training.getName());
        editText.setText(training.getNotepad());
    }

    class MyCardio extends HTTPService
    {
        private Context context;

        public MyCardio(Context context)
        {
            super(context);
            this.context = context;
        }

        @Override
        public void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try
            {
                JSONObject jsonObject = new JSONObject(s);

                Log.d(TAG, "onResponse: " + jsonObject);

                String name;
                double calories;
                int done = -1;
                int time = -1;
                String notepad = "";

                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                if (jsonArray.length() > 0)
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);

                        name = object.getString(RestAPI.DB_CARDIO_NAME);
                        calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);
                        notepad = object.getString(RestAPI.DB_CARDIO_NOTEPAD);

                        if (!object.getString(RestAPI.DB_CARDIO_TIME).equals("null"))
                        {
                            time = object.getInt(RestAPI.DB_CARDIO_TIME);
                        }

                        if (!object.getString(DB_CARDIO_DONE).equals("null"))
                        {
                            done = object.getInt(DB_CARDIO_DONE);
                        }

                        if (!object.getString(DB_CARDIO_NOTEPAD).equals("null"))
                        {
                            notepad = object.getString(DB_CARDIO_NOTEPAD);
                        }

                        Training t = new Training.Builder().name(name).kcal(calories).done(done).time(time).notepad(notepad).build();

                        load(context, t);
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

}
