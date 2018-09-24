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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.HTTPService;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStamp;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.ImageLoader;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.brus5.lukaszkrawczak.fitx.diet.DietProductDetailsActivity.PRODUCT_WEIGHT;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.DATE;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.ID;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.UPDATE_WEIGHT;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.USER_ID;
import static com.brus5.lukaszkrawczak.fitx.diet.DietService.WEIGHT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_DELETE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_GET_INFORMATIONS;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_INSERT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_DIET_PRODUCT_UPDATE_WEIGHT;

/**
 * That View devilers to user informations about products.
 * Downloaded data from REST server are in JSON format.
 * They are converted as a JSON objects and delivered to View.
 * <p>
 * In this Activity user can Delete, Update or Add new productTextViews to his daily list.
 * <p>
 * User can change Product Weight in EditText and also he can use whole
 * pieces as a Weight Type. For Example 1 banana as a 1 piece weights 118g
 * <p>
 * In UI user can see whole data about macronutrients which are dynamically
 * updates while user writes number in EditText
 */

@SuppressLint("LongLogTag")
public class DietProductDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IDefaultView
{
    private static final String TAG = "DietProductDetailsActivity";
    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private int productID;
    private String productTimeStamp, previousActivity, dateFormat, newTimeStamp;
    private Spinner spinner;
    private ChangerTextViews changerTextViews;


    protected static double PRODUCT_WEIGHT;
    private static Product PRODUCT = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_3_details);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        // This must by under getIntentFromPreviousActiity()
        String url = "http://justfitx.xyz/images/products/mid/" + productID + ".png";

        new ImageLoader(DietProductDetailsActivity.this, R.id.imageViewProduct, R.id.progressBar, url);


        final String params = "?product_id=" + productID;

        new ProductResult(this).execute(URL_DIET_PRODUCT_GET_INFORMATIONS, params);

        changerTextViews = new ChangerTextViews(DietProductDetailsActivity.this);
        changerTextViews.setEditText(PRODUCT_WEIGHT);
    }

    public void loadInput()
    {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutDietDetails);
        constraintLayout.requestFocus();
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(DietProductDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarDietProductShowActivity);
        activityView.showBackButton();
    }

    /**
     * This method is responsible for showing OptionsMenu
     *
     * @param menu item on ActionBar
     * @return created item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diet_4_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_product);
        if (previousActivity.equals(DietActivity.class.getSimpleName())) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This method is responsible for accepting, updating or deleting product
     *
     * @param item is item on ActionBar menu
     * @return clicked item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        String prodID = String.valueOf(productID);
        String userID = String.valueOf(SaveSharedPreference.getUserID(DietProductDetailsActivity.this));
        String productWeight = String.valueOf((int)PRODUCT_WEIGHT);

        switch (item.getItemId())
        {
            case R.id.menu_save_product:
                if (previousActivity.equals(DietActivity.class.getSimpleName()))
                {
                    Toast.makeText(this, R.string.product_updated, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(DietProductDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(ID, prodID);
                    params.put(USER_ID, userID);
                    params.put(UPDATE_WEIGHT, productWeight);
                    params.put(DATE, newTimeStamp);

                    service.post(params, URL_DIET_PRODUCT_UPDATE_WEIGHT);

                    finish();
                }

                else if (previousActivity.equals(DietProductSearchActivity.class.getSimpleName()))
                {
                    Toast.makeText(this, R.string.product_inserted, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(DietProductDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(ID, prodID);
                    params.put(USER_ID, userID);
                    params.put(WEIGHT, productWeight);
                    params.put(DATE, newTimeStamp);

                    service.post(params, URL_DIET_PRODUCT_INSERT);

                    finish();
                }
                break;

            case R.id.menu_delete_product:
                Toast.makeText(this, R.string.product_deleted, Toast.LENGTH_SHORT).show();

                DietService service = new DietService(DietProductDetailsActivity.this);

                HashMap<String, String> params = new HashMap<>();
                params.put(ID, prodID);
                params.put(USER_ID, userID);
                params.put(WEIGHT, productWeight);
                params.put(DATE, newTimeStamp);

                Log.d(TAG, "onOptionsItemSelected() called with: item = [" + productID + "]" + "[" + PRODUCT_WEIGHT + "]" + "[" + newTimeStamp + "]");

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

        TimeStamp time = new TimeStamp(dateFormat, productTimeStamp);
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
            // user chooses weight as paramter
            case 0:
                try
                {
                    changerTextViews.setEditText(PRODUCT, true);
                    changerTextViews.setWeightToZero();
                    changerTextViews.setEditText(PRODUCT_WEIGHT);
                } catch (NullPointerException e)
                {
                    Log.e(TAG, "onItemSelected: ", e);
                }
                break;
            // user chooses pieces as parameter
            case 1:
                try
                {
                    changerTextViews.setEditText(PRODUCT, false);
                    changerTextViews.setWeightToZero();
                    changerTextViews.setEditText(1);
                } catch (NullPointerException e)
                {
                    Log.e(TAG, "onItemSelected: ", e);
                }
                break;
        }
    }

    /**
     * This method is automatically implemented by AdapterView.OnItemSelectedListener interface
     * @param parent null
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    /**
     * Loading productTextViews informations from AsyncTask
     *
     * @param product contains productTextViews informations
     * @param context contains actual view
     */
    public void load(Product product, Context context)
    {
        Log.d(TAG, "load() called wit6h: productTextViews = [ " + "\n" + "PRODUCT_WEIGHT " + PRODUCT_WEIGHT + "\n" + "proteins: " + product.getProteins() + "\n" + "fats " + product.getFats() + "\n" + "carbs " + product.getCarbs() + "\n" + "saturatedFats " + product.getSaturatedFats() + "\n" + "unSaturatedFats " + product.getUnSaturatedFats() + "\n" + "carbsFiber " + product.getCarbsFiber() + "\n" + "carbsSugar " + product.getCarbsSugar() + "\n" + "multiplier " + product.getMultiplier() + "\n" + "verified  " + product.getVerified() + "]");

        // setting TextViews and name of the product
        ProductTextView p = new ProductTextView(context);
        p.tvName.setText(product.getName());
        p.setProductCalories(product, PRODUCT_WEIGHT);

        // passing object to setEditText method
        ChangerTextViews c = new ChangerTextViews(context);
        c.setEditText(product, true);

        // attribution product object to static object of main thread.
        PRODUCT = product;
    }

    @SuppressLint("StaticFieldLeak")
    private class ProductResult extends HTTPService
    {
        private Context context;

        ProductResult(Context context)
        {
            super(context);
            this.context = context;
        }

        @Override
        public void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try
            {
                JSONObject jsonObject = new JSONObject(s);
                String name;
                Log.i(TAG, "onResponse: " + jsonObject.toString(17));
                JSONArray server_response = jsonObject.getJSONArray("server_response");
                for (int i = 0; i < server_response.length(); i++)
                {
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
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }


    }
}

