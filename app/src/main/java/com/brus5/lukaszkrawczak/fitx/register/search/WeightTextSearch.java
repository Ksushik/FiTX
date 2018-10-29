package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightTextSearch extends TextSearch
{
    private String enteredText;

    public boolean isValid;

    public WeightTextSearch(EditText et, View acceptIv, View errorIv)
    {
        super(et, acceptIv, errorIv);
    }

    private boolean isValid()
    {
        double value = 0d;
        if (!enteredText.isEmpty()) value = Double.valueOf(enteredText);

        if (enteredText.isEmpty() || enteredText.equals("null"))
        {
            return false;
        }
        if (value < 30 || value > 200)
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
        isValid = isValid();
    }
}
