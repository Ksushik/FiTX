package com.brus5.lukaszkrawczak.fitx.Login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.RestApiNames;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginRegisterFacebookDTO;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService {


    private static final String TAG = "ActivityService";

    private static final String FACEBOOK_REGISTER = Configuration.BASE_URL + "Facebook/FacebookRegisterRequest.php";
    private static final String LOGIN_REQUEST = Configuration.BASE_URL + "User/UserLoginRequest.php";

    public void LoginWithFacebook(final UserLoginRegisterFacebookDTO dto, final Context ctx){

        StringRequest strRequest = new StringRequest(Request.Method.POST, FACEBOOK_REGISTER,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.e(TAG, "onResponse: "+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(ctx, Configuration.NEW_ACCOUNT,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean userused = jsonObject.getBoolean("userused");
                                if (userused){
                                    Toast.makeText(ctx,Configuration.EXISTING_ACCOUNT,Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                        Log.d(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USER_FIRSTNAME, dto.userFirstName);
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_PASSWORD, dto.userPassword);
                params.put(RestApiNames.DB_USER_BIRTHDAY, dto.userBirthday);
                params.put(RestApiNames.DB_USER_GENDER, dto.userGender);
                params.put(RestApiNames.DB_USER_EMAIL, dto.userEmail);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }
    public void LoginNormal(final UserLoginNormalDTO dto, final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(ctx, MainActivity.class);
                                SaveSharedPreference.setUserName(ctx,dto.userName);
                                SaveSharedPreference.setDefLogin(ctx,true);
                                ctx.startActivity(intent);
                                ((LoginActivity)ctx).finish();
                            } else {
                                Toast.makeText(ctx, Configuration.LOGIN_ERROR, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_PASSWORD, dto.userPassword);
                Log.e(TAG, "getParams: "+params);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }
    public void GraphKcalJson(final UserLoginNormalDTO dto, final Context ctx){
        StringRequest strRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(ctx, MainActivity.class);
                                SaveSharedPreference.setUserName(ctx,dto.userName);
                                SaveSharedPreference.setDefLogin(ctx,true);
                                ctx.startActivity(intent);
                                ((LoginActivity)ctx).finish();
                            } else {
                                Toast.makeText(ctx, Configuration.LOGIN_ERROR, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onResponse: "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String,String> params = new HashMap<>();
                params.put(RestApiNames.DB_USERNAME, dto.userName);
                params.put(RestApiNames.DB_PASSWORD, dto.userPassword);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

}
