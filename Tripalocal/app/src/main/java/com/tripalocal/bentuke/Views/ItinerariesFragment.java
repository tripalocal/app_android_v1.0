package com.tripalocal.bentuke.Views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.FragHelper;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.LoginFBRequest;
import com.umeng.analytics.MobclickAgent;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ItinerariesFragment extends Fragment {

    public TextView three_mel_one,three_mel_all,three_syn_one,three_syn_all,seven_one,seven_all,ten_one,ten_all;
    public static final String INT_EXTRA = "POSITION";

    public ItinerariesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.itineraries_item, container, false);
        initContent(view);
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle(getResources().getString(R.string.nav_itineraries));

        return view;
    }


    public void initContent(View view){
        three_mel_one=(TextView)view.findViewById(R.id.three_mel_one);
        three_mel_all=(TextView)view.findViewById(R.id.three_mel_all);
        three_syn_one=(TextView)view.findViewById(R.id.three_syn_one);
        three_syn_all=(TextView)view.findViewById(R.id.three_syn_all);
        seven_one=(TextView)view.findViewById(R.id.seven_one);
        seven_all=(TextView)view.findViewById(R.id.seven_all);
        ten_one=(TextView)view.findViewById(R.id.ten_one);
        ten_all=(TextView)view.findViewById(R.id.ten_all);

        setActionListener(three_mel_one,651);
        setActionListener(three_mel_all,701);
        setActionListener(three_syn_one,661);
        setActionListener(three_syn_all,711);
        setActionListener(seven_one,681);
        setActionListener(seven_all,731);
        setActionListener(ten_one,691);
        setActionListener(ten_all,771);


    }

    public void setActionListener(TextView text,int exp_id){
        final int id=exp_id;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.getHome_context(), ExpDetailActivity.class);
                intent.putExtra(INT_EXTRA, id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            }
        });

    }
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_login)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_login));
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
