package tripalocal.com.au.tripalocalbeta.Views;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class ExpDetailActivity extends AppCompatActivity {

    public static int position;
    public static final String PREFS_NAME_L = "TPPrefs_L";

    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                ToastHelper.shortToast("Searched with " + query);
            }else{
                position = intent.getIntExtra(INT_EXTRA,0);
            }
        }
        setContentView(R.layout.activity_exp_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exp_detail, menu);
        if(HomeActivity.getCurrent_user().isLoggedin()){
            menu.findItem(R.id.action_login).setTitle(getResources().getString(R.string.logout));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if(HomeActivity.getCurrent_user().isLoggedin()){
                HomeActivity.getCurrent_user().setLogin_token(null);
                HomeActivity.getCurrent_user().setLoggedin(false);
                HomeActivity.setAccessToken(null);
                SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.clear();
                editor_l.apply();
                HomeActivity.tpDrawer.invalidate();
                ToastHelper.shortToast(getResources().getString(R.string.logged_out));
            }else
            getSupportFragmentManager().beginTransaction().addToBackStack("login")
                    .replace(R.id.detail_frag_container,new LoginFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
