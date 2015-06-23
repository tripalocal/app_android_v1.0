package tripalocal.com.au.tripalocalbeta.Views;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

//import android

public class PhoneregisterActivity2 extends AppCompatActivity {

    public static String phone_no="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                ToastHelper.shortToast(getResources().getString(R.string.toast_searched_with) + query);
            }else{
                phone_no = intent.getStringExtra("phone_no");
            }
        }
        setContentView(R.layout.activity_phoneregister_2);

    }

    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    public void backdoor(View view){
        Intent intent =new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
