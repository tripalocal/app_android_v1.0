package com.tripalocal.bentuke.Views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.tripalocal.bentuke.models.exp_detail.WishList_Retrieve_Result;
import com.tripalocal.bentuke.models.network.Profile_result;
import com.tripalocal.bentuke.models.network.WishList_update_Request;
import com.tripalocal.bentuke.models.network.Wishlist_Update_Result;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.adapters.TPSuggestionsAdapter;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.Experience;
import com.tripalocal.bentuke.models.User;

import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

import static com.tripalocal.bentuke.R.layout;


public class HomeActivity extends AppCompatActivity {

    private static Context home_context;
    private static FragmentManager frag_manager;
    private static User current_user = new User();
    private static AccessToken accessToken;
    private static Menu menu_ref = null;
    public static DrawerLayout tpDrawer ;
    private ActionBarDrawerToggle tpDrawToggle;
    //HomeActivityFragment homeFrag;
    public static String[] poi_data;
    public static String[] db_poi_data;
    public static HashMap<String, Experience> wish_map = new HashMap<>();
    public static final String PREFS_NAME = "TPPrefs";
    public static final String PREFS_NAME_L = "TPPrefs_L";
    public static boolean login_flag = true;
    public static boolean login_ch=false;
    public static String webViewPage_info="";
    public static XMPPTCPConnection connection;
    public static String user_id;
    public static String user_img;
//    public static boolean
    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        HomeActivity.accessToken = accessToken;
    }

    public static FragmentManager getFrag_manager() {
        return frag_manager;
    }

    public static Context getHome_context() {
        return home_context;
    }

    public static void setHome_context(Context home_context) {
        HomeActivity.home_context = home_context;
    }

    public static void setFrag_manager(FragmentManager frag_manager) {
        HomeActivity.frag_manager = frag_manager;
    }

    public static User getCurrent_user() {
        return current_user;
    }

    public static void setCurrent_user(User current_user) {
        HomeActivity.current_user = current_user;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    public static void saveData() {
        if(wish_map != null){
            SharedPreferences settings = home_context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            editor.putBoolean("empty_check", true);
            Gson gson = new Gson();
            editor.putString("wish_map", gson.toJson(wish_map));
            editor.apply();
        }
        if(getCurrent_user().getLogin_token() != null) {
            SharedPreferences settings_l = home_context.getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
            if(!settings_l.getBoolean("login", false)){
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.putString("token", getCurrent_user().getLogin_token());
                System.out.println("check token on home activity" + getCurrent_user().getLogin_token());
                editor_l.putString("user_id", getCurrent_user().getUser_id());
                editor_l.putBoolean("login", true);
                editor_l.apply();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MobclickAgent.updateOnlineConfig(this);
        AnalyticsConfig.enableEncrypt(true);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(wish_map.isEmpty() && settings.getBoolean("empty_check", false)) {
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<HashMap<String,Experience>>(){}.getType();
            wish_map =  gson.fromJson(settings.getString("wish_map", "null"),type);
            //change here retrieve wish map here

        }

        SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
        if(settings_l.getBoolean("login", false)){
            getCurrent_user().setLogin_token(settings_l.getString("token", null));
            getCurrent_user().setLoggedin(true);
            getCurrent_user().setUser_id(settings_l.getString("user_id",null));
        }

        if(poi_data == null || db_poi_data == null){
            poi_data = getResources().getStringArray(R.array.poi_array);
            db_poi_data = getResources().getStringArray(R.array.db_cities_array);
        }
        home_context = getApplicationContext();
        frag_manager = getSupportFragmentManager();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(layout.activity_home);
//        if(login_flag){
        getSupportFragmentManager().beginTransaction().add(R.id.nav_drawer_container, new NavigationFragment()).commit();
//        login_flag = false;
//        }
        tpDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tpDrawToggle = new ActionBarDrawerToggle(this, tpDrawer, R.string.drawer_open, R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                    if(login_flag){
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_container, new NavigationFragment()).commit();
                        login_flag = false;
                    }
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeActivityFragment()).commit();
        tpDrawer.setDrawerListener(tpDrawToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(!checkFirstTime()){
            Intent intent =new Intent(getApplicationContext(), SlideShowActivtiy.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(!checkLogin() && (getResources().getString(R.string.version_language)).equals("Chinese")){
            Intent intent =new Intent(getApplicationContext(), PhoneregisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(!MessageSerivice.isRunning && checkLogin()){
            ChatActivity.sender_id="";
            MsgHelper.startMsgSerivice(getHome_context());

        }
        if(checkLogin()){
            System.out.println("System login token is "+HomeActivity.getCurrent_user().getLogin_token());
//            RetrieveWishListMap();
        }
        //start service for message
    }

    @Override
    protected void onResume() {
        System.out.println("onresume");

        super.onResume();
        invalidateOptionsMenu();
        if(!checkFirstTime()){
            Intent intent =new Intent(getApplicationContext(), SlideShowActivtiy.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(!checkLogin() && (getResources().getString(R.string.version_language)).equals("Chinese")){
            Intent intent =new Intent(getApplicationContext(), PhoneregisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(!MessageSerivice.isRunning && checkLogin()){
            ChatActivity.sender_id="";
            MsgHelper.startMsgSerivice(getHome_context());
//            getProfile();
        }
        System.out.println("oncreate and tooken is " + getAccessToken());

        MobclickAgent.onResume(this);

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        if(menu_ref == null)
            menu_ref= menu;

        SearchView searchView =  getSearchView(menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastHelper.shortToast(getResources().getString(R.string.toast_search_submitted));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Object[] temp = new Object[]{0, "default"};
                String[] columns = new String[]{"_id", "poi"};
                MatrixCursor cursor = new MatrixCursor(columns);
                for (int i = 0; i < poi_data.length; i++) {
                    temp[0] = i;
                    temp[1] = poi_data[i];
                    cursor.addRow(temp);
                }
                getSearchView(menu).setSuggestionsAdapter(new TPSuggestionsAdapter(getApplicationContext(), cursor, poi_data));
                return true;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                //ToastHelper.shortToast("sugg select "+position);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                //ToastHelper.shortToast("sugg click "+position +" : "+ db_poi_data[position]);
                Fragment exp_list_frag = new ExperiencesListFragment();
                Bundle args = new Bundle();
                args.putInt(ExperienceListAdapter.INT_EXTRA, position);
                exp_list_frag.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home_sugg").commit();
                return false;
            }
        });
        Fragment fragment_t = frag_manager.findFragmentById(R.id.fragment_container);
        if(fragment_t instanceof HomeActivityFragment) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tpDrawToggle.syncState();
    }


    @NonNull
    private SearchView getSearchView(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        return searchView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            if(HomeActivity.getCurrent_user().isLoggedin()){
//                HomeActivity.getCurrent_user().setLogin_token(null);
//                HomeActivity.getCurrent_user().setLoggedin(false);
//                HomeActivity.getCurrent_user().setUser_id(null);
//                HomeActivity.setAccessToken(null);
//                SharedPreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor_l = settings_l.edit();
//                editor_l.clear();
//                editor_l.apply();
//                HomeActivity.login_flag = true;
//                invalidateOptionsMenu();
//                MessageSerivice.isRunning=false;
//                MessageSerivice.connection.disconnect();
////                ExperiencesListFragment.rv.getAdapter().notifyDataSetChanged();
//                ToastHelper.shortToast(getResources().getString(R.string.logged_out));
            }else {
                getSupportFragmentManager().beginTransaction().addToBackStack("login")
                        .replace(R.id.fragment_container, new LoginFragment()).commit();
            }
            return true;
        }
        if (tpDrawToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Boolean checkFirstTime(){
        String restoredText = PreferenceManager.getDefaultSharedPreferences(this).getString("firsttime", null);
        //System.out.println("record text:" + restoredText);
        if (restoredText == null) {
            return false;
        }else{
            return true;
        }

    }

    public boolean checkLogin(){
        SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
        return settings_l.getBoolean("login", false);
    }

    public boolean getloginFragmentExtra(){
        Intent intent = getIntent();
        if(intent != null){
           String loginFra=intent.getStringExtra("login_fragment")+"";
            return intent.getBooleanExtra("login_fragment",false);
        }
        return false;
    }
    @Override
    public void onBackPressed(){
        saveData();
        Fragment fragment_t = frag_manager.findFragmentById(R.id.fragment_container);
        if(fragment_t instanceof HomeActivityFragment) {
            new AlertDialog.Builder(this)
                    .setMessage(getApplicationContext().getResources().getString(R.string.dialog_exit_app))
                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.dialog_option_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                            startActivity(intent);
                            MobclickAgent.onKillProcess(getApplicationContext());
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.dialog_option_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }else{
            super.onBackPressed();
        }
    }

    public void onPause() {
        super.onPause();
        saveData();
        MobclickAgent.onPause(this);
    }




}
