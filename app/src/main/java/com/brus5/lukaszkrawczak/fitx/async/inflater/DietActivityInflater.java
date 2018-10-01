package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.calculator.Calories;
import com.brus5.lukaszkrawczak.fitx.calculator.Carb;
import com.brus5.lukaszkrawczak.fitx.calculator.Fat;
import com.brus5.lukaszkrawczak.fitx.calculator.Protein;
import com.brus5.lukaszkrawczak.fitx.diet.DietService;
import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.diet.adapter.DietListAdapter;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.ProgressBarAnimation;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_DATE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_UPDATE_RESULT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.DB_USER_ID;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_DELETE_COUNTED_KCAL;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_UPDATE_COUNTED_KCAL;

public class DietActivityInflater
{
    private static final String TAG = "MainActivityInflater";

    private Context context;
    private ListView listView;
    private ArrayList<Product> arrayList = new ArrayList<>();

    private TextView tvProteins, tvFats, tvCarbs, tvCalories;
    private ProgressBar pBarProteins, pBarFats, pBarCarbs, pBarKcal;

    private String productTimeStamp;
    private String dateFormat = DateGenerator.getSelectedDate();

    private double maxCalories = 0d;

    private static int PROTEINS_FROM, FATS_FROM, CARBS_FROM, KCAL_FROM;

    public DietActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;
        loadInput();

