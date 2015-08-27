package com.tripalocal.bentuke.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tripalocal.bentuke.Services.MessageSerivice;
import com.umeng.analytics.MobclickAgent;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.exp_detail.Experience_Detail;

import static com.tripalocal.bentuke.adapters.ExperienceListAdapter.INT_EXTRA;

public class CheckoutActivity extends AppCompatActivity {

    public static Experience_Detail experience_to_book;
    public static int position = 999;
    public static String guest="";
    public static String date="";
    public static String time="";
    public static String coupon="";
//    public static String price="";
    public static String price_label_1="";
    public static String price_label_2="";
    public static String total_price="";
    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.title_activity_checkout));
        if (getIntent() != null ) {
            int temp = getIntent().getIntExtra(INT_EXTRA, 0);
            if(temp!=0){
                position=temp;
            }
        }
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);

        setContentView(R.layout.activity_checkout);

        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FF9933CC'>Booking Details </font>"));
    }


    //#FF5D5D5D -- grey
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
        if (HomeActivity.getCurrent_user().isLoggedin()) {
            menu.findItem(R.id.action_login).setTitle(getResources().getString(R.string.logout));
        }
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if (HomeActivity.getCurrent_user().isLoggedin()) {
                HomeActivity.getCurrent_user().setLogin_token(null);
                HomeActivity.getCurrent_user().setLoggedin(false);
                HomeActivity.getCurrent_user().setUser_id(null);
                HomeActivity.setAccessToken(null);
                SharedPreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.clear();
                editor_l.apply();
                HomeActivity.login_flag = true;
                invalidateOptionsMenu();
                MessageSerivice.isRunning=false;
                MessageSerivice.connection.disconnect();
//                ExperiencesListFragment.rv.getAdapter().notifyDataSetChanged();
                ToastHelper.shortToast(getResources().getString(R.string.logged_out));
            } else
                getSupportFragmentManager().beginTransaction().replace(R.id.checkout_fragment_container, new LoginFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
