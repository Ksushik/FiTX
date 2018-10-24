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
import com.brus5.lukaszkrawczak.fitx.register.search.CallBackTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.PasswordListener;
import com.brus5.lukaszkrawczak.fitx.register.search.RetypePasswordTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.SimpleTextSearch;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_EMAIL_CHECK_EXISTING;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_USER_CHECK_EXISTING;

public class RegisterActivity extends AppCompatActivity implements PasswordListener
{
    private static final String TAG = "RegisterActivity";

    private void nameEditText()
    {
        EditText et = findViewById(R.id.firstNameRegisterEt);
        ImageView acceptIv = findViewById(R.id.firstNameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.firstNameRegisterErrorIv);

        new SimpleTextSearch(et, acceptIv,errorIv);
    }

    private void userNameEditText()
    {
        EditText et = findViewById(R.id.usernameRegisterEt);
        ImageView acceptIv = findViewById(R.id.usernameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.usernameRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.usernameRegisterPb);

        String params = "?username=";
        String link = URL_USER_CHECK_EXISTING + params;

        new CallBackTextSearch(RegisterActivity.this,et,acceptIv,errorIv,progressBar,link);
    }

    private void emailEditText()
    {
        EditText et = findViewById(R.id.emailRegisterEt);
        ImageView acceptIv = findViewById(R.id.emailRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.emailRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.emailRegisterPb);

        String params = "?email=";
        String link = URL_EMAIL_CHECK_EXISTING + params;

        new CallBackTextSearch(RegisterActivity.this,et,acceptIv,errorIv,progressBar,link);
    }

    private void passwordEditText()
    {

    }

    private void retypePasswordEditText()
    {
        EditText et = findViewById(R.id.passwordRetypeRegisterEt);
        ImageView acceptIv = findViewById(R.id.passwordRetypeRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.passwordRetypeRegisterErrorIv);

        new RetypePasswordTextSearch(et, acceptIv,errorIv,this);
    }

    @Override
    public void firstPassword(boolean isValid)
    {
        Log.d(TAG, "firstPassword() called with: isValid = [" + isValid + "]");
    }

    @Override
    public void secondPassword(boolean isValid)
    {
        Log.d(TAG, "secondPassword() called with: isValid = [" + isValid + "]");
    }

    @Override
    public void callBack(String result)
    {
        Log.d(TAG, "callBack() called with: result = [" + result + "] dupaaa");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText();
        userNameEditText();
        emailEditText();
        passwordEditText();
        retypePasswordEditText();
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

