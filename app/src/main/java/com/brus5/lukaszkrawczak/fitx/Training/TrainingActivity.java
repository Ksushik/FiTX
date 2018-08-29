package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Async.Provider.Provider;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.Utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.Utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.Utils.MyCalendar;

import java.util.ArrayList;

import devs.mulham.horizontalcalendar.HorizontalCalendar;

public class TrainingActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "TrainingActivity";
    HorizontalCalendar calendar;
    DateGenerator cfg = new DateGenerator();
    String dateFormatView;
    TextView tvDate;
    ArrayList<Training> list = new ArrayList<>();
    ListView listView;
    TrainingAdapter adapter;
    Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_1);
        loadInput();
        loadDefaultView();

        new MyCalendar(this, this, R.id.calendarViewTraining, listView);

//        weekCalendar(cfg.calendarPast(), cfg.calendarFuture());
        onListViewItemSelected();
        training = new Training();
    }

    @Override
    public void loadInput()
    {
        tvDate = findViewById(R.id.textViewDate);
        listView = findViewById(R.id.listViewTraining);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(TrainingActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarTraining);
        activityView.showBackButton();
    }


//    private void weekCalendar(Calendar endDate, Calendar startDate)
//    {
//        calendar = new HorizontalCalendar.Builder(TrainingActivity.this, R.id.calendarViewTraining)
//                .startDate(startDate.getTime())
//                .endDate(endDate.getTime())
//                .datesNumberOnScreen(5)
//                .dayNameFormat("EE")
//                .dayNumberFormat("dd")
//                .showDayName(true)
//                .showMonthName(false).defaultSelectedDate(cfg.selectedDate(DateGenerator.getDate()))
//                .build();
//
//        calendar.setCalendarListener(new HorizontalCalendarListener()
//        {
//            @Override
//            public void onDateSelected(Date date, int position)
//            {
//                DateGenerator.setDate(cfg.getDateFormat().format(date.getTime()));
//                dateFormatView = cfg.getDateFormatView().format(date.getTime());
//                tvDate.setText(dateFormatView);
//                list.clear();
//                TrainingDTO dto = new TrainingDTO();
//                dto.setUserName(SaveSharedPreference.getUserName(TrainingActivity.this));
//                dto.setTrainingDate(DateGenerator.getDate());
////                dto.userName = SaveSharedPreference.getUserName(TrainingActivity.this);
////                dto.trainingDate = dateFormat;
////                dto.printStatus();
//                loadAsynchTask(dto, TrainingActivity.this);
//
//                Log.i(TAG, "onDateSelected: " + dto.toString());
//
//            }
//        });
//    }

//    public void loadAsynchTask(final TrainingDTO dto, final Context context)
//    {
//        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_SHOW_TRAINING_SHORT, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String globalResponse)
//            {
//
//        try
//            {
//                JSONObject jsonObject = new JSONObject(globalResponse);
//                Log.d(TAG, "onResponse: " + jsonObject.toString(1));
//
//                JSONArray training_types = jsonObject.getJSONArray("training_types");
//
//                JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");
//
//                int id;
//                String name;
//                String target;
//
//                int restTime;
//                int done;
//                String reps;
//                String weight;
//                String date;
//
//                if (trainings_info.length() > 0)
//                {
//                    for (int i = 0; i < trainings_info.length(); i++)
//                    {
//                        JSONObject trainings_infoObj = trainings_info.getJSONObject(i);
//
//                        restTime = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_REST_TIME);
//                        done = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_DONE);
//                        reps = trainings_infoObj.getString(RestAPI.DB_EXERCISE_REPS);
//                        weight = trainings_infoObj.getString(RestAPI.DB_EXERCISE_WEIGHT);
//                        date = trainings_infoObj.getString(RestAPI.DB_EXERCISE_DATE);
//
//
//
//
//
//                        JSONObject server_responseObj = training_types.getJSONObject(i);
//
//                        id = server_responseObj.getInt(RestAPI.DB_EXERCISE_ID);
//                        name = server_responseObj.getString(RestAPI.DB_EXERCISE_NAME);
//                        target = server_responseObj.getString(RestAPI.DB_EXERCISE_TARGET);
//
//                        training = new Training(id, done, name, restTime, weight, reps, date, target, 1);
//                        list.add(training);
//                        adapter = new TrainingAdapter(TrainingActivity.this, R.layout.row_training_excercise, list);
//
//                    }
//
//
//                }
//
//
//
//            } catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//
//
//
//
//
//
//
//            try
//            {
//                JSONObject jsonObject = new JSONObject(globalResponse);
//                Log.d(TAG, "onResponse: " + jsonObject.toString(1));
//
//                JSONArray cardio_types = jsonObject.getJSONArray("cardio_types");
//
//                JSONArray cardio_info = jsonObject.getJSONArray("cardio_info");
//
//                int id;
//                String name;
//                String kcalPerMin;
//
//                int done;
//                String time;
//                String date;
//
//                if (cardio_info.length() > 0)
//                {
//                    for (int i = 0; i < cardio_info.length(); i++)
//                    {
//                        JSONObject cardio = cardio_info.getJSONObject(i);
//
//                        done = cardio.getInt(            RestAPI.DB_CARDIO_DONE);
//                        time = cardio.getString(         RestAPI.DB_CARDIO_TIME);
//                        date = cardio.getString(         RestAPI.DB_DATE);
//
//
//
//                        JSONObject cardioType = cardio_types.getJSONObject(i);
//
//                        id = cardioType.getInt(                             RestAPI.DB_CARDIO_ID);
//                        name = cardioType.getString(                        RestAPI.DB_CARDIO_NAME);
//                        kcalPerMin = cardioType.getString(                  RestAPI.DB_CARDIO_CALORIES);
//
//
//
//                        training = new Training(id, done, name, time, date, kcalPerMin, 2);
//                        list.add(training);
//                        adapter = new TrainingAdapter(TrainingActivity.this, R.layout.row_training_excercise, list);
//                    }
//                }
//            } catch (JSONException e)
//            {
//                e.printStackTrace();
//            }
//
//            listView.setAdapter(adapter);
//            listView.invalidate();
//
//        }}, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
//                Log.e(TAG, "onErrorResponse: Error" + error);
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams()
//            {
//                HashMap<String, String> params = new HashMap<>();
//                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
//                params.put(RestAPI.DB_DATE, dto.getTrainingDate());
//
//                Log.i(TAG, "getParams: " + dto.toString());
//
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(context);
//        queue.add(strRequest);
//    }

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
                intent.putExtra("dateFormat", DateGenerator.getDate());
                TrainingActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                TextView tvTrainingType = view.findViewById(R.id.textViewTrainingType);

                boolean isGym = tvTrainingType.getText().toString().equals(TrainingActivity.this.getResources().getString(R.string.training_gym));
                boolean isCardio = tvTrainingType.getText().toString().equals(TrainingActivity.this.getResources().getString(R.string.training_cardio));

                if (isGym)
                {
                    TrainingProvider trainingProvider = new TrainingProvider(TrainingActivity.this, view, TrainingActivity.class);
                    trainingProvider.startTrainingDetailsActivity();
                }
                if (isCardio)
                {
                    TrainingProvider trainingProvider = new TrainingProvider(TrainingActivity.this, view, TrainingActivity.class);
                    trainingProvider.startCardioDetailsActivity();
                }
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        new Provider(this, this, listView).load();
    }
}

