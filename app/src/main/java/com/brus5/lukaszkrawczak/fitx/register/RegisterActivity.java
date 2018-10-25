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
import com.brus5.lukaszkrawczak.fitx.register.search.PasswordTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.RetypePasswordTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.FirstNameTextSearch;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_EMAIL_CHECK_EXISTING;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_USER_CHECK_EXISTING;

public class RegisterActivity extends AppCompatActivity implements PasswordListener
{
    private static final String TAG = "RegisterActivity";

    private FirstNameTextSearch firstNameTS;
    private CallBackTextSearch userNameTS;
    private CallBackTextSearch emailTS;
    private RetypePasswordTextSearch retypePassTS;


    /** EditText of name with dynamic validation */
    private void nameEditText()
    {
        EditText et = findViewById(R.id.firstNameRegisterEt);
        ImageView acceptIv = findViewById(R.id.firstNameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.firstNameRegisterErrorIv);

        firstNameTS = new FirstNameTextSearch(et, acceptIv,errorIv);
    }


    /** EditText of UserName with dynamic validation */
    private void userNameEditText()
    {
        EditText et = findViewById(R.id.usernameRegisterEt);
        ImageView acceptIv = findViewById(R.id.usernameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.usernameRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.usernameRegisterPb);

        String params = "?username=";
        String link = URL_USER_CHECK_EXISTING + params;

        userNameTS = new CallBackTextSearch(RegisterActivity.this,et,acceptIv,errorIv,progressBar,link);
    }

    /** EditText of Email with dynamic validation */
    private void emailEditText()
    {
        EditText et = findViewById(R.id.emailRegisterEt);
        ImageView acceptIv = findViewById(R.id.emailRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.emailRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.emailRegisterPb);

        String params = "?email=";
        String link = URL_EMAIL_CHECK_EXISTING + params;

        emailTS = new CallBackTextSearch(RegisterActivity.this,et,acceptIv,errorIv,progressBar,link);
    }

    /** EditText of Password */
    private void passwordEditText()
    {
        EditText et = findViewById(R.id.passwordRegisterEt);
        ImageView acceptIv = findViewById(R.id.passwordRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.passwordRegisterErrorIv);

        new PasswordTextSearch(et, acceptIv,errorIv,this);
    }

    /**
     * EditText of RetypePassword
     * @param firstPassword firstPw
     */
    private void retypePasswordEditText(String firstPassword)
    {
        EditText et = findViewById(R.id.passwordRetypeRegisterEt);
        ImageView acceptIv = findViewById(R.id.passwordRetypeRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.passwordRetypeRegisterErrorIv);

        retypePassTS = new RetypePasswordTextSearch(et, acceptIv,errorIv,this, firstPassword);
    }

    /**
     * Passing FirstPassword to RetypePasswordEditText to compare Strings
     * @param result String of FirstPasswordEditText
     */
    @Override
    public void callBackFirstPw(String result)
    {
        retypePasswordEditText(result);
    }

    @Override
    public void callBackSecondPw(String result) {}

    /**
     * Checking if all fields are valid
     * @return false if there is a mistake
     */
    private boolean isValid()
    {
        if (firstNameTS.isValid &&
                userNameTS.isValid &&
                emailTS.isValid &&
                retypePassTS.isValid) return true;
        else
            return false;
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
                Log.i(TAG, "onOptionsItemSelected: menu_save_user isValid() " + isValid());

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

