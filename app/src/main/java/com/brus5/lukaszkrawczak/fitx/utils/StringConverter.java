package com.brus5.lukaszkrawczak.fitx.utils;

public class StringConverter
{
    public static String toUpperFirstLetter(String name) throws StringIndexOutOfBoundsException
    {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}