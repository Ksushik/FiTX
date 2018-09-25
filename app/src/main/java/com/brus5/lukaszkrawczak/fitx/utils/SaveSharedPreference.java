package com.brus5.lukaszkrawczak.fitx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 06.04.2018.
 */

public class SaveSharedPreference
{
    private static final String TAG = "SaveSharedPreference";
    private static final String USER_ID = "userID";
    private static final String USER_NAME = "userName";
    private static final String DEFAULT_LOGIN = "defaultLogin";
    private static final String USER_FIRST_NAME = "userFirstName";
    private static final String USER_BIRTHDAY = "userBirthday";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_GENDER = "userGender";
    private static final String USER_AGE = "userAge";
    private static final String USER_PASSWORD = "userPassword";
    public static final String SAUTO_CALORIES = "autoCalories";
    public static final String LIMIT_CALORIES = "limitCalories";
    public static final int AUTO_CALORIES = 1;

    static SharedPreferences getSharedPreferences(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserName(Context context, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_NAME, userName);
        //        editor.commit();
        editor.apply();
    }

    public static void setDefLogin(Context context, boolean defaultLogin)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(DEFAULT_LOGIN, true);
        //        editor.commit();
        editor.apply();
    }

    public static String getUserName(Context context)
    {
        return getSharedPreferences(context).getString(USER_NAME, "");
    }

    public static Boolean getDefLogin(Context context)
    {
        return getSharedPreferences(context).getBoolean(DEFAULT_LOGIN, false);
    }

    public static void clearUserName(Context context)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear(); //clear all stored data
        //        editor.commit();
        editor.apply();
    }

    public static void setUserFirstName(Context context, String userFirstName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_FIRST_NAME, userFirstName);
        //        editor.commit();
        editor.apply();
    }

    public static String getUserFirstName(Context context)
    {
        return getSharedPreferences(context).getString(USER_FIRST_NAME, "");
    }

    public static void setUserBirthday(Context context, String userBirthday)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        int mDay = Integer.valueOf(userBirthday.substring(0, 2));
        int mMonth = Integer.valueOf(userBirthday.substring(3, 5));
        int mYear = Integer.valueOf(userBirthday.substring(6, 10));
        int userAgeint = Integer.valueOf(getAge(mYear, mMonth, mDay));
        setUserAge(context, Integer.valueOf(getAge(mYear, mMonth, mDay)));
        editor.putString(USER_BIRTHDAY, userBirthday);
        editor.apply();
    }

    public static String getUserBirthday(Context context)
    {
        return getSharedPreferences(context).getString(USER_BIRTHDAY, "");
    }

    public static void setUserEmail(Context context, String userEmail)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_EMAIL, userEmail);
        //        editor.commit();
        editor.apply();
    }

    public static String getUserEmail(Context context)
    {
        return getSharedPreferences(context).getString(USER_EMAIL, "");
    }

    public static void setUserGender(Context context, String userGender)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_GENDER, userGender);
        editor.apply();
    }

    public static String getUserGender(Context context)
    {
        return getSharedPreferences(context).getString(USER_GENDER, "");
    }

    public static void setUserID(Context context, int userID)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(USER_ID, userID);
        editor.apply();
    }

    public static int getUserID(Context context)
    {
        return getSharedPreferences(context).getInt(USER_ID, 0);
    }

    public static void setUserAge(Context context, int userAge)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(USER_AGE, userAge);
        editor.apply();
    }

    public static int getUserAge(Context context)
    {
        return getSharedPreferences(context).getInt(USER_AGE, 0);
    }

    private static String getAge(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarToday = Calendar.getInstance();

        calendar.set(year, month, day);

        int userAge = calendarToday.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (calendarToday.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR))
        {
            userAge--;
        }
        return String.valueOf(userAge);
    }

    public static void setUserPassword(Context context, String userPassword)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(USER_PASSWORD, userPassword);
        editor.apply();
    }

    public static void setAutoCalories(Context context, int autoCalories)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(SAUTO_CALORIES,autoCalories);
        editor.apply();
    }

    public static void setLimitCalories(Context context, String limitCalories)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LIMIT_CALORIES, limitCalories);
        editor.apply();
    }

    public static String getLimitCalories(Context context)
    {
        return getSharedPreferences(context).getString(LIMIT_CALORIES, "");
    }

    /**
     * Program automatically starts with automatic calories ON!
     *
     * @param context actual context
     * @return int value of auto_calories YES '1', NO '0'
     */
    public static int getAutoCalories(Context context)
    {
        return getSharedPreferences(context).getInt(SAUTO_CALORIES, 1);
    }

    public static String getUserPassword(Context context)
    {
        return getSharedPreferences(context).getString(USER_PASSWORD, "");
    }

    public static void logOff(Context context)
    {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }

}