package tripalocal.com.au.tripalocalbeta.Views;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class ExpDetailActivity extends AppCompatActivity {

    public static int position;

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
}
