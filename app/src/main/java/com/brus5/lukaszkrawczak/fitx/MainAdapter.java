package com.brus5.lukaszkrawczak.fitx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class MainAdapter extends ArrayAdapter<Main>
{
    private Context mContext;
    private int mResource;

    public MainAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Main> objects)
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
        int textViewBig = getItem(position).getTextViewBig();
        int textViewSmall = getItem(position).getTextViewSmall();
        int viewType = getItem(position).getViewType();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        if (viewType == 1)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_diet, parent, false);

            TextView tvCalories = convertView.findViewById(R.id.textViewCalories);
            TextView tvCaloriesLimit = convertView.findViewById(R.id.textViewCaloriesLimit);

            tvCalories.setText(String.valueOf(textViewBig));
            tvCaloriesLimit.setText(String.valueOf(textViewSmall));
        }

        if (viewType == 2)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_main_training, parent, false);

            TextView textViewLifted = convertView.findViewById(R.id.textViewLifted);
            TextView textViewTrainingRest = convertView.findViewById(R.id.textViewTrainingRest);

            textViewLifted.setText(String.valueOf(textViewBig));
            textViewTrainingRest.setText(String.valueOf(textViewSmall));
        }


        return convertView;
    }

}





















