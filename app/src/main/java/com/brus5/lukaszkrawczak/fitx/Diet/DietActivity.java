package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.brus5.lukaszkrawczak.fitx.Calculator.CarbCalc;
import com.brus5.lukaszkrawczak.fitx.Calculator.FatCalc;
import com.brus5.lukaszkrawczak.fitx.Calculator.ProteinCalc;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTODiet;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.R;
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

public class DietActivity extends AppCompatActivity
{
    private static final String TAG = "DietActivity";
    HorizontalCalendar calendar;
    TextView tvProteins, tvFats, tvCarbs, tvCalories, tvDate;
    ProgressBar pBarProteins, pBarFats, pBarCarbs, pBarKcal;
    ArrayList<Diet> list = new ArrayList<>();
    DietListAdapter adapter;
    ListView listView;
    Configuration cfg = new Configuration();
    String dateFormat, dateFormatView, productTimeStamp;
    double mCalories = 0d;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_1);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        weekCalendar(cfg.oldestDay(), cfg.newestDay());
        onListViewItemSelected();
    }

    public void runNextActivity(Context context, Class<?> aClass)
    {
        Intent intent = new Intent(context, aClass);
        DietActivity.this.startActivity(intent);
    }

    private void loadInput()
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

                    DietDTODiet dto = new DietDTODiet();
                    dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                    dto.dateToday = dateFormat;
                    dto.printStatus();
                    loadUsersDailyDietAsynchTask(dto, DietActivity.this);
                    Log.i(TAG, "onDateSelected: " + dateFormat);
                }
            }
                                    );
    }

    public void loadUsersDailyDietAsynchTask(final DietDTODiet dto, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_USER_SHOW_DAILY_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        double proteinsCounted = 0d;
                        double fatsCounted = 0d;
                        double carbsCounted = 0d;

                        double kcalCounted = 0d;

                        double proteinRatio = 0d;
                        double fatRatio = 0d;
                        double carbRatio = 0d;
                        double totalCalories = 0d;

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d(TAG, "onResponse: "+jsonObject.toString(1));

                            JSONArray dietratio = jsonObject.getJSONArray("dietratio");
                            if (dietratio.length() > 0)
                            {
                                for (int i = 0; i < dietratio.length(); i++)
                                {
                                    JSONObject dietratioJSONObject = dietratio.getJSONObject(i);
                                    proteinRatio = dietratioJSONObject.getDouble(RestApiNames.DB_PROTEIN_RATIO);
                                    fatRatio = dietratioJSONObject.getDouble(RestApiNames.DB_FATS_RATIO);
                                    carbRatio = dietratioJSONObject.getDouble(RestApiNames.DB_CARBS_RATIO);
                                }
                            }

                            JSONArray RESULT = jsonObject.getJSONArray("RESULT");
                            JSONObject d = RESULT.getJSONObject(0);
                            totalCalories = d.getDouble("RESULT");

                            String productName;
                            int productId;
                            double productProteins = 0d;
                            double productFats = 0d;
                            double productCarbs = 0d;
                            double productKcal;
                            double weight;
                            double finalkcal = 0d;
                            int productVerified = 0;
                            JSONArray product_informations = jsonObject.getJSONArray("server_response");
                            JSONArray product_weight_response = jsonObject.getJSONArray("product_weight_response");

                            if (product_informations.length() > 0)
                            {
                                for (int i = 0; i < product_informations.length(); i++)
                                {
                                    JSONObject productsJsonObj = product_informations.getJSONObject(i);

                                    productId = productsJsonObj.getInt(RestApiNames.DB_PRODUCT_ID);
                                    productName = productsJsonObj.getString(RestApiNames.DB_PRODUCT_NAME);
                                    productProteins = productsJsonObj.getDouble(RestApiNames.DB_PRODUCT_PROTEINS);
                                    productFats = productsJsonObj.getDouble(RestApiNames.DB_PRODUCT_FATS);
                                    productCarbs = productsJsonObj.getDouble(RestApiNames.DB_PRODUCT_CARBS);
                                    productKcal = productsJsonObj.getDouble(RestApiNames.DB_PRODUCT_KCAL);
                                    productVerified = productsJsonObj.getInt(RestApiNames.DB_PRODUCT_VERIFIED);

                                    JSONObject productWeightJsonObj = product_weight_response.getJSONObject(i);
                                    weight = productWeightJsonObj.getDouble(RestApiNames.DB_PRODUCT_WEIGHT);
                                    productTimeStamp = productWeightJsonObj.getString(RestApiNames.DB_DATE);

                                    productProteins = productProteins * (weight / 100);
                                    productFats = productFats * (weight / 100);
                                    productCarbs = productCarbs * (weight / 100);

                                    finalkcal = productKcal * weight * 0.01;
                                    String upName = productName.substring(0,1).toUpperCase() + productName.substring(1);

                                    proteinsCounted += productProteins;
                                    fatsCounted += productFats;
                                    carbsCounted += productCarbs;
                                    kcalCounted += finalkcal;

                                    Diet diet = new Diet(productId, upName, weight, productProteins, productFats, productCarbs,finalkcal, productVerified, productTimeStamp);
                                    list.add(diet);
                                }

                            }

                            ProteinCalc pCalc = new ProteinCalc();
                            int proteinGoal = pCalc.countProteinGoal(totalCalories,proteinRatio);
                            FatCalc fCalc = new FatCalc();
                            int fatGoal = fCalc.countFatsGoal(totalCalories,fatRatio);
                            CarbCalc cCalc = new CarbCalc();
                            int carbsGoal = cCalc.countCarbsGoal(totalCalories,carbRatio);

                            String wProtein = replaceCommaWithDot(proteinsCounted)+" / "+String.valueOf(proteinGoal);
                            String wFats = replaceCommaWithDot(fatsCounted)+" / "+String.valueOf(fatGoal);
                            String wCarbs = replaceCommaWithDot(carbsCounted)+" / "+String.valueOf(carbsGoal);

                            String wKcal = "Kcal: "+replaceCommaWithDot(kcalCounted)+" / "+replaceCommaWithDot(totalCalories);

                            tvProteins.setText(wProtein);
                            tvFats.setText(wFats);
                            tvCarbs.setText(wCarbs);
                            tvCalories.setText(wKcal);

                            progressBarProteinsChangeColor(proteinsCounted, proteinGoal);
                            progressBarFatsChangeColor(fatsCounted, fatGoal);
                            progressBarCarbsChangeColor(carbsCounted,carbsGoal);
                            progressBarKcalChangeColor(kcalCounted,totalCalories);

                            setmCalories(kcalCounted);

                            adapter = new DietListAdapter(DietActivity.this, R.layout.row_diet_meal, list);
                            listView.setAdapter(adapter);
                            listView.invalidate();

                            if (getmCalories() > 0d)
                            {
                                DietDTODiet dto = new DietDTODiet();
                                dto.userID = SaveSharedPreference.getUserID(DietActivity.this);
                                dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                                dto.updateKcalResult = String.format("%.1f",kcalCounted);
                                dto.dateToday = dateFormat;
                                dto.printStatus();
                                DietService dietService = new DietService();
                                dietService.DietUpdateCountedKcal(dto,DietActivity.this);

                            }
                            else if (getmCalories() == 0d)
                            {
                                DietDTODiet dto = new DietDTODiet();
                                dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                                dto.dateToday = dateFormat;
                                dto.printStatus();
                                DietService dietService = new DietService();
                                dietService.DietDeleteCountedKcal(dto,DietActivity.this);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, Configuration.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onErrorResponse: Error"+error);
                    }
                }
                )
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_DATE, dto.dateToday);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView productId = view.findViewById(R.id.dietMealID);
                        TextView productWeight = view.findViewById(R.id.dietMealWeight);
                        TextView productTimeStamp = view.findViewById(R.id.dietTimeStamp);

                        Intent intent = new Intent(DietActivity.this, DietProductShowActivity.class);
                        intent.putExtra("productID", productId.getText().toString());
                        intent.putExtra("dateFormat", dateFormat);
                        intent.putExtra("productWeight", Double.valueOf(productWeight.getText().toString()));
                        intent.putExtra("productTimeStamp", productTimeStamp.getText().toString());
                        intent.putExtra("previousActivity", "DietActivity");

                        startActivity(intent);
                    }
                }
                                       );
    }

    private void progressBarProteinsChangeColor(double result, double goal)
    {
        int intGoal = Integer.valueOf(String.format("%.0f",goal));
        int intResult = Integer.valueOf(String.format("%.0f",result));

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

    private String replaceCommaWithDot(double value)
    {
        return String.format("%.0f",value).replace(",",".");
    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietActivity);
        setSupportActionBar(toolbar);
    }

    public double getmCalories()
    {
        return mCalories;
    }

    public void setmCalories(double mCalories)
    {
        this.mCalories = mCalories;
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
        DietDTODiet dto = new DietDTODiet();
        dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
        dto.dateToday = dateFormat;
        dto.printStatus();
        loadUsersDailyDietAsynchTask(dto,DietActivity.this);
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
