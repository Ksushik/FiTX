package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Converter.NameConverter;
import com.brus5.lukaszkrawczak.fitx.DTO.TrainingDTO;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TrainingDetailsActivity";
    private LinearLayout container;
    private int trainingID;
    private String trainingTimeStamp, trainingTarget, previousActivity;
    private ImageView imageViewTraining, imageViewTraining2;
    private EditText editTextTrainingExerciseShow;
    private TextView textViewExerciseName,textViewShowTrainingDetails,textViewTime;
    private CheckBox checkBoxDone;
    private SeekBar seekBarTimer;
    private CountDownTimer countDownTimer;
    private Button buttonStartStopTimer, buttonResetTimer;
    private TrainingInflater inflater = new TrainingInflater(TrainingDetailsActivity.this);
    private long START_TIME_IN_MILLIS;
    private long timeLeftInMillis;
    private boolean timerRunning;
    private ProgressBar progressBarCircle;

    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();

        String url = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_1" + ".jpg";
        String url2 = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_2" + ".jpg";

        loadImages(imageViewTraining, url);
        loadImages(imageViewTraining2, url2);

        seekBarTimer();

        previousActivity(previousActivity);

    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save_exercise:
                if (previousActivity.equals("TrainingActivity") && (inflater.isValid())) {
                    TrainingService updateTraining = new TrainingService();
                    updateTraining.TrainingUpdate(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }

                else if (previousActivity.equals("TrainingListActivity") && (inflater.isValid())){
                    TrainingService acceptService = new TrainingService();
                    acceptService.TrainingInsert(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }
                else {
                    Configuration cfg = new Configuration();
                    cfg.showToastError(TrainingDetailsActivity.this);
                }
                break;
            case R.id.menu_delete_exercise:
                TrainingService deleteService = new TrainingService();
                deleteService.TrainingDelete(deleteDto(),TrainingDetailsActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private TrainingDTO saveDTO(){
        Log.i(TAG, "onClick: " + "\nisValid: " + inflater.isValid() + inflater.printResult());
        TrainingDTO dto = new TrainingDTO();
        dto.trainingID = String.valueOf(trainingID);
        dto.trainingDone = String.valueOf(trainingDone());
        dto.trainingRestTime = String.valueOf(START_TIME_IN_MILLIS);
        dto.trainingReps = inflater.getReps();
        dto.trainingWeight = inflater.getWeight();
        dto.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
        dto.trainingTimeStamp = timeStamp();
        dto.trainingNotepad = editTextTrainingExerciseShow.getText().toString();
        dto.printStatus();
        return dto;
    }

    private TrainingDTO deleteDto(){
        TrainingDTO dto = new TrainingDTO();
        dto.trainingID = String.valueOf(trainingID);
        dto.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
        dto.trainingTimeStamp = timeStamp();
        dto.printStatus();
        return dto;
    }

    private String timeStamp(){
        if (previousActivity.equals("TrainingActivity")){
            return trainingTimeStamp;
        }
        else {
            return timeStamp;
        }
    }

    private void trainingDone(int i){
        trainingDone();
        if (i == 0) {
            checkBoxDone.setChecked(false);
            checkBoxDone.setText(R.string.not_done);
        }
        else {
            checkBoxDone.setChecked(true);
            checkBoxDone.setText(R.string.done);
        }
    }

    private int trainingDone(){
        checkBoxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    checkBoxDone.setText(R.string.done);
                }
                else {
                    checkBoxDone.setText(R.string.not_done);
                }
            }
        });

        if (checkBoxDone.isChecked()) {
            return 1;
        }
        else {
            return 0;
        }
    }

    private void previousActivity(String previousActivity) {
        if (previousActivity.equals("TrainingActivity")){
            asynchTaskLoadUserTraining(TrainingDetailsActivity.this);

        }
        else if (previousActivity.equals("TrainingListActivity")){
            asynchTaskLoadTrainingInfo(TrainingDetailsActivity.this);
            seekBarTimer.setProgress(5);
        }
    }

    private void loadInput() {
        textViewTime = findViewById(R.id.textViewTime);
        seekBarTimer = findViewById(R.id.seekBarTimer);
        checkBoxDone = findViewById(R.id.checkBoxDone);
        container = findViewById(R.id.container);
        textViewShowTrainingDetails = findViewById(R.id.textViewShowTrainingDetails);
        textViewExerciseName = findViewById(R.id.textViewExerciseName);
        editTextTrainingExerciseShow = findViewById(R.id.editTextTrainingExerciseShow);
        editTextTrainingExerciseShow.clearFocus();
        editTextTrainingExerciseShow.didTouchFocusSelect();
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewTraining2 = findViewById(R.id.imageViewTraining1);
        buttonStartStopTimer = findViewById(R.id.buttonStartStopTimer);
        buttonResetTimer = findViewById(R.id.buttonResetTimer);
        progressBarCircle = findViewById(R.id.progressBarCircle);
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID",-1);
        trainingTarget = intent.getStringExtra("trainingTarget");
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        previousActivity = intent.getStringExtra("previousActivity");

        Log.e(TAG, "onCreate: "+trainingID);
        Log.e(TAG, "onCreate: "+trainingTimeStamp);
        Log.e(TAG, "onCreate: "+trainingTarget);
        Log.e(TAG, "onCreate: "+previousActivity);
    }

    private void loadImages(ImageView imageView, String url) {
        Picasso.with(TrainingDetailsActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
//                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        Configuration cfg = new Configuration();
                        cfg.showToastError(TrainingDetailsActivity.this);
                    }
                });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingDetailsActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingExerciseShow);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_exercise_add, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_exercise);
        if (previousActivity.equals("TrainingListActivity")){
            item.setVisible(false);
        }
        else {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void asynchTaskLoadUserTraining(final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            /* Getting DietRatio from MySQL */
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d(TAG, "onResponse: "+jsonObject.toString(1));


                            int done = 0;
                            String rest = "";
                            String reps;
                            String weight;
                            String notepad = "";

                            String exerciseName;

                            NameConverter name = new NameConverter();

                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    exerciseName = object.getString(RestApiNames.DB_EXERCISE_NAME);

                                    name.setName(exerciseName);
                                }
                            }

                            JSONArray trainings_info_array = jsonObject.getJSONArray("trainings_info");
                            if (trainings_info_array.length() > 0) {
                                for (int i = 0; i < trainings_info_array.length(); i++) {
                                    JSONObject object = trainings_info_array.getJSONObject(i);
                                    done = object.getInt(RestApiNames.DB_EXERCISE_DONE);
                                    rest = object.getString(RestApiNames.DB_EXERCISE_REST_TIME);
                                    reps = object.getString(RestApiNames.DB_EXERCISE_REPS);
                                    weight = object.getString(RestApiNames.DB_EXERCISE_WEIGHT);
                                    notepad = object.getString(RestApiNames.DB_EXERCISE_NOTEPAD);

                                    String mReps = reps.replaceAll("\\p{Punct}"," ");
                                    String[] mReps_table = mReps.split("\\s+");

                                    inflater.setReps(reps);
                                    inflater.setWeight(weight);

                                    trainingSetsGenerator(mReps_table.length);
                                }
                            }
                            /* End */

                            textViewExerciseName.setText(name.getName());
                            trainingDone(done);
                            editTextTrainingExerciseShow.setText(notepad);
                            convertSetTime(Integer.valueOf(rest));
                        } catch (JSONException e) {
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
                        Log.e(TAG, "onErrorResponse: Error"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_EXERCISE_ID, String.valueOf(trainingID));
                params.put(RestApiNames.DB_EXERCISE_DATE, trainingTimeStamp);
                params.put(RestApiNames.DB_USERNAME, SaveSharedPreference.getUserName(ctx));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void asynchTaskLoadTrainingInfo(final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_NEW_TRAINING,
                new Response.Listener<String>()
                {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            /* Getting DietRatio from MySQL */
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d(TAG, "onResponse: "+jsonObject.toString(1));

                            String exerciseName = "";
                            String description = "";


                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(0);
                                exerciseName = object.getString(RestApiNames.DB_EXERCISE_NAME);
                                description = object.getString(RestApiNames.DB_EXERCISE_DESCRITION);
                            }

                                   String trainingName = exerciseName.substring(0,1).toUpperCase() + exerciseName.substring(1);
                            Log.i(TAG, "trainingName: " + trainingName);
                            /* End */

                            textViewExerciseName.setText(trainingName);
                            textViewShowTrainingDetails.setText(description);
                            trainingDone();
                        } catch (JSONException e) {
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
                        Log.e(TAG, "onErrorResponse: Error"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_EXERCISE_ID, String.valueOf(trainingID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTrainingDetailsAdd:
                trainingGenerateNextSet();
                break;
            case R.id.buttonTrainingShowDetails:
                (v.findViewById(R.id.buttonTrainingShowDetails)).setVisibility(View.INVISIBLE);
                textViewShowTrainingDetails.setVisibility(View.VISIBLE);
                break;
            case R.id.buttonStartStopTimer:
                if (timerRunning){
                    pauseTimer();
                }
                else {
                    startTimer();
                }
                break;
            case R.id.buttonResetTimer:
                resetTimer();
                break;
        }
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        buttonStartStopTimer.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                buttonStartStopTimer.setText(R.string.start);
                buttonStartStopTimer.setVisibility(View.INVISIBLE);
                buttonResetTimer.setVisibility(View.VISIBLE);
                setProgressBarValues();
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();

            }
        }.start();
        timerRunning = true;
        buttonResetTimer.setVisibility(View.INVISIBLE);
        buttonStartStopTimer.setText(R.string.pause);
    }

    private void updateCountDownText() {
        int minutes = (int) timeLeftInMillis / 1000 / 60;
        int seconds = (int) timeLeftInMillis / 1000 % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        textViewTime.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        buttonStartStopTimer.setText(R.string.start);
        buttonResetTimer.setVisibility(View.VISIBLE);
    }

    private void trainingSetsGenerator(int seriesNumber){
        for (int i = 0; i < seriesNumber; i++) {
            container.addView(inflater.trainingSetGenerator());
        }
    }

    private void trainingGenerateNextSet(){
        container.addView(inflater.trainingSetGenerator());
    }

    private void seekBarTimer() {
        seekBarTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onProgressSetTime(progress);
                updateCountDownText();
                resetTimer();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void onProgressSetTime(int progress) {
        switch (progress){
            case 0:
                START_TIME_IN_MILLIS = 15000;
                break;
            case 1:
                START_TIME_IN_MILLIS = 30000;
                break;
            case 2:
                START_TIME_IN_MILLIS = 45000;
                break;
            case 3:
                START_TIME_IN_MILLIS = 60000;
                break;
            case 4:
                START_TIME_IN_MILLIS = 75000;
                break;
            case 5:
                START_TIME_IN_MILLIS = 90000;
                break;
            case 6:
                START_TIME_IN_MILLIS = 105000;
                break;
            case 7:
                START_TIME_IN_MILLIS = 120000;
                break;
            case 8:
                START_TIME_IN_MILLIS = 135000;
                break;
            case 9:
                START_TIME_IN_MILLIS = 150000;
                break;
        }
    }

    private void convertSetTime(int timeInMillis){
        switch (timeInMillis){
            case 15000:
                seekBarTimer.setProgress(0);
                break;
            case 30000:
                seekBarTimer.setProgress(1);
                break;
            case 45000:
                seekBarTimer.setProgress(2);
                break;
            case 60000:
                seekBarTimer.setProgress(3);
                break;
            case 75000:
                seekBarTimer.setProgress(4);
                break;
            case 90000:
                seekBarTimer.setProgress(5);
                break;
            case 105000:
                seekBarTimer.setProgress(6);
                break;
            case 120000:
                seekBarTimer.setProgress(7);
                break;
            case 135000:
                seekBarTimer.setProgress(8);
                break;
            case 150000:
                seekBarTimer.setProgress(9);
                break;
        }
    }
    private void setProgressBarValues() {
        progressBarCircle.setMax((int) START_TIME_IN_MILLIS / 1000);
        progressBarCircle.setProgress((int) START_TIME_IN_MILLIS / 1000);
    }

}