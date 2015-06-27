package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tripalocal.com.au.tripalocalbeta.R;

//import android

public class PhoneregisterActivity extends AppCompatActivity {
    public static final String PREFS_NAME_L = "TPPrefs_L";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
        if(settings_l.getBoolean("login", false)){
            Intent intent =new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
//        getActionBar().hide();
        setContentView(R.layout.activity_phoneregister);
//        if(checkFirstTime()){
//            Intent intent=new Intent(this,HomeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }

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
