package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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

public class DietListAdapter extends ArrayAdapter<Diet>{

    private static final String TAG = "DietListAdapter";

    private Context mContext;
    int mResource;

    public DietListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Diet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        RecyclerView.ViewHolder holder;

        final View result;

        int id = getItem(position).getId();
        String name = getItem(position).getName();
        double weight = getItem(position).getWeight();
        double proteins = getItem(position).getProteins();
        double fats = getItem(position).getFats();
        double carbs = getItem(position).getCarbs();
        double kcal = getItem(position).getKcal();

        new Diet(id, name, weight, proteins, fats, carbs, kcal);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView dietMealTitle = convertView.findViewById(R.id.dietMealTitle);
        TextView dietMealWeight = convertView.findViewById(R.id.dietMealWeight);
        TextView dietMealProteins = convertView.findViewById(R.id.dietMealProteins);
        TextView dietMealFats = convertView.findViewById(R.id.dietMealFats);
        TextView dietMealCarbs = convertView.findViewById(R.id.dietMealCarbs);
        TextView dietMealID = convertView.findViewById(R.id.dietMealID);
        TextView dietMealKcal = convertView.findViewById(R.id.dietMealKcal);

        dietMealID.setText(String.valueOf(id));

        dietMealTitle.setText(name);

        dietMealProteins.setText(replaceCommaWithDotWithFloatingPoint(proteins));
        dietMealFats.setText(replaceCommaWithDotWithFloatingPoint(fats));
        dietMealCarbs.setText(replaceCommaWithDotWithFloatingPoint(carbs));

        dietMealKcal.setText(replaceCommaWithDotNoFloatingPoint(kcal));

        dietMealWeight.setText(replaceCommaWithDotNoFloatingPoint(weight));

        ArrayList arrayList = new ArrayList();
        arrayList.add(kcal);

        Log.i(TAG, "getView: ArrayList "+arrayList);

        return convertView;
    }
    private String replaceCommaWithDotNoFloatingPoint(double value){
        return String.format("%.0f",value).replace(",",".");
    }

    private String replaceCommaWithDotWithFloatingPoint(double value){
        return String.format("%.1f",value).replace(",",".");
    }




}





















