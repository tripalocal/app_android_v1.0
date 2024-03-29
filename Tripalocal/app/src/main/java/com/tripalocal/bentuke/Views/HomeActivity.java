package com.tripalocal.bentuke.Views;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
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
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

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
    public static boolean updatedWishMap=false;
    private static boolean doubleClick=false;
    public static String user_email="";
    public static Activity homeActivity;
    private SearchView searchView;
    private Menu menu;
    //public static boolean
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
                //System.out.println("check token on home activity" + getCurrent_user().getLogin_token());
                editor_l.putString("user_id", getCurrent_user().getUser_id());
                editor_l.putBoolean("login", true);
                editor_l.apply();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//Tripalocal.updatedChatList.add("test");

        super.onCreate(savedInstanceState);
        MobclickAgent.updateOnlineConfig(this);
        AnalyticsConfig.enableEncrypt(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
            if(getCurrent_user().getUser_id()==null){
                HomeActivity.getCurrent_user().setLogin_token(null);
                HomeActivity.getCurrent_user().setLoggedin(false);
                HomeActivity.getCurrent_user().setUser_id(null);
                HomeActivity.setAccessToken(null);
                 settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.clear();
                editor_l.apply();
                HomeActivity.login_flag = true;
                invalidateOptionsMenu();
                MessageSerivice.isRunning = false;
                MessageSerivice.connection.disconnect();
            }

        }

        if(poi_data == null || db_poi_data == null){
            poi_data = getResources().getStringArray(R.array.poi_array);
            db_poi_data = getResources().getStringArray(R.array.db_cities_array);
        }
        home_context = getApplicationContext();
        frag_manager = getSupportFragmentManager();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(layout.activity_home);

//if(login_flag){
        getSupportFragmentManager().beginTransaction().add(R.id.nav_drawer_container, new NavigationFragment()).commit();
//login_flag = false;
//}
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


//addMixPanelData();
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
            //System.out.println("System login token is " + HomeActivity.getCurrent_user().getLogin_token());
            //RetrieveWishListMap();
            getExperienceMap();
        }
        GeneralHelper.getCurrencyRate();
        identifyExtra();
        //start service for message
        //System.out.println("Date time showing here : "+GeneralHelper.getLocalTime("2015/11/08/06/42"));
    }


    @Override
    protected void onResume() {
        //System.out.println("onresume");
        //System.out.println("ArrayList count: " + Tripalocal.updatedChatList.size());
        if(menu_ref!=null) {
            searchView = getSearchView(menu_ref);
            searchView.clearFocus();
            searchView.setFocusable(false);
        }
        super.onResume();
       
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
            //getProfile();
        }
        //System.out.println("oncreate and tooken is " + getAccessToken());
        if(checkLogin()){
            //System.out.println(HomeActivity.getCurrent_user().getUser_id()+"test login toke login token is "+HomeActivity.getCurrent_user().getLogin_token());
            //RetrieveWishListMap();
            getExperienceMap();
        }
        MobclickAgent.onResume(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        if(menu_ref == null)
            menu_ref= menu;

        searchView =  getSearchView(menu);
        searchView.setFocusable(false);
        searchView.clearFocus();
        searchView.onActionViewCollapsed();

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
                    temp[1] = poi_data[i].split(":")[0];
                    cursor.addRow(temp);
                }
                //String passing_data_temp[]=poi_data;
                //String passing_data[]=new String[];
                ArrayList<String> output=new ArrayList<String>();
                for(String i :poi_data){
                    if(!output.contains(i)){
                        output.add(i);
                        //System.out.println("city " + output);
                    }
                }

                String passingdata[]=output.toArray(new String[output.size()]);
                //System.out.println("query text is "+newText+"\n size is "+output.size());
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
        if(fragment_t instanceof ExperiencesListFragment) {
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
            //HomeActivity.getCurrent_user().setLogin_token(null);
            //HomeActivity.getCurrent_user().setLoggedin(false);
            //HomeActivity.getCurrent_user().setUser_id(null);
            //HomeActivity.setAccessToken(null);
            //SharedPFsreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
            //SharedPreferences.Editor editor_l = settings_l.edit();
            //editor_l.clear();
            //editor_l.apply();
            //HomeActivity.login_flag = true;
            //invalidateOptionsMenu();
            //MessageSerivice.isRunning=false;
            //MessageSerivice.connection.disconnect();
            //ExperiencesListFragment.rv.getAdapter().notifyDataSetChanged();
            //ToastHelper.shortToast(getResources().getString(R.string.logged_out));
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
            //    new AlertDialog.Builder(this)
            //    .setMessage(getApplicationContext().getResources().getString(R.string.dialog_exit_app))
            //    .setPositiveButton(getApplicationContext().getResources().getString(R.string.dialog_option_yes), new DialogInterface.OnClickListener() {
            //public void onClick(DialogInterface dialog, int which) {
            //    Intent intent = new Intent(Intent.ACTION_MAIN);
            //    intent.addCategory(Intent.CATEGORY_HOME);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
            //    startActivity(intent);
            //    MobclickAgent.onKillProcess(getApplicationContext());
            //    finish();
            //    System.exit(0);
            //}
            //    })
            //    .setNegativeButton(getApplicationContext().getResources().getString(R.string.dialog_option_no), new DialogInterface.OnClickListener() {
            //public void onClick(DialogInterface dialog, int which) {
            //}
            //    })
            //    .show();
            if (doubleClick) {
                finish();

                System.exit(0);
                return;
            }

            this.doubleClick = true;
            //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            ToastHelper.shortToast(getResources().getString(R.string.double_click_exit));
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleClick=false;
                }
            }, 2000);
        }else if(fragment_t instanceof NoMsgFragment){
            Fragment home_fragment = new HomeActivityFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_fragment).addToBackStack("home").commit();
        }else{
            super.onBackPressed();
        }
    }

    public void onPause() {
        super.onPause();
        saveData();
        MobclickAgent.onPause(this);
    }

    public void getExperienceMap() {
        if (!updatedWishMap) {
            updatedWishMap = true;
            final String tooken = HomeActivity.getCurrent_user().getLogin_token();
            RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + tooken);
                    }
                })
                .build();

            ApiService apiService = restAdapter.create(ApiService.class);
            apiService.RetrieveWishList(new Callback<ArrayList<Experience>>() {
                @Override
                public void success(ArrayList<Experience> experiences,Response response) {
                    HomeActivity.wish_map.clear();
                    for (Experience exp : experiences) {
                        HomeActivity.wish_map.put(exp.getId() + "", exp);
                        //System.out.println("experience photo "+exp.getPhoto_url());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    public  boolean identifyExtra(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int commnad=extras.getInt(GeneralHelper.login_fragment_extra);
            Fragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();

            return true;
        }
        return false;
    }
}