package com.brus5.lukaszkrawczak.fitx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class MainAdapterTraining extends ArrayAdapter<MainTraining>
{
    private Context mContext;
    int mResource;

    public MainAdapterTraining(@NonNull Context context, int resource, @NonNull ArrayList<MainTraining> objects)
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
        int lifted = getItem(position).getLifted();
        int reps = getItem(position).getReps();

        new MainTraining(reps,lifted);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvLifted = convertView.findViewById(R.id.textViewLifted);


        tvLifted.setText(String.valueOf(lifted));



        return convertView;
    }

}





















