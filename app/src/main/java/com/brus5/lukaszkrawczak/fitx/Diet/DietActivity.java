package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Calculator.Calories;
import com.brus5.lukaszkrawczak.fitx.Calculator.Carb;
import com.brus5.lukaszkrawczak.fitx.Calculator.Fat;
import com.brus5.lukaszkrawczak.fitx.Calculator.Protein;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Converter.NameConverter;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
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
import java.util.Locale;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class DietActivity extends AppCompatActivity implements DefaultView, DietAsynchTaskDTO
{
    private static final String TAG = "DietActivity";
    private HorizontalCalendar calendar;
    private TextView tvProteins, tvFats, tvCarbs, tvCalories, tvDate;
    private ProgressBar pBarProteins, pBarFats, pBarCarbs, pBarKcal;
    private ArrayList<Diet> list = new ArrayList<>();
    private DietListAdapter adapter;
    private ListView listView;
    private Configuration cfg = new Configuration();
    private String dateFormat, dateFormatView, productTimeStamp;
    private double maxCalories = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_1);
        cfg.changeStatusBarColor(this, getApplicationContext(), R.id.toolbarDietActivity,this);
        onBackButtonPressed();
        loadInput();
        weekCalendar(cfg.oldestDay(), cfg.newestDay());
        onListViewItemSelected();
    }

    public void loadInput()
    {
        tvDate = findViewById(R.id.textViewDay);
        pBarProteins = findViewById(R.id.progressBarProteins);
        pBarFats = findViewById(R.id.progressBarFats);
        pBarCarbs = findViewById(R.id.progressBarCarbs);
        pBarKcal = findViewById(R.id.progressBarKcal);
        tvProteins = findViewById(R.id.textViewProteins);
        tvFats = findViewById(R.id.textViewFats);
        tvCarbs = findViewById(R.id.textViewCarbs);
        tvCalories = findViewById(R.id.textViewKcal);
        listView = findViewById(R.id.listViewDiet);
    }

    private void weekCalendar(Calendar endDate, Calendar startDate)
    {
        calendar = new HorizontalCalendar.Builder(DietActivity.this, R.id.calendarViewDietActivity)
                       .defaultSelectedDate(   cfg.selectedDate(Configuration.getDate())    )

                       .startDate(startDate.getTime())
                       .endDate(endDate.getTime())
                       .datesNumberOnScreen(5)
                       .dayNameFormat("EE")
                       .dayNumberFormat("dd")
                       .showDayName(true)
                       .showMonthName(false)
                       .build();

        calendar.setCalendarListener(
            new HorizontalCalendarListener() {
                @Override
                public void onDateSelected(Date date, int position) {
                    dateFormat = cfg.getDateFormat().format(date.getTime());
                    dateFormatView = cfg.getDateFormatView().format(date.getTime());
                    tvDate.setText(dateFormatView);

                    list.clear();
                    listView.setAdapter(adapter);

                    DietDTO dto = new DietDTO();
                    dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                    dto.dateToday = dateFormat;
                    loadAsynchTask(dto, DietActivity.this);
                    Log.i(TAG, "onDateSelected: " + dateFormat);
                }
            }
        );
    }

    public void loadAsynchTask(final DietDTO dto, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_SHOW_BY_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String stringResponse)
            {

        double countProteins = 0d;
            double countFats = 0d;
            double countCarbs = 0d;
            double countCalories = 0d;

            double ratioProteins = 0d;
            double ratioFats = 0d;
            double ratioCarbs = 0d;
            double totalCalories;

            try
            {
                JSONObject jsonObject = new JSONObject(stringResponse);

                Log.d(TAG, "onResponse: "+jsonObject.toString(1));

                JSONArray response_ratio = jsonObject.getJSONArray("response_ratio");

                if (response_ratio.length() > 0)
                {
                    for (int i = 0; i < response_ratio.length(); i++)
                    {
                        JSONObject rRatio  = response_ratio.getJSONObject(i);

                        ratioProteins       = rRatio.getDouble(RestAPI.DB_PROTEIN_RATIO);
                        ratioFats           = rRatio.getDouble(RestAPI.DB_FATS_RATIO);
                        ratioCarbs          = rRatio.getDouble(RestAPI.DB_CARBS_RATIO);
                    }
                }

                JSONArray response_kcal_limit   = jsonObject.getJSONArray("response_kcal_limit");

                JSONObject rKcalLimit           = response_kcal_limit.getJSONObject(0);

                totalCalories                   = rKcalLimit.getDouble("RESULT");

                String productName;
                int productId;
                double proteins;
                double fats;
                double carbs;
                double calories;
                double weight;

                int productVerified;

                JSONArray response = jsonObject.getJSONArray("response");

                JSONArray response_weight = jsonObject.getJSONArray("response_weight");

                if (response.length() > 0)
                {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject rJSON = response.getJSONObject(i);

                        productId           = rJSON.getInt(RestAPI.DB_PRODUCT_ID);
                        productName         = rJSON.getString(RestAPI.DB_PRODUCT_NAME);
                        proteins            = rJSON.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
                        fats                = rJSON.getDouble(RestAPI.DB_PRODUCT_FATS);
                        carbs               = rJSON.getDouble(RestAPI.DB_PRODUCT_CARBS);
                        calories            = rJSON.getDouble(RestAPI.DB_PRODUCT_KCAL);
                        productVerified     = rJSON.getInt(RestAPI.DB_PRODUCT_VERIFIED);


                        JSONObject rWeight  = response_weight.getJSONObject(i);

                        weight              = rWeight.getDouble(RestAPI.DB_PRODUCT_WEIGHT);
                        productTimeStamp    = rWeight.getString(RestAPI.DB_DATE);

                        Protein protein = new Protein();
                        protein.setProteins(proteins, weight);

                        Fat fat = new Fat();
                        fat.setFats(fats, weight);

                        Carb carb = new Carb();
                        carb.setCarbs(carbs, weight);

                        Calories cal = new Calories();
                        cal.setCalories(calories, weight);

                        NameConverter name = new NameConverter();
                        name.setName(productName);

                        countProteins   += protein.getProteins();
                        countFats       += fat.getFats();
                        countCarbs      += carb.getCarbs();
                        countCalories   += cal.getKcal();

                        Diet diet = new Diet(
                                            productId,
                                            name.getName(),
                                            weight,
                                            protein.getProteins(),
                                            fat.getFats(),
                                            carb.getCarbs(),
                                            cal.getKcal(),
                                            productVerified,
                                            productTimeStamp
                                            );
                        list.add(diet);
                    }

                }

                Protein protein = new Protein();
                int proteinGoal = protein.proteinsGoal(totalCalories, ratioProteins);

                Fat fat = new Fat();
                int fatGoal = fat.fatsGoal(totalCalories, ratioFats);

                Carb carb = new Carb();
                int carbsGoal = carb.carbsGoal(totalCalories, ratioCarbs);

                String sProtein = doubleFormatter(countProteins) + " / " + String.valueOf(proteinGoal);
                String sFats = doubleFormatter(countFats) + " / " + String.valueOf(fatGoal);
                String sCarbs = doubleFormatter(countCarbs) + " / " + String.valueOf(carbsGoal);
                String sCalories = "Kcal: " + doubleFormatter(countCalories) + " / " + doubleFormatter(totalCalories);

                tvProteins.setText(sProtein);
                tvFats.setText(sFats);
                tvCarbs.setText(sCarbs);
                tvCalories.setText(sCalories);

                progressBarProteinsChangeColor(countProteins, proteinGoal);
                progressBarFatsChangeColor(countFats, fatGoal);
                progressBarCarbsChangeColor(countCarbs,carbsGoal);
                progressBarKcalChangeColor(countCalories,totalCalories);

                setMaxCalories(countCalories);

                adapter = new DietListAdapter(DietActivity.this, R.layout.row_diet_meal, list);
                listView.setAdapter(adapter);
                listView.invalidate();

                if (getMaxCalories() > 0d)
                {
                    DietDTO dto1 = new DietDTO();
                    dto1.userID              = SaveSharedPreference.getUserID(DietActivity.this);
                    dto1.updateKcalResult    = (int) countCalories;
                    dto1.dateToday           = dateFormat;

                    DietService dietService = new DietService();
                    dietService.updateCalories(dto1,DietActivity.this);
                }
                else if (getMaxCalories() == 0d)
                {
                    DietDTO dto1 = new DietDTO();
                    dto1.userID          = SaveSharedPreference.getUserID(DietActivity.this);
                    dto1.dateToday       = dateFormat;

                    DietService dietService = new DietService();
                    dietService.deleteCalories(dto1,DietActivity.this);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }}, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestAPI.DB_USERNAME, dto.userName);
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE, dto.dateToday);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView productId = view.findViewById(R.id.dietMealID);
                TextView productWeight = view.findViewById(R.id.dietMealWeight);
                TextView productTimeStamp = view.findViewById(R.id.dietTimeStamp);

                Intent intent = new Intent(DietActivity.this, DietProductShowActivity.class);
                intent.putExtra("productID", Integer.valueOf(productId.getText().toString()));
                intent.putExtra("dateFormat", dateFormat);
                intent.putExtra("productWeight", Double.valueOf(productWeight.getText().toString()));
                intent.putExtra("productTimeStamp", productTimeStamp.getText().toString());
                intent.putExtra("previousActivity", "DietActivity");

                DietActivity.this.startActivity(intent);
            }
        });
    }

    private void progressBarProteinsChangeColor(double result, double goal)
    {
        int intGoal = Integer.valueOf(String.format(Locale.getDefault(),"%.0f",goal));
        int intResult = Integer.valueOf(String.format(Locale.getDefault(),"%.0f",result));

        pBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
        pBarProteins.setMax(intGoal);
        pBarProteins.setProgress(intResult);

        if (result > goal)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                pBarProteins.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else
                {
                pBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
                }
        }
    }

    private void progressBarFatsChangeColor(double result, double goal)
    {
        pBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
        pBarFats.setMax((int) goal);
        pBarFats.setProgress((int) result);

        if (result > goal)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                pBarFats.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else
                {
                pBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
                }
        }
    }

    private void progressBarCarbsChangeColor(double result, double goal)
    {
        pBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
        pBarCarbs.setMax((int) goal);
        pBarCarbs.setProgress((int) result);

        if (result > goal)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                pBarCarbs.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else
                {
                pBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
                }
        }
    }

    private void progressBarKcalChangeColor(double result, double goal)
    {
        pBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
        pBarKcal.setMax((int) goal);
        pBarKcal.setProgress((int) result);

        if (result > goal)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                pBarKcal.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            } else
                {
                pBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
                }
        }
    }

    private String doubleFormatter(double value)
    {
        return String.format(Locale.getDefault(),"%.0f",value).replace(",",".");
    }

    public double getMaxCalories()
    {
        return maxCalories;
    }

    public void setMaxCalories(double maxCalories)
    {
        this.maxCalories = maxCalories;
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        // this backendcall
        adapter.clear();
        DietDTO dto = new DietDTO();
        dto.userName        = SaveSharedPreference.getUserName(DietActivity.this);
        dto.dateToday       = dateFormat;

        Log.i(TAG, "onRestart: " + dto.toString());

        loadAsynchTask(dto,DietActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_1_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_search_product:
            Intent intent = new Intent(DietActivity.this,DietProductSearchActivity.class);
            intent.putExtra("dateFormat", dateFormat);
            DietActivity.this.startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }

}
