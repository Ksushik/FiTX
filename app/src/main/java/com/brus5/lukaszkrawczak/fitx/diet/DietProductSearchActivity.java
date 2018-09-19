package com.brus5.lukaszkrawczak.fitx.diet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("LongLogTag")

public class DietProductSearchActivity extends AppCompatActivity implements IDefaultView
{
    private static final String TAG = "DietProductSearchActivity";
    private String dateFormat;
    private EditText etSearch;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_2_search_product);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();
        searchProduct();
        onListViewItemSelected();

        new Provider(DietProductSearchActivity.this, listView).load("");

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
                        new Provider(DietProductSearchActivity.this, listView).load(s.toString());
                    }
                }, DELAY);
            }
        });
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                TextView productId = view.findViewById(R.id.dietMealSearchID);
                Intent intent = new Intent(DietProductSearchActivity.this, DietProductDetailsActivity.class);
                intent.putExtra("productID", Integer.valueOf(productId.getText().toString()));
                intent.putExtra("previousActivity", DietProductSearchActivity.this.getClass().getSimpleName());
                intent.putExtra("dateFormat", dateFormat);
                DietProductSearchActivity.this.startActivity(intent);
            }
        });
    }
}