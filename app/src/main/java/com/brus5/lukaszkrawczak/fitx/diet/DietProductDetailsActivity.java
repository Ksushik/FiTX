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
import android.widget.Adapter;
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

import static com.brus5.lukaszkrawczak.fitx.diet.DietProductDetailsActivity.PRODUCT_WEIGHT;
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
 * In this Activity user can Delete, Update or Add new productTextView to his daily list.
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
    private int productID;
    private String productTimeStamp, previousActivity, dateFormat, newTimeStamp;
    private Spinner spinner;
    private ConstraintLayout constraintLayout;
    ProductTextView productTextView;

    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private double productWeightPerItems;
    public static double PRODUCT_WEIGHT;

    ChangerTextViews changerTextViews;

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

        new Provider(DietProductDetailsActivity.this, DietProductDetailsActivity.this).load(String.valueOf(productID));

        productTextView = new ProductTextView(DietProductDetailsActivity.this);

        changerTextViews = new ChangerTextViews(DietProductDetailsActivity.this);
        changerTextViews.setWeight(PRODUCT_WEIGHT);
    }

    public void loadInput()
    {
        imgVerified = findViewById(R.id.imageViewVerified);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        constraintLayout = findViewById(R.id.constraintLayoutDietDetails);
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

                    DietService service = new DietService(DietProductDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(ID, String.valueOf(productID));
                    params.put(USER_ID, String.valueOf(SaveSharedPreference.getUserID(DietProductDetailsActivity.this)));
                    params.put(UPDATE_WEIGHT, String.valueOf(PRODUCT_WEIGHT));
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
                    params.put(WEIGHT, String.valueOf(PRODUCT_WEIGHT));
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
                params.put(WEIGHT, String.valueOf(PRODUCT_WEIGHT));
                params.put(DATE, newTimeStamp);

                service.post(params, URL_DIET_PRODUCT_DELETE);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Getting necessary values from previous activity.
     */
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
        Log.d(TAG, "getIntentFromPreviousActiity() called\n" + "dateFormat: " + dateFormat + "\n" + "productID: " + productID + "\n" + "productTimeStamp: " + productTimeStamp + "\n" + "PRODUCT_WEIGHT: " + PRODUCT_WEIGHT + "previousActivity: " + previousActivity + "\n" + "newTimeStamp: " + newTimeStamp);
    }

    private String timeStampChanger(String productTimeStamp)
    {
        if (productTimeStamp == null)
            return timeStamp;
        else
            return this.productTimeStamp;
    }

    /**
     * This is responsible for showing spinner, where you can choose product depends
     * on weight, and depends on pieces of the product.
     *
     * @param parent   An AdapterView is a view whose children are determined by an {@link Adapter}.
     * @param view     actual view
     * @param position clicked position
     * @param id       of the view
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            case 0:
                changerTextViews.setWeight(PRODUCT_WEIGHT);
                //                etWeight.setText(String.valueOf((int) PRODUCT_WEIGHT));
                //                setWeight(false);
                //                setProductCalories(productTextView, PRODUCT_WEIGHT);
                //                etWeight.clearFocus();
                //                etWeight.didTouchFocusSelect();
                // TODO: 04.09.2018 tutaj są GRAMY
                Log.d(TAG, "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                break;
            case 1:
                changerTextViews.setWeightToZero();
                //                etWeight.setText("1");
                //                setWeight(true);
                //                setProductCalories(productTextView, 100 / multiplier);
                //                etWeight.clearFocus();
                //                etWeight.didTouchFocusSelect();

                // TODO: 04.09.2018 tutaj są PIECES
                Log.d(TAG, "onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                break;
        }
    }

    /**
     * This method is automatically implemented by AdapterView.OnItemSelectedListener interface
     * @param parent null
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    private String getProductWeightPerItems()
    {
        return String.format(Locale.getDefault(), "%.0f", productWeightPerItems);
    }

    private void setProductWeightPerItems(double productWeightPerItems)
    {
        this.productWeightPerItems = productWeightPerItems;
    }

    /**
     * Loading productTextView informations from AsyncTask
     *
     * @param product contains productTextView informations
     * @param context contains actual view
     */
    public void load(Product product, Context context)
    {
        Log.d(TAG, "load() called wit6h: productTextView = [ " + "\n" + "PRODUCT_WEIGHT" + PRODUCT_WEIGHT + "\n" + "proteins: " + product.getProteins() + "\n" + "fats " + product.getFats() + "\n" + "carbs " + product.getCarbs() + "\n" + "saturatedFats " + product.getSaturatedFats() + "\n" + "unSaturatedFats " + product.getUnSaturatedFats() + "\n" + "carbsFiber " + product.getCarbsFiber() + "\n" + "carbsSugar " + product.getCarbsSugar() + "\n" + "multiplier " + product.getMultiplier() + "\n" + "verified  " + product.getVerified() + "]");

        ProductTextView p = new ProductTextView(context);

        p.tvName.setText(product.getName());
        p.setProductCalories(product, PRODUCT_WEIGHT);

        ChangerTextViews c = new ChangerTextViews(context);
        c.setWeight(product, false);
    }
}

