package com.brus5.lukaszkrawczak.fitx.diet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.dto.DietDTO;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DietProductSearchActivity extends AppCompatActivity implements DefaultView, DietAsynchTaskDTO
{
    private static final String TAG = "DietProductSearchActivi";
    private String dateFormat;
    private EditText etSearch;
    private ListView listView;
    private DietSearchListAdapter adapter;
    private ArrayList<DietSearch> arrayList = new ArrayList<>();
    private DietDTO dto = new DietDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_2_search_product);
        loadInput();
        loadDefaultView();
        searchProduct();
        onListViewItemSelected();
        getIntentFromPreviousActiity();
    }

    public void loadInput()
    {
        etSearch = findViewById(R.id.editTextSearchProduct);
        listView = findViewById(R.id.listViewShowProducts);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(DietProductSearchActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarDietSearchActivity);
        activityView.showBackButton();
    }

    private void getIntentFromPreviousActiity()
    {
        dateFormat = getIntent().getStringExtra("dateFormat");
    }

    private void searchProduct()
    {
        etSearch.addTextChangedListener(new TextWatcher()
        {
            private final int DELAY = 1500; // milliseconds
            private Timer timer = new Timer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                arrayList.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(final Editable s)
            {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        dto.productName = s.toString();
                        loadAsynchTask(dto, DietProductSearchActivity.this);
                    }
                }, DELAY);
            }
        });
    }

    public void loadAsynchTask(final DietDTO dietDTO, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_SEARCH_PRODUCT, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

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

                            DietSearch dietSearch = new DietSearch(id, nameUpperCase, calories, verified);
                            arrayList.add(dietSearch);
                        }
                    }
                    /* End */

                    adapter = new DietSearchListAdapter(DietProductSearchActivity.this, R.layout.row_diet_meal_search, arrayList);
                    listView.setAdapter(adapter);
                    listView.invalidate();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_PRODUCT_NAME, dietDTO.productName);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView productId = view.findViewById(R.id.dietMealSearchID);
                Intent intent = new Intent(DietProductSearchActivity.this, DietProductShowActivity.class);
                intent.putExtra("productID", Integer.valueOf(productId.getText().toString()));
                intent.putExtra("previousActivity", DietProductSearchActivity.this.getClass().getSimpleName());
                intent.putExtra("dateFormat", dateFormat);
                DietProductSearchActivity.this.startActivity(intent);
            }
        });
    }
}