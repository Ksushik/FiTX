package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Validator.CharacterLimit;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Converter.NameConverter;
import com.brus5.lukaszkrawczak.fitx.DTO.TrainingDTO;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "TrainingDetailsA";
    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private String trainingTimeStamp, trainingTarget, previousActivity;
    private LinearLayout linearLayout;
    private int trainingID;
    private ImageView imgTrainingL, imgTrainingR;
    private EditText etNotepad;
    private TextView tvName, tvDetails, tvCharsLeft;
    private CheckBox checkBox;
    private TrainingInflater inflater = new TrainingInflater(TrainingDetailsActivity.this);
    private Timer timer;
    private CharacterLimit characterLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_4_details);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();
        String url = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_1" + ".jpg";
        String url2 = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_2" + ".jpg";
        loadImages(imgTrainingL, url);
        loadImages(imgTrainingR, url2);
        timer = new Timer(this);
        timer.seekBarTimer();
        getPreviousActivity(previousActivity);
        characterLimit = new CharacterLimit(etNotepad, tvCharsLeft, 280);
        etNotepad.addTextChangedListener(characterLimit);
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_exercise:
                if (previousActivity.equals("TrainingActivity") && (inflater.isValid()) && (characterLimit.isLimit()))
                {
                    TrainingService updateTraining = new TrainingService();
                    updateTraining.TrainingUpdate(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }

                else if (previousActivity.equals("TrainingListActivity") && (inflater.isValid()) && (characterLimit.isLimit()))
                {
                    TrainingService acceptService = new TrainingService();
                    acceptService.TrainingInsert(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }
                else
                {
                    Configuration cfg = new Configuration();
                    cfg.showError(TrainingDetailsActivity.this);
                }
                break;
            case R.id.menu_delete_exercise:
                TrainingService deleteService = new TrainingService();
                deleteService.TrainingDelete(deleteDto(), TrainingDetailsActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private TrainingDTO saveDTO()
    {
        Log.i(TAG, "onClick: " + "\nisValid: " + inflater.isValid() + inflater.printResult());
        TrainingDTO dto = new TrainingDTO();
        dto.trainingID = String.valueOf(trainingID);
        dto.trainingDone = String.valueOf(setOnCheckedChangeListener());
        dto.trainingRestTime = String.valueOf(timer.START_TIME_IN_MILLIS);
        dto.trainingReps = inflater.getReps();
        dto.trainingWeight = inflater.getWeight();
        dto.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
        dto.trainingTimeStamp = setTimeStamp();
        dto.trainingNotepad = etNotepad.getText().toString();
        dto.printStatus();
        return dto;
    }

    private TrainingDTO deleteDto()
    {
        TrainingDTO dto = new TrainingDTO();
        dto.trainingID = String.valueOf(trainingID);
        dto.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
        dto.trainingTimeStamp = setTimeStamp();
        dto.printStatus();
        return dto;
    }

    private String setTimeStamp()
    {
        if (previousActivity.equals("TrainingActivity"))
        {
            return trainingTimeStamp;
        }
        else
        {
            return timeStamp;
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

    private void getPreviousActivity(String previousActivity)
    {
        if (previousActivity.equals("TrainingActivity"))
        {
            getUserTrainingDetailsAsynch(TrainingDetailsActivity.this);
            getTrainingNameAsynch(TrainingDetailsActivity.this);
        }
        else if (previousActivity.equals("TrainingListActivity"))
        {
            getTrainingNameAsynch(TrainingDetailsActivity.this);
            timer.seekBar.setProgress(5);
        }
    }

    private void loadInput()
    {
        checkBox = findViewById(R.id.checkBox);
        linearLayout = findViewById(R.id.container);
        tvDetails = findViewById(R.id.textViewDetails);
        tvCharsLeft = findViewById(R.id.textViewCharsLeft);
        tvName = findViewById(R.id.textViewExerciseName);
        etNotepad = findViewById(R.id.editTextNotepad);
        etNotepad.clearFocus();
        etNotepad.didTouchFocusSelect();

        imgTrainingL = findViewById(R.id.imageViewTraining);
        imgTrainingR = findViewById(R.id.imageViewTraining1);
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID", -1);
        trainingTarget = intent.getStringExtra("trainingTarget");
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        previousActivity = intent.getStringExtra("previousActivity");

        Log.e(TAG, "onCreate: " + trainingID);
        Log.e(TAG, "onCreate: " + trainingTimeStamp);
        Log.e(TAG, "onCreate: " + trainingTarget);
        Log.e(TAG, "onCreate: " + previousActivity);
    }

    private void loadImages(final ImageView imageView, String url)
    {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(0)
                .cornerRadiusDp(5)
                .oval(false)
                .build();


        Picasso.with(TrainingDetailsActivity.this).load(url).placeholder(null).transform(transformation)
                .error(R.mipmap.ic_launcher_error)
                .into(imageView, new com.squareup.picasso.Callback()
                {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError()
                    {
                        Configuration cfg = new Configuration();
                        cfg.showError(TrainingDetailsActivity.this);
                    }
                });


    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingDetailsActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingExerciseShow);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_4_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_exercise);
        if (previousActivity.equals("TrainingListActivity"))
        {
            item.setVisible(false);
        }
        else
        {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void trainingSetsGenerator(int seriesNumber)
    {
        for (int i = 0; i < seriesNumber; i++)
        {
            linearLayout.addView(inflater.trainingSetGenerator());
        }
    }

    private void trainingGenerateNextSet()
    {
        linearLayout.addView(inflater.trainingSetGenerator());
    }

    private void getUserTrainingDetailsAsynch(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_TRAINING_URL,
            new Response.Listener<String>()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                        int done = 0;
                        String rest = "";
                        String reps;
                        String weight;
                        String notepad = "";

                        String exerciseName;

                        NameConverter nameUpperCase = new NameConverter();

                        JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            exerciseName = object.getString(RestApiNames.DB_EXERCISE_NAME);
                            nameUpperCase.setName(exerciseName);
                        }


                        JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

                        for (int i = 0; i < trainings_info.length(); i++)
                        {
                            JSONObject tr_info = trainings_info.getJSONObject(i);
                            done = tr_info.getInt(RestApiNames.DB_EXERCISE_DONE);
                            rest = tr_info.getString(RestApiNames.DB_EXERCISE_REST_TIME);
                            reps = tr_info.getString(RestApiNames.DB_EXERCISE_REPS);
                            weight = tr_info.getString(RestApiNames.DB_EXERCISE_WEIGHT);
                            notepad = tr_info.getString(RestApiNames.DB_EXERCISE_NOTEPAD);

                            String mReps = reps.replaceAll("\\p{Punct}", " ");
                            String[] mReps_table = mReps.split("\\s+");

                            inflater.setReps(reps);
                            inflater.setWeight(weight);

                            trainingSetsGenerator(mReps_table.length);
                        }


                        tvName.setText(nameUpperCase.getName());
                        onTrainingChangerListener(done);
                        etNotepad.setText(notepad);
                        timer.convertSetTime(Integer.valueOf(rest));

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(ctx, Configuration.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: Error" + error);
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestApiNames.DB_EXERCISE_ID, String.valueOf(trainingID));
                params.put(RestApiNames.DB_EXERCISE_DATE, trainingTimeStamp);
                params.put(RestApiNames.DB_USERNAME, SaveSharedPreference.getUserName(ctx));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void getTrainingNameAsynch(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_TRAINING_DETAILS,
            new Response.Listener<String>()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.d(TAG, "onResponse: " + jsonObject.toString(1));
                        String name = "";
                        JSONArray server_response = jsonObject.getJSONArray("server_response");
                        NameConverter nameUpperCase = new NameConverter();
                        for (int i = 0; i < server_response.length(); i++)
                        {
                            JSONObject object = server_response.getJSONObject(0);
                            name = object.getString(RestApiNames.DB_EXERCISE_NAME);
                        }


                        nameUpperCase.setName(name);
                        tvName.setText(nameUpperCase.getName());
                        setOnCheckedChangeListener();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(ctx, Configuration.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: Error" + error);
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestApiNames.DB_EXERCISE_ID, String.valueOf(trainingID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void getTrainingDescAsynch(final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_TRAINING_DESCRIPTION,
            new Response.Listener<String>()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response)
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);

                        Log.d(TAG, "onResponse: " + jsonObject.toString(1));


                        String description = "";

                        JSONArray server_response = jsonObject.getJSONArray("server_response");

                        for (int i = 0; i < server_response.length(); i++)
                        {
                            JSONObject object = server_response.getJSONObject(0);
                            description = object.getString(RestApiNames.DB_EXERCISE_DESCRITION);
                        }

                        tvDetails.setText(description);
                        setOnCheckedChangeListener();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener()
            {
                @SuppressLint("LongLogTag")
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(context, Configuration.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: Error" + error);
                }
            }
        )
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestApiNames.DB_EXERCISE_ID, String.valueOf(trainingID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonAddSeries:
                trainingGenerateNextSet();
                break;
            case R.id.buttonShowDescription:
                (view.findViewById(R.id.buttonShowDescription)).setVisibility(View.INVISIBLE);
                getTrainingDescAsynch(TrainingDetailsActivity.this);
                tvDetails.setVisibility(View.VISIBLE);
                break;
            case R.id.floatingButtonStartPause:
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
                timer.resetTimer();
                break;
        }
    }
}