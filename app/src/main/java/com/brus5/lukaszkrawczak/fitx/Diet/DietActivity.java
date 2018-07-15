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
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietDeleteCountedKcalDTO;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietUpdateKcalResultDTO;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietUserShowDailyDTO;
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

public class DietActivity extends AppCompatActivity {

    private static final String TAG = "DietActivity";
    HorizontalCalendar horizontalCalendar;
    TextView textViewProteins, textViewFats, textViewCarbs, textViewKcal, textViewShowDayDietActivity;
    ProgressBar progressBarProteins, progressBarFats, progressBarCarbs, progressBarKcal;
    ArrayList<Diet> dietArrayList = new ArrayList<>();
    DietListAdapter dietListAdapter;
    ListView listViewDietActivity;
    double kcalResult = 0d;
    Configuration cfg = new Configuration();
    String dateInside, dateInsideTextView, productTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();

        weekCalendar(cfg.generateEndDay(),cfg.generateNextDay());

        onListViewItemSelected();

    }

    public void runNextActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        DietActivity.this.startActivity(intent);
    }

    private void loadInput() {
        textViewShowDayDietActivity = findViewById(R.id.textViewShowDayDietActivity);

        progressBarProteins = findViewById(R.id.progressBarProteins);
        progressBarFats = findViewById(R.id.progressBarFats);
        progressBarCarbs = findViewById(R.id.progressBarCarbs);
        progressBarKcal = findViewById(R.id.progressBarKcal);

        textViewProteins = findViewById(R.id.textViewProteins);
        textViewFats = findViewById(R.id.textViewFats);
        textViewCarbs = findViewById(R.id.textViewCarbs);
        textViewKcal = findViewById(R.id.textViewKcal);

        listViewDietActivity = findViewById(R.id.listViewDietActivity);
    }

    private void weekCalendar(Calendar endDate, Calendar startDate) {
        horizontalCalendar = new HorizontalCalendar.Builder(DietActivity.this, R.id.calendarViewDietActivity)
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
                dateInside = cfg.getSimpleDateDateInside().format(date.getTime());
                dateInsideTextView = cfg.getSimpleDateTextView().format(date.getTime());
                textViewShowDayDietActivity.setText(dateInsideTextView);

                dietArrayList.clear();

                DietUserShowDailyDTO dto = new DietUserShowDailyDTO();
                dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                dto.dateToday = dateInside;
                dto.printStatus();
                loadUsersDailyDietAsynchTask(dto,DietActivity.this);

                Log.i(TAG, "onDateSelected: "+ dateInside);
            }
        });
    }

    public void loadUsersDailyDietAsynchTask(final DietUserShowDailyDTO dietUserShowDailyDTO, final Context ctx){
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

                        try {
                            /* Getting DietRatio from MySQL */
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d(TAG, "onResponse: "+jsonObject.toString(1));

                            JSONArray dietratio = jsonObject.getJSONArray("dietratio");
                            if (dietratio.length() > 0) {
                                for (int i = 0; i < dietratio.length(); i++) {
                                    JSONObject dietratioJSONObject = dietratio.getJSONObject(i);
                                    proteinRatio = dietratioJSONObject.getDouble(RestApiNames.DB_PROTEIN_RATIO);
                                    fatRatio = dietratioJSONObject.getDouble(RestApiNames.DB_FATS_RATIO);
                                    carbRatio = dietratioJSONObject.getDouble(RestApiNames.DB_CARBS_RATIO);
                                }
                            }
                            /* End */

                            /* Getting calorie limit from MySQL */
                            JSONArray RESULT = jsonObject.getJSONArray("RESULT");
                            JSONObject d = RESULT.getJSONObject(0);
                            totalCalories = d.getDouble("RESULT");
                            /* End */

                            /* Getting product information with weight from MySQL */
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

                            if (product_informations.length() > 0){
                                for (int i = 0; i < product_informations.length(); i++){
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

                                    Diet diet = new Diet(productId, upName, weight, productProteins, productFats, productCarbs,finalkcal, productVerified);
                                    dietArrayList.add(diet);
                                }

                                /* End of getting product information with weight from MySQL */

                            }

                            int proteinGoal = countProteinGoal(totalCalories,proteinRatio);
                            int fatGoal = countFatsGoal(totalCalories,fatRatio);
                            int carbsGoal = countCarbsGoal(totalCalories,carbRatio);

                            String wProtein = replaceCommaWithDot(proteinsCounted)+" / "+String.valueOf(proteinGoal);
                            String wFats = replaceCommaWithDot(fatsCounted)+" / "+String.valueOf(fatGoal);
                            String wCarbs = replaceCommaWithDot(carbsCounted)+" / "+String.valueOf(carbsGoal);

                            String wKcal = "Kcal: "+replaceCommaWithDot(kcalCounted)+" / "+replaceCommaWithDot(totalCalories);

                            textViewProteins.setText(wProtein);
                            textViewFats.setText(wFats);
                            textViewCarbs.setText(wCarbs);
                            textViewKcal.setText(wKcal);

                            progressBarProteinsChangeColor(proteinsCounted, proteinGoal);
                            progressBarFatsChangeColor(fatsCounted, fatGoal);
                            progressBarCarbsChangeColor(carbsCounted,carbsGoal);
                            progressBarKcalChangeColor(kcalCounted,totalCalories);

                            setKcalResult(kcalCounted);

                            Log.i(TAG, "onResponse: RESULT"+RESULT);
                            Log.i(TAG, "onResponse: ratio " + proteinRatio+":"+ fatRatio+":"+carbRatio);
                            Log.i(TAG, "onResponse: totalCalories "+totalCalories);
                            Log.i(TAG, "onResponse: proteinGoal: "+proteinGoal+" fatGoal: "+fatGoal+" carbGoal: "+carbsGoal);
                            Log.i(TAG, "onResponse: productKcalArrayList: "+kcalCounted+" P: "+proteinsCounted+" F: "+fatsCounted+" C: "+carbsCounted);

                            Log.e(TAG, "getKcalResult(): "+getKcalResult());

                            dietListAdapter = new DietListAdapter(DietActivity.this,R.layout.diet_meal_row,dietArrayList);
                            listViewDietActivity.setAdapter(dietListAdapter);
                            listViewDietActivity.invalidate();

                            if (getKcalResult() > 0d) {
                                DietUpdateKcalResultDTO dto = new DietUpdateKcalResultDTO();
                                dto.userId = SaveSharedPreference.getUserID(DietActivity.this);
                                dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                                dto.updateKcalResult = String.format("%.1f",kcalCounted);
                                dto.dateToday = dateInside;
                                dto.printStatus();
                                DietService dietService = new DietService();
                                dietService.DietUpdateCountedKcal(dto,DietActivity.this);

                            }
                            else if (getKcalResult() == 0d){
                                DietDeleteCountedKcalDTO dto = new DietDeleteCountedKcalDTO();
                                dto.userName = SaveSharedPreference.getUserName(DietActivity.this);
                                dto.dateToday = dateInside;
                                dto.printStatus();
                                DietService dietService = new DietService();
                                dietService.DietDeleteCountedKcal(dto,DietActivity.this);
                            }
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
                params.put(RestApiNames.DB_USERNAME, dietUserShowDailyDTO.userName);
                params.put(RestApiNames.DB_DATE, dietUserShowDailyDTO.dateToday);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void onListViewItemSelected() {
        listViewDietActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView productId = view.findViewById(R.id.dietMealID);
                TextView productWeight = view.findViewById(R.id.dietMealWeight);

                Intent intent = new Intent(DietActivity.this,DietProductShowActivity.class);
                intent.putExtra("productId",productId.getText().toString());
                intent.putExtra("dateInside",dateInside);
                intent.putExtra("productWeight",Double.valueOf(productWeight.getText().toString()));
                intent.putExtra("productTimeStamp", productTimeStamp);
                intent.putExtra("previousActivity", "DietActivity");

                startActivity(intent);

            }
        });
    }

    private void progressBarProteinsChangeColor(double result, double goal){
        int intGoal = Integer.valueOf(String.format("%.0f",goal));
        int intResult = Integer.valueOf(String.format("%.0f",result));

        progressBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
        progressBarProteins.setMax(intGoal);
        progressBarProteins.setProgress(intResult);

        if (result > goal){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBarProteins.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else {
                progressBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
            }
        }
    }
    private void progressBarFatsChangeColor(double result, double goal){
        int intGoal = Integer.valueOf(String.format("%.0f",goal));
        int intResult = Integer.valueOf(String.format("%.0f",result));

        progressBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
        progressBarFats.setMax(intGoal);
        progressBarFats.setProgress(intResult);

        if (result > goal){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBarFats.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else {
                progressBarFats.getProgressDrawable().setColorFilter(0xFFF3AE28, PorterDuff.Mode.SRC_IN);
            }
        }
    }
    private void progressBarCarbsChangeColor(double result, double goal){
        int intGoal = Integer.valueOf(String.format("%.0f",goal));
        int intResult = Integer.valueOf(String.format("%.0f",result));

        progressBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
        progressBarCarbs.setMax(intGoal);
        progressBarCarbs.setProgress(intResult);

        if (result > goal){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBarCarbs.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else {
                progressBarCarbs.getProgressDrawable().setColorFilter(0xFFBD2121, PorterDuff.Mode.SRC_IN);
            }
        }
    }
    private void progressBarKcalChangeColor(double result, double goal){
        int intGoal = Integer.valueOf(String.format("%.0f",goal));
        int intResult = Integer.valueOf(String.format("%.0f",result));

        progressBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
        progressBarKcal.setMax(intGoal);
        progressBarKcal.setProgress(intResult);

        if (result > goal){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                progressBarKcal.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            } else {
                progressBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private String replaceCommaWithDot(double value){
        return String.format("%.0f",value).replace(",",".");
    }

    private int countProteinGoal(double kcalResult, double valueRatio){
        double result = (kcalResult*valueRatio*0.01)/4;
        return (int) result;
    }
    private int countFatsGoal(double kcalResult, double valueRatio){
        double result = (kcalResult*valueRatio*0.01)/9;
        return (int) result;
    }
    private int countCarbsGoal(double kcalResult, double valueRatio){
        double result = (kcalResult*valueRatio*0.01)/4;
        return (int) result;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietActivity);
        setSupportActionBar(toolbar);
    }

    public double getKcalResult() {
        return kcalResult;
    }

    public void setKcalResult(double kcalResult) {
        this.kcalResult = kcalResult;
    }

    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // this backendcall
        dietListAdapter.clear();
        DietUserShowDailyDTO dietUserShowDailyDTO = new DietUserShowDailyDTO();
        dietUserShowDailyDTO.userName = SaveSharedPreference.getUserName(DietActivity.this);
        dietUserShowDailyDTO.dateToday = dateInside;
        loadUsersDailyDietAsynchTask(dietUserShowDailyDTO,DietActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_product_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search_product:
            Intent intent = new Intent(DietActivity.this,DietProductSearchActivity.class);
            intent.putExtra("dateInside", dateInside);
            DietActivity.this.startActivity(intent);
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
