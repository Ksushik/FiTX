package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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

public class TrainingListAdapter extends ArrayAdapter<Training> {

    final private static String TAG = "TrainingListAdapter";

    public static final String WEIGHT_UNITS = "kg ";
    public static final String DIVIDER = "x";

    private Context mContext;
    int mResource;

    public TrainingListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        int restTime = getItem(position).getRestTime();
        String weight = getItem(position).getWeight();
        String reps = getItem(position).getReps();
        String timeStamp = getItem(position).getTimeStamp();

        new Training(id, name, restTime,weight, reps, timeStamp);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvId = convertView.findViewById(R.id.trainingExcerciseID);
        TextView tvName = convertView.findViewById(R.id.trainingExcerciseTitle);
        TextView tvRest = convertView.findViewById(R.id.trainingExcerciseRestTime);
        TextView tvTimeStamp = convertView.findViewById(R.id.trainingTimeStamp);

        tvId.setText(String.valueOf(id));
        tvName.setText(name);
        tvRest.setText(String.valueOf(restTime));
        tvTimeStamp.setText(timeStamp);

        return convertView;
    }
}
