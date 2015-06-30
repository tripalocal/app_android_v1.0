package tripalocal.com.Views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import tripalocal.com.R;
import tripalocal.com.helpers.ToastHelper;
import android.support.v7.app.*;

import com.umeng.analytics.MobclickAgent;

import static tripalocal.com.adapters.ExperienceListAdapter.INT_EXTRA;

public class PaymentActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
                getSupportFragmentManager().beginTransaction().replace(R.id.payment_content, new LoginFragment()).commit();
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
