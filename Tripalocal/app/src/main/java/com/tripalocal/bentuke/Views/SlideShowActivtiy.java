package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.tripalocal.bentuke.adapters.PagerAdapter;
import com.umeng.analytics.MobclickAgent;

import com.tripalocal.bentuke.R;

import java.util.List;
import java.util.Vector;

public class SlideShowActivtiy extends FragmentActivity {

RelativeLayout background_layout;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println("oncreate go thererere");
        setContentView(R.layout.activity_slideshow);
        initialisePaging();

//        getSupportActionBar().hide();

    }
    private void initialisePaging(){
        List<Fragment> fragments=new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, SlideShowFragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, SlideShowFragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, SlideShowFragment3.class.getName()));
        mPagerAdapter=new PagerAdapter(this.getSupportFragmentManager(),fragments);
        ViewPager pager=(ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(mPagerAdapter);
    }

    public void afterSlide(View view){
        if(checkFirstTime()){
            Intent intent;
            if((getResources().getString(R.string.version_language)).equals("Chinese")) {
                 intent = new Intent(getApplicationContext(), PhoneregisterActivity.class);
            }else{
                intent = new Intent(getApplicationContext(), HomeActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
                Intent intent = new Intent(this, HomeActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
