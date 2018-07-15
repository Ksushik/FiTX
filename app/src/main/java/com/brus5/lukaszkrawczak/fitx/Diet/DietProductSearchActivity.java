package com.brus5.lukaszkrawczak.fitx.Diet;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Diet.DTO.DietSearchProductDTO;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DietProductSearchActivity extends AppCompatActivity {
    private static final String TAG = "DietProductSearchActivi";
    String dateInside;
    EditText editTextSearchProduct;
    ListView listViewShowProducts;
    DietSearchListAdapter dietSearchListAdapter;
    ArrayList<DietSearch> dietSearchArrayList = new ArrayList<>();
    DietSearchProductDTO dto = new DietSearchProductDTO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_product_search);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        searchProduct();
        onListViewItemSelected();
        getIntentFromPreviousActiity();

    }

    private void getIntentFromPreviousActiity() {
        dateInside = getIntent().getStringExtra("dateInside");
    }

    private void loadInput() {
        editTextSearchProduct = findViewById(R.id.editTextSearchProduct);
        listViewShowProducts = findViewById(R.id.listViewShowProducts);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietProductSearchActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietSearchActivity);
        setSupportActionBar(toolbar);
    }
    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void searchProduct() {
        editTextSearchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                dietSearchArrayList.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            private Timer timer=new Timer();
            private final int DELAY = 1500; // milliseconds
            @Override
            public void afterTextChanged(final Editable s) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                dto.name = s.toString();
                                searchProductsAsynchTask(dto,DietProductSearchActivity.this);
                                dto.printStatus();
                            }
                        },
                        DELAY
                );
            }
        });
    }
    public void searchProductsAsynchTask(final DietSearchProductDTO dto, final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_SEARCH_PRODUCT,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            /* Getting DietRatio from MySQL */
                            JSONObject jsonObject = new JSONObject(response);

                            Log.d(TAG, "onResponse: "+jsonObject.toString(1));

                            int productId = 0;
                            String productName = "";
                            double productKcal = 0;
                            int productVerified = 0;

                            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    productId = object.getInt(RestApiNames.DB_PRODUCT_ID);
                                    productName = object.getString(RestApiNames.DB_PRODUCT_NAME);
                                    productKcal = object.getDouble(RestApiNames.DB_PRODUCT_KCAL);
                                    productVerified = object.getInt(RestApiNames.DB_PRODUCT_VERIFIED);

                                    String upName = productName.substring(0,1).toUpperCase() + productName.substring(1);

                                    DietSearch dietSearch = new DietSearch(productId, upName,productKcal,productVerified);
                                    dietSearchArrayList.add(dietSearch);
                                }
                            }
                            /* End */

                            dietSearchListAdapter = new DietSearchListAdapter(DietProductSearchActivity.this,R.layout.diet_meal_search_row, dietSearchArrayList);
                            listViewShowProducts.setAdapter(dietSearchListAdapter);
                            listViewShowProducts.invalidate();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(ctx, Configuration.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onErrorResponse: Error"+error);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_PRODUCT_NAME, dto.name);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }
    private void onListViewItemSelected() {
        listViewShowProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView productId = view.findViewById(R.id.dietMealSearchID);
                Intent intent = new Intent(DietProductSearchActivity.this,DietProductShowActivity.class);
                intent.putExtra("productId",productId.getText().toString());
                intent.putExtra("previousActivity","DietProductSearchActivity");
                intent.putExtra("dateInside",dateInside);
                startActivity(intent);

            }
        });
    }


}
