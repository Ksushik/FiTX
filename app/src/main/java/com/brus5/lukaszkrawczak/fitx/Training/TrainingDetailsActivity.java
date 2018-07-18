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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TrainingDetailsActivity";
    private LinearLayout container;
    private int trainingID;
    private String trainingTimeStamp, trainingTarget;
    private ImageView imageViewTraining, imageViewTraining2;
    private EditText editTextTrainingExerciseShow;
    private TextView textViewExerciseName;

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

        asynchTask(TrainingDetailsActivity.this);

    }

    private void loadInput() {
        container = findViewById(R.id.container);

        textViewExerciseName = findViewById(R.id.textViewExerciseName);
        editTextTrainingExerciseShow = findViewById(R.id.editTextTrainingExerciseShow);
        imageViewTraining = findViewById(R.id.imageViewTraining);
        imageViewTraining2 = findViewById(R.id.imageViewTraining1);
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID",-1);
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        trainingTarget = intent.getStringExtra("trainingTarget");

        Log.e(TAG, "onCreate: "+trainingID);
        Log.e(TAG, "onCreate: "+trainingTimeStamp);
        Log.e(TAG, "onCreate: "+trainingTarget);


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

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save_exercise:
                saveExerciseToDB();
                editTextTrainingExerciseShow.getText();
                Log.e(TAG, "save button pressed: "+editTextTrainingExerciseShow.getText());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveExerciseToDB() {
    }

//    private void buttonTrigger(final int itemNum) {
//
//        buttonTrainingDetailsAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                final View addView = layoutInflater.inflate(R.layout.training_details_add_row, null);
//                TextView textViewTrainingDetailsID = addView.findViewById(R.id.textViewTrainingDetailsID);
//
//
//                EditText editTextTrainingRowReps = addView.findViewById(R.id.editTextTrainingRowReps);
//                editTextTrainingRowReps.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(final Editable s) {
//                        final TextView textViewNoEmpty = addView.findViewById(R.id.textViewNoEmpty);
//
//                        if (s.length() == 0) {
//                            textViewNoEmpty.setVisibility(View.VISIBLE);
//                            mapReps.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
//                        } else{
//                            textViewNoEmpty.setVisibility(View.INVISIBLE);
//                            mapReps.put(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()),s.toString());
//                            Log.i(TAG, "onClick: " + mapReps);
//                            }
//                    }
//                });
//                EditText editTextTrainingRowWeight = addView.findViewById(R.id.editTextTrainingRowWeight);
//                editTextTrainingRowWeight.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(final Editable s) {
//                        final TextView textViewNoEmpty1 = addView.findViewById(R.id.textViewNoEmpty1);
//
//                        if (s.length() == 0){
//                            textViewNoEmpty1.setVisibility(View.VISIBLE);
//                            mapWeight.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
//                        } else {
//                            textViewNoEmpty1.setVisibility(View.INVISIBLE);
//                            mapWeight.put(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()),s.toString());
//                            Log.i(TAG, "onClick: " + mapWeight);
//                        }
//                    }
//                });
//
//                Button buttonRemove = addView.findViewById(R.id.buttonTrainingRowRemove);
//                buttonRemove.setOnClickListener(new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//
//                        mapReps.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
//                        mapWeight.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
//
//                        ((LinearLayout)addView.getParent()).removeView(addView);
//
//                        clickCounter--;
//                        Log.i(TAG, "onClick: " + clickCounter);
//
//                    }});
//
//                textViewTrainingDetailsID.setText(String.valueOf(clickCounter));
//                editTextTrainingRowReps.setText(mapReps.get(clickCounter));
//                editTextTrainingRowWeight.setText(mapWeight.get(clickCounter));
//
//                Log.i(TAG, "onClick: reps: " + ((EditText)addView.findViewById(R.id.editTextTrainingRowReps)).getText().toString() + " weight: " + ((EditText)addView.findViewById(R.id.editTextTrainingRowWeight)).getText().toString());
//
//                Log.i(TAG, "onClick: " + mapReps);
//                Log.i(TAG, "onClick: " + mapWeight);
//
//                container.addView(addView);
//                clickCounter++;
//            }
//        });
//
//        for (int i = 0; i < itemNum; i++){
//            buttonTrainingDetailsAdd.performClick();
//        }
//    }

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

                                    String mWeight = weight.replaceAll("\\p{Punct}"," ");
                                    String[] mWeight_table = mWeight.split("\\s+");

                                    for (int a = 0; a < mReps_table.length; a++){
                                        inflater.setMapWeight(a,mWeight_table[a]);
                                        inflater.setMapReps(a,mReps_table[a]);
                                        inflater.setReps();
                                        inflater.setWeight();
                                    }

//                                    buttonTrigger(mReps_table.length);


                                    downloadSeriesNumber(mReps_table.length);
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
                    Log.i(TAG, "onClick: isValid" + inflater.isValid());
                }
                Log.i(TAG, "onClick: " + inflater.getWeight());
                Log.i(TAG, "onClick: " + inflater.getReps());
                break;
            case R.id.buttonTrainingDetailsAdd:
                generateTrainingSets();
                break;

        }
    }

    private void downloadSeriesNumber(int seriesNumber){
        for (int i = 0; i < seriesNumber; i++) {
            container.addView(inflater.generateTrainingSets());
        }
    }

    void generateTrainingSets(){
        container.addView(inflater.generateTrainingSets());
    }














}