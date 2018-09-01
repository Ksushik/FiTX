package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.diet.DietProductDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.diet.adapter.DietSearchListAdapter;

import java.util.ArrayList;

@SuppressLint({"LongLogTag", "Registered"})

public class CardioDetailsActivityInflater extends DietProductDetailsActivity
{
    private static final String TAG = "DietProductDetailsActivityInflater";

    private Context context;

    private ListView listView;

    private DietSearchListAdapter adapter;

    private ArrayList<Product> list = new ArrayList<>();


    public CardioDetailsActivityInflater(Context context, String response)
    {
        this.context = context;

        dataInflater(response);
    }

    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");

    }

}
