package com.brus5.lukaszkrawczak.fitx.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.register.RegisterActivity;
import com.brus5.lukaszkrawczak.fitx.login.dto.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.login.dto.UserLoginRegisterFacebookDTO;
import com.brus5.lukaszkrawczak.fitx.register.RulesRegisterActivity;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import static com.facebook.Profile.getCurrentProfile;

public class LoginActivity extends AppCompatActivity implements IDefaultView, View.OnClickListener
{
    private static final String TAG = "LoginActivity";
    private CallbackManager manager;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // initializing FacebookSdk
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_user_login);
        loadInput();
        loadDefaultView();
        userLogged();
        userButtonFacebookLogin();
    }

    private void userLogged()
    {
        if (!SaveSharedPreference.getUserName(this).isEmpty())
        {
            runNextActivity(LoginActivity.this, MainActivity.class, true);
        }
    }

    @Override
    public void loadInput()
    {
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(LoginActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarLoginActivity);
    }


    private void userButtonFacebookLogin()
    {
        // releasing method updateWithToken
        // if connected via Facebook the first handler is gonna run up after one second
        // if not connected via Facebook the second handler is gonna run up after one second
        updateWithToken(AccessToken.getCurrentAccessToken());

        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        manager = CallbackManager.Factory.create();

        loginButton.registerCallback(manager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(final LoginResult loginResult)
            {
                ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Loading application View, please wait...", false, false
                );
                dialog.show();


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response)
                            {

                                if (response.getError() != null)
                                {
                                    Log.e(TAG, "onCompleted: " + response.getError());
                                }
                                else
                                {
                                    String lastName = me.optString("last_name");
                                    String firstName = me.optString("first_name");
                                    String email = response.getJSONObject().optString("email");
                                    String birthday = me.optString("birthday");
                                    String gender = me.optString("gender");
                                    String location = me.optString("location");

                                    if (gender.equals("male"))
                                    {
                                        gender = "m";
                                    }
                                    else if (gender.equals("female"))
                                    {
                                        gender = "w";
                                    }

                                    // constricting my new data type from 09/04/1989 to convertedBirthday: 04.09.1989
                                    String mDay = birthday.substring(3, 5);
                                    String mMonth = birthday.substring(0, 2);
                                    String mYear = birthday.substring(6, 10);

                                    String convertedBirthday = mDay + "." + mMonth + "." + mYear;

                                    UserLoginRegisterFacebookDTO dto = new UserLoginRegisterFacebookDTO();
                                    dto.userName = loginResult.getAccessToken().getUserId();
                                    dto.userFirstName = firstName;
                                    dto.userBirthday = convertedBirthday;
                                    dto.userPassword = "123";
                                    dto.userGender = gender;
                                    dto.userEmail = email;

                                    // do not pass DB_USERNAME from Facebook to SaveSharedPreference class
                                    SaveSharedPreference.setUserFirstName(LoginActivity.this, dto.userFirstName);
                                    SaveSharedPreference.setUserBirthday(LoginActivity.this, dto.userBirthday);
                                    SaveSharedPreference.setUserGender(LoginActivity.this, dto.userGender);
                                    SaveSharedPreference.setUserEmail(LoginActivity.this, dto.userEmail);

                                    LoginService loginService = new LoginService();
                                    loginService.LoginWithFacebook(dto, LoginActivity.this);
                                    Log.d(TAG, "Connecting via Facebook " + " userID_facebook [" + loginResult.getAccessToken().getUserId() + "]" + " email [" + email + "]" + " firstName [" + firstName + "]" + " lastName [" + lastName + "]" + " birthday [" + birthday + "]" + " gender [" + gender + "]" + " location [" + location + "]" + " birthday [" + birthday + "]" + " convertedBirthday [" + convertedBirthday + "]" + " token [" + loginResult.getAccessToken().getToken() + "]");
                                }
                            }
                        });
                // pushing parameters from facebook
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();

                runNextActivity(LoginActivity.this, MainActivity.class, false);
            }

            @Override
            public void onCancel()
            {
                toastError(R.string.cancel);
            }

            @Override
            public void onError(FacebookException error)
            {
                toastError(R.string.error);
            }
        });

        // getting accesstoken
        AccessToken token = AccessToken.getCurrentAccessToken();


        // if accesstoken isn't null then start new Activity which is MainActivity.class
        if (token != null)
        {
            runNextActivity(LoginActivity.this, MainActivity.class, false);
        }
    }

    private void toastError(int resID)
    {
        Toast.makeText(this, resID, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // running up facebookLogin
        manager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateWithToken(AccessToken currentAccessToken)
    {
        // checking currentAccessToken
        // if connected via Facebook the first handler is gonna run up after one second
        // if not connected via Facebook the second handler is gonna run up after one second
        if (currentAccessToken != null)
        {
            Log.d(TAG, "Connected via Facebook = ID [" + getCurrentProfile().getId() + "]" + " firstName [" + getCurrentProfile().getFirstName() + "]" + " middleName [" + getCurrentProfile().getMiddleName() + "]" + " lastName [" + getCurrentProfile().getLastName() + "]");
        }
        else
        {
            Log.d(TAG, "Not connected via Facebook");
        }
    }

    private void runNextActivity(Context packageContext, Class<?> cls, boolean defaultLogin)
    {
        Intent intent = new Intent(packageContext, cls);
        intent.putExtra("defaultLogin", defaultLogin);
        LoginActivity.this.startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.buttonLogin:
                login();
                break;
            case R.id.buttonRegister:
                runNextActivity(LoginActivity.this, RulesRegisterActivity.class, false);
                break;
        }
    }

    private void login()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        EditText etLogin = findViewById(R.id.editTextLogin);
        EditText etPassword = findViewById(R.id.editTextPassword);

        UserLoginNormalDTO dto = new UserLoginNormalDTO();
        dto.userName = etLogin.getText().toString();
        dto.userPassword = etPassword.getText().toString();


        LoginService loginService = new LoginService();
        loginService.LoginNormal(dto, LoginActivity.this);
    }
}