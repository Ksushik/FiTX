package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Training.TrainingInflater;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class MainAdapter extends ArrayAdapter<Main>
{
    private Context mContext;
    private int mResource;
    private static final int KG_ONE_TONE = 1000;
    private static final String TAG = "MainAdapter";
    public MainAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Main> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
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

        Log.e(TAG, "position: "+position );

        if (viewType == 1)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_diet, parent, false);

            TextView tvCalories = convertView.findViewById(R.id.textViewCalories);
            TextView tvCaloriesLimit = convertView.findViewById(R.id.textViewCaloriesLimit);

            tvCalories.setText(String.valueOf((int)textViewBig));
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


        return convertView;


    }

}





















