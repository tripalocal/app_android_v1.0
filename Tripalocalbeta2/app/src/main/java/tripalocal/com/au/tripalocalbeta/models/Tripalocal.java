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
}
