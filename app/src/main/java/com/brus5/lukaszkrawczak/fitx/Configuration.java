package com.brus5.lukaszkrawczak.fitx;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class Configuration {
    private static final String TAG = "Configuration";
    public static final String BASE_URL = "http://justfitx.xyz/";



    public static final String DIET_USER_SHOW_DAILY_URL = BASE_URL + "Diet/" + "ShowByUser.php";
    public static final String DIET_SEND_COUNTED_KCAL_URL = BASE_URL + "Diet/" + "UpdateKcalResult.php";
    public static final String DIET_RESET_KCAL_URL = BASE_URL + "Diet/" + "DeleteMeal.php";


    public static final String SHOW_TRAINING_URL = Configuration.BASE_URL + "Training/" + "ShowByUser.php";


    public static final String USER_CANCEL = "Zakończono przez użytkownika";
    public static final String LOGIN_ERROR = "Nieudana próba połączenia";

    public static final String NEW_ACCOUNT = "Witamy nowego użytkownika";
    public static final String EXISTING_ACCOUNT = "Witaj ponownie";

    public static final String CONNECTION_INTERNET_FAILED = "Błąd połączenia";


    SimpleDateFormat simpleDateDateInside = new SimpleDateFormat("yyyy-MM-dd");
    public SimpleDateFormat getSimpleDateDateInside() {
        return simpleDateDateInside;
    }

    SimpleDateFormat simpleDateTextView = new SimpleDateFormat("dd MMMM yyyy");
    public SimpleDateFormat getSimpleDateTextView() {
        return simpleDateTextView;
    }

    public Calendar generateEndDay(){
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        return endDate;
    }
    public Calendar generateNextDay(){
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);
        Log.e(TAG, "generateNextDay: "+startDate );
        return startDate;
    }


}
