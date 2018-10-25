package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstNameTextSearch extends TextSearch
{
    private String enteredText;

    public boolean isValid;

    // This patter contains only letters and polish letters for name
    private Pattern pattern = Pattern.compile("\\p{Lu}\\p{Ll}*");

    public FirstNameTextSearch(EditText et, View acceptIv, View errorIv)
    {
        super(et, acceptIv, errorIv);
    }

    private boolean isValid()
    {
        Matcher matcher = pattern.matcher(enteredText);

        if (enteredText.isEmpty() || enteredText.equals("null"))
        {
            return false;
        }
        if (enteredText.length() < 3 || enteredText.length() > 15)
        {
            return false;
        }
        if (!matcher.matches())
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
