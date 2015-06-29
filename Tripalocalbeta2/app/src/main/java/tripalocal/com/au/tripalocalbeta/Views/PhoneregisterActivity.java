package tripalocal.com.au.tripalocalbeta.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        if(checkLogin()){
            Intent intent =new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.out.println("go there 1");
        }
        System.out.println("go there 2");
//        getActionBar().hide();
        setContentView(R.layout.activity_phoneregister);
    }

    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(checkLogin()){
            Intent intent =new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            System.out.println("go there 1");
        }
    }
    public void backdoor(View view){
        Intent intent =new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public boolean checkLogin(){
        SharedPreferences settings_l = getSharedPreferences(PREFS_NAME_L, Context.MODE_PRIVATE);
        return settings_l.getBoolean("login", false);
    }
    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage(getApplicationContext().getResources().getString(R.string.dialog_exit_app))
                .setPositiveButton(getApplicationContext().getResources().getString(R.string.dialog_option_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(getApplicationContext().getResources().getString(R.string.dialog_option_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

}
