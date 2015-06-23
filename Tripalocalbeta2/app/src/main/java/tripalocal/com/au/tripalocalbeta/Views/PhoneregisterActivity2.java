package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tripalocal.com.au.tripalocalbeta.R;

//import android

public class PhoneregisterActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
