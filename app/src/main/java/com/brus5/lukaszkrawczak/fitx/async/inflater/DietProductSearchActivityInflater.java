package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.diet.adapter.DietSearchListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("LongLogTag")

public class DietProductSearchActivityInflater
{
    private static final String TAG = "DietProductSearchActivityInflater";

    private Context context;

    private ListView listView;

    private DietSearchListAdapter adapter;

    private ArrayList<Product> list = new ArrayList<>();

    public DietProductSearchActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;

        dataInflater(response);
    }

    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");
        try
        {
            JSONObject jsonObject = new JSONObject(s);

            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            int id;
            String name;
            double calories;
            int verified;

            JSONArray server_response = jsonObject.getJSONArray("server_response");
            if (server_response.length() > 0)
            {
                for (int i = 0; i < server_response.length(); i++)
                {
                    JSONObject srv_response = server_response.getJSONObject(i);
                    id = srv_response.getInt(RestAPI.DB_PRODUCT_ID);
                    name = srv_response.getString(RestAPI.DB_PRODUCT_NAME);
                    calories = srv_response.getDouble(RestAPI.DB_PRODUCT_KCAL);
                    verified = srv_response.getInt(RestAPI.DB_PRODUCT_VERIFIED);

                    String nameUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1);

                    Product dietSearch = new Product(id, nameUpperCase, calories, verified);
                    list.add(dietSearch);
                }
            }
            /* End */

            adapter = new DietSearchListAdapter(context, R.layout.row_diet_meal_search, list);
            listView.setAdapter(adapter);
            listView.invalidate();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
