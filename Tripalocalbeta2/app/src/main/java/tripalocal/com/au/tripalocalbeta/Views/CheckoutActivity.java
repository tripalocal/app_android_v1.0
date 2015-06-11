package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class CheckoutActivity extends AppCompatActivity {

      public static Experience_Detail experience_to_book;
      public static int position = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null){
           position =getIntent().getIntExtra(INT_EXTRA,0);
        }
        setContentView(R.layout.activity_checkout);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FF9933CC'>Booking Details </font>"));
    }
//#FF5D5D5D -- grey


}
