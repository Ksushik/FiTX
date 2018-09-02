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

import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStampReplacer;
import com.brus5.lukaszkrawczak.fitx.diet.DietService;
import com.brus5.lukaszkrawczak.fitx.training.addons.TimerCardio;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.ImageLoader;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

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
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_DELETE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_INSERT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_CARDIO_UPDATE;

@SuppressLint("SimpleDateFormat")
public class CardioDetailsActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "CardioDetailsActivity";
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private String trainingTimeStamp, previousActivity, newTimeStamp;
    private int trainingID, trainingTime;
    private EditText etNotepad;
    public TextView tvName, textViewBurned;
    private CheckBox checkBox;
    public TimerCardio timer;
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

        timer = new TimerCardio(this, this);
        getPreviousActivity(previousActivity);

        final String url = RestAPI.MAIN + "images/cardio/" + trainingID + ".jpg";
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
                    params.put(DB_CARDIO_NOTEPAD, etNotepad.getText().toString());
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
                    params.put(DB_CARDIO_NOTEPAD, etNotepad.getText().toString());
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


    public void loadInput()
    {
        tvName = findViewById(R.id.textViewCardioName);
        textViewBurned = findViewById(R.id.textViewBurned);
        etNotepad = findViewById(R.id.editTextNotepadCardio);

        checkBox = findViewById(R.id.checkBox);

        constraintLayout = findViewById(R.id.constraingLayoutCardioDetails);
        constraintLayout.requestFocus();
    }

    private void loadInputAsync(Context context)
    {
        tvName = ((Activity) context).findViewById(R.id.textViewCardioName);
        checkBox = ((Activity) context).findViewById(R.id.checkBox);
    }


    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(CardioDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarCardio);
        activityView.showBackButton();
    }

    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID", -1);
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        trainingTimeStamp = timeStampChanger(trainingTimeStamp);
        trainingTime = intent.getIntExtra("trainingTime", -1);
        kcalPerMin = intent.getDoubleExtra("kcalPerMin", -1);
        previousActivity = intent.getStringExtra("previousActivity");


        TimeStampReplacer time = new TimeStampReplacer(DateGenerator.getSelectedDate(), trainingTimeStamp);
        newTimeStamp = time.getNewTimeStamp();

        Log.i(TAG, "getIntentFromPreviousActiity: \n" + "trainingID: " + trainingID + "\n" + "trainingTimeStamp: " + trainingTimeStamp + "\n" + "trainingTime: " + trainingTime + "\n" + "kcalPerMin: " + kcalPerMin + "\n" + "previousActivity: " + previousActivity + "\n" + "DateGenerator.getSelectedDate(): " + DateGenerator.getSelectedDate() + "\n" + "newTimeStamp: " + newTimeStamp);
    }

    private String timeStampChanger(String trainingTimeStamp)
    {
        if (trainingTimeStamp == null) return timeStamp;
        else return this.trainingTimeStamp;
    }

    private void getPreviousActivity(String previousActivity)
    {
        if (previousActivity.equals(TrainingActivity.class.getSimpleName()))
        {
            timer.seekBarTimer();
            timer.setBurned(kcalPerMin);
            int iMiliseconds = trainingTime * 60 * 1000;
            timer.convertSetTimeBig(iMiliseconds);
            onTrainingChangerListener(0);
            new Provider(CardioDetailsActivity.this, CardioDetailsActivity.this).load(String.valueOf(trainingID), newTimeStamp);

        }
        if (previousActivity.equals(CardioListActivity.class.getSimpleName()))
        {
            new Provider(CardioDetailsActivity.this, CardioDetailsActivity.this).load(String.valueOf(trainingID));
        }

    }

    //    private void detailsAsynchTask(final Context ctx)
    //    {
    //        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_SHOW, new Response.Listener<String>()
    //        {
    //            @Override
    //            public void onResponse(String response)
    //            {
    //                try
    //                {
    //                    JSONObject jsonObject = new JSONObject(response);
    //
    //                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));
    //
    //                    String name;
    //                    double calories;
    //                    int done;
    //
    //                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
    //                    if (jsonArray.length() > 0)
    //                    {
    //                        for (int i = 0; i < jsonArray.length(); i++)
    //                        {
    //                            JSONObject object = jsonArray.getJSONObject(i);
    //
    //                            name = object.getString(RestAPI.DB_CARDIO_NAME);
    //                            calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);
    //                            done = object.getInt(DB_CARDIO_DONE);
    //
    //                            Log.e(TAG, "done: " + done);
    //
    //                            String trainingName = name.substring(0, 1).toUpperCase() + name.substring(1);
    //
    //                            tvName.setText(trainingName);
    //
    //                            CardioDetailsActivity.this.onTrainingChangerListener(done);
    //
    //                            timer.setBurned(calories);
    //                        }
    //                    }
    //                } catch (JSONException e)
    //                {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }, new Response.ErrorListener()
    //        {
    //            @Override
    //            public void onErrorResponse(VolleyError error)
    //            {
    //                Toast.makeText(ctx, R.string.connection_error, Toast.LENGTH_SHORT).show();
    //                Log.e(TAG, "onErrorResponse: Error" + error);
    //            }
    //        })
    //        {
    //            @Override
    //            protected Map<String, String> getParams()
    //            {
    //                HashMap<String, String> params = new HashMap<>();
    //                params.put(DB_CARDIO_ID, String.valueOf(trainingID));
    //                params.put(DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(CardioDetailsActivity.this)));
    //                params.put(DB_CARDIO_DATE, trainingTimeStamp);
    //                return params;
    //            }
    //        };
    //        RequestQueue queue = Volley.newRequestQueue(ctx);
    //        queue.add(strRequest);
    //    }

    //    private void cardioAsynch(final Context ctx)
    //    {
    //        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_SHOW, new Response.Listener<String>()
    //        {
    //            @Override
    //            public void onResponse(String response)
    //            {
    //                try
    //                {
    //                    JSONObject jsonObject = new JSONObject(response);
    //
    //                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));
    //
    //                    String name;
    //                    double calories;
    //
    //                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
    //                    if (jsonArray.length() > 0)
    //                    {
    //                        for (int i = 0; i < jsonArray.length(); i++)
    //                        {
    //                            JSONObject object = jsonArray.getJSONObject(i);
    //
    //                            name = object.getString(RestAPI.DB_CARDIO_NAME);
    //                            calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);
    //
    //                            String trainingName = name.substring(0, 1).toUpperCase() + name.substring(1);
    //
    //                            tvName.setText(trainingName);
    //
    //
    //                            timer.setBurned(calories);
    //                        }
    //                    }
    //                } catch (JSONException e)
    //                {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }, new Response.ErrorListener()
    //        {
    //            @Override
    //            public void onErrorResponse(VolleyError error)
    //            {
    //                Toast.makeText(ctx, R.string.connection_error, Toast.LENGTH_SHORT).show();
    //                Log.e(TAG, "onErrorResponse: Error" + error);
    //            }
    //        })
    //        {
    //            @Override
    //            protected Map<String, String> getParams()
    //            {
    //                HashMap<String, String> params = new HashMap<>();
    //                params.put(DB_CARDIO_ID, String.valueOf(trainingID));
    //                return params;
    //            }
    //        };
    //        RequestQueue queue = Volley.newRequestQueue(ctx);
    //        queue.add(strRequest);
    //    }

    //    @Override
    //    public void onClick(View v)
    //    {
    //        switch (v.getId()){
    //            case R.id.floatingButtonStartPause:
    //                Log.i(TAG, "onClick: play" );
    //                if (timer.timerRunning)
    //                {
    //                    timer.pauseTimer();
    //                }
    //                else
    //                {
    //                    timer.startTimer();
    //                }
    //                break;
    //            case R.id.buttonResetTimer:
    //                Log.i(TAG, "onClick: reset" );
    //                timer.resetTimer();
    //                break;
    //        }
    //    }

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
     * @param activity actual activity
     * @param context  actual context
     * @param training training object
     */
    public void load(Activity activity, Context context, Training training)
    {

        Log.d(TAG, "load() called with: training = [" + training + "], context = [" + context + "]" + "\n" + "trainingName: " + training.getName() + "\n" + "calories: " + training.getKcal() + "\n" + "done: " + training.getDone() + "\n" + "time: " + training.getTime());

        loadInputAsync(context);

        timer = new TimerCardio(activity, context);

        if (training.getTime() != -1)
        {
            timer.convertSetTimeBig(training.getTime() * 60 * 1000);
            timer.setBurned(training.getKcal());
        }
        else
        {
            timer.convertSetTimeBig(600_000);
            timer.setBurned(training.getKcal());
        }

        if (training.getDone() != -1)
        {
            onTrainingChangerListener(training.getDone());
        }

        timer.seekBarTimer(); // this method must be at the end of the set
        tvName.setText(training.getName());
    }


}
