package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.Training.DTO.TrainingShowByUserDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class TrainingActivity extends AppCompatActivity {

    private static final String TAG = "TrainingActivity";

    HorizontalCalendar horizontalCalendar;

    /* Gettings DB_DATE */
    Configuration cfg = new Configuration();
    String dateInsde, dateInsideTextView;
    TextView textViewShowDayTrainingActivity;

    private ArrayList<String> trainingID = new ArrayList<>();
    private ArrayList<String> trainingName = new ArrayList<>();
    private ArrayList<String> trainingDone = new ArrayList<>();
    private ArrayList<String> trainingRestTime = new ArrayList<>();
    private ArrayList<String> trainingReps = new ArrayList<>();
    private ArrayList<String> trainingWeight = new ArrayList<>();

    ArrayList<Training> trainingArrayList = new ArrayList<>();
    ListView listViewTrainingActivity;
    TrainingListAdapter trainingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();

        weekCalendar(cfg.generateEndDay(),cfg.generateNextDay());
    }

    private void loadInput() {
        textViewShowDayTrainingActivity = findViewById(R.id.textViewShowDayTrainingActivity);
        listViewTrainingActivity = findViewById(R.id.listViewTrainingActivity);
    }

    private void weekCalendar(Calendar endDate, Calendar startDate) {
        horizontalCalendar = new HorizontalCalendar.Builder(TrainingActivity.this, R.id.calendarViewTrainingActivity)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
//                .textSize(10f, 16f, 14f)
                .showDayName(true)
                .showMonthName(false)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                dateInsde = cfg.getSimpleDateDateInside().format(date.getTime());
                dateInsideTextView = cfg.getSimpleDateTextView().format(date.getTime());

                textViewShowDayTrainingActivity.setText(dateInsideTextView);

                trainingArrayList.clear();

                TrainingShowByUserDTO trainingShowByUserDTO = new TrainingShowByUserDTO();
                trainingShowByUserDTO.userName = SaveSharedPreference.getUserName(TrainingActivity.this);
                trainingShowByUserDTO.dateToday = dateInsde;
                loadUsersDailyTrainingAsynchTask(trainingShowByUserDTO,TrainingActivity.this);

                Log.i(TAG, "onDateSelected: "+dateInsde+" position: "+position);
            }
        });
    }

    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadUsersDailyTrainingAsynchTask(final TrainingShowByUserDTO trainingShowByUserDTO, final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.SHOW_TRAINING_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {


                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");
                            int excerciseRestTime;
                            int excerciseReps;
                            int excerciseWeight;

                            JSONArray server_response = jsonObject.getJSONArray("server_response");
                            int excerciseId;
                            String excerciseDescription;

                            if (trainings_info.length() > 0) {
                                for (int i = 0; i < trainings_info.length(); i++) {
                                    JSONObject trainingInfoJsonObj = trainings_info.getJSONObject(i);
                                    excerciseRestTime = trainingInfoJsonObj.getInt(RestApiNames.DB_EXCERCISE_REST_TIME);
                                    excerciseReps = trainingInfoJsonObj.getInt(RestApiNames.DB_EXCERCISE_REPS);
                                    excerciseWeight = trainingInfoJsonObj.getInt(RestApiNames.DB_EXCERCISE_WEIGHT);

                                    JSONObject trainingNameJsonObj = server_response.getJSONObject(i);
                                    excerciseId = trainingNameJsonObj.getInt(RestApiNames.DB_EXCERCISE_ID);
                                    excerciseDescription = trainingNameJsonObj.getString(RestApiNames.DB_EXCERCISE_DESCRIPTION);


                                    Training training = new Training(excerciseId,excerciseDescription,excerciseRestTime,excerciseWeight,excerciseReps);
                                    trainingArrayList.add(training);
                                }
                            }


                            trainingListAdapter = new TrainingListAdapter(TrainingActivity.this,R.layout.training_excercise_row,trainingArrayList);
                            listViewTrainingActivity.setAdapter(trainingListAdapter);
                            listViewTrainingActivity.invalidate();

                            Log.d(TAG, "trainingID "+trainingID);
                            Log.d(TAG, "trainingName "+ TrainingActivity.this.trainingName);
                            Log.d(TAG, "trainingRestTime "+ TrainingActivity.this.trainingRestTime);
                            Log.d(TAG, "trainingWeight "+ TrainingActivity.this.trainingWeight);
                            Log.d(TAG, "trainingReps "+ TrainingActivity.this.trainingReps);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
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
                params.put(RestApiNames.DB_USERNAME, trainingShowByUserDTO.userName);
                params.put(RestApiNames.DB_DATE, trainingShowByUserDTO.dateToday);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingActivity);
        setSupportActionBar(toolbar);
    }


}
