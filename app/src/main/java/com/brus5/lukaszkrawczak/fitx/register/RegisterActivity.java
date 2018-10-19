package com.brus5.lukaszkrawczak.fitx.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.register.search.ProgressTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.SimpleTextSearch;

public class RegisterActivity extends AppCompatActivity
{
    private static final String TAG = "RegisterActivity";


    private void nameEditText()
    {
        EditText et = findViewById(R.id.firstNameRegisterEt);
        ImageView acceptIv = findViewById(R.id.firstNameRegisterAcceptedIv);
        ImageView errorv = findViewById(R.id.firstNameRegisterErrorIv);

        new SimpleTextSearch(et, acceptIv,errorv);
    }

    private void userNameEditText()
    {
        EditText et = findViewById(R.id.usernameRegisterEt);
        ImageView acceptIv = findViewById(R.id.usernameRegisterAcceptedIv);
        ImageView errorv = findViewById(R.id.usernameRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.usernameRegisterPb);

        new ProgressTextSearch(et,acceptIv,errorv,progressBar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText();
        userNameEditText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_save_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_user:
                Log.i(TAG, "onOptionsItemSelected: menu_save_user clicked!");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

