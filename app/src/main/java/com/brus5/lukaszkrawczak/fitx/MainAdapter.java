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

public class MainAdapter extends ArrayAdapter<Main>
{
    private Context mContext;
    int mResource;

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
        int kcal = getItem(position).getKcal();
        int kcalLimit = getItem(position).getKcalLimit();

        new Main(kcal, kcalLimit);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvCalories = convertView.findViewById(R.id.textViewCalories);
        TextView tvCaloriesLimit = convertView.findViewById(R.id.textViewCaloriesLimit);

        tvCalories.setText(String.valueOf(kcal));
        tvCaloriesLimit.setText(String.valueOf(kcalLimit));

        return convertView;
    }

}





















