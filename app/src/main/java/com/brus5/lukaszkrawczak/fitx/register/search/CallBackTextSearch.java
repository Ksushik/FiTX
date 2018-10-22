package com.brus5.lukaszkrawczak.fitx.register.search;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressTextSearch extends TextSearch
{

    private static final String TAG = "ProgressTextSearch";
    private final int DELAY = 1500; // milliseconds
    private Timer timer = new Timer();

    public ProgressTextSearch(EditText et, View acceptIv, View errorIv, ProgressBar progressBar)
    {
        super(et, acceptIv, errorIv, progressBar);
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        checkInfo(s.toString());
        Log.d(TAG, "afterTextChanged: teraz pokazuje progressBar'a" );
    }

    private void checkInfo(final String s)
    {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                Log.d(TAG, "run() called... Sending " + s + " to datebase");
                Log.d(TAG, "Teraz pokazujemy wynik, isError albo isOK");
            }
        }, DELAY);
    }


}
