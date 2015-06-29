package tripalocal.com.au.tripalocalbeta.models;

import android.app.Application;

/**
 * Created by YiHan on 2015/6/15.
 */
public class Tripalocal extends Application {

    private static String serverUrl = "https://www.tripalocal.com/cn/";

    public static String getServerUrl() {
        return serverUrl;
    }

    public static String getFullName(String firstName, String lastName)
    {
        if(firstName.matches("^[\\u0000-\\u0080]+$") && lastName.matches("^[\\u0000-\\u0080]+$"))
        {
            return firstName + " " + lastName.substring(0, 1) + ".";
        }
        else
        {
            return lastName + firstName;
        }
    }
}
