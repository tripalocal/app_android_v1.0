package com.tripalocal.bentuke.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenf_000 on 23/07/2015.
 */
public class GeneralHelper {

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
