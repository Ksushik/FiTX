package com.brus5.lukaszkrawczak.fitx.register;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * This class is responsible for dynamically searching Input text
 * in DB or validation texts.
 */
public class TextSearch implements TextWatcher
{

    private static final String TAG = "TextSearch";
    private EditText et;
    private View acceptIv;
    private View errorIv;


    TextSearch(EditText et, View acceptIv, View errorIv)
    {
        this.et = et;
        et.addTextChangedListener(this);

        this.acceptIv = acceptIv;
        this.errorIv = errorIv;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        acceptIv.setVisibility(View.GONE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        Log.i(TAG, "afterTextChanged typing... " + s);
        acceptIv.setVisibility(View.VISIBLE);
    }
}
