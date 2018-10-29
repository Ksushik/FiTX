package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class is responsible for validate entered date by user.
 * The date format should be dd.mm.yyyy
 */
public class BirthdayTextSearch extends TextSearch
{
    private String enteredDate;
    public boolean isValid;
    private DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    public BirthdayTextSearch(EditText et, View acceptIv, View errorIv)
    {
        super(et, acceptIv, errorIv);
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        enteredDate = s.toString();
        update(isValid() && isBirthday());
        isValid = isValid() && isBirthday();
    }

    /** Checking if user entered proper date format which should be dd.mm.yyyy */
    private boolean isValid()
    {
        if (enteredDate.isEmpty() || enteredDate.equals("null")) return false;
        if (enteredDate.matches("^((0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).\\d{4})$")) return true;
        return false;
    }

    /** Checking if user is minimum 12 years old from today date */
    private boolean isBirthday()
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -12);
        cal.add(Calendar.DATE, -1);
        Date dateToday = cal.getTime();
        Date enterDate;
        try
        {
            if (enteredDate.isEmpty() || enteredDate.equals("null")) return false;
            else enterDate = sdf.parse(enteredDate);

            if (dateToday.compareTo(enterDate) > 0) return true;

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
