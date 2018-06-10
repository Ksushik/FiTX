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
        int task_weight = getItem(position).getTask_weight();
        int task_reps = getItem(position).getTask_reps();

        Training training = new Training(task_id,task_name,task_rest,task_weight,task_reps);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);


        TextView tvId = convertView.findViewById(R.id.trainingExcerciseID);
        TextView tvName = convertView.findViewById(R.id.trainingExcerciseTitle);
        TextView tvRest = convertView.findViewById(R.id.trainingExcerciseRestTime);

        tvId.setText(String.valueOf(task_id));
        tvName.setText(task_name);
        tvRest.setText(String.valueOf(task_rest));

//        TextView tvSet1 = convertView.findViewById(R.id.task_set1);
//        TextView tvSet2 = convertView.findViewById(R.id.task_set2);
//        TextView tvSet3 = convertView.findViewById(R.id.task_set3);
//        TextView tvSet4 = convertView.findViewById(R.id.task_set4);
//        TextView tvSet5 = convertView.findViewById(R.id.task_set5);
//        TextView tvSet6 = convertView.findViewById(R.id.task_set6);
//        TextView tvSet7 = convertView.findViewById(R.id.task_set7);
//        TextView tvSet8 = convertView.findViewById(R.id.task_set8);
//        TextView tvSet9 = convertView.findViewById(R.id.task_set9);
//        TextView tvSet10 = convertView.findViewById(R.id.task_set10);
//
//        tvId.setText(task_id);
//        tvName.setText(task_name);
//        tvRest.setText(task_rest+"s");
//
//        String mTask_weight = task_weight.replaceAll("\\p{Punct}"," ");
//        String[] mWeight_table = mTask_weight.split("\\s+");
//
//        String mTask_reps = task_reps.replaceAll("\\p{Punct}"," ");
//        String[] mReps_table = mTask_reps.split("\\s+");
//
//        if (mWeight_table.length >= 1){
//            tvSet1.setVisibility(View.VISIBLE);
//            tvSet1.setText(mReps_table[0]+DIVIDER+mWeight_table[0]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 2){
//            tvSet2.setVisibility(View.VISIBLE);
//            tvSet2.setText(mReps_table[1]+DIVIDER+mWeight_table[1]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 3){
//            tvSet3.setVisibility(View.VISIBLE);
//            tvSet3.setText(mReps_table[2]+DIVIDER+mWeight_table[2]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 4){
//            tvSet4.setVisibility(View.VISIBLE);
//            tvSet4.setText(mReps_table[3]+DIVIDER+mWeight_table[3]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 5){
//            tvSet5.setVisibility(View.VISIBLE);
//            tvSet5.setText(mReps_table[4]+DIVIDER+mWeight_table[4]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 6){
//            tvSet6.setVisibility(View.VISIBLE);
//            tvSet6.setText(mReps_table[5]+DIVIDER+mWeight_table[5]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 7){
//            tvSet7.setVisibility(View.VISIBLE);
//            tvSet7.setText(mReps_table[6]+DIVIDER+mWeight_table[6]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 8){
//            tvSet8.setVisibility(View.VISIBLE);
//            tvSet8.setText(mReps_table[7]+DIVIDER+mWeight_table[7]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 9){
//            tvSet9.setVisibility(View.VISIBLE);
//            tvSet9.setText(mReps_table[8]+DIVIDER+mWeight_table[8]+WEIGHT_UNITS);
//        }
//        if (mWeight_table.length >= 10){
//            tvSet10.setVisibility(View.VISIBLE);
//            tvSet10.setText(mReps_table[9]+DIVIDER+mWeight_table[9]+WEIGHT_UNITS);
//        }


        return convertView;

    }
}
