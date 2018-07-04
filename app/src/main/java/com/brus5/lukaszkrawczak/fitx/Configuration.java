package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.Login.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class Configuration extends AppCompatActivity{
    private static final String TAG = "Configuration";
    public static final String BASE_URL = "http://justfitx.xyz/";

    public static final String DIET_GET_PRODUCT_INFORMATIONS        = BASE_URL + "Diet/" + "GetProductInformations.php";
    public static final String DIET_DELETE_COUNTED_KCAL_URL         = BASE_URL + "Diet/" + "KcalResultDelete.php";
    public static final String DIET_UPDATE_COUNTED_KCAL_URL         = BASE_URL + "Diet/" + "KcalResultUpdate.php";
    public static final String DIET_DELETE_PRODUCT                  = BASE_URL + "Diet/" + "ProductDelete.php";
    public static final String DIET_INSERT_PRODUCT                  = BASE_URL + "Diet/" + "ProductInsert.php";
    public static final String DIET_SEARCH_PRODUCT                  = BASE_URL + "Diet/" + "ProductsSearch.php";
    public static final String DIET_USER_SHOW_DAILY_URL             = BASE_URL + "Diet/" + "ShowByUser.php";
    public static final String DIET_UPDATE_WEIGHT_PRODUCT           = BASE_URL + "Diet/" + "UpdateProductWeight.php";

    public static final String SHOW_TRAINING_URL                    = BASE_URL + "Training/" + "ShowByUser.php";

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
        return startDate;
    }

}
