package com.brus5.lukaszkrawczak.fitx.login;

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
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.login.dto.GetUserInfoDTO;
import com.brus5.lukaszkrawczak.fitx.login.dto.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.login.dto.UserLoginRegisterFacebookDTO;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginService
{
    private static final String TAG = "LoginService";
    private static final String FACEBOOK_REGISTER = RestAPI.SERVER_URL + "Facebook/FacebookRegisterRequest.php";
    private static final String LOGIN_REQUEST = RestAPI.SERVER_URL + "User/UserLoginRequest.php";
    private static final String GET_USER_INFO = RestAPI.SERVER_URL + "User/GetData.php";

    public void LoginWithFacebook(final UserLoginRegisterFacebookDTO dto, final Context ctx)
    {

        StringRequest strRequest = new StringRequest(Request.Method.POST, FACEBOOK_REGISTER, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.e(TAG, "onResponse: " + response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success)
                    {
                        Toast.makeText(ctx, R.string.welcome_new_user, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();

                    try
                    {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean userused = jsonObject.getBoolean("userused");
                        if (userused)
                        {
                            Toast.makeText(ctx, R.string.welcome_again, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e1)
                    {
                        e1.printStackTrace();
                    }

                }
                Log.d(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener()
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
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USER_FIRSTNAME, dto.userFirstName);
                params.put(RestAPI.DB_USERNAME, dto.userName);
                params.put(RestAPI.DB_PASSWORD, dto.userPassword);
                params.put(RestAPI.DB_USER_BIRTHDAY, dto.userBirthday);
                params.put(RestAPI.DB_USER_GENDER, dto.userGender);
                params.put(RestAPI.DB_USER_EMAIL, dto.userEmail);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    public void LoginNormal(final UserLoginNormalDTO dto, final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, LOGIN_REQUEST, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success)
                    {

                        SaveSharedPreference.setUserName(ctx, dto.userName);
                        SaveSharedPreference.setDefLogin(ctx, true);


                        GetUserInfoDTO dto1 = new GetUserInfoDTO();
                        dto1.userName = SaveSharedPreference.getUserName(ctx);
                        LoginService.this.GetUserInfo(dto1, ctx);



                    }
                    else
                    {
                        Toast.makeText(ctx, R.string.error, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                Log.d(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener()
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
                HashMap<String, String> params = new HashMap<>();

                params.put(RestAPI.DB_USERNAME, dto.userName);
                params.put(RestAPI.DB_PASSWORD, computeHash(dto.userPassword));

                Log.e(TAG, "getParams: " + params);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);

    }

    public void GetUserInfo(final GetUserInfoDTO dto, final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, GET_USER_INFO, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    int user_id = jsonObject.getInt("user_id");
                    String username = jsonObject.getString("username");
                    String user_first_name = jsonObject.getString("user_first_name");
                    String user_birthday = jsonObject.getString("user_birthday");
                    String user_email = jsonObject.getString("user_email");
                    String user_gender = jsonObject.getString("user_gender");
                    int user_auto_calories = jsonObject.getInt("user_auto_calories");
                    int user_diet_goal = jsonObject.getInt("user_diet_goal");

                    SaveSharedPreference.setUserID(ctx, user_id);
                    SaveSharedPreference.setUserName(ctx, username);
                    SaveSharedPreference.setUserFirstName(ctx, user_first_name);
                    SaveSharedPreference.setUserBirthday(ctx, user_birthday);
                    SaveSharedPreference.setUserEmail(ctx, user_email);
                    SaveSharedPreference.setUserGender(ctx, user_gender);
                    SaveSharedPreference.setAutoCalories(ctx, user_auto_calories);
                    SaveSharedPreference.setDietGoal(ctx, user_diet_goal);

                    Intent intent = new Intent(ctx, MainActivity.class);
                    ctx.startActivity(intent);
                    ((LoginActivity) ctx).finish();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
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
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_USERNAME, dto.userName);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }


    private String computeHash(String input)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            // This MessageDigest class provides applications the functionality of a
            // message digest algorithm, such as SHA-1 or SHA-256.
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            for (Byte b : md.digest(input.getBytes()))
            {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return sb.toString().toLowerCase();
    }






}
