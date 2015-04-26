package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

import static tripalocal.com.au.tripalocalbeta.R.layout;


public class HomeActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastHelper.appln_context = getApplicationContext();
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(layout.activity_home);
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
            ToastHelper.shortToast("menu called");
            FragHelper.addReplace(getSupportFragmentManager(),new LoginFragment());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
