package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingSearchListAdapter extends ArrayAdapter<TrainingSearch>
{
    int mResource;
    private Context mContext;

    public TrainingSearchListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TrainingSearch> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint({"LongLogTag", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        double calories = getItem(position).getCalories();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTrainingID = convertView.findViewById(R.id.trainingSearchID);
        TextView tvTrainingTitle = convertView.findViewById(R.id.trainingSearchTitle);
        TextView tvCalories = convertView.findViewById(R.id.textViewCalories);

        tvTrainingID.setText(String.valueOf(id));

        tvCalories.setText(String.valueOf(calories));

        tvTrainingTitle.setText(name);

        return convertView;
    }

}





















