package com.brus5.lukaszkrawczak.fitx.register.search;

public interface PasswordListener
{
    void firstPassword(boolean isValid);
    void secondPassword(boolean isValid);
    void callBack(String result);
}
