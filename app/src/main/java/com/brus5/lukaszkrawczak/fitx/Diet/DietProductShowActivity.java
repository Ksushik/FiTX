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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DietProductShowActivity extends AppCompatActivity {
    private static final String TAG = "DietProductShowActivity";
    ImageView productImageView;

    TextView textViewProductName, textViewProductProteins, textViewProductFats, textViewProductCarbs, textViewProductCalories;
    EditText editTextProductWeight;
    String productId;

    double productProteins = 0d;
    double productFats = 0d;
    double productCarbs = 0d;
    double productFiber = 0d;
    double proudtSugars = 0d;
    double productCalories = 0d;

    double productWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_product_show);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        productWeight = intent.getDoubleExtra("productWeight",0);

        String url = "http://justfitx.xyz/images/products/mid/" + productId + ".png";

        loadImageFromUrl(url);

        loadProductInformationAsynchTask(DietProductShowActivity.this);

        productWeightChanger();




    }

    private void productWeightChanger() {
        editTextProductWeight.didTouchFocusSelect();
        editTextProductWeight.setText(String.valueOf(productWeight));

        editTextProductWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty() || s.toString().startsWith("0")){
                    s.append("");
                    resetTextViewsToZero();
                }
                else if (Double.valueOf(s.toString()) > 2000){
                    isError(true);
                    resetTextViewsToZero();
                    editTextProductWeight.setText("");
                }
                else {
                    setProductWeight(Double.valueOf(s.toString()));
                }
            }
        });

    }

    private void loadImageFromUrl(String url) {
        Picasso.with(DietProductShowActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .resize(0, 150)
                .into(productImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void loadProductInformationAsynchTask(final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, Configuration.DIET_GET_PRODUCT_INFORMATIONS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String productName = "";

                            JSONArray server_response = jsonObject.getJSONArray("server_response");
                                for (int i = 0; i < server_response.length(); i++) {
                                    JSONObject productInfoJsonObject = server_response.getJSONObject(i);

                                    productName = productInfoJsonObject.getString(RestApiNames.DB_PRODUCT_NAME);
                                    productProteins = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_PROTEINS);
                                    productFats = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_FATS);
                                    productCarbs = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_CARBS);

                                    String upName = productName.substring(0,1).toUpperCase() + productName.substring(1);
                                    textViewProductName.setText(upName);
                                    setProductWeight(productWeight);
                            }
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
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_PRODUCT_ID, productId);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void setProductWeight(Double aDouble) {
        textViewProductProteins.setText(countProteinsGrams(aDouble));
        textViewProductFats.setText(countFatsGrams(aDouble));
        textViewProductCarbs.setText(countCarbsGrams(aDouble));

        double proteins = Double.valueOf(textViewProductProteins.getText().toString());
        double fats = Double.valueOf(textViewProductFats.getText().toString());
        double carbs = Double.valueOf(textViewProductCarbs.getText().toString());

        textViewProductCalories.setText(countCalories(proteins,fats,carbs));
    }

    private void resetTextViewsToZero() {
        String s = "0";
        textViewProductProteins.setText(s);
        textViewProductFats.setText(s);
        textViewProductCarbs.setText(s);
        textViewProductCalories.setText(s);
    }

    private boolean isError(boolean b) {
        Toast.makeText(DietProductShowActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
        return true;
    }

    private String countProteinsGrams(Double weight) {
        Double mProductProteins = productProteins*(weight*0.01);
        return String.format("%.1f",mProductProteins).replace(",",".");
    }
    private String countFatsGrams(Double weight) {
        Double mProductProteins = productFats*(weight*0.01);
        return String.format("%.1f",mProductProteins).replace(",",".");
    }
    private String countCarbsGrams(Double weight) {
        Double mProductProteins = productCarbs*(weight*0.01);
        return String.format("%.1f",mProductProteins).replace(",",".");
    }
    private String countCalories(double proteins, double fats, double carbs) {
        double calories = proteins*4+fats*9+carbs*4;
        return String.format("%.1f",calories).replace(",",".");
    }

    private void loadInput() {
        productImageView = findViewById(R.id.productImageView);
        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductProteins = findViewById(R.id.textViewProductProteins);
        textViewProductFats = findViewById(R.id.textViewProductFats);
        textViewProductCarbs = findViewById(R.id.textViewProductCarbs);
        editTextProductWeight = findViewById(R.id.editTextProductWeight);
        textViewProductCalories = findViewById(R.id.textViewProductCalories);

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietProductShowActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietProductShowActivity);
        setSupportActionBar(toolbar);
    }
    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
