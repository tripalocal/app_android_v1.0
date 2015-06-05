package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import tripalocal.com.au.tripalocalbeta.R;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class ExpDetailActivity extends ActionBarActivity {

    public static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            position = intent.getIntExtra(INT_EXTRA,0);
        }
        setContentView(R.layout.activity_exp_detail);

      /*  ImageView searchbtn = (ImageView)findViewById(R.id.searchButton);
        searchbtn.setImageResource(R.drawable.search_s);
        ImageView myprofilebtn = (ImageView) findViewById(R.id.myProfileButton);
        myprofilebtn.setImageResource(R.drawable.myprofile);
        ImageView homebtn = (ImageView)findViewById(R.id.homeButton);
        homebtn.setImageResource(R.drawable.home);
        ImageView mytrip = (ImageView)findViewById(R.id.myTripButton);
        mytrip.setImageResource(R.drawable.mytrip);


        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpDetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpDetailActivity.this, HomeActivity.class);
                intent.putExtra("fragmentNumber", 1);
                startActivity(intent);
            }
        });


        mytrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpDetailActivity.this, HomeActivity.class);
                intent.putExtra("fragmentNumber", 2);
                startActivity(intent);
            }
        });

        myprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpDetailActivity.this, HomeActivity.class);
                intent.putExtra("fragmentNumber", 3);
                startActivity(intent);
            }
        });*/

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
