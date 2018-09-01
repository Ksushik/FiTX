package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.dto.DietDTO;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import java.util.HashMap;
import java.util.Map;

public class DietService
{

    private static final String TAG = "DietService";

    public static final String PRODUCT_INSERT = RestAPI.SERVER_URL + "Diet/ProductInsert.php";
    public static final String PRODUCT_UPDATE_WEIGHT = RestAPI.SERVER_URL + "Diet/UpdateProductWeight.php";
    public static final String PRODUCT_DELETE = RestAPI.SERVER_URL + "Diet/ProductDelete.php";
    public static final String PRODUCT_KCAL_DELETE = RestAPI.SERVER_URL + "Diet/KcalResultDelete.php";
    public static final String PRODUCT_KCAL_UPDATE = RestAPI.SERVER_URL + "Diet/KcalResultUpdate.php";

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String WEIGHT = "weight";
    public static final String DATE = "date";

    public static final String UPDATE_WEIGHT = "update_weight";

    private Context context;

    public DietService(Context context)
    {
        this.context = context;
    }

    public DietService()
    {

    }

    public void post(final HashMap params, final String LINK)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LINK, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void updateCalories(final HashMap params, final Context context)
    {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, PRODUCT_KCAL_UPDATE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "onResponse() called with: response = [" + response + "]");
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "onErrorResponse: ", error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void deleteCalories(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, PRODUCT_KCAL_DELETE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_DATE, dto.dateToday);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void updateProductWeight(final DietDTO dto, Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, PRODUCT_UPDATE_WEIGHT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(ID, String.valueOf(dto.productID));
                params.put(USER_ID, String.valueOf(dto.userID));
                params.put(UPDATE_WEIGHT, String.valueOf(dto.updateProductWeight));
                params.put(DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void insert(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, PRODUCT_INSERT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(ID, String.valueOf(dto.productID));
                params.put(USER_ID, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(WEIGHT, String.valueOf(dto.productWeight));
                params.put(DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    public void delete(final DietDTO dto, final Context context)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, PRODUCT_DELETE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {Log.e(TAG, "onResponse: " + response);}
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_DIET_ID, String.valueOf(dto.productID));
                params.put(RestAPI.DB_USER_WEIGHT, String.valueOf(dto.updateProductWeight));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(context)));
                params.put(RestAPI.DB_USER_DIET_DATE, String.valueOf(dto.productTimeStamp));
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }
}
