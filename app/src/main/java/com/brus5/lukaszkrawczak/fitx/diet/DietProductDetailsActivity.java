package com.brus5.lukaszkrawczak.fitx.diet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStampReplacer;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.ImageLoader;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.brus5.lukaszkrawczak.fitx.diet.DietService.DATE;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.ID;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.UPDATE_WEIGHT;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.USER_ID;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.WEIGHT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_DELETE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_INSERT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_UPDATE_WEIGHT;

/**
 * That View devilers to user informations about products.
 * Downloaded data from REST server are in JSON format.
 * They are converted as a JSON objects and delivered to View.
 * <p>
 * In this Activity user can Delete, Update or Add new product to his daily list.
 * <p>
 * User can change Product Weight in EditText and also he can use whole
 * pieces as a Weight Type. For Example 1 banana as a 1 piece weights 118g
 * <p>
 * In UI user can see whole data about macronutrients which are dynamically
 * updates while user writes number in EditText
 */

@SuppressLint("LongLogTag")
public class DietProductDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DefaultView
{
    private static final String TAG = "DietProductDetailsActivity";
    private ImageView imgVerified;
    private TextView tvName, tvProteins, tvFats, tvCarbs, tvCalories, tvFatsSaturated, tvFatsUnsaturated, tvCarbsFiber, tvCarbsSugars;
    private EditText etWeight;
    private int productID;
    private String productTimeStamp, previousActivity, dateFormat, newTimeStamp;
    private Spinner spinner;
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
    private static double PRODUCT_WEIGHT;
    private int verified = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_3_details);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        String url = "http://justfitx.xyz/images/products/mid/" + productID + ".png"; // This must by under getIntentFromPreviousActiity()

        new ImageLoader(DietProductDetailsActivity.this, R.id.imageViewProduct, R.id.progressBar, url);

        //        loadAsynchTask(DietProductDetailsActivity.this);
        new Provider(DietProductDetailsActivity.this, DietProductDetailsActivity.this).load(String.valueOf(productID));

        etWeight.setText(String.valueOf(PRODUCT_WEIGHT));
        setWeight(false);
    }

    public void loadInput()
    {
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


        constraintLayout = findViewById(R.id.constraintLayoutDietDetails);
        constraintLayout.requestFocus();
    }

    public void loadInput(Context context)
    {
        imgVerified = ((Activity) context).findViewById(R.id.imageViewVerified);

        etWeight = ((Activity) context).findViewById(R.id.editTextWeight);
        etWeight.clearFocus();
        etWeight.didTouchFocusSelect();

        tvName = ((Activity) context).findViewById(R.id.textViewProductName);
        tvProteins = ((Activity) context).findViewById(R.id.textViewProteins);
        tvFats = ((Activity) context).findViewById(R.id.textViewFats);
        tvCarbs = ((Activity) context).findViewById(R.id.textViewCarbs);
        tvCalories = ((Activity) context).findViewById(R.id.textViewCalories);
        tvFatsSaturated = ((Activity) context).findViewById(R.id.textViewFatsSaturated);
        tvFatsUnsaturated = ((Activity) context).findViewById(R.id.textViewFatsUnsaturated);
        tvCarbsFiber = ((Activity) context).findViewById(R.id.textViewCarbsFiber);
        tvCarbsSugars = ((Activity) context).findViewById(R.id.textViewCarbsSugars);

        spinner = ((Activity) context).findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


        constraintLayout = ((Activity) context).findViewById(R.id.constraintLayoutDietDetails);
        constraintLayout.requestFocus();
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(DietProductDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarDietProductShowActivity);
        activityView.showBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_4_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_product);
        if (previousActivity.equals(DietActivity.class.getSimpleName()))
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
                if (previousActivity.equals(DietActivity.class.getSimpleName()))
                {
                    Toast.makeText(this, R.string.product_updated, Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onClick: " + getProductWeightPerItems());


                    DietService service = new DietService(DietProductDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(ID, String.valueOf(productID));
                    params.put(USER_ID, String.valueOf(SaveSharedPreference.getUserID(DietProductDetailsActivity.this)));
                    params.put(UPDATE_WEIGHT, getProductWeightPerItems());
                    params.put(DATE, newTimeStamp);

                    service.post(params, URL_DIET_PRODUCT_UPDATE_WEIGHT);

                    finish();
                }

                else if (previousActivity.equals(DietProductSearchActivity.class.getSimpleName()))
                {
                    Toast.makeText(this, R.string.product_inserted, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(DietProductDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(ID, String.valueOf(productID));
                    params.put(USER_ID, String.valueOf(SaveSharedPreference.getUserID(DietProductDetailsActivity.this)));
                    params.put(WEIGHT, getProductWeightPerItems());
                    params.put(DATE, newTimeStamp);

                    service.post(params, URL_DIET_PRODUCT_INSERT);

                    finish();
                }
                break;

            case R.id.menu_delete_product:
                Toast.makeText(this, R.string.product_deleted, Toast.LENGTH_SHORT).show();

                DietService service = new DietService(DietProductDetailsActivity.this);

                HashMap<String, String> params = new HashMap<>();
                params.put(ID, String.valueOf(productID));
                params.put(USER_ID, String.valueOf(SaveSharedPreference.getUserID(DietProductDetailsActivity.this)));
                params.put(WEIGHT, getProductWeightPerItems());
                params.put(DATE, newTimeStamp);

                service.post(params, URL_DIET_PRODUCT_DELETE);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        dateFormat = intent.getStringExtra("dateFormat");
        productID = intent.getIntExtra("productID", -1);
        productTimeStamp = intent.getStringExtra("productTimeStamp");
        productTimeStamp = timeStampChanger(productTimeStamp);
        PRODUCT_WEIGHT = intent.getDoubleExtra("productWeight", 50);
        previousActivity = intent.getStringExtra("previousActivity");

        TimeStampReplacer time = new TimeStampReplacer(dateFormat, productTimeStamp);
        newTimeStamp = time.getNewTimeStamp();

        Log.e(TAG, "dateFormat: " + dateFormat);
        Log.e(TAG, "productID: " + productID);
        Log.e(TAG, "productTimeStamp: " + productTimeStamp);
        Log.e(TAG, "productTimeStamp: " + productTimeStamp);
        Log.e(TAG, "PRODUCT_WEIGHT: " + PRODUCT_WEIGHT);
        Log.e(TAG, "previousActivity: " + previousActivity);
        Log.e(TAG, "newTimeStamp: " + newTimeStamp);
    }

    private String timeStampChanger(String productTimeStamp)
    {
        if (productTimeStamp == null)
            return timeStamp;
        else
            return this.productTimeStamp;
    }

    //    public void loadAsynchTask(final Context ctx)
    //    {
    //        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_DIET_GET_PRODUCT_INFORMATIONS, new Response.Listener<String>()
    //        {
    //            @Override
    //            public void onResponse(String response)
    //            {
    //                try
    //                {
    //                    JSONObject jsonObject = new JSONObject(response);
    //                    String name;
    //                    Log.i(TAG, "onResponse: " + jsonObject.toString(17));
    //                    JSONArray server_response = jsonObject.getJSONArray("server_response");
    //                    for (int i = 0; i < server_response.length(); i++)
    //                    {
    //                        JSONObject srv_response = server_response.getJSONObject(i);
    //
    //                        name = srv_response.getString(RestAPI.DB_PRODUCT_NAME);
    //                        proteins = srv_response.getDouble(RestAPI.DB_PRODUCT_PROTEINS);
    //                        fats = srv_response.getDouble(RestAPI.DB_PRODUCT_FATS);
    //                        carbs = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS);
    //                        saturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_SATURATED_FATS);
    //                        unsaturatedFats = srv_response.getDouble(RestAPI.DB_PRODUCT_UNSATURATED_FATS);
    //                        carbsFiber = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_FIBER);
    //                        carbsSugars = srv_response.getDouble(RestAPI.DB_PRODUCT_CARBS_SUGAR);
    //                        multiplier = srv_response.getDouble(RestAPI.DB_PRODUCT_MULTIPLIER_PIECE);
    //                        verified = srv_response.getInt(RestAPI.DB_PRODUCT_VERIFIED);
    //
    //                        if (verified == 1)
    //                        {
    //                            imgVerified.setVisibility(View.VISIBLE);
    //                        }
    //
    //                        String upName = name.substring(0, 1).toUpperCase() + name.substring(1);
    //                        tvName.setText(upName);
    //                        DietProductDetailsActivity.this.setProductWeight(PRODUCT_WEIGHT);
    //                    }
    //                } catch (JSONException e)
    //                {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }, new Response.ErrorListener()
    //        {
    //            @Override
    //            public void onErrorResponse(VolleyError error)
    //            {Toast.makeText(ctx, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();}
    //        })
    //        {
    //            @Override
    //            protected Map<String, String> getParams()
    //            {
    //                HashMap<String, String> params = new HashMap<>();
    //                params.put(RestAPI.DB_PRODUCT_ID, String.valueOf(productID));
    //                return params;
    //            }
    //        };
    //        RequestQueue queue = Volley.newRequestQueue(ctx);
    //        queue.add(strRequest);
    //    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            case 0:
                etWeight.setText(String.valueOf((int) PRODUCT_WEIGHT));
                setWeight(false);
                setProductWeight(PRODUCT_WEIGHT);
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

    public void setProductWeight(double enteredWeight)
    {
        Calories p = new Calories(enteredWeight, proteins);
        p.countCalories(p);
        tvProteins.setText(p.toString());

        Calories f = new Calories(enteredWeight, fats);
        f.countCalories(f);
        tvFats.setText(f.toString());

        Calories c = new Calories(enteredWeight, carbs);
        c.countCalories(c);
        tvCarbs.setText(c.toString());

        Calories sat = new Calories(enteredWeight, saturatedFats);
        sat.countCalories(sat);
        tvFatsSaturated.setText(sat.toString());

        Calories un = new Calories(enteredWeight, unsaturatedFats);
        un.countCalories(un);
        tvFatsUnsaturated.setText(un.toString());

        Calories fib = new Calories(enteredWeight, carbsFiber);
        fib.countCalories(fib);
        tvCarbsFiber.setText(fib.toString());

        Calories sug = new Calories(enteredWeight, carbsSugars);
        sug.countCalories(sug);
        tvCarbsSugars.setText(sug.toString());

        double proteins = Double.valueOf(tvProteins.getText().toString());
        double fats = Double.valueOf(tvFats.getText().toString());
        double carbs = Double.valueOf(tvCarbs.getText().toString());


        Product prod = new Product();
        tvCalories.setText(String.valueOf((int) prod.countCalories(proteins, fats, carbs)));

        setProductWeightPerItems(enteredWeight);
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

    private String getProductWeightPerItems()
    {
        return String.format(Locale.getDefault(), "%.0f", productWeightPerItems);
    }

    private void setProductWeightPerItems(double productWeightPerItems)
    {
        this.productWeightPerItems = productWeightPerItems;
    }

    public void load(Product product, Context context)
    {
        proteins = product.getProteins();
        fats = product.getFats();
        carbs = product.getCarbs();
        saturatedFats = product.getSaturatedFats();
        unsaturatedFats = product.getUnSaturatedFats();
        carbs = product.getCarbs();
        carbsSugars = product.getCarbsSugar();
        carbsFiber = product.getCarbsFiber();
        multiplier = product.getMultiplier();
        verified = product.getVerified();



        Log.d(TAG, "load() called wit6h: product = [ " + "\n" + "PRODUCT_WEIGHT" + PRODUCT_WEIGHT + "\n" + "proteins: " + product.getProteins() + "\n" + "fats " + product.getFats() + "\n" + "carbs " + product.getCarbs() + "\n" + "saturatedFats " + product.getSaturatedFats() + "\n" + "unSaturatedFats " + product.getUnSaturatedFats() + "\n" + "carbsFiber " + product.getCarbsFiber() + "\n" + "carbsSugar " + product.getCarbsSugar() + "\n" + "multiplier " + product.getMultiplier() + "\n" + "verified  " + product.getVerified() + "]");
        loadInput(context);
        setProductWeight(PRODUCT_WEIGHT);
    }
}
