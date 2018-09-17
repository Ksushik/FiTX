package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.diet.DietProductDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.diet.Product;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"LongLogTag", "Registered"})

public class DietProductDetailsActivityInflater extends DietProductDetailsActivity
{
    private static final String TAG = "DietProductDetailsActivityInflater";

    private Context context;

    public DietProductDetailsActivityInflater(Context context, String response)
    {
        this.context = context;

        dataInflater(response);
    }

    /**
     * Fetching information's from REST API
     *
     * @param s value from REST API in JSON format
     */
    private void dataInflater(String s)
    {
        Log.d(TAG, "dataInflater() called with: s = [" + s + "]");

        try {
            JSONObject jsonObject = new JSONObject(s);
            String name;
            Log.i(TAG, "onResponse: " + jsonObject.toString(17));
            JSONArray server_response = jsonObject.getJSONArray("server_response");
            for (int i = 0; i < server_response.length(); i++) {
                JSONObject srv_response = server_response.getJSONObject(i);


                name = srv_response.getString(RestAPI.DB_PRODUCT_NAME);
                double proteins = srv_response.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
                double fats = srv_response.getDouble(RestAPI.DB_PRODUCT_FATS);
                double carbs = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS);
                double saturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_SATURATED_FATS);
                double unsaturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_UNSATURATED_FATS);
                double carbsFiber = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_FIBER);
                double carbsSugars = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_SUGAR);
                double multiplier = srv_response.getDouble(RestAPI.DB_PRODUCT_MULTIPLIER_PIECE);
                int verified = srv_response.getInt(RestAPI.DB_PRODUCT_VERIFIED);


                // Creating new product
                Product p = new Product.Builder().name(name).proteins(proteins).fats(fats).carbs(carbs).saturatedFats(saturatedFats).unSaturatedFats(unsaturatedFats).carbsFiber(carbsFiber).carbsSugar(carbsSugars).multiplier(multiplier).verified(verified).build();

                load(p, context);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
