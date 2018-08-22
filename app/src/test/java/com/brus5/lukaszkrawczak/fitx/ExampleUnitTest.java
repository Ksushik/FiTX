package com.brus5.lukaszkrawczak.fitx;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{
    LoginUtils loginUtils;

    @Before
    public void start()
    {
        loginUtils = new LoginUtils();
    }

    @Test
    public void addition_isCorrect()
    {
            assertEquals(4, 2 + 2);
    }

    @Test
    public void emailContainsAtSymbol_isCorrect()
    {
        assertEquals(loginUtils.isEmailContainsAtSymbol("lukasz@634gmail.com"),true);
    }

    @Test
    public void passwordContainsOneBigLetter_isCorrect()
    {
        assertEquals(loginUtils.isPasswordContainsBigLetter("Bra"), true);
    }

    @Test
    public void passwordContainsOneDigit_isCorrect()
    {
        assertEquals(loginUtils.isPasswordContainsOneDigit("brus5"),true);
    }

    @Test
    public void emailContainsDot_isCorrect()
    {
        assertEquals(loginUtils.isEmailContainsProperDotPlacement("lukasz634@gmail.com"),true);
    }



}