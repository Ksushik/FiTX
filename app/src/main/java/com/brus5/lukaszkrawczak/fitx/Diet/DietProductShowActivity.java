package com.brus5.lukaszkrawczak.fitx.Diet;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Converter.TimeStampReplacer;
import com.brus5.lukaszkrawczak.fitx.Converter.WeightConverter;
import com.brus5.lukaszkrawczak.fitx.DTO.DietDTODiet;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DietProductShowActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private static final String TAG = "DietProductShowActivity";
    ImageView productImageView,imageViewShowProductVerified;

    TextView textViewProductName, textViewProductProteins, textViewProductFats, textViewProductCarbs, textViewProductCalories, textViewProductSaturatedFats, textViewProductUnsaturatedFats, textViewProductFiber, textViewProductSugars;
    EditText editTextProductWeight;
    String productId, userName, productTimeStamp, previousActivity, dateToday, newTimeStamp;
    Spinner spinnerChooser;
    Button buttonAcceptChanges, buttonDelete;
    ProgressBar progressBarDietProductShowActivity;

    @SuppressLint("SimpleDateFormat")
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private double productProteins = 0d;
    private double productFats = 0d;
    private double productCarbs = 0d;

    private double productSaturatedFats = 0d;
    private double productUnsaturatedFats = 0d;
    private double productCarbsFiber = 0d;
    private double productCarbsSugars = 0d;
    private double productMultiplierPiece = 0d;
    private int productVerified = 0;

    private double productWeightPerItems;
    private double productWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_product_show);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();

        String url = "http://justfitx.xyz/images/products/mid/" + productId + ".png";
        loadImageFromUrl(url);
        loadProductInformationAsynchTask(DietProductShowActivity.this);

        editTextProductWeight.setText(String.valueOf(productWeight));
        productWeightChangerEditText(false);
        hideDeleteButtonIfActivityIsDifferent();
    }

    private void hideDeleteButtonIfActivityIsDifferent() {
        if (previousActivity.equals("DietProductSearchActivity")){
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        else {
            buttonDelete.setVisibility(View.VISIBLE);
        }

    }

    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        dateToday = intent.getStringExtra("dateInside");
        Log.i(TAG, "getIntentFromPreviousActiity: "+dateToday);
        productId = intent.getStringExtra("productId");
        userName = SaveSharedPreference.getUserName(DietProductShowActivity.this);
        productTimeStamp = intent.getStringExtra("productTimeStamp");
        productTimeStamp = timeStampChanger(productTimeStamp);
        productWeight = intent.getDoubleExtra("productWeight",50);
        previousActivity = intent.getStringExtra("previousActivity");

        TimeStampReplacer time = new TimeStampReplacer(dateToday, productTimeStamp);
        newTimeStamp = time.getNewTimeStamp();

        Log.i(TAG, "time.getNewTimeStamp(): " +  timeStamp);
        Log.i(TAG, "time.getNewTimeStamp(): " +  time.toString());
        Log.i(TAG, "time.getNewTimeStamp(): " +  time.getNewTimeStamp());
    }

    private String timeStampChanger(String productTimeStamp) {
        if (productTimeStamp == null) return timeStamp;
        else return this.productTimeStamp;
    }

    private void loadInput() {
        productImageView = findViewById(R.id.productImageView);
        imageViewShowProductVerified = findViewById(R.id.imageViewShowProductVerified);

        editTextProductWeight = findViewById(R.id.editTextProductWeight);

        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductProteins = findViewById(R.id.textViewProductProteins);
        textViewProductFats = findViewById(R.id.textViewProductFats);
        textViewProductCarbs = findViewById(R.id.textViewProductCarbs);
        textViewProductCalories = findViewById(R.id.textViewProductCalories);
        textViewProductSaturatedFats = findViewById(R.id.textViewProductSaturatedFats);
        textViewProductUnsaturatedFats = findViewById(R.id.textViewProductUnsaturatedFats);
        textViewProductFiber = findViewById(R.id.textViewProductFiber);
        textViewProductSugars = findViewById(R.id.textViewProductSugars);

        spinnerChooser = findViewById(R.id.spinnerChooser);
        spinnerChooser.setOnItemSelectedListener(this);

        buttonAcceptChanges = findViewById(R.id.buttonAcceptChanges);
        buttonAcceptChanges.setOnClickListener(this);

        buttonDelete = findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(this);

        progressBarDietProductShowActivity = findViewById(R.id.progressBarDietProductShowActivity);
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(DietProductShowActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_error)
                .into(productImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        isError();
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
                            String productName;
                            Log.i(TAG, "onResponse: "+jsonObject.toString(17));
                            JSONArray server_response = jsonObject.getJSONArray("server_response");
                                for (int i = 0; i < server_response.length(); i++) {
                                    JSONObject productInfoJsonObject = server_response.getJSONObject(i);

                                    productName = productInfoJsonObject.getString(RestApiNames.DB_PRODUCT_NAME);
                                    productProteins = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_PROTEINS);
                                    productFats = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_FATS);
                                    productCarbs = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_CARBS);
                                    productSaturatedFats = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_SATURATED_FATS);
                                    productUnsaturatedFats = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_UNSATURATED_FATS);
                                    productCarbsFiber = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_CARBS_FIBER);
                                    productCarbsSugars = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_CARBS_SUGAR);
                                    productMultiplierPiece = productInfoJsonObject.getDouble(RestApiNames.DB_PRODUCT_MULTIPLIER_PIECE);
                                    productVerified = productInfoJsonObject.getInt(RestApiNames.DB_PRODUCT_VERIFIED);

                                    if (productVerified == 1){
                                        imageViewShowProductVerified.setVisibility(View.VISIBLE);
                                    }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                editTextProductWeight.clearFocus();
                editTextProductWeight.setText(String.valueOf(productWeight));
                productWeightChangerEditText(false);
                setProductWeight(productWeight);
                break;
            case 1:
                editTextProductWeight.clearFocus();
                editTextProductWeight.setText("1");
                productWeightChangerEditText(true);
                setProductWeight(100/productMultiplierPiece);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void setProductWeight(Double aDouble) {

        WeightConverter mProteins = new WeightConverter(aDouble,productProteins);
        WeightConverter mFats = new WeightConverter(aDouble,productFats);
        WeightConverter mCarbs = new WeightConverter(aDouble,productCarbs);
        WeightConverter mSaturatedFats = new WeightConverter(aDouble,productSaturatedFats);
        WeightConverter mUnsaturatedFats = new WeightConverter(aDouble,productUnsaturatedFats);
        WeightConverter mFiber = new WeightConverter(aDouble,productCarbsFiber);
        WeightConverter mSugars = new WeightConverter(aDouble,productCarbsSugars);

        textViewProductProteins.setText(mProteins.getConvertedWeight());
        textViewProductFats.setText(mFats.getConvertedWeight());
        textViewProductCarbs.setText(mCarbs.getConvertedWeight());
        textViewProductSaturatedFats.setText(mSaturatedFats.getConvertedWeight());
        textViewProductUnsaturatedFats.setText(mUnsaturatedFats.getConvertedWeight());
        textViewProductFiber.setText(mFiber.getConvertedWeight());
        textViewProductSugars.setText(mSugars.getConvertedWeight());

        double proteins = Double.valueOf(textViewProductProteins.getText().toString());
        double fats = Double.valueOf(textViewProductFats.getText().toString());
        double carbs = Double.valueOf(textViewProductCarbs.getText().toString());

        WeightConverter countCalories = new WeightConverter();

        textViewProductCalories.setText(countCalories.countCalories(proteins,fats,carbs));

//        convertProductPerItem(aDouble);
        setProductWeightPerItems(aDouble);
    }

