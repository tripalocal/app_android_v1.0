package tripalocal.com.au.tripalocalbeta.Views;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.TPSuggestionsAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Experience;
import tripalocal.com.au.tripalocalbeta.models.User;

import static tripalocal.com.au.tripalocalbeta.R.layout;
import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;


public class HomeActivity extends ActionBarActivity {

    private static Context home_context;
    private static FragmentManager frag_manager;
    private static User current_user = new User();
    private static String current_userid;
    private static AccessToken accessToken;
    private static Menu menu_ref = null;

    HomeActivityFragment homeFrag;
    public static String[] poi_data;
    public static String[] db_poi_data;
    public static HashMap<String, Experience> wish_map = new HashMap<>();
    public static ArrayList<String> wish_list = new ArrayList<>();
    public static final String PREFS_NAME = "TPPrefs";
    public static final String PREFS_NAME_L = "TPPrefs_L";

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        HomeActivity.accessToken = accessToken;
    }

    public static String getCurrent_userid() {
        return current_userid;
    }

    public static void setCurrent_userid(String current_userid) {
        HomeActivity.current_userid = current_userid;
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

    public void saveData() {
        if(wish_list != null && !wish_list.isEmpty()){
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();
            Gson gson = new Gson();
            editor.putString("wish_map", gson.toJson(wish_list));
            /*for(String s : wish_map.keySet()){
                String hashString = gson.toJson(wish_map.get(s));
                editor.putString(s, hashString);
            }*/
            editor.commit();
        }
        if(getCurrent_user().getLogin_token() != null) {
            SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_l = settings_l.edit();
            editor_l.clear();
            editor_l.putString("token", getCurrent_user().getLogin_token());
            editor_l.putBoolean("login", true);
            editor_l.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(wish_list.isEmpty()){
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            //java.lang.reflect.Type type = new TypeToken<HashMap<String, Experience>>(){}.getType();
            java.lang.reflect.Type type = new TypeToken<ArrayList<String>>(){}.getType();
            if(!settings.getBoolean("new", true))
            wish_list =  gson.fromJson(settings.getString("wish_map", "null"),type);
        }

        SharedPreferences settings_l = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(settings_l.getBoolean("login", false)){
            getCurrent_user().setLogin_token(settings_l.getString("token", null));
            getCurrent_user().setLoggedin(true);
        }

        if(poi_data == null || db_poi_data == null){
            poi_data = getResources().getStringArray(R.array.poi_array);
            db_poi_data = getResources().getStringArray(R.array.db_cities_array);
        }

        home_context = getApplicationContext();
        frag_manager = getSupportFragmentManager();
        ToastHelper.appln_context = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(layout.activity_home);
        homeFrag = new HomeActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFrag).commit();
        //0:home, 1:search, 2:mytrip, 3: profile
        if(getIntent().getIntExtra("fragmentNumber",0)==1)
        {
            FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment());
        }
        else if(getIntent().getIntExtra("fragmentNumber",0)==2)
        {
            Intent intent = new Intent(HomeActivity.this, MyTripActivity.class);
            startActivity(intent);
        }
        else if(getIntent().getIntExtra("fragmentNumber",0)==3)
        {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        if(menu_ref == null)
            menu_ref= menu;
        // Associate searchable configuration with the SearchView
        SearchView searchView =  getSearchView(menu);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ToastHelper.shortToast("search submitted");
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
                ToastHelper.shortToast("sugg select "+position);
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                ToastHelper.shortToast("sugg click "+position +" : "+ db_poi_data[position]);
                Intent intent = new Intent(HomeActivity.getHome_context(), ExpListActvity2.class);
                intent.putExtra(INT_EXTRA,position);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    @NonNull
    private SearchView getSearchView(Menu menu) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return searchView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
