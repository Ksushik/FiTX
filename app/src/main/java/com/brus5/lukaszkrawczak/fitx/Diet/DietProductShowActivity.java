package com.brus5.lukaszkrawczak.fitx.Diet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.AsynchTask;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Converter.TimeStampReplacer;
import com.brus5.lukaszkrawczak.fitx.Converter.WeightConverter;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTO;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class DietProductShowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DefaultView, AsynchTask
{
    private static final String TAG = "DietProductShowActivity";
    private ImageView imgProduct, imgVerified;
    private TextView tvName, tvProteins, tvFats, tvCarbs, tvCalories, tvFatsSaturated, tvFatsUnsaturated, tvCarbsFiber, tvCarbsSugars;
    private EditText etWeight;
    private int productID;
    private String productTimeStamp, previousActivity, dateFormat, newTimeStamp;
    private Spinner spinner;
    private ProgressBar progrssBar;
    private Configuration cfg = new Configuration();
    private ConstraintLayout constraintLayout;

    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());


    private double proteins = 0d;
    private double fats = 0d;
    private double carbs = 0d;
    private double saturatedFats = 0d;
    private double unsaturatedFats = 0d;
    private double carbsFiber = 0d;
    private double carbsSugars = 0d;
    private double multiplier = 0d;
    private double productWeightPerItems;
    private double productWeight;
    private int verified = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_3_details);
        cfg.changeStatusBarColor(this, getApplicationContext(), R.id.toolbarDietProductShowActivity,this);
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();

        String url = "http://justfitx.xyz/images/products/mid/" + productID + ".png";
        loadImageFromUrl(url);
        loadAsynchTask(DietProductShowActivity.this);

        etWeight.setText(String.valueOf(productWeight));
        setWeight(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_4_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_product);
        if (previousActivity.equals( DietActivity.class.getSimpleName() ))
        {
            item.setVisible(true);
        }
        else
        {
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_product:
                if (previousActivity.equals( DietActivity.class.getSimpleName() ) )
                {
                    Toast.makeText(this, R.string.product_updated, Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onClick: " + getProductWeightPerItems());
                    DietDTO updateDTO = new DietDTO();
                    updateDTO.productID = productID;
                    updateDTO.userID = SaveSharedPreference.getUserID(DietProductShowActivity.this);
                    updateDTO.updateProductWeight = Integer.valueOf(getProductWeightPerItems());
                    updateDTO.productTimeStamp = newTimeStamp;

                    Log.i(TAG, "onClick: " + updateDTO.toString());

                    DietService service = new DietService();
                    service.updateProductWeight(updateDTO, DietProductShowActivity.this);
                    finish();
                }

                else if (previousActivity.equals( DietProductSearchActivity.class.getSimpleName() ) )
                {
                    Toast.makeText(this, R.string.product_inserted, Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onClick: " + getProductWeightPerItems());

                    DietDTO insertDTO = new DietDTO();
                    insertDTO.productID = productID;
                    insertDTO.userName = SaveSharedPreference.getUserName(DietProductShowActivity.this);
                    insertDTO.productWeight = Integer.valueOf(getProductWeightPerItems());
                    insertDTO.productTimeStamp = newTimeStamp;

                    Log.i(TAG, "onClick: " + insertDTO.toString());


                    DietService service = new DietService();
                    service.insert(insertDTO, DietProductShowActivity.this);
                    finish();
                }
                break;

            case R.id.menu_delete_product:
                Toast.makeText(this, R.string.product_deleted, Toast.LENGTH_SHORT).show();

                DietDTO deleteDTO = new DietDTO();
                deleteDTO.productID = productID;
                deleteDTO.userName = SaveSharedPreference.getUserName(DietProductShowActivity.this);
                deleteDTO.updateProductWeight = Integer.valueOf(getProductWeightPerItems());
                deleteDTO.productTimeStamp = newTimeStamp;

                Log.i(TAG, "onClick: " + deleteDTO.toString());

                DietService service1 = new DietService();
                service1.delete(deleteDTO, DietProductShowActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        dateFormat = intent.getStringExtra("dateFormat");
        productID = intent.getIntExtra("productID",-1);
        productTimeStamp = intent.getStringExtra("productTimeStamp");
        productTimeStamp = timeStampChanger(productTimeStamp);
        productWeight = intent.getDoubleExtra("productWeight", 50);
        previousActivity = intent.getStringExtra("previousActivity");

        TimeStampReplacer time = new TimeStampReplacer(dateFormat, productTimeStamp);
        newTimeStamp = time.getNewTimeStamp();

        Log.e(TAG, "dateFormat: "           + dateFormat);
        Log.e(TAG, "productID: "    + productID);
        Log.e(TAG, "productTimeStamp: "         + productTimeStamp);
        Log.e(TAG, "productTimeStamp: "           + productTimeStamp);
        Log.e(TAG, "productWeight: "     + productWeight);
        Log.e(TAG, "previousActivity: "           + previousActivity);
        Log.e(TAG, "newTimeStamp: "         + newTimeStamp);
    }

    private String timeStampChanger(String productTimeStamp)
    {
        if (productTimeStamp == null) return timeStamp;
        else return this.productTimeStamp;
    }

    public void loadInput()
    {
        imgProduct = findViewById(R.id.imageViewProduct);
        imgVerified = findViewById(R.id.imageViewVerified);

        etWeight = findViewById(R.id.editTextWeight);
        etWeight.clearFocus();
        etWeight.didTouchFocusSelect();

        tvName = findViewById(R.id.textViewProductName);
        tvProteins = findViewById(R.id.textViewProteins);
        tvFats = findViewById(R.id.textViewFats);
        tvCarbs = findViewById(R.id.textViewCarbs);
        tvCalories = findViewById(R.id.textViewCalories);
        tvFatsSaturated = findViewById(R.id.textViewFatsSaturated);
        tvFatsUnsaturated = findViewById(R.id.textViewFatsUnsaturated);
        tvCarbsFiber = findViewById(R.id.textViewCarbsFiber);
        tvCarbsSugars = findViewById(R.id.textViewCarbsSugars);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        progrssBar = findViewById(R.id.progressBar);

        constraintLayout = findViewById(R.id.constraintLayoutDietDetails);
        constraintLayout.requestFocus();
    }

    private void loadImageFromUrl(String url)
    {
        Picasso.with(DietProductShowActivity.this).load(url).placeholder(null)
                .error(R.drawable.image_no_available)
                .into(imgProduct, new com.squareup.picasso.Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        progrssBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError()
                    {
                        progrssBar.setVisibility(View.INVISIBLE);
                        cfg.showError(DietProductShowActivity.this);
                    }
                });
    }

    public void loadAsynchTask(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_GET_PRODUCT_INFORMATIONS, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    String name;
                    Log.i(TAG, "onResponse: " + jsonObject.toString(17));
                    JSONArray server_response = jsonObject.getJSONArray("server_response");
                    for (int i = 0; i < server_response.length(); i++)
                    {
                        JSONObject srv_response = server_response.getJSONObject(i);

                        name = srv_response.getString(RestAPI.DB_PRODUCT_NAME);
                        proteins = srv_response.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
                        fats = srv_response.getDouble(RestAPI.DB_PRODUCT_FATS);
                        carbs = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS);
                        saturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_SATURATED_FATS);
                        unsaturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_UNSATURATED_FATS);
                        carbsFiber = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_FIBER);
                        carbsSugars = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_SUGAR);
                        multiplier = srv_response.getDouble(RestAPI.DB_PRODUCT_MULTIPLIER_PIECE);
                        verified = srv_response.getInt(RestAPI.DB_PRODUCT_VERIFIED);

                        if (verified == 1)
                        {
                            imgVerified.setVisibility(View.VISIBLE);
                        }

                        String upName = name.substring(0, 1).toUpperCase() + name.substring(1);
                        tvName.setText(upName);
                        DietProductShowActivity.this.setProductWeight(productWeight);
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {Toast.makeText(ctx, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();}
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_PRODUCT_ID, String.valueOf(productID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            case 0:
                etWeight.setText(String.valueOf((int)productWeight));
                setWeight(false);
                setProductWeight(productWeight);
                etWeight.clearFocus();
                etWeight.didTouchFocusSelect();
                break;
            case 1:
                etWeight.setText("1");
                setWeight(true);
                setProductWeight(100 / multiplier);
                etWeight.clearFocus();
                etWeight.didTouchFocusSelect();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void setProductWeight(Double aDouble)
    {
        WeightConverter mProteins = new WeightConverter(aDouble, proteins);
        WeightConverter mFats = new WeightConverter(aDouble, fats);
        WeightConverter mCarbs = new WeightConverter(aDouble, carbs);
        WeightConverter mSaturatedFats = new WeightConverter(aDouble, saturatedFats);
        WeightConverter mUnsaturatedFats = new WeightConverter(aDouble, unsaturatedFats);
        WeightConverter mFiber = new WeightConverter(aDouble, carbsFiber);
        WeightConverter mSugars = new WeightConverter(aDouble, carbsSugars);

        tvProteins.setText(mProteins.getConvertedWeight());
        tvFats.setText(mFats.getConvertedWeight());
        tvCarbs.setText(mCarbs.getConvertedWeight());
        tvFatsSaturated.setText(mSaturatedFats.getConvertedWeight());
        tvFatsUnsaturated.setText(mUnsaturatedFats.getConvertedWeight());
        tvCarbsFiber.setText(mFiber.getConvertedWeight());
        tvCarbsSugars.setText(mSugars.getConvertedWeight());

        double proteins = Double.valueOf(tvProteins.getText().toString());
        double fats = Double.valueOf(tvFats.getText().toString());
        double carbs = Double.valueOf(tvCarbs.getText().toString());

        WeightConverter converter = new WeightConverter();

        tvCalories.setText(converter.countCalories(proteins, fats, carbs));

        setProductWeightPerItems(aDouble);
    }

    private void setWeight(final boolean item)
    {
        etWeight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!item)
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        resetTextViewsToZero();
                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        cfg.showError(DietProductShowActivity.this);
                        resetTextViewsToZero();
                        etWeight.setText("");
                    }
                    else
                    {
                        setProductWeight(Double.valueOf(s.toString()));
                    }
                }
                else
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        resetTextViewsToZero();
                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        cfg.showError(DietProductShowActivity.this);
                        resetTextViewsToZero();
                        etWeight.setText("");
                    }
                    else
                    {
                        setProductWeight(Double.valueOf(s.toString()) * (100 / multiplier));

                    }
                }
            }
        });
    }

    private void resetTextViewsToZero()
    {
        String s = "0";
        tvProteins.setText(s);
        tvFats.setText(s);
        tvCarbs.setText(s);
        tvCalories.setText(s);
        tvFatsSaturated.setText(s);
        tvFatsUnsaturated.setText(s);
        tvCarbsFiber.setText(s);
        tvCarbsSugars.setText(s);
    }

    private void onBackButtonPressed()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setProductWeightPerItems(double productWeightPerItems)
    {
        this.productWeightPerItems = productWeightPerItems;
    }

    private String getProductWeightPerItems()
    {
        return String.format(Locale.getDefault(),"%.0f", productWeightPerItems);
    }
}
