package com.tripalocal.bentuke.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.models.network.Profile_result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

//        progress.show();
// To dismiss the dialog
        return progress;
    }
    public static void closeLoadingProgress(){
        progress.dismiss();
    }

    private void getProfile(Activity activity)
    {
        GeneralHelper.showLoadingProgress(activity);
        final String tooken_en="804db40bac2e17f35932693dd4925b930be6925e";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " +tooken_en);
                    }
                })
                .build();


        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getPublicProfile("8",new Callback<Profile_result>() {
            @Override
            public void success(Profile_result result, Response response) {
                GeneralHelper.closeLoadingProgress();

                System.out.println("retrieve profile successfully"+result.getFirst_name()+result.getImage()+result.getLast_name()+"end");
            }
            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                System.out.println("ERROR MYTRIP :" + error+"\n Tooken is "
                        +HomeActivity.getCurrent_user().getLogin_token());
            }
        });
        GeneralHelper.closeLoadingProgress();

    }
}
