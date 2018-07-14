package com.brus5.lukaszkrawczak.fitx;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Training.TrainingActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

/**
 * Created by lukaszkrawczak on 24.05.2018.
 */

public class Configuration {
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

    private String dateInside;
    private String dateInsideTextView;

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

    public void setHorizontalCalendar(final Context ctx, int resId, final TextView tv, final Class<?> cls, final Bundle savedInstanceState){
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder((Activity) ctx, resId)
                .startDate(generateNextDay().getTime())
                .endDate(generateEndDay().getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EE")
                .dayNumberFormat("dd")
                .showDayName(true)
                .showMonthName(false)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Log.i(TAG, "*********************************************************************");
                tv.setText(getSimpleDateTextView().format(date.getTime()));

                DTO dto = new DTO();
                dto.userName = SaveSharedPreference.getUserName(ctx);
                dto.dateToday = getSimpleDateDateInside().format(date.getTime());

//                DTOAdapter dtoAdapter = new DTOAdapter(ctx);
//                dtoAdapter.setCtx(ctx);
//                dtoAdapter.setDto(dto);

//                TrainingActivity tr = new TrainingActivity();
//                tr.loadAsynchTask(dto, ctx);


                Log.i(TAG, "onDateSelected: "+cls.getName());


                Method[] methods = cls.getMethods();
                for (int i = 0; i<methods.length; i++){
                    Log.i(TAG, "myMethods: " + methods[i].getName());
                }


//              Reflection used here, with calling asynch method to get data from database

                try {

                    Class<?> c = Class.forName(cls.getName());

                    Object obj = c.newInstance();

                    Log.e(TAG, "c: " + c);

                    Method method = c.getDeclaredMethod("loadAsynchTask", DTO.class, Context.class);

                    Method bundle = c.getDeclaredMethod("onCreate", Bundle.class);

// FIXME: 13.07.2018 WORK HERE!
                    Log.d(TAG, "method: "+method);


                    method.invoke(obj,dto,ctx);
/*
                    Class<?> c = Class.forName(cls.getName());

                    Object obj = c.newInstance();

                    Log.e(TAG, "c: " + c);

                    Method method = c.getDeclaredMethod("say");

                    Log.d(TAG, "method: "+method);

                    method.invoke(obj);
*/

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


                Log.i(TAG, "onDateSelected() is called. DTO.userName: " + dto.userName + "; DTO.dateInside: " + dto.dateToday);
                Log.i(TAG, "*********************************************************************");
            }
        });




    }


}
