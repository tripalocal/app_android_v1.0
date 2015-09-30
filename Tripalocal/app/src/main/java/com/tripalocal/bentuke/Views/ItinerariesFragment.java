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


    public ItinerariesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.itineraries_item, container, false);

        getActivity().invalidateOptionsMenu();
        getActivity().setTitle(getResources().getString(R.string.nav_itineraries));

        return view;
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
