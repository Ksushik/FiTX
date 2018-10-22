package com.brus5.lukaszkrawczak.fitx.register.search;

import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.brus5.lukaszkrawczak.fitx.utils.callback.OnDataLoaded;
import com.brus5.lukaszkrawczak.fitx.utils.callback.CallBackService;

import java.util.Timer;
import java.util.TimerTask;

public class CallBackTextSearch extends TextSearch
{



    private static final String TAG = "CallBackTextSearch";

    private Context context;
    private String link;

    private final int DELAY = 1500; // milliseconds
    private Timer timer = new Timer();

    public CallBackTextSearch(Context context, EditText et, View acceptIv, View errorIv, ProgressBar progressBar, String link)
    {
        super(et, acceptIv, errorIv, progressBar);
        this.context = context;
        this.link = link;
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

                new CallBackService(context).post(link + s, new OnDataLoaded() {
                    @Override
                    public void onSuccess(String s)
                    {
                        Log.d(TAG, "onSuccess() called with: s = [" + s + "] dupa");
                    }

                    @Override
                    public void onError(String s)
                    {
                        Log.d(TAG, "onError() called with: s = [" + s + "]");
                    }
                });

                Log.d(TAG, "Teraz pokazujemy wynik, isError albo isOK");
            }
        }, DELAY);
    }



}
