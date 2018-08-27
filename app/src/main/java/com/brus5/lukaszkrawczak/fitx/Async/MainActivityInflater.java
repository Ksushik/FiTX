package com.brus5.lukaszkrawczak.fitx.Async;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.Main;
import com.brus5.lukaszkrawczak.fitx.MainAdapter;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.Training.TrainingInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityInflater
{
    private static final String TAG = "MainActivityInflater";


    private Context context;
    private ListView listView;
    private Main main;
    private MainAdapter adapter;
    private ArrayList<Main> list = new ArrayList<>();

    MainActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;

        dataInflater(response);
    }

    private void dataInflater(String s)
    {
        try
        {
            int kcal = 0;
            int kcalLimit;

            JSONObject jsonObject = new JSONObject(s);

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

                    adapter = new MainAdapter(context, R.layout.row_main_diet, list);
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
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray response = jsonObject1.getJSONArray("response");

            JSONObject repetitionsObj = response.getJSONObject(2);
            JSONObject liftedObj = response.getJSONObject(3);
            JSONObject restObj = response.getJSONObject(4);

            String weight = "0";
            String rest;
            String reps;

            TrainingInflater inflater = new TrainingInflater(context);

            for (int i = 0; i < response.length(); i++)
            {
                reps = repetitionsObj.getString("repetitions");
                weight = liftedObj.getString("lifted");
                rest = restObj.getString("rest");

                inflater.setWeight(weight);
                inflater.setReps(reps);

                adapter = new MainAdapter(context, R.layout.row_main_diet, list);

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



        try
        {
            JSONObject jsonObject1 = new JSONObject(s);
            JSONArray response = jsonObject1.getJSONArray("response");

            JSONObject cardio_counted = response.getJSONObject(5);
            JSONObject cardio_time = response.getJSONObject(6);


            double kcalBurned = 0;
            int time;

            for (int i = 0; i < response.length(); i++)
            {
                kcalBurned = cardio_counted.getDouble("cardio_counted");
                time = cardio_time.getInt("cardio_time");

                adapter = new MainAdapter(context, R.layout.row_main_diet, list);

                main = new Main(kcalBurned, time, 3);
            }

            if (kcalBurned != 0)
            {
                list.add(main);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.postInvalidate();
    }

}
