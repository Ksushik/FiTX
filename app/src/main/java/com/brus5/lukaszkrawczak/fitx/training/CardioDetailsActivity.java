package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStampReplacer;
import com.brus5.lukaszkrawczak.fitx.dto.TrainingDTO;
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
import java.util.Map;

public class CardioDetailsActivity extends AppCompatActivity implements View.OnClickListener, DefaultView
{
    private static final String TAG = "CardioDetailsAct";
    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private String trainingTimeStamp, previousActivity, newTimeStamp;
    private int trainingID, trainingTime;
    private EditText etNotepad;
    private TextView tvName;
    private CheckBox checkBox;
    private Timer timer;
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

        timer = new Timer(this);
        timer.seekBarTimer();
        getPreviousActivity(previousActivity);

        final String url = RestAPI.MAIN + "images/cardio/" + trainingID + ".jpg";
        new ImageLoader(CardioDetailsActivity.this, R.id.imageViewCardio, R.id.progressBarCardioDetails, url);
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_cardio:
                if (previousActivity.equals( TrainingActivity.class.getSimpleName() ))
                {
                    Toast.makeText(this, R.string.training_updated, Toast.LENGTH_SHORT).show();

                    TrainingService updateTraining = new TrainingService();
                    updateTraining.CardioUpdate(saveDTO(),CardioDetailsActivity.this);
                    finish();
                }

                else if (previousActivity.equals( CardioListActivity.class.getSimpleName() ))
                {
                    Toast.makeText(this, R.string.training_inserted, Toast.LENGTH_SHORT).show();

                    TrainingService acceptService = new TrainingService();
                    acceptService.CardioInsert(saveDTO(), CardioDetailsActivity.this);
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

                TrainingService deleteService = new TrainingService();
                deleteService.CaardioDelete(saveDTO(),CardioDetailsActivity.this);
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
        etNotepad = findViewById(R.id.editTextNotepadCardio);

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

    @SuppressLint("LongLogTag")
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

        Log.i(TAG, "trainingID: "           + trainingID);
        Log.i(TAG, "trainingTimeStamp: "    + trainingTimeStamp);
        Log.i(TAG, "trainingTime: "         + trainingTime);
        Log.i(TAG, "kcalPerMin: "           + kcalPerMin);
        Log.i(TAG, "previousActivity: "     + previousActivity);
        Log.i(TAG, "getSelectedDate: " + DateGenerator.getSelectedDate());
        Log.i(TAG, "newTimeStamp: "         + newTimeStamp);

    }

    private String timeStampChanger(String trainingTimeStamp)
    {
        if (trainingTimeStamp == null) return timeStamp;
        else return this.trainingTimeStamp;
    }

    private void getPreviousActivity(String previousActivity)
    {
        if (previousActivity.equals( TrainingActivity       .class.getSimpleName() ))
        {
            detailsAsynchTask(CardioDetailsActivity.this);
            int iMiliseconds = trainingTime * 60 * 1000;
            timer.convertSetTimeBig(iMiliseconds);

        }
        if (previousActivity.equals( CardioListActivity     .class.getSimpleName() ))
        {
            cardioAsynch(CardioDetailsActivity.this);
            timer.convertSetTimeBig(1_800_000);
        }

    }

    private void detailsAsynchTask(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_SHOW, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                    String name;
                    double calories;
                    int done;

                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    if (jsonArray.length() > 0)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            name = object.getString(RestAPI.DB_CARDIO_NAME);
                            calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);
                            done = object.getInt(RestAPI.DB_CARDIO_DONE);

                            Log.e(TAG, "done: " + done);

                            String trainingName = name.substring(0, 1).toUpperCase() + name.substring(1);

                            tvName.setText(trainingName);

                            CardioDetailsActivity.this.onTrainingChangerListener(done);

                            timer.setBurned(calories);
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ctx, R.string.connection_error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_CARDIO_ID, String.valueOf(trainingID));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(CardioDetailsActivity.this)));
                params.put(RestAPI.DB_CARDIO_DATE, trainingTimeStamp);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void cardioAsynch(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_SHOW, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                    String name;
                    double calories;

                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    if (jsonArray.length() > 0)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            name = object.getString(RestAPI.DB_CARDIO_NAME);
                            calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);

                            String trainingName = name.substring(0, 1).toUpperCase() + name.substring(1);

                            tvName.setText(trainingName);


                            timer.setBurned(calories);
                        }
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(ctx, R.string.connection_error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_CARDIO_ID, String.valueOf(trainingID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.floatingButtonStartPause:
                Log.i(TAG, "onClick: play" );
                if (timer.timerRunning)
                {
                    timer.pauseTimer();
                }
                else
                {
                    timer.startTimer();
                }
                break;
            case R.id.buttonResetTimer:
                Log.i(TAG, "onClick: reset" );
                timer.resetTimer();
                break;
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

    private void onTrainingChangerListener(int i)
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

    private TrainingDTO saveDTO()
    {
        TrainingDTO dto = new TrainingDTO();
        dto.setTrainingID(trainingID);
        dto.setTrainingDone(setOnCheckedChangeListener());
        dto.setUserID(SaveSharedPreference.getUserID(CardioDetailsActivity.this));
        dto.setTrainingNotepad(etNotepad.getText().toString());
        dto.setTrainingTimeStamp(newTimeStamp);
        dto.setTrainingTime(timer.START_TIME_IN_MILLIS / 1000 / 60);

        Log.i(TAG, "saveDTO: " + dto.toString());

        return dto;
    }
}
