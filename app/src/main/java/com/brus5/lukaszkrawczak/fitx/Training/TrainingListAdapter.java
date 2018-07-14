package com.brus5.lukaszkrawczak.fitx.Training;

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
        int task_id = getItem(position).getTask_id();
        String task_name = getItem(position).getTask_name();
        int task_rest = getItem(position).getTask_rest();
        String task_weight = getItem(position).getTask_weight();
        String task_reps = getItem(position).getTask_reps();

        new Training(task_id,task_name,task_rest,task_weight,task_reps);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);


        TextView tvId = convertView.findViewById(R.id.trainingExcerciseID);
        TextView tvName = convertView.findViewById(R.id.trainingExcerciseTitle);
        TextView tvRest = convertView.findViewById(R.id.trainingExcerciseRestTime);

        tvId.setText(String.valueOf(task_id));
        tvName.setText(task_name);
        tvRest.setText(String.valueOf(task_rest));

        return convertView;

    }
}
