package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.diet.DietProductDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.diet.adapter.DietSearchListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint({"LongLogTag", "Registered"})

public class DietProductDetailsActivityInflater
{
    private static final String TAG = "DietProductDetailsActivityInflater";

    private Context context;

    private ListView listView;

    private DietSearchListAdapter adapter;

    private ArrayList<Product> list = new ArrayList<>();


    public DietProductDetailsActivityInflater(Context context, ListView listView, String response)
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
            String name;
            Log.i(TAG, "onResponse: " + jsonObject.toString(17));
            JSONArray server_response = jsonObject.getJSONArray("server_response");
            for (int i = 0; i < server_response.length(); i++)
            {
                JSONObject srv_response = server_response.getJSONObject(i);

                DietProductDetailsActivity diet = new DietProductDetailsActivity();
                name = srv_response.getString(RestAPI.DB_PRODUCT_NAME);
                //                diet1.proteins = srv_response.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
                //                diet1.fats = srv_response.getDouble(RestAPI.DB_PRODUCT_FATS);
                //                diet1.carbs = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS);
                //                diet1.saturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_SATURATED_FATS);
                //                diet1.unsaturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_UNSATURATED_FATS);
                //                diet1.carbsFiber = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_FIBER);
                //                diet1.carbsSugars = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_SUGAR);
                //                diet1.multiplier = srv_response.getDouble(RestAPI.DB_PRODUCT_MULTIPLIER_PIECE);
                //                diet1.verified = srv_response.getInt(RestAPI.DB_PRODUCT_VERIFIED);

                //                if (diet.verified == 1)
                //                {
                //                    diet.imgVerified.setVisibility(View.VISIBLE);
                //                }

                String upName = name.substring(0, 1).toUpperCase() + name.substring(1);
                //                tvName.setText(upName);
                //                setProductWeight(productWeight);



            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

}
