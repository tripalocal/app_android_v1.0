package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.helpers.GeneralHelper;

/**
 * Created by Frank on 5/08/2015.
 */
public class SlideShowFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);

        View view=(LinearLayout)inflater.inflate(R.layout.slideshow_fragment2,container,false);
        ImageView img=(ImageView)view.findViewById(R.id.img_slide_2);
        if((getResources().getString(R.string.version_language)).equals("Chinese")) {
            //img.setImageDrawable(getResources().getDrawable(R.drawable.slide2_ch));
            img.setImageBitmap(
            GeneralHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.slide2_cn, 300, 300));
            //img.setImageDrawable(getResources().getDrawable(R.drawable.slide2_en));
        }else{
            img.setImageBitmap(
            GeneralHelper.decodeSampledBitmapFromResource(getResources(), R.drawable.slide2_en, 300, 300));
        }
        return view;
    }
}