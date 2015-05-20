package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.User;

import static tripalocal.com.au.tripalocalbeta.R.layout;


public class HomeActivity extends ActionBarActivity {

    private static Context home_context;
    private static FragmentManager frag_manager;
    private static User current_user = new User();
    private static String current_userid;
    private static AccessToken accessToken;
    HomeActivityFragment homeFrag;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else{
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
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
            drawerLayout.openDrawer(R.id.navigation_drawer);
        }

        ImageView searchbtn = (ImageView)findViewById(R.id.searchButton);
        searchbtn.setImageResource(R.drawable.search);
        ImageView myprofilebtn = (ImageView) findViewById(R.id.myProfileButton);
        myprofilebtn.setImageResource(R.drawable.myprofile);
        ImageView homebtn = (ImageView)findViewById(R.id.homeButton);
        homebtn.setImageResource(R.drawable.home_s);
        ImageView mytrip = (ImageView)findViewById(R.id.myTripButton);
        mytrip.setImageResource(R.drawable.mytrip);


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });


        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ExperienceListAdapter.current_city == 1)
                homeFrag.displayListFrag("Sydney");
                else
                    homeFrag.displayListFrag("Melbourne");
                /*if (ExperienceListAdapter.current_city == 0)
                        ToastHelper.longToast("experiences from Melbourne");
                else if(ExperienceListAdapter.current_city == 101)
                    ToastHelper.longToast("Showing Default experiences from Melbourne");
                else
                    ToastHelper.longToast("experiences from Melbourne");
                FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment());*/
            }
        });

        mytrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MyTripActivity.class);
                startActivity(intent);
            }
        });

        myprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(R.id.navigation_drawer);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Context getContextInstance(){
        return home_context;
    }
}
