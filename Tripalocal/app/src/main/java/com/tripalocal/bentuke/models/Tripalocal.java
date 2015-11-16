package com.tripalocal.bentuke.models;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by YiHan on 2015/6/15.
 */
public class Tripalocal extends Application {

    private static String serverUrl = "https://tripalocal-static.s3.amazonaws.com/";


    public static String getServerUrl() {
        return serverUrl;
    }
    public static String getFullName(String firstName, String lastName)
    {
        if(firstName.matches("^[\\u0000-\\u0080]+$") && lastName.matches("^[\\u0000-\\u0080]+$"))
        {
            return Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1) + " " + lastName.substring(0, 1).toUpperCase() + ".";
        }
        else
        {
            return lastName + firstName;
        }
    }
    public static ArrayList<String> updatedChatList=new ArrayList<String>();




}
