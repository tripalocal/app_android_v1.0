package com.tripalocal.bentuke.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.models.network.Profile_result;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private static String prefernece_property="mixpanel_property";
    private static String preference_email="email";
    public static double currency_rate=5;
    public static String login_fragment_extra="loginExtra";
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd/HH/mm/ss/SSSSSS", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getUTCTime(String dateTimeString){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSSSSS", Locale.getDefault());
        Date oldDate=new Date();
        try {
            oldDate = dateFormat.parse(dateTimeString);
        }catch (Exception e){

        }
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(oldDate);
    }


    public static String getLocalTime(String dateTimeString){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSSSSS",Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date oldDate=new Date();
        try {
            oldDate = dateFormat.parse(dateTimeString);
        }catch (Exception e){

        }
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(oldDate);
    }

    public static Date getDateByString(String dateTimeString){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy/MM/dd/HH/mm/ss/SSSSSS", Locale.getDefault());
        Date date=new Date();
        try {
            date = dateFormat.parse(dateTimeString);
        }catch (Exception e){

        }
       return  date;
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
                        request.addHeader("Authorization", "Token " + tooken_en);
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
                "yyyy/MM/dd/HH/mm/ss/SSSSSS", Locale.getDefault());
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

    public static boolean validatePwd(String passwd){
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z]).{8,}";
        if(!passwd.matches(pattern)) {
            ToastHelper.errorToast(HomeActivity.getHome_context().getString(R.string.toast_password_incorrect_format));
        }
        return passwd.matches(pattern);
    }

    public static boolean validateEmail(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        boolean checkMail=m.matches();
        if(!checkMail){
            ToastHelper.errorToast(HomeActivity.getHome_context().getString(R.string.toast_invalid_email));
        }
        return checkMail;
    }

    public static boolean EmptyCheck(String[] fields){
        for(String s : fields){
            if(s.trim().isEmpty()){
                ToastHelper.errorToast(HomeActivity.getHome_context().getString(R.string.toast_empty_field));
                return false;
            }
        }
        return true;
    }


    public static String getEmail(){
        SharedPreferences settings_l = HomeActivity.getHome_context().getSharedPreferences(prefernece_property, Context.MODE_PRIVATE);
        return settings_l.getString(preference_email, null);
    }


    public static void recordEmail(String email){
        SharedPreferences settings_l = HomeActivity.getHome_context().getSharedPreferences(prefernece_property, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings_l.edit();
        editor.clear();
        editor.putString(preference_email,email);
        editor.apply();
    }
    public static void addMixPanelData(Activity ac,String event_name){
        MixpanelAPI mixpanel =MixpanelAPI.getInstance(ac, ac.getResources().getString(R.string.mixpanel_token));
        mixpanel.identify(GeneralHelper.getEmail());
        JSONObject people=new JSONObject();
//        System.out.println("Email "+GeneralHelper.getEmail());
        JSONObject props = new JSONObject();
        try {
            props.put("language", HomeActivity.getHome_context().getString(R.string.version_language));

        }catch (Exception e){
            System.out.println("mixpanel exception here "+e.getMessage().toString());
        }
        mixpanel.getPeople().identify(GeneralHelper.getEmail());
        mixpanel.getPeople().set(people);
        mixpanel.track(event_name, props);


        mixpanel.flush();
        Log.i("mixpanel", "addEvenet success");
    }

    public static void getCurrencyRate(){
       new getCurrencyTask().execute();
    }

    private static class getCurrencyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String from="AUD";
                String to="CNY";
                URL url = new URL("http://quote.yahoo.com/d/quotes.csv?f=l1&s=" + from + to + "=X");
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = reader.readLine();
                if (line.length() > 0) {

//                    String result = String.format("%.2f", Double.parseDouble(line));
                    GeneralHelper.currency_rate=Double.parseDouble(line);
//                    System.out.println("currency now "+GeneralHelper.currency_rate);
                }
                reader.close();
            } catch (IOException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    public static void openApp() {
        String packageName="com.tencent.mm";
        PackageInfo pi;
        try {
            pi = HomeActivity.getHome_context().getPackageManager().getPackageInfo(packageName, 0);
            PackageManager pm = HomeActivity.getHome_context().getPackageManager();
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null ) {
                String packageName2 = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName2, className);

                intent.setComponent(cn);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            }
        }catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
//            Toast.makeText(HomeActivity.getHome_context(), "请先下载支付宝钱包", 0).show();
        }
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }
        return inSampleSize;
    }
}
