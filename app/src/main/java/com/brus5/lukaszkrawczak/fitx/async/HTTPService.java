package com.brus5.lukaszkrawczak.fitx.async;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.brus5.lukaszkrawczak.fitx.async.inflater.CardioDetailsActivityInflater;
import com.brus5.lukaszkrawczak.fitx.async.inflater.DietActivityInflater;
import com.brus5.lukaszkrawczak.fitx.async.inflater.DietProductDetailsActivityInflater;
import com.brus5.lukaszkrawczak.fitx.async.inflater.DietProductSearchActivityInflater;
import com.brus5.lukaszkrawczak.fitx.async.inflater.MainActivityInflater;
import com.brus5.lukaszkrawczak.fitx.async.inflater.TrainingActivityInflater;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This delivers informations about connection to MySQL server
 * <p>
 * Created by Łukasz Krawczak
 */

@SuppressLint("StaticFieldLeak")
public class HTTPService extends AsyncTask<String, String, String>
{
    private static final String TAG = "HTTPService";
    private Activity activity;
    private Context context;
    private ListView listView;

    public HTTPService(Activity activity, Context context, ListView listView)
    {
        this.activity = activity;
        this.context = context;
        this.listView = listView;
    }

    // Invoked on the background thread
    @Override
    public String doInBackground(String... strings)
    {
        // isConnected is false by default
        String response = "Response value";

        try
        {
            // new url connection handled with exeption
            // link is handled by parameters of strings, so strings[0]
            // contains link, and strings[1] contains parameters.
            URL url = new URL(strings[0] + strings[1]);

            // url object has method with open url connection
            // and returns object of (HttpURLConnection)
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            // set the buffer size to use internally by the BufferedInputStream.
            InputStream stream = new BufferedInputStream(con.getInputStream());


            // Java.io.BufferedReader class reads text from a character-input stream, buffering
            // characters so as to provide for the efficient reading of sequence of characters
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));


            // StringBuilder that we append each line of the response to the StringBuilder
            StringBuilder builder = new StringBuilder();


            // The reason we use a String is so that in case we don’t get a response back,
            // we can account for that.
            String input;


            // A String containing the contents of the line, not including
            // any line-termination characters, or null if the end of the stream has been reached
            // Repeat operation if bufferReader can't read next line, then finish while loop
            while ((input = bufferedReader.readLine()) != null)
            {
                // Filling builder with input
                builder.append(input);
            }


            // Converting StringBuilder to JSONObject
            JSONObject jsonObject = new JSONObject(builder.toString());

            Log.i(TAG, "doInBackground: " + jsonObject.toString());
            response = jsonObject.toString();


            // Closing connection
            con.disconnect();

        } catch (Exception e)
        {
            // Handles exeption of readLine() and JSONObject if so, then printStackTrace
            e.printStackTrace();
        }

        // Returns String value
        return response;
    }

    @Override
    public void onPostExecute(String s)
    {
        super.onPostExecute(s);

        switch (context.getClass().getSimpleName())
        {
            case "MainActivity":
                new MainActivityInflater(context, listView, s);
                break;
            case "DietActivity":
                new DietActivityInflater(activity, context, listView, s);
                break;
            case "TrainingActivity":
                new TrainingActivityInflater(activity, context, listView, s);
                break;
            case "DietProductSearchActivity":
                new DietProductSearchActivityInflater(context, listView, s);
                break;
            case "DietProductDetailsActivity":
                new DietProductDetailsActivityInflater(context, listView, s);
                break;
            case "CardioDetailsActivity":
                new CardioDetailsActivityInflater(context, s);
                break;
        }
        Log.d(TAG, "onPostExecute() called with: s = [" + s + "]");
    }

}
