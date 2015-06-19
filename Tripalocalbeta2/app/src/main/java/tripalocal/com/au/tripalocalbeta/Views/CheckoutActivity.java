package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class CheckoutActivity extends AppCompatActivity {

    public static Experience_Detail experience_to_book;
    public static int position = 999;
    public static String guest="";
    public static String date="";
    public static String time="";
    public static String price="";
    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null ) {
            int temp = getIntent().getIntExtra(INT_EXTRA, 0);
            if(temp!=0){
                position=temp;
            }
        }
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
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if (HomeActivity.getCurrent_user().isLoggedin()) {
                HomeActivity.getCurrent_user().setLogin_token(null);
                HomeActivity.getCurrent_user().setLoggedin(false);
                HomeActivity.setAccessToken(null);
                SharedPreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.clear();
                editor_l.apply();
                ToastHelper.shortToast(getResources().getString(R.string.logged_out));
            } else
                getSupportFragmentManager().beginTransaction().replace(R.id.checkout_fragment_container, new LoginFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
