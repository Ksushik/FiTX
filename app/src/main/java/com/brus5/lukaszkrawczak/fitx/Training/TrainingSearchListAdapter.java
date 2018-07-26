package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Diet.DietSearch;
import com.brus5.lukaszkrawczak.fitx.R;

import java.util.ArrayList;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class TrainingSearchListAdapter extends ArrayAdapter<TrainingSearch>
{
    private Context mContext;
    int mResource;

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

        new TrainingSearch(id, name);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView trainingID = convertView.findViewById(R.id.trainingSearchID);
        TextView trainingTitle = convertView.findViewById(R.id.trainingSearchTitle);

        trainingID.setText(String.valueOf(id));

        trainingTitle.setText(name);

        return convertView;
    }

}





















