package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;

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
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        searchProduct();
        onListViewItemSelected();
        getIntentFromPreviousActiity();
    }

    private void getIntentFromPreviousActiity()
    {
        dateFormat = getIntent().getStringExtra("dateFormat");
    }

    public void loadInput()
    {
        etSearch = findViewById(R.id.editTextSearchProduct);
        listView = findViewById(R.id.listViewShowProducts);
    }

    public void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietProductSearchActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietSearchActivity);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void searchProduct()
    {
        etSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                arrayList.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}


            private Timer timer=new Timer();
            private final int DELAY = 1500; // milliseconds

            @Override
            public void afterTextChanged(final Editable s)
            {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask()
                        {
                            @Override
                            public void run()
                            {
                                dto.productName = s.toString();
                                loadAsynchTask(dto,DietProductSearchActivity.this);
                                dto.printStatus();
                            }
                        },
                        DELAY
                );
            }
        });
    }

    public void loadAsynchTask(final DietDTO dietDTO, final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_SEARCH_PRODUCT, response -> {
            try
            {
                JSONObject jsonObject = new JSONObject(response);

                Log.d(TAG, "onResponse: "+jsonObject.toString(1));

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

                        String nameUpperCase = name.substring(0,1).toUpperCase() + name.substring(1);

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
        }, error -> {
            Toast.makeText(context, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onErrorResponse: Error"+error);
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestAPI.DB_PRODUCT_NAME, dietDTO.productName);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView productId = view.findViewById(R.id.dietMealSearchID);
            Intent intent = new Intent(DietProductSearchActivity.this, DietProductShowActivity.class);
            intent.putExtra("productID", productId.getText().toString());
            intent.putExtra("previousActivity", "DietProductSearchActivity");
            intent.putExtra("dateFormat", dateFormat);
            startActivity(intent);

        });
    }
}