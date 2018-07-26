package com.brus5.lukaszkrawczak.fitx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class Configuration
{

    public static final String BASE_URL = "http://justfitx.xyz/";

    public static final String DIET_GET_PRODUCT_INFORMATIONS = BASE_URL + "Diet/" + "GetProductInformations.php";
    public static final String DIET_DELETE_COUNTED_KCAL_URL = BASE_URL + "Diet/" + "KcalResultDelete.php";
    public static final String DIET_UPDATE_COUNTED_KCAL_URL = BASE_URL + "Diet/" + "KcalResultUpdate.php";
    public static final String DIET_DELETE_PRODUCT = BASE_URL + "Diet/" + "ProductDelete.php";
    public static final String DIET_INSERT_PRODUCT = BASE_URL + "Diet/" + "ProductInsert.php";
    public static final String DIET_SEARCH_PRODUCT = BASE_URL + "Diet/" + "ProductsSearch.php";
    public static final String DIET_USER_SHOW_DAILY_URL = BASE_URL + "Diet/" + "ShowByUser.php";
    public static final String DIET_UPDATE_WEIGHT_PRODUCT = BASE_URL + "Diet/" + "UpdateProductWeight.php";

    public static final String SHOW_TRAINING_DETAILS = BASE_URL + "Training/" + "ShowTrainingDetails.php";
    public static final String SHOW_TRAINING_URL = BASE_URL + "Training/" + "ShowByUser.php";
    public static final String SHOW_TRAINING_SHORT_URL = BASE_URL + "Training/" + "ShowByUserShort.php";
    public static final String SHOW_TRAINING_DESCRIPTION = BASE_URL + "Training/" + "ShowTrainingDescription.php";
    public static final String TRAINING_SEARCH_BY_TARGET = BASE_URL + "Training/" + "SearchByTarget.php";
    public static final String TRAINING_INSERT = BASE_URL + "Training/" + "Insert.php";
    public static final String TRAINING_DELETE = BASE_URL + "Training/" + "Delete.php";
    public static final String TRAINING_UPDATE = BASE_URL + "Training/" + "Update.php";

    public static final String USER_CANCEL = "Zakończono przez użytkownika";
    public static final String LOGIN_ERROR = "Nieudana próba połączenia";

    public static final String NEW_ACCOUNT = "Witamy nowego użytkownika";
    public static final String EXISTING_ACCOUNT = "Witaj ponownie";

    public static final String CONNECTION_INTERNET_FAILED = "Błąd połączenia";

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SimpleDateFormat getDateFormat()
    {
        return dateFormat;
    }

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat dateFormatView = new SimpleDateFormat("dd MMMM yyyy");

    public SimpleDateFormat getDateFormatView()
    {
        return dateFormatView;
    }

    public Calendar oldestDay()
    {
        Calendar oldest = Calendar.getInstance();
        oldest.add(Calendar.MONTH, 1);
        return oldest;
    }

    public Calendar newestDay()
    {
        Calendar newest = Calendar.getInstance();
        newest.add(Calendar.MONTH, -1);
        return newest;
    }

    public void showError(Context context)
    {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
    }

}