//    private String convertProductPerItem(Double aDouble) {
//        return String.format("%.0f", aDouble);
//    }

    private void productWeightChangerEditText(final boolean productPieces) {
        editTextProductWeight.didTouchFocusSelect();
        editTextProductWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!productPieces) {
                    if (s.toString().isEmpty() || s.toString().startsWith("0")) {
                        s.append("");
                        resetTextViewsToZero();
                    } else if (Double.valueOf(s.toString()) > 2000) {
                        isError();
                        resetTextViewsToZero();
                        editTextProductWeight.setText("");
                    } else {
                        setProductWeight(Double.valueOf(s.toString()));
                    }
                }
                else {
                        if (s.toString().isEmpty() || s.toString().startsWith("0")) {
                            s.append("");
                            resetTextViewsToZero();
                        } else if (Double.valueOf(s.toString()) > 2000) {
                            isError();
                            resetTextViewsToZero();
                            editTextProductWeight.setText("");
                        } else {
                            setProductWeight(Double.valueOf(s.toString())*(100/productMultiplierPiece));

                        }
                }
            }
        });

    }

    private void resetTextViewsToZero() {
        String s = "0";
        textViewProductProteins.setText(s);
        textViewProductFats.setText(s);
        textViewProductCarbs.setText(s);
        textViewProductCalories.setText(s);
        textViewProductSaturatedFats.setText(s);
        textViewProductUnsaturatedFats.setText(s);
        textViewProductFiber.setText(s);
        textViewProductSugars.setText(s);
    }

    private void isError() {
            Toast.makeText(DietProductShowActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(DietProductShowActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarDietProductShowActivity);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setProductWeightPerItems(double productWeightPerItems) {
        this.productWeightPerItems = productWeightPerItems;
    }

    @SuppressLint("DefaultLocale")
    private String getProductWeightPerItems() {
        return String.format("%.0f", productWeightPerItems);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonAcceptChanges:
                if (previousActivity.equals("DietProductSearchActivity")){
                    Log.e(TAG, "onClick: "+ getProductWeightPerItems() );
                    DietDTODiet dto = new DietDTODiet();
                    dto.productID = productId;
                    dto.userName = SaveSharedPreference.getUserName(DietProductShowActivity.this) ;
                    dto.productWeight = getProductWeightPerItems();
                    dto.productTimeStamp = newTimeStamp;
                    dto.printStatus();
                    DietService service = new DietService();
                    service.DietProductInsert(dto, DietProductShowActivity.this);
                    finish();
                }
                else {
                    Log.e(TAG, "onClick: " + getProductWeightPerItems());
                    DietDTODiet dto = new DietDTODiet();
                    dto.productID = productId;
                    dto.userName = SaveSharedPreference.getUserName(DietProductShowActivity.this);
                    dto.updateProductWeight = getProductWeightPerItems();
                    dto.productTimeStamp = newTimeStamp;
                    dto.printStatus();
                    DietService service = new DietService();
                    service.DietProductWeightUpdate(dto, DietProductShowActivity.this);
                    finish();
                }
                break;
            case R.id.buttonDelete:
                DietDTODiet dto1 = new DietDTODiet();
                dto1.productID = productId;
                dto1.userName = SaveSharedPreference.getUserName(DietProductShowActivity.this);
                dto1.updateProductWeight = getProductWeightPerItems();
                dto1.productTimeStamp = newTimeStamp;
                dto1.printStatus();
                DietService service1 = new DietService();
                service1.DietDeleteProduct(dto1,DietProductShowActivity.this);
                finish();
                break;
        }
    }
}