class ProductTextView
{
    private Context context;

    public TextView tvName, tvProteins, tvFats, tvCarbs, tvCalories, tvFatsSaturated, tvFatsUnsaturated, tvCarbsFiber, tvCarbsSugars;

    ProductTextView(Context context)
    {
        this.context = context;
        load();
    }

    /**
     * Loading objects from reference.
     */
    private void load()
    {
        tvName = ((Activity) context).findViewById(R.id.textViewProductName);
        tvProteins = ((Activity) context).findViewById(R.id.textViewProteins);
        tvFats = ((Activity) context).findViewById(R.id.textViewFats);
        tvCarbs = ((Activity) context).findViewById(R.id.textViewCarbs);
        tvCalories = ((Activity) context).findViewById(R.id.textViewCalories);
        tvFatsSaturated = ((Activity) context).findViewById(R.id.textViewFatsSaturated);
        tvFatsUnsaturated = ((Activity) context).findViewById(R.id.textViewFatsUnsaturated);
        tvCarbsFiber = ((Activity) context).findViewById(R.id.textViewCarbsFiber);
        tvCarbsSugars = ((Activity) context).findViewById(R.id.textViewCarbsSugars);
    }

    /**
     * That method is for resetting TextView to '0' value.
     */
    public void resetTextViewsToZero()
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

    /**
     * Enter the weight of productTextView and final value will be
     * converted to calories.
     *
     * @param weight of productTextView.
     */
    public void setProductCalories(Product product, double weight)
    {
        ProductTextView productTextView = new ProductTextView(context);

        Calories p = new Calories(weight, product.getProteins());
        p.countCalories(p);
        productTextView.tvProteins.setText(p.toString());

        Calories f = new Calories(weight, product.getFats());
        f.countCalories(f);
        productTextView.tvFats.setText(f.toString());

        Calories c = new Calories(weight, product.getCarbs());
        c.countCalories(c);
        productTextView.tvCarbs.setText(c.toString());

        Calories sat = new Calories(weight, product.getSaturatedFats());
        sat.countCalories(sat);
        productTextView.tvFatsSaturated.setText(sat.toString());

        Calories un = new Calories(weight, product.getUnSaturatedFats());
        un.countCalories(un);
        productTextView.tvFatsUnsaturated.setText(un.toString());

        Calories fib = new Calories(weight, product.getCarbsFiber());
        fib.countCalories(fib);
        productTextView.tvCarbsFiber.setText(fib.toString());

        Calories sug = new Calories(weight, product.getCarbsSugar());
        sug.countCalories(sug);
        productTextView.tvCarbsSugars.setText(sug.toString());

        double proteins = Double.valueOf(productTextView.tvProteins.getText().toString());
        double fats = Double.valueOf(productTextView.tvFats.getText().toString());
        double carbs = Double.valueOf(productTextView.tvCarbs.getText().toString());


        Product prod = new Product();
        productTextView.tvCalories.setText(String.valueOf((int) prod.countCalories(proteins, fats, carbs)));

        PRODUCT_WEIGHT = weight;
    }
}

class ChangerTextViews
{
    private static final String TAG = "ChangerTextViews";
    private Context context;
    private EditText weight;
    private ProductTextView productTextView;

    ChangerTextViews(Context context)
    {
        this.context = context;
        productTextView = new ProductTextView(context);
        load();
    }

    private void load()
    {
        weight = ((Activity) context).findViewById(R.id.editTextWeight);
        weight.clearFocus();
        weight.didTouchFocusSelect();
    }

    public void setWeight(double weight)
    {
        this.weight.setText(String.valueOf((int) weight));
    }

    public void setWeightToZero()
    {
        weight.setText("0");
    }

    public void setWeight(final Product product, final boolean item)
    {
        weight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s)
            {

                Log.i(TAG, "afterTextChanged: "+s.toString());
                if (!item)
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        //                        resetTextViewsToZero();
                        productTextView.resetTextViewsToZero();

                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        //                        resetTextViewsToZero();
                        productTextView.resetTextViewsToZero();
                        weight.setText("");
                    }
                    else
                    {
                        //                        Product p = new Product("bulka",2,3,4,5,6,7,8,8,10);

                        productTextView.setProductCalories(product, Double.valueOf(s.toString()));
                    }
                }
                else
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        //                        resetTextViewsToZero();
                        productTextView.resetTextViewsToZero();
                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        //                        resetTextViewsToZero();
                        productTextView.resetTextViewsToZero();
                        weight.setText("");
                    }
                    else
                    {
                        //                        Product p = new Product("bulka",2,3,4,5,6,7,8,8,10);
                        productTextView.setProductCalories(product, Double.valueOf(s.toString()) * (100 / product.getMultiplier()));
                    }
                }
            }
        });
    }
}
