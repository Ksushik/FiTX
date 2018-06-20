package com.brus5.lukaszkrawczak.fitx.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginRegisterFacebookDTO;

import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    LoginButton loginButton;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    CallbackManager callbackManager;
    String userName, userPassword;

    EditText editTextLogin, editTextPassword;
    Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initializing FacebookSdk
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_user_login);

        changeStatusBarColor();
        loadInputs();
        userButtonNormalLogin();
        userButtonRegister();
        userButtonFacebookLogin();
    }

    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarLoginActivity);
        setSupportActionBar(toolbar);
    }

    private void loadInputs() {
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void userButtonNormalLogin() {
        if (SaveSharedPreference.getUserName(LoginActivity.this).length() == 0){
            buttonLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    setUserName(editTextLogin.getText().toString());
                    setUserPassword(editTextPassword.getText().toString());

                    UserLoginNormalDTO dto = new UserLoginNormalDTO();
                    dto.userName = getUserName();
                    dto.userPassword = getUserPassword();

                    LoginService loginService = new LoginService();
                    loginService.LoginNormal(dto,LoginActivity.this);

                }
            });
        }
        else {
            runNextActivity(LoginActivity.this,MainActivity.class,true);
        }
    }

    private void userButtonRegister() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runNextActivity(LoginActivity.this, null, false);
            }
        });
    }

    private void userButtonFacebookLogin() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.brus5.lukaszkrawczak.fitx", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }  } catch (PackageManager.NameNotFoundException e) { } catch (NoSuchAlgorithmException e) { }

        // releasing method updateWithToken
        // if connected via Facebook the first handler is gonna run up after one second
        // if not connected via Facebook the second handler is gonna run up after one second
        updateWithToken(AccessToken.getCurrentAccessToken());

        loginButton = findViewById(R.id.buttonFacebookLogin);
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
            }
        };
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            ProgressDialog dialog = ProgressDialog.show(LoginActivity.this,"Loading...",
                    "Loading application View, please wait...", false, false);
            dialog.show();
            Log.e(TAG,"Login success \n" + loginResult.getAccessToken().getUserId() + "\n" + loginResult.getAccessToken().getToken());

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject me, GraphResponse response) {

                            if (response.getError() != null) {
                                // handle error
                            } else {
                                String userLastname = me.optString("last_name");
                                String userFirstname = me.optString("first_name");
                                String userEmail = response.getJSONObject().optString("email");
                                String userBirthday = me.optString("birthday");
                                String userGender = me.optString("gender");
                                String userLocation = me.optString("location");

                                if (userGender.equals("male")){
                                    userGender = "m";
                                }
                                else if (userGender.equals("female")){
                                    userGender = "w";
                                }

                                // constricting my new data type from 09/04/1989 to convertedBirthday: 04.09.1989
                                String mDay = userBirthday.substring(3,5);
                                String mMonth = userBirthday.substring(0,2);
                                String mYear = userBirthday.substring(6,10);

                                String convertedBirthday = mDay+"."+mMonth+"."+mYear;

                                UserLoginRegisterFacebookDTO registerFacebookDTO = new UserLoginRegisterFacebookDTO();
                                registerFacebookDTO.userName = loginResult.getAccessToken().getUserId();
                                registerFacebookDTO.userFirstName = userFirstname;
                                registerFacebookDTO.userBirthday = convertedBirthday;
                                registerFacebookDTO.userPassword = "123";
                                registerFacebookDTO.userGender = userGender;
                                registerFacebookDTO.userEmail = userEmail;

                                // do not pass DB_USERNAME from Facebook to SaveSharedPreference class
                                SaveSharedPreference.setUserFirstName(LoginActivity.this, registerFacebookDTO.userFirstName);
                                SaveSharedPreference.setUserBirthday(LoginActivity.this, registerFacebookDTO.userBirthday);
                                SaveSharedPreference.setUserGender(LoginActivity.this, registerFacebookDTO.userGender);
                                SaveSharedPreference.setUserEmail(LoginActivity.this, registerFacebookDTO.userEmail);

                                LoginService loginService = new LoginService();
                                loginService.LoginWithFacebook(registerFacebookDTO,LoginActivity.this);

                                Log.d(TAG,"convertedBirthday: "+convertedBirthday);
                                Log.d(TAG,"user_email: "+userEmail);
                                Log.d(TAG,"user_lastname: "+userLastname);
                                Log.d(TAG,"user_firstname: "+userFirstname);
                                Log.d(TAG,"birthday: "+userBirthday);
                                Log.d(TAG,"gender: "+userGender);
                                Log.d(TAG,"location: "+userLocation);
                                Log.d(TAG,"gender: "+userGender);
                            }
                        }
                    });
            // pushing parameters from facebook
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
            request.setParameters(parameters);
            request.executeAsync();

            runNextActivity(LoginActivity.this,MainActivity.class,false);
        }

        @Override
        public void onCancel() {
            toastError(Configuration.USER_CANCEL);
        }

        @Override
        public void onError(FacebookException error) {
            toastError(Configuration.LOGIN_ERROR);
        }
    });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };

        // getting accesstoken
        accessToken = AccessToken.getCurrentAccessToken();

        /*
        if accesstoken isn't null then start new Activity which is MainActivity.class
        */
        if (accessToken != null){
            runNextActivity(LoginActivity.this,MainActivity.class, false);
        }
    }

    private void toastError(String connectionError) {
        Toast.makeText(this, connectionError, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        running up facebookLogin
        */
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void updateWithToken(AccessToken currentAccessToken) {
        /*
        checking currentAccessToken
        if connected via Facebook the first handler is gonna run up after one second
        if not connected via Facebook the second handler is gonna run up after one second
        */
        if (currentAccessToken != null) {
            Log.d(TAG,"Connected via Facebook");
            Log.d(TAG,"com.facebook.Profile.getCurrentProfile().getId()         "+com.facebook.Profile.getCurrentProfile().getId());
            Log.d(TAG,"com.facebook.Profile.getCurrentProfile().getFirstName()  "+com.facebook.Profile.getCurrentProfile().getFirstName());
            Log.d(TAG,"com.facebook.Profile.getCurrentProfile().getMiddleName() "+com.facebook.Profile.getCurrentProfile().getMiddleName());
            Log.d(TAG,"com.facebook.Profile.getCurrentProfile().getLastName()   "+com.facebook.Profile.getCurrentProfile().getLastName());
        } else {
            Log.d(TAG,"Not connected via Facebook");
        }
    }

    public void runNextActivity(Context packageContext, Class<?> cls, boolean defaultLogin){
        Intent intent = new Intent(packageContext,cls);
        intent.putExtra("defaultLogin",defaultLogin);
        LoginActivity.this.startActivity(intent);
        finish();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
