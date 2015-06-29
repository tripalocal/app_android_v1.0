package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

import tripalocal.com.au.tripalocalbeta.R;

public class SlideShowActivtiy extends AppCompatActivity {

RelativeLayout background_layout;
    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("oncreate go thererere");

        getSupportActionBar().hide();
        if(checkFirstTime()){
            Intent intent =new Intent(getApplicationContext(), PhoneregisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {

            setContentView(R.layout.activity_slideshow);
//            background_layout = (RelativeLayout) findViewById(R.id.background_layout);
            new CountDownTimer(2000, 1000) {
                int sdk = android.os.Build.VERSION.SDK_INT;
                int count = -1;
                int drawArr[] = new int[]{R.drawable.slide01, R.drawable.slide02, R.drawable.slide03};

                public void onTick(long millisUntilFinished) {
//                    count++;
//                    int id = drawArr[count];
//                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                        background_layout.setBackgroundDrawable(getResources().getDrawable(id));
//                    } else {
//                        background_layout.setBackground(getResources().getDrawable(id));
//                    }
                    //System.out.println(count + "");
                }

                public void onFinish() {
                    Intent intent = new Intent(getApplicationContext(), PhoneregisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }.start();
        }

    }


    public Boolean checkFirstTime(){
        String restoredText = PreferenceManager.getDefaultSharedPreferences(this).getString("firsttime", null);
        //System.out.println("record text:" + restoredText);
        if (restoredText == null) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("firsttime","no").apply();

            return false;
        }else{
            return true;
        }

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }





}
