package com.brus5.lukaszkrawczak.fitx.Async;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This delivers informations about connection to MySQL server
 * <p>
 * Created by Łukasz Krawczak
 */

public class Connected extends AsyncTask<Boolean, Boolean, Boolean>
{
    String strUrl = "http://justfitx.xyz/Utils/isConnected.php";

    // Invoked on the background thread
    @Override
    public Boolean doInBackground(Boolean... booleans)
    {

        // isConnected is false by default
        boolean isConnected = false;

        try
        {
            // new url connection handled with exeption
            URL url = new URL(strUrl);

            // url object has method with open url connection
            // and returns object of (HttpURLConnection)
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            // Creates a BufferedInputStream and saves its argument, the input stream in,
            // for later use.
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


            // Getting Boolean value from JSONObject
            isConnected = jsonObject.getBoolean("connected");


            // Closing connection
            con.disconnect();

        } catch (IOException | JSONException e)
        {
            // Handles exeption of readLine() and JSONObject if so, then printStackTrace
            e.printStackTrace();
        }


        // Returns boolean value of connection status
        return isConnected;


    }

}
