package com.brus5.lukaszkrawczak.fitx.register.search;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.callback.CallBackService;
import com.brus5.lukaszkrawczak.fitx.utils.callback.OnDataLoaded;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for showing dynamic progressbar while typing text.
 * For example: if user types text in EditText user see progressbar while that,
 * the information is sended to REST database and showing status after retrieving data.
 */
public class CallBackTextSearch extends TextSearch
{
    private Context context;
    private String link;
    private ProgressBar progressBar;
    private View acceptIv;
    private View errorIv;

    public boolean isValid;

    private Timer timer = new Timer();

    public CallBackTextSearch(Context context, EditText et, View acceptIv, View errorIv, ProgressBar progressBar, String link)
    {
        super(et, acceptIv, errorIv, progressBar);
        this.context = context;
        this.link = link;
        this.acceptIv = acceptIv;
        this.errorIv = errorIv;
        this.progressBar = progressBar;
    }

    /** Showing up progressbar and hiding other images */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        progressBar.setVisibility(View.VISIBLE);
        errorIv.setVisibility(View.INVISIBLE);
        acceptIv.setVisibility(View.INVISIBLE);
    }

    /** Sending info MySQL */
    @Override
    public void afterTextChanged(Editable s)
    {
        checkInfo(s.toString());
    }

    /** Starting timer 1.5s which runs request to MySQL */
    private void checkInfo(final String s)
    {
        timer.cancel();
        timer = new Timer();

        // milliseconds
        int DELAY = 1500;
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                new CallBackService(context).post(link + s, new OnDataLoaded() {
                    @Override
                    public void onSuccess(String s)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(s);

                            // for example if username is already taken, then status = true
                            boolean status = jsonObject.getBoolean("error");
                            statusChecker(status);
                            isValid = !status;
                        }
                        catch (JSONException ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String s)
                    {
                        Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, DELAY);
    }

    private void statusChecker(boolean status)
    {
        if (!status)
        {
            progressBar.setVisibility(View.INVISIBLE);
            errorIv.setVisibility(View.INVISIBLE);
            acceptIv.setVisibility(View.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(View.INVISIBLE);
            errorIv.setVisibility(View.VISIBLE);
            acceptIv.setVisibility(View.INVISIBLE);
        }
    }
}
