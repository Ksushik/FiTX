package com.brus5.lukaszkrawczak.fitx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lukaszkrawczak on 25.05.2018.
 */
public class UserLoginActivityTest {
    @Test
    public void getUserName() throws Exception {

        int inputLenght;

        UserLoginActivity user = new UserLoginActivity();
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