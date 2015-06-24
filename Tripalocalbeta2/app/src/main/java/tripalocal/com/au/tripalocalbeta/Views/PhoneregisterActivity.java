package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
//import android
import tripalocal.com.au.tripalocalbeta.R;

public class PhoneregisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public Boolean checkFirstTime(){
        SharedPreferences prefs = getSharedPreferences("Baisc", MODE_PRIVATE);
        String restoredText = prefs.getString("firsttime", null);
        if (restoredText == null) {
            SharedPreferences.Editor editor = getSharedPreferences("Basic", MODE_PRIVATE).edit();
            editor.putString("firsttime", "yes");
            editor.commit();
            return false;
        }else{
            return true;
        }

    }
}
