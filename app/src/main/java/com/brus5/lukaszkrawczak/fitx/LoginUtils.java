package com.brus5.lukaszkrawczak.fitx;

public class LoginUtils
{

    /**
     * This method checks if provided string represents a
     * valid email address and returns true if it is.
     *
     * @param email
     * @return
     */
    public boolean isEmailContainsAtSymbol(String email)
    {
        return email.contains("@");
    }

    /**
     * This method checks if provided string placement dot is proper.
     *
     * @param email
     * @return
     */
    public boolean isEmailContainsProperDotPlacement(String email)
    {
        char ch;
        char at = '@';
        char dot = '.';

        for (int i = 0; i < email.length(); i++)
        {
            ch = email.charAt(i);

            if ( email.startsWith(".") ) // if starts with dot Example: ".example@gmail.com" then false;
            {
                return false;
            }

            if ( (ch == dot) && (email.charAt(i + 1) == dot) ) // if two dots are next to each other then false;
            {
                return false;
            }

            if (email.substring(0,i).endsWith(".") && email.charAt(i + 1) == at ) // if prefix of email ends with "." Example: "example.@gmai.com";
            {
                return false;
            }

            if (ch == at)
            {
                if (email.substring(i+1).startsWith(".")) // if starts with "." return false. Example: ".gmail.com";
                {
                    return false;
                }
                if (email.substring(i+1).endsWith(".")) // if ends with "." return false. Example: "example@gmail.com.";
                {
                    return false;
                }
                if (email.substring(i-1).startsWith(".")) // if prefix ends with ".". Example: example.@gmail.com";
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method check if the provided string contains a
     * atleast one big letter.
     *
     * @param string
     * @return
     */
    public boolean isPasswordContainsBigLetter(String string)
    {
        char ch;
        for (int i = 0; i < string.length(); i++)
        {
            ch = string.charAt(i);
            if (Character.isUpperCase(ch))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * This method check if prodivded string contains
     * atleast one digit.
     *
     * @param string
     * @return
     */
    public boolean isPasswordContainsOneDigit(String string)
    {
        char ch;
        for (int i = 0; i < string.length(); i++)
        {
            ch = string.charAt(i);
            if (Character.isDigit(ch))
            {
                return true;
            }
        }
        return false;
    }






}



