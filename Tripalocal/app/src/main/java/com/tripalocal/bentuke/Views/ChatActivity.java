package com.tripalocal.bentuke.Views;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.adapters.TPSuggestionsAdapter;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg_detail, menu);
//        if(menu_ref == null)
//            menu_ref= menu;
//        if(getCurrent_user().isLoggedin()){
//            menu.findItem(R.id.action_login).setTitle(getResources().getString(R.string.logout));
//        }
//
//        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                //ToastHelper.shortToast("sugg select "+position);
//                return false;
//            }
//
//            @Override
//            public boolean onSuggestionClick(int position) {
//                //ToastHelper.shortToast("sugg click "+position +" : "+ db_poi_data[position]);
//                Fragment exp_list_frag = new ExperiencesListFragment();
//                Bundle args = new Bundle();
//                args.putInt(ExperienceListAdapter.INT_EXTRA, position);
//                exp_list_frag.setArguments(args);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home_sugg").commit();
//                return false;
//            }
//        });
        return true;
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
