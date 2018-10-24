package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class RetypePasswordTextSearch extends TextSearch
{
    private String enteredText;
    private static final String TAG = "RetypePasswordTextSearc";

    PasswordListener myListener;

    public RetypePasswordTextSearch(EditText et, View acceptIv, View errorIv, PasswordListener myListener)
    {
        super(et, acceptIv, errorIv);
        this.myListener = myListener;
    }

    private boolean isValid()
    {
        if (enteredText.isEmpty() || enteredText.equals("null"))
        {
            return false;
        }
        if (enteredText.length() < 5 || enteredText.length() > 64)
        {
            return false;
        }
        return true;
    }


    @Override
    public void afterTextChanged(Editable s)
    {
        enteredText = s.toString();
        update(isValid());
        myListener.callBack(s.toString());
        myListener.secondPassword(isValid());
    }

}
