package com.brus5.lukaszkrawczak.fitx.validator;

import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;


public class CharacterLimit extends MainTextWatcher
{
    private TextView textView;
    private int limit;
    private int charNum = 0;

    public CharacterLimit(View view, TextView textView, int charLimit)
    {
        super(view, textView);
        this.textView = textView;
        this.limit = charLimit;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        charNum = s.length();
        charCounter();
        isLimit();
    }

    private void charCounter()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(charNum);
        String s = builder + " / " + String.valueOf(limit);
        textView.setText(s);
    }

    public boolean isLimit()
    {
        if (charNum > limit)
        {
            textView.setTextColor(Color.RED);
            return false;
        }
        else textView.setTextColor(Color.BLACK);
        return true;
    }

}
