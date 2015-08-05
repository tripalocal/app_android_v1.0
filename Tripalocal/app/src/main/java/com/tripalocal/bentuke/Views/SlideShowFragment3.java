package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tripalocal.bentuke.R;

/**
 * Created by Frank on 5/08/2015.
 */
public class SlideShowFragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return (LinearLayout)inflater.inflate(R.layout.slideshow_fragment3,container,false);
    }
}
