package com.brus5.lukaszkrawczak.fitx;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.brus5.lukaszkrawczak.fitx.Async.Connected;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest
{
    Context appContext;
    Connected conn;

    @Before
    public void start()
    {
        appContext = InstrumentationRegistry.getTargetContext();
        conn = new Connected();
    }


    @Test
    public void useAppContext()
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.brus5.lukaszkrawczak.fitx", appContext.getPackageName());
    }

    @Test
    public void isConnectedToMySQL()
    {
        assertEquals(true, conn.doInBackground());
    }


}
