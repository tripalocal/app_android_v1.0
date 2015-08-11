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
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

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
                "yyyy/MM/dd/HH/mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getUTCTime(String dateTimeString){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/HH/mm", Locale.getDefault());
        Date oldDate=new Date();
        try {
            oldDate = dateFormat.parse(dateTimeString);
        }catch (Exception e){

        }
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(oldDate);
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

    public static HashMap<String,String> getProfile(String user_id)
    {
        final HashMap<String,String> map=new HashMap<String,String>();
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

        apiService.getPublicProfile(user_id, new Callback<Profile_result>() {
            @Override
            public void success(Profile_result result, Response response) {
                GeneralHelper.closeLoadingProgress();
                map.put("name", result.getFirst_name() + " " + result.getLast_name());
                map.put("image", result.getImage());
                System.out.println("retrieve profile successfully" + result.getFirst_name() + result.getImage() + result.getLast_name() + "end");
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                System.out.println("ERROR MYTRIP :" + error + "\n Tooken is "
                        + HomeActivity.getCurrent_user().getLogin_token());
            }
        });
        return map;
    }


    public static String getTimeClp(String dateTime){
        String datetimeR_arr[]=dateTime.split("/");
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd/HH/mm", Locale.getDefault());
        String datetimeC_arr[]= dateFormat.format(new Date()).split("/");
        if(Integer.parseInt(datetimeC_arr[0])>Integer.parseInt(datetimeR_arr[0])){
            int gap=Integer.parseInt(datetimeC_arr[0])-Integer.parseInt(datetimeR_arr[0]);
            return gap+" "+HomeActivity.getHome_context().getResources().getString(R.string.msg_years_ago);
        }
        if(Integer.parseInt(datetimeC_arr[1])>Integer.parseInt(datetimeR_arr[1])){
            int gap=Integer.parseInt(datetimeC_arr[1])-Integer.parseInt(datetimeR_arr[1]);
            return gap+" "+HomeActivity.getHome_context().getResources().getString(R.string.msg_months_ago);
        }
        if(Integer.parseInt(datetimeC_arr[2])>Integer.parseInt(datetimeR_arr[2])){
            int gap=Integer.parseInt(datetimeC_arr[2])-Integer.parseInt(datetimeR_arr[2]);
            return gap+" "+HomeActivity.getHome_context().getResources().getString(R.string.msg_days_ago);
        }
        if(Integer.parseInt(datetimeC_arr[3])>Integer.parseInt(datetimeR_arr[3])){
            int gap=Integer.parseInt(datetimeC_arr[3])-Integer.parseInt(datetimeR_arr[3]);
            return gap+" "+HomeActivity.getHome_context().getResources().getString(R.string.msg_hours_ago);
        }
        if(Integer.parseInt(datetimeC_arr[4])>Integer.parseInt(datetimeR_arr[4])){
            int gap=Integer.parseInt(datetimeC_arr[4])-Integer.parseInt(datetimeR_arr[4]);
            return gap+" "+HomeActivity.getHome_context().getResources().getString(R.string.msg_mins_ago);
        }
        return HomeActivity.getHome_context().getResources().getString(R.string.msg_just_now);

    }


}