        dataInflater(response);
    }

    private void loadInput()
    {
        pBarProteins = ((Activity)context).findViewById(R.id.progressBarProteins);
        pBarFats = ((Activity)context).findViewById(R.id.progressBarFats);
        pBarCarbs = ((Activity)context).findViewById(R.id.progressBarCarbs);
        pBarKcal = ((Activity)context).findViewById(R.id.progressBarKcal);
        tvProteins = ((Activity)context).findViewById(R.id.textViewProteins);
        tvFats = ((Activity)context).findViewById(R.id.textViewFats);
        tvCarbs = ((Activity)context).findViewById(R.id.textViewCarbs);
        tvCalories = ((Activity)context).findViewById(R.id.textViewKcal);
    }

    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");
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
            JSONObject jsonObject = new JSONObject(s);

            Log.d(TAG, "onResponse: " + " \n" + jsonObject.toString(1));

            JSONArray response_ratio = jsonObject.getJSONArray("response_ratio");

            if (response_ratio.length() > 0)
            {
                for (int i = 0; i < response_ratio.length(); i++)
                {
                    JSONObject rRatio = response_ratio.getJSONObject(i);

                    ratioProteins = rRatio.getDouble(RestAPI.DB_PROTEIN_RATIO);
                    ratioFats = rRatio.getDouble(RestAPI.DB_FATS_RATIO);
                    ratioCarbs = rRatio.getDouble(RestAPI.DB_CARBS_RATIO);
                }
            }

            JSONArray response_kcal_limit = jsonObject.getJSONArray("response_kcal_limit");

            JSONObject rKcalLimit = response_kcal_limit.getJSONObject(0);

            totalCalories = rKcalLimit.getDouble("RESULT");

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

                    productId = rJSON.getInt(RestAPI.DB_PRODUCT_ID);
                    productName = rJSON.getString(RestAPI.DB_PRODUCT_NAME);
                    proteins = rJSON.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
                    fats = rJSON.getDouble(RestAPI.DB_PRODUCT_FATS);
                    carbs = rJSON.getDouble(RestAPI.DB_PRODUCT_CARBS);
                    calories = rJSON.getDouble(RestAPI.DB_PRODUCT_KCAL);
                    productVerified = rJSON.getInt(RestAPI.DB_PRODUCT_VERIFIED);


                    JSONObject rWeight = response_weight.getJSONObject(i);

                    weight = rWeight.getDouble(RestAPI.DB_PRODUCT_WEIGHT);
                    productTimeStamp = rWeight.getString(DB_DATE);

                    Protein protein = new Protein();
                    protein.setProteins(proteins, weight);

                    Fat fat = new Fat();
                    fat.setFats(fats, weight);

                    Carb carb = new Carb();
                    carb.setCarbs(carbs, weight);

                    Calories cal = new Calories();
                    cal.setCalories(calories, weight);

                    countProteins += protein.getProteins();
                    countFats += fat.getFats();
                    countCarbs += carb.getCarbs();
                    countCalories += cal.getKcal();

                    Product p = new Product.Builder().id(productId).name(productName).weight(weight).proteins(protein.getProteins()).fats(fat.getFats()).carbs(carb.getCarbs()).kcal(cal.getKcal()).verified(productVerified).dateTimeStamp(productTimeStamp).build();

                    arrayList.add(p);
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
            progressBarCarbsChangeColor(countCarbs, carbsGoal);
            progressBarKcalChangeColor(countCalories, totalCalories);

            setMaxCalories(countCalories);

            DietListAdapter adapter = new DietListAdapter(context, R.layout.row_diet_meal, arrayList);

            ProgressBar pb = ((Activity)context).findViewById(R.id.progressBarListView);
            pb.setVisibility(View.GONE);

            listView.setAdapter(adapter);
            listView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fadein));
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();

            if (arrayList.size() == 0)
            {
                TextView noData = ((Activity) context).findViewById(R.id.textViewNoData);
                noData.setVisibility(View.VISIBLE);
            }
            else
            {
                TextView noData = ((Activity) context).findViewById(R.id.textViewNoData);
                noData.setVisibility(View.INVISIBLE);
            }

            if (getMaxCalories() > 0d)
            {
                DietService dietService = new DietService(context);

                HashMap<String, String> params = new HashMap<>();
                params.put(DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(DB_UPDATE_RESULT, String.valueOf((int) countCalories));
                params.put(DB_DATE, dateFormat);

                dietService.post(params, URL_DIET_UPDATE_COUNTED_KCAL);
            }
            else if (getMaxCalories() == 0d)
            {
                DietService dietService = new DietService(context);

                HashMap<String, String> params = new HashMap<>();
                params.put(DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(DB_DATE, dateFormat);

                dietService.post(params, URL_DIET_DELETE_COUNTED_KCAL);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }



    private void progressBarProteinsChangeColor(double result, double goal)
    {
        int intGoal = Integer.valueOf(String.format(Locale.getDefault(), "%.0f", goal));
        int intResult = Integer.valueOf(String.format(Locale.getDefault(), "%.0f", result));

        pBarProteins.getProgressDrawable().setColorFilter(0xFF3287C3, PorterDuff.Mode.SRC_IN);
        pBarProteins.setMax(intGoal);
        pBarProteins.setProgress(intResult);

        ProgressBarAnimation anim = new ProgressBarAnimation(pBarProteins, PROTEINS_FROM, (int)result);
        anim.setDuration(1000);
        pBarProteins.startAnimation(anim);

        PROTEINS_FROM = (int)result;


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
//        pBarFats.setProgress((int) result);

        ProgressBarAnimation anim = new ProgressBarAnimation(pBarFats, FATS_FROM,(int)result);
        anim.setDuration(1000);
        pBarFats.startAnimation(anim);

        FATS_FROM = (int)result;

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
//        pBarCarbs.setProgress((int) result);

        ProgressBarAnimation anim = new ProgressBarAnimation(pBarCarbs, CARBS_FROM,(int)result);
        anim.setDuration(1000);
        pBarCarbs.startAnimation(anim);

        CARBS_FROM = (int)result;

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
//        pBarKcal.setProgress((int) result);

        ProgressBarAnimation anim = new ProgressBarAnimation(pBarKcal, KCAL_FROM,(int)result);
        anim.setDuration(1000);
        pBarKcal.startAnimation(anim);

        KCAL_FROM = (int)result;

        if (result > goal)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                pBarKcal.getProgressDrawable().setColorFilter(0xFFFF001A, PorterDuff.Mode.SRC_IN);
            }
            else
            {
                pBarKcal.getProgressDrawable().setColorFilter(0xFF89C611, PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private String doubleFormatter(double value)
    {
        return String.format(Locale.getDefault(), "%.0f", value).replace(",", ".");
    }

    public double getMaxCalories()
    {
        return maxCalories;
    }

    public void setMaxCalories(double maxCalories)
    {
        this.maxCalories = maxCalories;
    }
}
