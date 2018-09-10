package com.brus5.lukaszkrawczak.fitx;

public class LoginUtils
{

    /**
     * This method checks if provided string contains '@'
     * symbol.
     *
     * @param email email address
     * @return returns true if contains '@'
     */
    public boolean isEmailContainsAtSymbol(String email)
    {
        return email.contains("@");
    }

    /**
     * This method checks if provided string placement dot is proper.
     *
     * @param email email address
     * @return returns true if dot placement in email address is proper
     */
    public boolean isEmailContainsProperDotPlacement(String email)
    {
        char ch;
        char at = '@';
        char dot = '.';

        for (int i = 0; i < email.length(); i++)
        {
            ch = email.charAt(i);
            // if starts with dot Example: ".example@gmail.com" then false;
            if ( email.startsWith(".") )
            {
                return false;
            }

            // if two dots are next to each other then false;
            if ( (ch == dot) && (email.charAt(i + 1) == dot) )
            {
                return false;
            }

            // if prefix of email ends with "." Example: "example.@gmai.com";
            if (email.substring(0,i).endsWith(".") && email.charAt(i + 1) == at )
            {
                return false;
            }

            if (ch == at)
            {
                // if starts with "." return false. Example: ".gmail.com";
                if (email.substring(i+1).startsWith("."))
                {
                    return false;
                }
                // if ends with "." return false. Example: "example@gmail.com.";
                if (email.substring(i+1).endsWith("."))
                {
                    return false;
                }
                // if prefix ends with ".". Example: example.@gmail.com";
                if (email.substring(i-1).startsWith("."))
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
     * @param string user password
     * @return true if password contains atleast one big letter
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
     * @param string  user password
     * @return true if password contains atleast one digit
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



