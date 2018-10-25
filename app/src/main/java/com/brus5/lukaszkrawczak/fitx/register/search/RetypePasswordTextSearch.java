package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

public class RetypePasswordTextSearch extends TextSearch
{
    private String enteredText;

    private PasswordListener myListener;
    private String secondPassword;
    public boolean isValid;

    public RetypePasswordTextSearch(EditText et, View acceptIv, View errorIv, PasswordListener myListener, String firstPassword)
    {
        super(et, acceptIv, errorIv);
        this.myListener = myListener;
        this.secondPassword = firstPassword;
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        enteredText = s.toString();
        update(s.toString().equals(secondPassword));
        isValid = s.toString().equals(secondPassword);
    }
}