package com.brus5.lukaszkrawczak.fitx.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.login.LoginActivity;
import com.brus5.lukaszkrawczak.fitx.login.LoginService;
import com.brus5.lukaszkrawczak.fitx.register.search.BirthdayTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.CallBackTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.FirstNameTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.HeightTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.PasswordListener;
import com.brus5.lukaszkrawczak.fitx.register.search.PasswordTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.RetypePasswordTextSearch;
import com.brus5.lukaszkrawczak.fitx.register.search.SexListener;
import com.brus5.lukaszkrawczak.fitx.register.search.WeightTextSearch;
import com.brus5.lukaszkrawczak.fitx.utils.callback.CallBackService;
import com.brus5.lukaszkrawczak.fitx.utils.callback.OnDataLoaded;

import org.json.JSONException;
import org.json.JSONObject;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_EMAIL_CHECK_EXISTING;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_USER_CHECK_EXISTING;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_USER_REGISTER;

public class RegisterActivity extends AppCompatActivity implements PasswordListener, SexListener
{
    private static final String TAG = "RegisterActivity";

    private EditText nameET;
    private EditText usernameET;
    private EditText emailET;
    private EditText passET;
    private EditText passRetypeET;
    private EditText birthdayET;
    private EditText weightET;
    private EditText heightET;

    private FirstNameTextSearch firstNameTS;
    private CallBackTextSearch userNameTS;
    private CallBackTextSearch emailTS;
    private RetypePasswordTextSearch retypePassTS;
    private RadioGroupTwo radioGroupTwo;
    private BirthdayTextSearch birthdayTS;
    private WeightTextSearch weightTS;
    private HeightTextSearch heightTS;

    private String password;
    private String sex;

    /** EditText of name with dynamic validation */
    private void nameEditText()
    {
        ImageView acceptIv = findViewById(R.id.firstNameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.firstNameRegisterErrorIv);

        firstNameTS = new FirstNameTextSearch(nameET, acceptIv,errorIv);
    }


    /** EditText of UserName with dynamic validation */
    private void userNameEditText()
    {
        ImageView acceptIv = findViewById(R.id.usernameRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.usernameRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.usernameRegisterPb);

        String params = "?username=";
        String link = URL_USER_CHECK_EXISTING + params;

        userNameTS = new CallBackTextSearch(RegisterActivity.this, usernameET,acceptIv,errorIv,progressBar,link);
    }

    /** EditText of Email with dynamic validation */
    private void emailEditText()
    {
        ImageView acceptIv = findViewById(R.id.emailRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.emailRegisterErrorIv);
        ProgressBar progressBar = findViewById(R.id.emailRegisterPb);

        String params = "?email=";
        String link = URL_EMAIL_CHECK_EXISTING + params;

        emailTS = new CallBackTextSearch(RegisterActivity.this, emailET,acceptIv,errorIv,progressBar,link);
    }

    /** EditText of Password */
    private void passwordEditText()
    {
        ImageView acceptIv = findViewById(R.id.passwordRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.passwordRegisterErrorIv);

        new PasswordTextSearch(passET, acceptIv,errorIv,this);
    }

    /**
     * EditText of RetypePassword
     * @param firstPassword firstPw
     */
    private void retypePasswordEditText(String firstPassword)
    {
        ImageView acceptIv = findViewById(R.id.passwordRetypeRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.passwordRetypeRegisterErrorIv);

        retypePassTS = new RetypePasswordTextSearch(passRetypeET, acceptIv,errorIv,this, firstPassword);
    }

    private void sexChooser()
    {
        RadioGroup radioGroup = findViewById(R.id.radioSexChooserRegister);
        radioGroupTwo = new RadioGroupTwo(radioGroup, this);
    }

    /**  EditText of Birthday */
    private void birthdayEditText()
    {
        ImageView acceptIv = findViewById(R.id.birthdayRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.birthdayRegisterErrorIv);

        birthdayTS = new BirthdayTextSearch(birthdayET, acceptIv,errorIv);
    }

    /**  EditText of Weight */
    private void weightEditText()
    {
        ImageView acceptIv = findViewById(R.id.weightRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.weightRegisterErrorIv);

        weightTS = new WeightTextSearch(weightET, acceptIv,errorIv);
    }

    /**  EditText of Height */
    private void heightEditText()
    {
        ImageView acceptIv = findViewById(R.id.heightRegisterAcceptedIv);
        ImageView errorIv = findViewById(R.id.heightRegisterErrorIv);

        heightTS = new HeightTextSearch(heightET, acceptIv,errorIv);
    }

    /**
     * Passing FirstPassword to RetypePasswordEditText to compare Strings
     * @param result String of FirstPasswordEditText
     */
    @Override
    public void callBackFirstPw(String result)
    {
        retypePasswordEditText(result);
        this.password = result;
    }

    @Override
    public void callBackSecondPw(String result) {}

    @Override
    public void callBackSexChoose(String sex)
    {
        Log.d(TAG, "callBackSexChoose() called with: sex = [" + sex + "]");
        this.sex = sex;
    }

    /**
     * Checking if all fields are valid
     * @return false if there is a mistake
     */
    private boolean isValid()
    {
        if (firstNameTS.isValid &&
                userNameTS.isValid &&
                emailTS.isValid &&
                retypePassTS.isValid &&
                radioGroupTwo.isSelected &&
                birthdayTS.isValid &&
                weightTS.isValid &&
                heightTS.isValid) return true;
        else
            return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        nameEditText();
        userNameEditText();
        emailEditText();
        passwordEditText();
        sexChooser();
        birthdayEditText();
        weightEditText();
        heightEditText();
    }

    private void init()
    {
        nameET = findViewById(R.id.firstNameRegisterEt);
        usernameET = findViewById(R.id.usernameRegisterEt);
        emailET = findViewById(R.id.emailRegisterEt);
        passET = findViewById(R.id.passwordRegisterEt);
        passRetypeET = findViewById(R.id.passwordRetypeRegisterEt);
        birthdayET = findViewById(R.id.birthdayRegisterEt);
        weightET = findViewById(R.id.weightRegisterEt);
        heightET = findViewById(R.id.heightRegisterEt);
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
                if (isValid())
                {
                    String name = nameET.getText().toString();
                    String username = usernameET.getText().toString();
                    String email = emailET.getText().toString();
                    String birthday = birthdayET.getText().toString();
                    String weight = weightET.getText().toString();
                    String height = heightET.getText().toString();

                    String link = URL_USER_REGISTER + "?name=" + name +
                            "&username=" + username +
                            "&birthday=" + birthday +
                            "&email=" + email +
                            "&password=" + LoginService.computeHash(password) +
                            "&sex=" + sex +
                            "&height=" + height +
                            "&weight=" + weight;

                    new CallBackService(RegisterActivity.this).post(link, new OnDataLoaded() {
                        @Override
                        public void onSuccess(String s)
                        {
                            finishRegistration(s);
                        }

                        @Override
                        public void onError(String s)
                        {
                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Complete registration
     * @param callBack boolean value of JSON format
     */
    private void finishRegistration(String callBack)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(callBack);
            boolean isSuccess = jsonObject.getBoolean("success");
            if (isSuccess)
            {
                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.welcome_new_user), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
        catch (JSONException e)
        {
            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.register_user_failed), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}

