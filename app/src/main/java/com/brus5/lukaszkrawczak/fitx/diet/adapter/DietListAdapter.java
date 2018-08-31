package com.brus5.lukaszkrawczak.fitx.diet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.diet.Product;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by lukaszkrawczak on 18.03.2018.
 */

public class DietListAdapter extends ArrayAdapter<Product>
{
    private Context mContext;
    int mResource;

    public DietListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects)
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
        String name = getItem(position).getName();
        double weight = getItem(position).getWeight();
        double proteins = getItem(position).getProteins();
        double fats = getItem(position).getFats();
        double carbs = getItem(position).getCarbs();
        double kcal = getItem(position).getKcal();
        int verified = getItem(position).getVerified();
        String dateTimeStamp = getItem(position).getDateTimeStamp();

        new Product(id, name, weight, proteins, fats, carbs, kcal, verified, dateTimeStamp);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView dietMealTitle = convertView.findViewById(R.id.dietMealTitle);
        TextView dietMealWeight = convertView.findViewById(R.id.dietMealWeight);
        TextView dietMealProteins = convertView.findViewById(R.id.dietMealProteins);
        TextView dietMealFats = convertView.findViewById(R.id.dietMealFats);
        TextView dietMealCarbs = convertView.findViewById(R.id.dietMealCarbs);
        TextView dietMealID = convertView.findViewById(R.id.dietMealID);
        TextView dietMealKcal = convertView.findViewById(R.id.dietMealKcal);
        TextView dietTimeStamp = convertView.findViewById(R.id.dietTimeStamp);
        ImageView imageViewProductVerified = convertView.findViewById(R.id.imageViewProductVerified);

        dietMealID.setText(String.valueOf(id));

        dietMealTitle.setText(name);

        dietMealProteins.setText(replaceCommaWithDotWithFloatingPoint(proteins));
        dietMealFats.setText(replaceCommaWithDotWithFloatingPoint(fats));
        dietMealCarbs.setText(replaceCommaWithDotWithFloatingPoint(carbs));

        dietMealKcal.setText(replaceCommaWithDotNoFloatingPoint(kcal));

        dietMealWeight.setText(replaceCommaWithDotNoFloatingPoint(weight));

        dietTimeStamp.setText(dateTimeStamp);

        if (verified == 0)
        {
            imageViewProductVerified.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
    private String replaceCommaWithDotNoFloatingPoint(double value)
    {
        return String.format(Locale.getDefault(),"%.0f",value).replace(",",".");
    }

    private String replaceCommaWithDotWithFloatingPoint(double value)
    {
        return String.format(Locale.getDefault(),"%.1f",value).replace(",",".");
    }

}





















