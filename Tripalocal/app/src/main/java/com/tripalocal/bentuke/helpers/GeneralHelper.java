package com.tripalocal.bentuke.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenf_000 on 23/07/2015.
 */
public class GeneralHelper {

    public static ProgressDialog progress;
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static ProgressDialog showLoadingProgress(Activity activity){
         progress = new ProgressDialog(activity);//MUST BE activity instrad of getApplicationContext
//        progress.setTitle(HomeActivity.getHome_context().getResources().getString(R.string.loading_dialog_title));
//        progress.setMessage(HomeActivity.getHome_context().getResources().getString(R.string.loading_dialog_text));
        try {
            progress.show();
        } catch (WindowManager.BadTokenException e) {

        }
        progress.setCancelable(false);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress.setContentView(R.layout.progressdialog);

        progress.show();
// To dismiss the dialog
        return progress;
    }
    public static void closeLoadingProgress(){
        progress.dismiss();

    }
}