/**
 * This class represents final value of entered weight by user in EditText
 */
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
     * Enter the weight of productTextViews and final value will be
     * converted to calories.
     *
     * @param weight of productTextViews.
     */
    public void setProductCalories(Product product, double weight)
    {
        ProductTextView productTextView = new ProductTextView(context);

        Calories p = new Calories(weight, product.getProteins());
        p.countCalories(p);
        productTextView.tvProteins.setText(s(p.toString()));

        Calories f = new Calories(weight, product.getFats());
        f.countCalories(f);
        productTextView.tvFats.setText(s(f.toString()));

        Calories c = new Calories(weight, product.getCarbs());
        c.countCalories(c);
        productTextView.tvCarbs.setText(s(c.toString()));

        Calories sat = new Calories(weight, product.getSaturatedFats());
        sat.countCalories(sat);
        productTextView.tvFatsSaturated.setText(s(sat.toString()));

        Calories un = new Calories(weight, product.getUnSaturatedFats());
        un.countCalories(un);
        productTextView.tvFatsUnsaturated.setText(s(un.toString()));

        Calories fib = new Calories(weight, product.getCarbsFiber());
        fib.countCalories(fib);
        productTextView.tvCarbsFiber.setText(s(fib.toString()));

        Calories sug = new Calories(weight, product.getCarbsSugar());
        sug.countCalories(sug);
        productTextView.tvCarbsSugars.setText(s(sug.toString()));

        double proteins = Double.valueOf(productTextView.tvProteins.getText().toString());
        double fats = Double.valueOf(productTextView.tvFats.getText().toString());
        double carbs = Double.valueOf(productTextView.tvCarbs.getText().toString());


        Product prod = new Product();
        productTextView.tvCalories.setText(String.valueOf((int) prod.countCalories(proteins, fats, carbs)));

        PRODUCT_WEIGHT = weight;
    }

    private String s(String s1)
    {
        return s1.replace(",",".");
    }

}

/**
 * This class cooperates with ProductTextView.class
 */
class ChangerTextViews
{
    private Context context;
    private EditText weight;
    private ProductTextView productTextView;

    /**
     * Constructor of TextViews which automatically loads objects from reference
     *
     * @param context actual context
     */
    ChangerTextViews(Context context)
    {
        this.context = context;
        productTextView = new ProductTextView(context);
        load();
    }

    /**
     * Loading objects from reference.
     */
    private void load()
    {
        weight = ((Activity) context).findViewById(R.id.editTextWeight);
        weight.clearFocus();
        weight.didTouchFocusSelect();
    }

    public void setEditText(double weight)
    {
        this.weight.setText(String.valueOf((int) weight));
    }

    public void setWeightToZero()
    {
        weight.setText("0");
    }


    /**
     * Method for setting EditText
     *
     * @param product  getting Product object
     * @param isWeight boolean value if isWeight then user entering weight
     *                 if not then user entering pieces
     */
    public void setEditText(final Product product, final boolean isWeight)
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
                // if user choosed weight as paramter
                if (isWeight)
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        productTextView.resetTextViewsToZero();

                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        productTextView.resetTextViewsToZero();
                        weight.setText("");
                    }
                    else
                    {
                        productTextView.setProductCalories(product, Double.valueOf(s.toString()));
                    }
                }

                // if user choosed pieces as parameter
                else
                {
                    if (s.toString().isEmpty() || s.toString().startsWith("0"))
                    {
                        s.append("");
                        productTextView.resetTextViewsToZero();

                    }
                    else if (Double.valueOf(s.toString()) > 2000)
                    {
                        productTextView.resetTextViewsToZero();
                        weight.setText("");
                    }
                    else
                    {
                        productTextView.setProductCalories(product, Double.valueOf(s.toString()) * (100 / product.getMultiplier()));
                    }
                }
            }
        });
    }
}
