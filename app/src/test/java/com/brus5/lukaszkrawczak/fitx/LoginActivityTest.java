package com.brus5.lukaszkrawczak.fitx;

import com.brus5.lukaszkrawczak.fitx.Login.LoginActivity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lukaszkrawczak on 25.05.2018.
 */
public class LoginActivityTest {
    @Test
    public void getUserName() throws Exception {

        int inputLenght;

        LoginActivity user = new LoginActivity();
        user.setUserName("12");

//        assertEquals(2,charLenght(user.getUserName()));
        assertNotNull(user.getUserName());

    }
    public int charLenght(String string){
//        if (string.length()<1) return 1;
        return 3;
    }
    @Test
    public void getUserPassword() throws Exception {
    }

}