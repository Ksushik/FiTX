package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 06.04.2018.
 */

public class SaveSharedPreference
{
    private static final String PREF_USER_ID            = "userID";
    private static final String PREF_USER_NAME          = "userName";
    private static final String DEF_LOGIN               = "defaultLogin";
    private static final String PREF_USER_FIRST_NAME    = "userFirstName";
    private static final String PREF_USER_BIRTHDAY      = "userBirthday";
    private static final String PREF_USER_EMAIL         = "userEmail";
    private static final String PREF_USER_GENDER        = "userGender";
    private static final String PREF_USER_AGE           = "userAge";
    private static final String PREF_USER_PASSWORD      = "userPassword";

    private static final String TAG = "SaveSharedPreference";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static void setDefLogin(Context ctx, boolean defaultLogin) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(DEF_LOGIN, true);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static Boolean getDefLogin(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(DEF_LOGIN, false);
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void setUserFirstName(Context ctx, String userFirstName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_FIRST_NAME, userFirstName);
        editor.commit();
    }

    public static String getUserFirstName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_FIRST_NAME, "");
    }

    public static void setUserBirthday(Context ctx, String userBirthday) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        int mDay = Integer.valueOf(userBirthday.substring(0,2));
        int mMonth = Integer.valueOf(userBirthday.substring(3,5));
        int mYear = Integer.valueOf(userBirthday.substring(6,10));
        int userAgeint = Integer.valueOf(getAge(mYear,mMonth,mDay,null));
        setUserAge(ctx,Integer.valueOf(getAge(mYear,mMonth,mDay,null)));
        editor.putString(PREF_USER_BIRTHDAY, userBirthday);
        editor.commit();
    }

    public static String getUserBirthday(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_BIRTHDAY, "");
    }

    public static void setUserEmail(Context ctx, String userEmail) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, userEmail);
        editor.commit();
    }

    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static void setUserGender(Context ctx, String userGender) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_GENDER, userGender);
        editor.commit();
    }

    public static String getUserGender(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_GENDER, "");
    }

    public static void setUserID(Context ctx, int userID) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_ID, userID);
        editor.commit();
    }

    public static int getUserID(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_USER_ID,0);
    }

    public static void setUserAge(Context ctx, int userAge) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_USER_AGE, userAge);
        editor.commit();
    }

    public static int getUserAge(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_USER_AGE,0);
    }

    private static String getAge(int year, int month, int day, Context ctx){
        Calendar calendar = Calendar.getInstance();
        Calendar calendarToday = Calendar.getInstance();

        calendar.set(year, month, day);

        int userAge = calendarToday.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        if (calendarToday.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)){
            userAge--;
        }
        Integer ageInt = new Integer(userAge);
        String ageString = ageInt.toString();
        return ageString;
    }

    public static void setUserPassword(Context ctx, String userPassword) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASSWORD, userPassword);
        editor.commit();
    }

    public static String getUserPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_PASSWORD, "");
    }





}