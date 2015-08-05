package com.tripalocal.bentuke.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Frank on 5/08/2015.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    public PagerAdapter(FragmentManager fm ,List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }
    @Override
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
