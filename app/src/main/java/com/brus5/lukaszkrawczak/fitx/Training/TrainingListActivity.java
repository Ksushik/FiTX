package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingListActivity extends AppCompatActivity {
    private static final String TAG = "TrainingListActivity";
    ArrayList<TrainingSearch> trainingSearchArrayList = new ArrayList<>();

    TrainingSearchListAdapter trainingSearchListAdapter;
    ListView listViewTrainingActivity;
    private String trainingTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);
        loadInput();
        changeStatusBarColor();
        onBackButtonPressed();
        getIntentFromPreviousActiity();
        asynchTask(TrainingListActivity.this);
        onListViewItemSelected();
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        int mExercise = intent.getIntExtra("exercise",-1);
        Log.e(TAG, "onCreate: "+mExercise);

        TrainingList trainingList = new TrainingList(TrainingListActivity.this);
        trainingList.setResId(mExercise);
        trainingTarget = trainingList.getResourceName();
        Log.e(TAG, "getIntentFromPreviousActiity: " + trainingTarget);
    }

    private void loadInput() {
        listViewTrainingActivity = findViewById(R.id.listViewTrainingActivity);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingListActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingExcerciseList);
        setSupportActionBar(toolbar);
    }
    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void asynchTask(final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.TRAINING_SEARCH_BY_TARGET,
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
                            String productName;

                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    trainingID = object.getInt(RestApiNames.DB_EXERCISE_ID);
                                    productName = object.getString(RestApiNames.DB_EXERCISE_NAME);

                                    String trainingName = productName.substring(0,1).toUpperCase() + productName.substring(1);

                                    TrainingSearch trainingSearch = new TrainingSearch(trainingID, trainingName);
                                    trainingSearchArrayList.add(trainingSearch);
                                }
                            }
                            /* End */

                            trainingSearchListAdapter = new TrainingSearchListAdapter(TrainingListActivity.this,R.layout.training_exercise_search_row, trainingSearchArrayList);
                            listViewTrainingActivity.setAdapter(trainingSearchListAdapter);
                            listViewTrainingActivity.invalidate();
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
                params.put(RestApiNames.DB_EXERCISE_TARGET, trainingTarget);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void onListViewItemSelected() {
        listViewTrainingActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView trainingID = view.findViewById(R.id.trainingSearchID);

                Intent intent = new Intent(TrainingListActivity.this,TrainingDetailsActivity.class);
                intent.putExtra("trainingID",Integer.valueOf(trainingID.getText().toString()));
                intent.putExtra("trainingTarget",trainingTarget);
                intent.putExtra("previousActivity","TrainingListActivity");
                startActivity(intent);
            }
        });
    }


}