class TrainingProvider
{

    private static final String TAG = "TrainingProvider";

    private View view;
    private Context context;

    private String previousActivity;
    private String dateFormat;

    /**
     * Constructor of Training Provider
     *
     * @param context pass actual context
     * @param view    pass view of listView
     */
    TrainingProvider(Context context, View view, Class<?> aClass)
    {
        this.context = context;
        this.view = view;

        previousActivity = aClass.getSimpleName();
        dateFormat = DateGenerator.getDate();

        Log.d(TAG, "TrainingProvider: \n" + "context: " + context.getClass() + "\n" + "view: " + view.getId() + "\n" + "class: " + aClass.getSimpleName());
    }

    /**
     * This method starts TrainingDetailsActivity with values of concrete TextView's:
     * trainingID, timeStamp, trainingTarget
     * and passing it to TrainingDetailsActivity where are grabbed by getIntent() method.
     */
    protected void startTrainingDetailsActivity()
    {
        TextView tvTrainingID = view.findViewById(R.id.trainingID);
        TextView tvTrainingTimeStamp = view.findViewById(R.id.trainingTimeStamp);
        TextView tvTrainingTarget = view.findViewById(R.id.trainingTarget);

        int trainingID = Integer.parseInt(tvTrainingID.getText().toString());
        String trainingTimeStamp = tvTrainingTimeStamp.getText().toString();
        String trainingTarget = tvTrainingTarget.getText().toString();

        Intent intent = new Intent(context, TrainingDetailsActivity.class);

        intent.putExtra("trainingID", trainingID);
        intent.putExtra("trainingTimeStamp", trainingTimeStamp);
        intent.putExtra("trainingTarget", trainingTarget);
        intent.putExtra("previousActivity", this.previousActivity);
        intent.putExtra("dateFormat", this.dateFormat);

        this.context.startActivity(intent);
    }

    /**
     * This method starts CardioDetailsActivity with values of concrete TextView's:
     * cardioID, timeStamp, cardioTime, caloriesBurnedPerMinute
     * and passing it to CardioDetailsActivity where are grabbed by getIntent() method.
     */
    protected void startCardioDetailsActivity()
    {

        TextView tvCardioID = view.findViewById(R.id.cardioID);
        TextView tvTimeStamp = view.findViewById(R.id.cardioTimeStamp);
        TextView tvTime = view.findViewById(R.id.cardioTime);
        TextView tvKcalPerMin = view.findViewById(R.id.cardioBurnPerMin);

        int trainingID = Integer.parseInt(tvCardioID.getText().toString());
        String trainingTimeStamp = tvTimeStamp.getText().toString();
        double kcalPerMin = Double.parseDouble(tvKcalPerMin.getText().toString());
        int cardioTime;


        if (tvTime.length() < 5)
        {
            cardioTime = Integer.valueOf("0" + tvTime.getText().toString().substring(0, 1));
        }
        else
        {
            cardioTime = Integer.valueOf(tvTime.getText().toString().substring(0, 2));
        }

        Intent intent = new Intent(context, CardioDetailsActivity.class);

        intent.putExtra("trainingID", trainingID);
        intent.putExtra("trainingTimeStamp", trainingTimeStamp);
        intent.putExtra("trainingTime", cardioTime);
        intent.putExtra("kcalPerMin", kcalPerMin);
        intent.putExtra("previousActivity", this.previousActivity);
        intent.putExtra("dateFormat", this.dateFormat);

        this.context.startActivity(intent);
    }
}