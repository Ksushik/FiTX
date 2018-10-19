package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * This class is responsible for dynamically searching Input text
 * in DB or validation texts.
 * It must show to user dynamically while he typing text ImageViews:
 * - red for error,
 * - green for OK result.
 */
public class TextSearch implements TextWatcher
{
    private View acceptIv;
    private View errorIv;
    private ProgressBar progressBar;

    /**
     * Simple TextSearch with
     */
    TextSearch(EditText et, View acceptIv, View errorIv)
    {
        et.addTextChangedListener(this);
        this.acceptIv = acceptIv;
        this.errorIv = errorIv;
    }

    /**
     * Contains progressBar
     */
    TextSearch(EditText et, View acceptIv, View errorIv, ProgressBar progressBar)
    {
        et.addTextChangedListener(this);
        this.acceptIv = acceptIv;
        this.errorIv = errorIv;
        this.progressBar = progressBar;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        acceptIv.setVisibility(View.GONE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {}

    private void isError()
    {
        acceptIv.setVisibility(View.INVISIBLE);
        errorIv.setVisibility(View.VISIBLE);
    }

    private void isOK()
    {
        acceptIv.setVisibility(View.VISIBLE);
        errorIv.setVisibility(View.INVISIBLE);
    }

    void update(boolean isValid)
    {
        if (isValid)
        {
            isOK();
        }
        else
        {
            isError();
        }
    }

}
