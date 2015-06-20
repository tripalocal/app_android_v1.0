package tripalocal.com.au.tripalocalbeta.Views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import tripalocal.com.au.tripalocalbeta.R;
import android.content.Intent;
/**
 * Created by user on 15/06/2015.
 */
public class PaymentSuccessActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_success);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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

    /** Needs to be Modified to suit latest refractoring in activities by @naveen **/
    public void viewtriphisttory(View v){
        System.out.println("this is a test");

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//        drawerLayout.closeDrawers();
//        Fragment my_trip_fragment = new MyTripFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_trip_fragment).addToBackStack("navigation_my_trip").commit();

        getApplicationContext().startActivity(intent);
    }
}
