package com.brus5.lukaszkrawczak.fitx.Training;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.DTO.TrainingDTO;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

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

public class TrainingActivity extends AppCompatActivity
{
    private static final String TAG = "TrainingActivity";
    HorizontalCalendar calendar;
    Configuration cfg = new Configuration();
    String dateFormat, dateFormatView;
    TextView tvDate;
    ArrayList<Training> list = new ArrayList<>();
    ListView listView;
    TrainingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_1);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        weekCalendar(cfg.oldestDay(), cfg.newestDay());
        onListViewItemSelected();
    }

    private void loadInput()
    {
        tvDate = findViewById(R.id.textViewDate);
        listView = findViewById(R.id.listViewTraining);
    }

    private void weekCalendar(Calendar endDate, Calendar startDate)
    {
        calendar = new HorizontalCalendar.Builder(TrainingActivity.this, R.id.calendarViewTraining).startDate(startDate.getTime()).endDate(endDate.getTime()).datesNumberOnScreen(5).dayNameFormat("EE").dayNumberFormat("dd").showDayName(true).showMonthName(false).build();

        calendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Date date, int position)
            {
                dateFormat = cfg.getDateFormat().format(date.getTime());
                dateFormatView = cfg.getDateFormatView().format(date.getTime());
                tvDate.setText(dateFormatView);
                list.clear();
                TrainingDTO dto = new TrainingDTO();
                dto.userName = SaveSharedPreference.getUserName(TrainingActivity.this);
                dto.trainingDate = dateFormat;
                dto.printStatus();
                loadAsynchTask(dto, TrainingActivity.this);
            }
        });
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadAsynchTask(final TrainingDTO dto, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_SHOW_TRAINING_SHORT, globalResponse -> {
            try
            {
                JSONObject jsonObject = new JSONObject(globalResponse);
                Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");
                JSONArray server_response = jsonObject.getJSONArray("server_response");

                int id;
                String name;
                String target;

                int restTime;
                int done;
                String reps;
                String weight;
                String date;

                if (trainings_info.length() > 0)
                {
                    for (int i = 0; i < trainings_info.length(); i++)
                    {
                        JSONObject trainings_infoObj = trainings_info.getJSONObject(i);
                        restTime = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_REST_TIME);
                        done = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_DONE);
                        reps = trainings_infoObj.getString(RestAPI.DB_EXERCISE_REPS);
                        weight = trainings_infoObj.getString(RestAPI.DB_EXERCISE_WEIGHT);
                        date = trainings_infoObj.getString(RestAPI.DB_EXERCISE_DATE);


                        JSONObject server_responseObj = server_response.getJSONObject(i);
                        id = server_responseObj.getInt(RestAPI.DB_EXERCISE_ID);
                        name = server_responseObj.getString(RestAPI.DB_EXERCISE_NAME);
                        target = server_responseObj.getString(RestAPI.DB_EXERCISE_TARGET);

                        Training training = new Training(id, done, name, restTime, weight, reps, date, target);
                        list.add(training);
                    }
                }
                adapter = new TrainingAdapter(TrainingActivity.this, R.layout.row_training_excercise, list);
                listView.setAdapter(adapter);
                listView.invalidate();


            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onErrorResponse: Error" + error);
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE, dto.trainingDate);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTraining);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_1_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_search_exercise:
                Intent intent = new Intent(TrainingActivity.this, TrainingSearchActivity.class);
                TrainingActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener((parent, view, position, id) -> {

            TextView tvTrainingID = view.findViewById(R.id.trainingID);
            TextView tvTrainingTimeStamp = view.findViewById(R.id.trainingTimeStamp);
            TextView tvTrainingTarget = view.findViewById(R.id.trainingTarget);

            Intent intent = new Intent(TrainingActivity.this, TrainingDetailsActivity.class);

            intent.putExtra("trainingID",               Integer.valueOf(tvTrainingID.getText().toString()));
            intent.putExtra("trainingTimeStamp",        tvTrainingTimeStamp.getText().toString());
            intent.putExtra("trainingTarget",           tvTrainingTarget.getText().toString());
            intent.putExtra("previousActivity",   "TrainingActivity");

            startActivity(intent);
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        adapter.clear();
        TrainingDTO dto = new TrainingDTO();
        dto.userName = SaveSharedPreference.getUserName(TrainingActivity.this);
        dto.trainingDate = dateFormat;
        dto.printStatus();
        loadAsynchTask(dto, TrainingActivity.this);
    }


}
