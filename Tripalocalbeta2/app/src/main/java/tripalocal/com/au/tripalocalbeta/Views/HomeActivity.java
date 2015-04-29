package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.FacebookSdk;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.User;

import static tripalocal.com.au.tripalocalbeta.R.layout;


public class HomeActivity extends ActionBarActivity {

    private ImageView mytrip;

    private static FragmentManager frag_manager;


    public static FragmentManager getFrag_manager() {
        return frag_manager;
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

    private static User current_user = new User();

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


        frag_manager = getSupportFragmentManager();
        ToastHelper.appln_context = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(layout.activity_home);

        mytrip = (ImageView)findViewById(R.id.myTripButton);
        mytrip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HomeActivity.this, MyTripActivity.class);
                startActivity(intent);
            }
        });
        HomeActivityFragment homeFrag = new HomeActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFrag).commit();
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
            callLoginFrag();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void callLoginFrag(){
        ToastHelper.shortToast("calling login frag");
        FragHelper.addReplace(frag_manager ,new LoginFragment());
    }
}
