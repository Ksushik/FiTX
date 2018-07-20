package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.DTO.TrainingDTO;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TrainingDetailsActivity";
    private LinearLayout container;
    private int trainingID;
    private String trainingTimeStamp, trainingTarget, previousActivity;
    private ImageView imageViewTraining, imageViewTraining2;
    private EditText editTextTrainingExerciseShow;
    private TextView textViewExerciseName;

    @SuppressLint("SimpleDateFormat")
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    TrainingInflater inflater = new TrainingInflater(TrainingDetailsActivity.this);

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

        Log.i(TAG, "onCreate: " + url);
        Log.i(TAG, "onCreate: " + url2);

        loadImageFromUrl(url);
        loadImageFromUrl2(url2);

        previousActivity(previousActivity);

    }

    private void previousActivity(String previousActivity) {
        if (previousActivity.equals("TrainingActivity")){
            asynchTask(TrainingDetailsActivity.this);
        }
        else if (previousActivity.equals("TrainingListActivity")){
            Log.i(TAG, "previousActivity: " + previousActivity);
        }
    }

    private void loadInput() {
        container = findViewById(R.id.container);

        textViewExerciseName = findViewById(R.id.textViewExerciseName);
        editTextTrainingExerciseShow = findViewById(R.id.editTextTrainingExerciseShow);
        editTextTrainingExerciseShow.clearFocus();
        editTextTrainingExerciseShow.didTouchFocusSelect();
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewTraining2 = findViewById(R.id.imageViewTraining1);
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

    private void loadImageFromUrl(String url) {
        Picasso.with(TrainingDetailsActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .into(imageViewTraining, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
//                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        isError();
                    }
                });
    }
    private void loadImageFromUrl2(String url) {
        Picasso.with(TrainingDetailsActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .into(imageViewTraining2, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
//                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        isError();
                    }
                });
    }

    private void isError() {
        Toast.makeText(TrainingDetailsActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
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
        return super.onCreateOptionsMenu(menu);
    }

    private void asynchTask(final Context ctx){
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

                            int trainingID;
                            int done;
                            int rest;
                            String reps = "";
                            String weight = "";
                            String timeStamp;
                            String notepad = "";

                            String exerciseName;
                            String trainingName = "";

                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    exerciseName = object.getString(RestApiNames.DB_EXERCISE_NAME);


                                    trainingName = exerciseName.substring(0,1).toUpperCase() + exerciseName.substring(1);
                                }
                            }

                            JSONArray trainings_info_array = jsonObject.getJSONArray("trainings_info");
                            if (trainings_info_array.length() > 0) {
                                for (int i = 0; i < trainings_info_array.length(); i++) {
                                    JSONObject object = trainings_info_array.getJSONObject(i);
                                    reps = object.getString(RestApiNames.DB_EXERCISE_REPS);
                                    weight = object.getString(RestApiNames.DB_EXERCISE_WEIGHT);
                                    notepad = object.getString(RestApiNames.DB_EXERCISE_NOTEPAD);

                                    String mReps = reps.replaceAll("\\p{Punct}"," ");
                                    String[] mReps_table = mReps.split("\\s+");

                                    inflater.setReps(reps);
                                    inflater.setWeight(weight);

                                    trainingSetsGenerator(mReps_table.length);
                                    editTextTrainingExerciseShow.setText(notepad);
                                }
                            }
                            /* End */

                            textViewExerciseName.setText(trainingName);

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

    private void asynchTaskOnlyTrainingInfo(final Context ctx){
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

                            int trainingID;
                            int done;
                            int rest;
                            String reps = "";
                            String weight = "";
                            String timeStamp;
                            String notepad = "";

                            String exerciseName;
                            String trainingName = "";

                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    exerciseName = object.getString(RestApiNames.DB_EXERCISE_NAME);


                                    trainingName = exerciseName.substring(0,1).toUpperCase() + exerciseName.substring(1);
                                }
                            }

                            JSONArray trainings_info_array = jsonObject.getJSONArray("trainings_info");
                            if (trainings_info_array.length() > 0) {
                                for (int i = 0; i < trainings_info_array.length(); i++) {
                                    JSONObject object = trainings_info_array.getJSONObject(i);
                                    reps = object.getString(RestApiNames.DB_EXERCISE_REPS);
                                    weight = object.getString(RestApiNames.DB_EXERCISE_WEIGHT);
                                    notepad = object.getString(RestApiNames.DB_EXERCISE_NOTEPAD);

                                    String mReps = reps.replaceAll("\\p{Punct}"," ");
                                    String[] mReps_table = mReps.split("\\s+");

                                    inflater.setReps(reps);
                                    inflater.setWeight(weight);

                                    trainingSetsGenerator(mReps_table.length);
                                    editTextTrainingExerciseShow.setText(notepad);
                                }
                            }
                            /* End */

                            textViewExerciseName.setText(trainingName);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTrainingShowDetails:
                if (inflater.isValid()){

                    Log.i(TAG, "onClick: " + "\nisValid: " + inflater.isValid() + inflater.printResult());

                }
                break;
            case R.id.buttonTrainingDetailsAdd:
                trainingGenerateNextSet();
                break;

        }
    }

    private void trainingSetsGenerator(int seriesNumber){
        for (int i = 0; i < seriesNumber; i++) {
            container.addView(inflater.trainingSetGenerator());
        }
    }

    private void trainingGenerateNextSet(){
        container.addView(inflater.trainingSetGenerator());
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save_exercise:

                if (previousActivity.equals("TrainingActivity")) {
                    Log.i(TAG, "onOptionsItemSelected: TrainingActivity");
                    TrainingService updateTraining = new TrainingService();
                    updateTraining.TrainingUpdate(generateDTO(), TrainingDetailsActivity.this);
                }

                else if (previousActivity.equals("TrainingListActivity")){
                    Log.i(TAG, "onOptionsItemSelected: TrainingListActivity");
                    TrainingService acceptService = new TrainingService();
                    acceptService.TrainingInsert(generateDTO(), TrainingDetailsActivity.this);
                }
                break;
            case R.id.menu_delete_exercise:
                Log.i(TAG, "onOptionsItemSelected: delete");
                TrainingDTO deleteDTO = new TrainingDTO();
                deleteDTO.trainingID = String.valueOf(trainingID);
                deleteDTO.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
                deleteDTO.trainingTimeStamp = timeStamp();
                deleteDTO.printStatus();

                TrainingService deleteService = new TrainingService();
                deleteService.TrainingDelete(deleteDTO,TrainingDetailsActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private TrainingDTO generateDTO(){
        TrainingDTO dto = new TrainingDTO();
        dto.trainingID = String.valueOf(trainingID);
        dto.trainingDone = "0";
        dto.trainingRestTime = "90";
        dto.trainingReps = inflater.getReps();
        dto.trainingWeight = inflater.getWeight();
        dto.userName = SaveSharedPreference.getUserName(TrainingDetailsActivity.this);
        dto.trainingTimeStamp = timeStamp();
        dto.trainingNotepad = editTextTrainingExerciseShow.getText().toString();
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

}