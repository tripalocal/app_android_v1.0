package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tripalocal.bentuke.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        return view;

    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_payment)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_payment));
    }
}
