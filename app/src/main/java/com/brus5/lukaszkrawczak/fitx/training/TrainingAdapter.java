package com.brus5.lukaszkrawczak.fitx.training;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.converter.NameConverter;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingAdapter extends ArrayAdapter<Training>
{
    private Context mContext;
    int mResource;
    private static final String TAG = "TrainingAdapter";
    private static int KG_ONE_TONE = 1000;

    public TrainingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int viewType = getItem(position).getViewType();

        int id = getItem(position).getId();
        int done = getItem(position).getDone();
        String name = getItem(position).getName();
        int restTime = getItem(position).getRestTime();
        String weight = getItem(position).getWeight();
        String reps = getItem(position).getReps();
        String timeStamp = getItem(position).getTimeStamp();
        String target = getItem(position).getTarget();

        String time = getItem(position).getTime();
        String kcalPerMin = getItem(position).getKcalPerMin();



        /*training = new Training(id, done, name, time, date, kcalPerMin, 2);*/

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);




        if (viewType == 1)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_training_excercise, parent, false);

            CheckBox checkBox = convertView.findViewById(R.id.trainingExcerciseCheckBox);
            TextView tvId = convertView.findViewById(R.id.trainingID);
            TextView tvName = convertView.findViewById(R.id.trainingExcerciseTitle);
            TextView tvRest = convertView.findViewById(R.id.trainingExcerciseRestTime);
            TextView tvTimeStamp = convertView.findViewById(R.id.trainingTimeStamp);
            TextView tvTarget = convertView.findViewById(R.id.trainingTarget);
            TextView tvSeriesNum = convertView.findViewById(R.id.textViewSeriesNum);
            TextView tvLifted = convertView.findViewById(R.id.textViewSumLiftedWeight);
            TextView tvWeightType = convertView.findViewById(R.id.textViewWeightType);

            if (done == 1)
            {
                checkBox.setChecked(true);
            }
            else
            {
                checkBox.setChecked(false);
            }

            tvId.setText(String.valueOf(id));
            NameConverter nameConverter = new NameConverter();
            nameConverter.setName(name);
            tvName.setText(nameConverter.getName());
            tvTimeStamp.setText(timeStamp);
            tvTarget.setText(target);

            int minutes = restTime / 1000 / 60;
            int seconds = restTime / 1000 % 60;

            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

            tvRest.setText(timeLeftFormatted);

            TrainingInflater trainingInflater = new TrainingInflater(mContext);
            trainingInflater.setReps(reps);
            trainingInflater.setWeight(weight);

            tvSeriesNum.setText(String.valueOf(trainingInflater.getSetNumber()));

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

            return convertView;

        }




        if (viewType == 2)
        {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_training_cardio, parent, false);

            CheckBox checkBox = convertView.findViewById(R.id.cardioCheckBox);
            TextView tvId = convertView.findViewById(R.id.cardioID);
            TextView tvName = convertView.findViewById(R.id.cardioTitle);
            TextView tvTimeStamp = convertView.findViewById(R.id.cardioTimeStamp);
            TextView tvTime = convertView.findViewById(R.id.cardioTime);
            TextView tvKcalBurned = convertView.findViewById(R.id.cardioKcalBurned);
            TextView tvKcalPerMin = convertView.findViewById(R.id.cardioBurnPerMin);


            if (done == 1)
            {
                checkBox.setChecked(true);
            }
            else
            {
                checkBox.setChecked(false);
            }

            tvId.setText(String.valueOf(id));

            NameConverter nameConverter = new NameConverter();
            nameConverter.setName(name);
            tvName.setText(nameConverter.getName());

            tvTimeStamp.setText(timeStamp);

            StringBuilder builder = new StringBuilder();
            builder.append(time);
            builder.append(":");
            builder.append("00");

            tvTime.setText(builder);

            double iBurned = Integer.valueOf(time) * Double.parseDouble(kcalPerMin);
            String burned = String.format(Locale.getDefault(),"%.0f",iBurned);


            tvKcalBurned.setText(String.valueOf(burned));

            tvKcalPerMin.setText(kcalPerMin);

            Log.e(TAG, "tvId: " + tvId.getText().toString());
            Log.e(TAG, "tvName: " + tvName.getText().toString());


            return convertView;
        }

        return convertView;

    }
}
