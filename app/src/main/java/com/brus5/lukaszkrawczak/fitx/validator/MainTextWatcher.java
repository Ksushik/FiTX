package com.brus5.lukaszkrawczak.fitx.validator;

import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

public abstract class MainTextWatcher implements TextWatcher
{
    private View view;
    private TextView textView;

    public MainTextWatcher(View view, TextView textView)
    {
        this.view = view;
        this.textView = textView;
    }
}
