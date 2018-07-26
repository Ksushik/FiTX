package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Converter.NameConverter;
import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingAdapter extends ArrayAdapter<Training>
{
    private Context mContext;
    int mResource;

    private static int KG_ONE_TONE = 1000;

    public TrainingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int id = getItem(position).getId();
        int done = getItem(position).getDone();
        String name = getItem(position).getName();
        int restTime = getItem(position).getRestTime();
        String weight = getItem(position).getWeight();
        String reps = getItem(position).getReps();
        String timeStamp = getItem(position).getTimeStamp();
        String target = getItem(position).getTarget();

        new Training(id, done, name, restTime, weight, reps, timeStamp, target);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        CheckBox cbDone = convertView.findViewById(R.id.trainingExcerciseCheckBox);
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
            cbDone.setChecked(true);
        }
        else
        {
            cbDone.setChecked(false);
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
}
