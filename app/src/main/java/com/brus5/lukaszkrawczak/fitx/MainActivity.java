package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DTO.MainDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.Stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingActivity;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingInflater;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "MainActivity";
    private Button btnDiet, btnTraining, btnSettings, btnStats;
    private HorizontalCalendar calendar;
    private Configuration cfg = new Configuration();
    private String dateFormat, dateFormatView;
    private ArrayList<Main> list = new ArrayList<>();
    private ListView listView;
    private MainAdapter adapter;
    private Main main;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarColor();
        loadInputs();
        weekCalendar(cfg.oldestDay(), cfg.newestDay());
        main = new Main();
    }

    private void weekCalendar(Calendar endDate, Calendar startDate)
    {
        calendar = new HorizontalCalendar.Builder(MainActivity.this, R.id.calendarViewMainActivity)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
                .showDayName(true)
                .showMonthName(false)
                .build();

        calendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Date date, int position)
            {
                dateFormat = cfg.getDateFormat().format(date.getTime());
                dateFormatView = cfg.getDateFormatView().format(date.getTime());
                Log.e(TAG, "onDateSelected: " + dateFormat);
                // TODO: 30.07.2018 add tvDate.setText
                asynchPreparator();
            }
        });
    }

    private void asynchPreparator()
    {
        list.clear();
        MainDTO dto = new MainDTO();
        dto.userID = String.valueOf(SaveSharedPreference.getUserID(MainActivity.this));
        dto.date = dateFormat;
        dto.printStatus();
        loadAsynchTask(dto, MainActivity.this);
    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarMainActivity);
        setSupportActionBar(toolbar);
    }

    private void loadInputs()
    {
        listView = findViewById(R.id.listViewMain);
        btnDiet = findViewById(R.id.buttonDiet);
        btnDiet.setOnClickListener(this);
        btnTraining = findViewById(R.id.buttonTraining);
        btnTraining.setOnClickListener(this);
        btnSettings = findViewById(R.id.buttonSettings);
        btnSettings.setOnClickListener(this);
        btnStats = findViewById(R.id.buttonStats);
        btnStats.setOnClickListener(this);
    }

    public void loadAsynchTask(final MainDTO dto, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_MAIN_DIET, globalResponse -> {

            try
            {
                int kcal = 0;
                int kcalLimit;

                JSONObject jsonObject = new JSONObject(globalResponse);

                Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                JSONArray response = jsonObject.getJSONArray("response");

                JSONObject kcalObj = response.getJSONObject(0);
                JSONObject kcalLimitObj = response.getJSONObject(1);

                if (response.length() > 0)
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        kcal = kcalObj.getInt("kcal");
                        kcalLimit = kcalLimitObj.getInt("kcal_limit");

                        main = new Main(kcal, kcalLimit, 1);

                        adapter = new MainAdapter(MainActivity.this, R.layout.row_main_diet, list);
                    }
                    if (kcal > 0)
                    {
                        list.add(main);
                    }
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }





            try
            {
                JSONObject jsonObject1 = new JSONObject(globalResponse);
                JSONArray response = jsonObject1.getJSONArray("response");

                JSONObject repetitionsObj = response.getJSONObject(2);
                JSONObject liftedObj = response.getJSONObject(3);
                JSONObject restObj = response.getJSONObject(4);

                String weight = "0";
                String rest = "0";
                String reps = "0";

                TrainingInflater inflater = new TrainingInflater(MainActivity.this);

                for (int i = 0; i < response.length(); i++)
                {
                    reps = repetitionsObj.getString("repetitions");
                    weight = liftedObj.getString("lifted");
                    rest = restObj.getString("rest");

                    inflater.setWeight(weight);
                    inflater.setReps(reps);

                    adapter = new MainAdapter(MainActivity.this, R.layout.row_main_diet, list);

                    main = new Main(rest, reps, weight, 2);
                }

                if (!weight.equals("0"))
                {
                    list.add(main);
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }

            listView.setDividerHeight(0);
            listView.setAdapter(adapter);
            // TODO: 01.08.2018 work here

            listView.invalidate();
            // TODO: 31.07.2018 zrobic if listview is empty to pokazuj wiadomość "Brak danych z dnia: xx.XX.xxxx"

        },
                error -> {
                    Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onErrorResponse: Error" + error);
                }
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE, dto.date);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonDiet:
                runNextActivity(MainActivity.this, DietActivity.class);
                break;
            case R.id.buttonTraining:
                runNextActivity(MainActivity.this, TrainingActivity.class);
                break;
            case R.id.buttonStats:
                runNextActivity(MainActivity.this, StatsActivity.class);
                break;
            case R.id.buttonSettings:
                break;
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        asynchPreparator();
    }

    public void runNextActivity(Context packageContext, Class<?> cls)
    {
        Intent intent = new Intent(packageContext, cls);
        MainActivity.this.startActivity(intent);

    }
}
