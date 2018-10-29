package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class HeightTextSearch extends TextSearch
{
    private String enteredText;

    public boolean isValid;

    public HeightTextSearch(EditText et, View acceptIv, View errorIv)
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
        if (value < 60 || value > 250)
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
