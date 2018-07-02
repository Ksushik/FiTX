package com.brus5.lukaszkrawczak.fitx.Diet;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.R;

public class DietProductSearchActivity extends AppCompatActivity {
    EditText editTextSearchProduct;
    ListView listViewShowProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_product_search);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        searchProduct();
        //test
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

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
