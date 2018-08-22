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
import com.brus5.lukaszkrawczak.fitx.Login.DTO.GetUserInfoDTO;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginNormalDTO;
import com.brus5.lukaszkrawczak.fitx.Login.DTO.UserLoginRegisterFacebookDTO;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginService
{
    private static final String TAG = "ActivityService";
    private static final String FACEBOOK_REGISTER = RestAPI.URL + "Facebook/FacebookRegisterRequest.php";
    private static final String LOGIN_REQUEST = RestAPI.URL + "User/UserLoginRequest.php";
    private static final String GET_USER_INFO = RestAPI.URL + "User/UserInfoShowRequest.php";

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
                        Toast.makeText(ctx, RestAPI.NEW_ACCOUNT, Toast.LENGTH_LONG).show();
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
                            Toast.makeText(ctx, RestAPI.EXISTING_ACCOUNT, Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(ctx, MainActivity.class);
                        SaveSharedPreference.setUserName(ctx, dto.userName);
                        SaveSharedPreference.setDefLogin(ctx, true);
                        ctx.startActivity(intent);
                        ((LoginActivity) ctx).finish();


                        GetUserInfoDTO dto1 = new GetUserInfoDTO();
                        dto1.userName = SaveSharedPreference.getUserName(ctx);
                        LoginService.this.GetUserInfo(dto1, ctx);


                    }
                    else
                    {
                        Toast.makeText(ctx, RestAPI.LOGIN_ERROR, Toast.LENGTH_LONG).show();
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
                params.put(RestAPI.DB_PASSWORD, dto.userPassword);
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
                    int userId = 0;
                    String userFirstName = "";
                    String userBirthday = "";
                    String userPassword = "";
                    String userEmail = "";
                    String userGender = "";
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        userId = jsonObject1.getInt("user_id");
                        userFirstName = jsonObject1.getString("name");
                        userBirthday = jsonObject1.getString("birthday");
                        userPassword = jsonObject1.getString("password");
                        userEmail = jsonObject1.getString("email");
                        userGender = jsonObject1.getString("male");

                    }
                    SaveSharedPreference.setUserID(ctx, userId);
                    SaveSharedPreference.setUserFirstName(ctx, userFirstName);
                    SaveSharedPreference.setUserBirthday(ctx, userBirthday);
                    SaveSharedPreference.setUserPassword(ctx, userPassword);
                    SaveSharedPreference.setUserEmail(ctx, userEmail);
                    SaveSharedPreference.setUserGender(ctx, userGender);

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

    public void GraphKcalJson(final UserLoginNormalDTO dto, final Context ctx)
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
                        Intent intent = new Intent(ctx, MainActivity.class);
                        SaveSharedPreference.setUserName(ctx, dto.userName);
                        SaveSharedPreference.setDefLogin(ctx, true);
                        ctx.startActivity(intent);
                        ((LoginActivity) ctx).finish();
                    }
                    else
                    {
                        Toast.makeText(ctx, RestAPI.LOGIN_ERROR, Toast.LENGTH_LONG).show();
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
                params.put(RestAPI.DB_PASSWORD, dto.userPassword);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

}
