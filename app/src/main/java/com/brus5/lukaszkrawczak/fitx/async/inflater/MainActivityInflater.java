package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.MainRow;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.training.TrainingInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivityInflater
{
    private static final String TAG = "MainActivityInflater";

    private Context context;
    private ListView listView;
    private MainRow mainRow;
    private MainAdapter adapter;
    private ArrayList<MainRow> list = new ArrayList<>();

    public MainActivityInflater(Context context, ListView listView, String response)
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

                    mainRow = new MainRow(kcal, kcalLimit, 1);

                    adapter = new MainAdapter(context, R.layout.row_main_diet, list);
                }

                if (kcal > 0)
                {
                    list.add(mainRow);
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

                mainRow = new MainRow(rest, reps, weight, 2);
            }

            if (!weight.equals("0"))
            {
                list.add(mainRow);
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

                mainRow = new MainRow(kcalBurned, time, 3);
            }

            if (kcalBurned != 0)
            {
                list.add(mainRow);
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


class MainAdapter extends ArrayAdapter<MainRow>
{
    private static final int KG_ONE_TONE = 1000;
    private static final String TAG = "MainAdapter";
    private Context mContext;
    private int mResource;

    public MainAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MainRow> objects)
    {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        double textViewBig = getItem(position).getTextViewBig();
        int textViewSmall = getItem(position).getTextViewSmall();
        int viewType = getItem(position).getViewType();


        String rest = getItem(position).getRest();
        String reps = getItem(position).getReps();
        String weight = getItem(position).getWeight();


        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        Log.e(TAG, "position: " + position);

        if (viewType == 1)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_diet, parent, false);

            TextView tvCalories = convertView.findViewById(R.id.textViewCalories);
            TextView tvCaloriesLimit = convertView.findViewById(R.id.textViewCaloriesLimit);

            tvCalories.setText(String.valueOf((int) textViewBig));
            tvCaloriesLimit.setText(String.valueOf(textViewSmall));

            return convertView;
        }



        if (viewType == 2)
        {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_training, parent, false);

            TextView tvLifted = convertView.findViewById(R.id.textViewLifted);
            TextView tvTime = convertView.findViewById(R.id.textViewTrainingTime);
            TextView tvWeightType = convertView.findViewById(R.id.textViewWeightType);



            TrainingInflater trainingInflater = new TrainingInflater(mContext);
            trainingInflater.setReps(reps);
            trainingInflater.setWeight(weight);


            //            int mRest = ((Integer.valueOf(rest) / 1000) + trainingInflater.countRepsTime(reps)) / 60;


            int mRest = Integer.valueOf(rest) + (trainingInflater.countRepsTime(reps) / 60);


            double mWeight = trainingInflater.countLiftedWeight();
            double toneConverter;

            if (mWeight < KG_ONE_TONE)
            {
                tvLifted.setText(String.valueOf(trainingInflater.countLiftedWeight()));
                tvWeightType.setText(R.string.kg_short);
            }
            else
            {
                toneConverter = mWeight / KG_ONE_TONE;
                String value = String.format(Locale.getDefault(), "%.2f", toneConverter);
                tvLifted.setText(value);
                tvWeightType.setText(R.string.t_short);
            }


            tvTime.setText(String.valueOf(mRest));


            return convertView;
        }


        if (viewType == 3)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_cardio, parent, false);

            TextView tvBurned = convertView.findViewById(R.id.textViewBurned);
            TextView tvTrainingTime = convertView.findViewById(R.id.textViewTrainingTime);

            tvBurned.setText(String.valueOf((int) textViewBig));
            tvTrainingTime.setText(String.valueOf(textViewSmall));

            return convertView;
        }


        return convertView;


    }

}