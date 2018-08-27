package com.brus5.lukaszkrawczak.fitx.Login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginRegisterFacebookDTO;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.Utils.ActivityView;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.facebook.Profile.getCurrentProfile;

public class LoginActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "LoginActivity";
    protected AccessTokenTracker tokenTracker;
    private LoginButton loginButton;
    private AccessToken token;
    private CallbackManager manager;
    private String userName, userPassword;
    private EditText etLogin, etPassword;
    private Button btLogin, btRegister;

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

        userButtonNormalLogin();
        userButtonRegister();
        userButtonFacebookLogin();
    }

    @Override
    public void loadInput()
    {
        etLogin = findViewById(R.id.editTextLogin);
        etPassword = findViewById(R.id.editTextPassword);
        btLogin = findViewById(R.id.buttonLogin);
        btRegister = findViewById(R.id.buttonRegister);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(LoginActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarLoginActivity);
    }

    private void userButtonNormalLogin()
    {
        if (SaveSharedPreference.getUserName(LoginActivity.this).length() == 0)
        {
            btLogin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    setUserName(etLogin.getText().toString());
                    setUserPassword(etPassword.getText().toString());

                    UserLoginNormalDTO dto = new UserLoginNormalDTO();
                    dto.userName = getUserName();
                    dto.userPassword = getUserPassword();

                    LoginService loginService = new LoginService();
                    loginService.LoginNormal(dto, LoginActivity.this);

                }
            });
        }
        else
        {
            runNextActivity(LoginActivity.this, MainActivity.class, true);
        }
    }

    private void userButtonRegister()
    {
        btRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                runNextActivity(LoginActivity.this, null, false);
            }
        });
    }

    private void userButtonFacebookLogin()
    {
        try
        {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo("com.brus5.lukaszkrawczak.fitx", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.e(TAG, "userButtonFacebookLogin: "+e);
        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e(TAG, "userButtonFacebookLogin: "+e);
        }

        // releasing method updateWithToken
        // if connected via Facebook the first handler is gonna run up after one second
        // if not connected via Facebook the second handler is gonna run up after one second
        updateWithToken(AccessToken.getCurrentAccessToken());

        loginButton = findViewById(R.id.buttonFacebookLogin);
        manager = CallbackManager.Factory.create();
        tokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken)
            {
            }
        };
        loginButton.registerCallback(manager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(final LoginResult loginResult)
            {
                ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, "Loading...",
                        "Loading application View, please wait...", false, false
                );
                dialog.show();
                Log.e(TAG, "Login success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                        {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response)
                            {

                                if (response.getError() != null)
                                {
                                    // handle error
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

                                    UserLoginRegisterFacebookDTO registerFacebookDTO = new UserLoginRegisterFacebookDTO();
                                    registerFacebookDTO.userName = loginResult.getAccessToken().getUserId();
                                    registerFacebookDTO.userFirstName = firstName;
                                    registerFacebookDTO.userBirthday = convertedBirthday;
                                    registerFacebookDTO.userPassword = "123";
                                    registerFacebookDTO.userGender = gender;
                                    registerFacebookDTO.userEmail = email;

                                    // do not pass DB_USERNAME from Facebook to SaveSharedPreference class
                                    SaveSharedPreference.setUserFirstName(LoginActivity.this, registerFacebookDTO.userFirstName);
                                    SaveSharedPreference.setUserBirthday(LoginActivity.this, registerFacebookDTO.userBirthday);
                                    SaveSharedPreference.setUserGender(LoginActivity.this, registerFacebookDTO.userGender);
                                    SaveSharedPreference.setUserEmail(LoginActivity.this, registerFacebookDTO.userEmail);

                                    LoginService loginService = new LoginService();
                                    loginService.LoginWithFacebook(registerFacebookDTO, LoginActivity.this);

                                    Log.d(TAG, "convertedBirthday: " + convertedBirthday);
                                    Log.d(TAG, "user_email: " + email);
                                    Log.d(TAG, "user_lastname: " + lastName);
                                    Log.d(TAG, "user_firstname: " + firstName);
                                    Log.d(TAG, "birthday: " + birthday);
                                    Log.d(TAG, "gender: " + gender);
                                    Log.d(TAG, "location: " + location);
                                    Log.d(TAG, "gender: " + gender);
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
                toastError(RestAPI.USER_CANCEL);
            }

            @Override
            public void onError(FacebookException error)
            {
                toastError(RestAPI.LOGIN_ERROR);
            }
        });

        tokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
            }
        };

        // getting accesstoken
        token = AccessToken.getCurrentAccessToken();

        /*
        if accesstoken isn't null then start new Activity which is MainActivity.class
        */
        if (token != null)
        {
            runNextActivity(LoginActivity.this, MainActivity.class, false);
        }
    }

    private void toastError(String connectionError)
    {
        Toast.makeText(this, connectionError, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        /*
        running up facebookLogin
        */
        manager.onActivityResult(requestCode, resultCode, data);
    }

    private void updateWithToken(AccessToken currentAccessToken)
    {
        /*
        checking currentAccessToken
        if connected via Facebook the first handler is gonna run up after one second
        if not connected via Facebook the second handler is gonna run up after one second
        */
        if (currentAccessToken != null)
        {
            Log.d(TAG, "Connected via Facebook");
            Log.d(TAG, "getId()         " + getCurrentProfile().getId());
            Log.d(TAG, "getFirstName()  " + getCurrentProfile().getFirstName());
            Log.d(TAG, "getMiddleName() " + getCurrentProfile().getMiddleName());
            Log.d(TAG, "getLastName()   " + getCurrentProfile().getLastName());
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

    private String getUserName()
    {
        return userName;
    }

    private void setUserName(String userName)
    {
        this.userName = userName;
    }

    private String getUserPassword()
    {
        return userPassword;
    }

    private void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }
}