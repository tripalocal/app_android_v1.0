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
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.tripalocal.bentuke.models.User;
import com.umeng.analytics.MobclickAgent;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.FragHelper;
import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.LoginFBRequest;

public class LoginFragment extends Fragment {

    String log_in_success;
    String log_in_failed;
    String fb_log_in_success;
    String fb_log_in_failed;
    String fb_server_log_in_success;
    String fb_server_log_in_failed;

    public static boolean cancelled = false;
    private CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.fragment_login, container, false);
        log_in_success = getActivity().getResources().getString(R.string.toast_login_success);
        log_in_failed = getActivity().getResources().getString(R.string.toast_login_failure);
        fb_log_in_success = getActivity().getResources().getString(R.string.toast_fb_login_success);
        fb_log_in_failed = getActivity().getResources().getString(R.string.toast_fb_login_failure);

        getActivity().setTitle(getResources().getString(R.string.title_login_fragment));
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        if(getResources().getString(R.string.version_language).equals("Chinese")){
                loginButton.setVisibility(View.INVISIBLE);
        }else{
            loginButton.setVisibility(View.VISIBLE);
        }
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                HomeActivity.setAccessToken(loginResult.getAccessToken());
                //HomeActivity.setCurrent_userid("9900"); //id for FB login

                loginFBUser();
            }

            @Override
            public void onCancel() {
                ToastHelper.warnToast(getActivity().getResources().getString(R.string.toast_fb_login_cancel));
                HomeActivity.getCurrent_user().setLoggedin(false);
            }

            @Override
            public void onError(FacebookException exception) {
                ToastHelper.errorToast(fb_log_in_failed);
                HomeActivity.getCurrent_user().setLoggedin(false);
            }
        });

        TextView forgotBtn = (TextView) view.findViewById(R.id.login_forgot_pwd);
//        TextView textView =(TextView)findViewById(R.id.textView);
        forgotBtn.setClickable(true);
        forgotBtn.setMovementMethod(LinkMovementMethod.getInstance());
        String link_string=(getResources().getString(R.string.version_language).equals("Chinese"))?"<a href='https://www.tripalocal.com/cn/accounts/password/reset/'>":
                "<a href='https://www.tripalocal.com/accounts/password/reset/'>";

        String forgot_pwd_text =link_string+getResources().getString(R.string.login_forget_password)+"</a>";
        //System.out.println(forgot_pwd_text);
        forgotBtn.setText(Html.fromHtml(forgot_pwd_text));
//        forgotBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastHelper.warnToast(getActivity().getResources().getString(R.string.toast_service_down));
//            }
//        });
        Button loginBtn = (Button) view.findViewById(R.id.normal_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


        Button sign_up_btn = (Button) view.findViewById(R.id.login_signup);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getResources().getString(R.string.version_language).equals("Chinese")) {
                    Intent intent =new Intent(getActivity().getApplicationContext(), PhoneregisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    FragHelper.addReplace(getActivity().getSupportFragmentManager(), new SignUpFragment());
                }
            }
        });

        cancelled = false;
        getActivity().invalidateOptionsMenu();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginFBUser(){
        GeneralHelper.addMixPanelData(getActivity(), getActivity().getString(R.string.mixpanel_event_signin));

        GeneralHelper.showLoadingProgress(getActivity());
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.loginFBUser(new LoginFBRequest(HomeActivity.getAccessToken().getToken()), new Callback<Login_Result>() {
            @Override
            public void success(Login_Result login_result, Response response) {
                GeneralHelper.closeLoadingProgress();
                InputMethodManager imm = (InputMethodManager)HomeActivity.getHome_context().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow( ((EditText) getActivity().findViewById(R.id.login_password)).getWindowToken(), 0);
                //set login
                HomeActivity.getCurrent_user().setLogin_token(login_result.getToken());
                HomeActivity.getCurrent_user().setLoggedin(true);
                HomeActivity.login_flag = true;
                HomeActivity.getCurrent_user().setUser_id(login_result.getUser_id());
                getActivity().invalidateOptionsMenu();
                getActivity().onBackPressed();
                ToastHelper.longToast(log_in_success);
                HomeActivity.saveData();

                if(!MessageSerivice.isRunning ){
                    ChatActivity.sender_id="";
                    MsgHelper.startMsgSerivice(HomeActivity.getHome_context());
                }
                if (HomeActivity.login_ch) {
                    HomeActivity.login_ch = false;
                    Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                ToastHelper.longToast(fb_log_in_success);
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                ToastHelper.errorToast(fb_log_in_failed);
                HomeActivity.getCurrent_user().setLoggedin(false);
            }
        });
    }


    public void loginUser(){
        GeneralHelper.addMixPanelData(getActivity(), getActivity().getString(R.string.mixpanel_event_signin));

        GeneralHelper.showLoadingProgress(getActivity());
        //ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_contacting));
        RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                    .build();
        ApiService apiService = restAdapter.create(ApiService.class);

        String username = ((EditText) getActivity().findViewById(R.id.login_email)).getText().toString();
        String pwd = ((EditText) getActivity().findViewById(R.id.login_password)).getText().toString();
        GeneralHelper.recordEmail(username);
            apiService.loginUser(username, pwd, new Callback<Login_Result>() {
                @Override
                public void success(Login_Result result, Response response) {
                    GeneralHelper.closeLoadingProgress();
                    if(!cancelled) {
                        InputMethodManager imm = (InputMethodManager)HomeActivity.getHome_context().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow( ((EditText) getActivity().findViewById(R.id.login_password)).getWindowToken(), 0);
                        //set login
                        HomeActivity.getCurrent_user().setLogin_token(result.getToken());
                        HomeActivity.getCurrent_user().setLoggedin(true);
                        HomeActivity.login_flag = true;
                        HomeActivity.getCurrent_user().setUser_id(result.getUser_id());
                        getActivity().invalidateOptionsMenu();
                        getActivity().onBackPressed();
                        ToastHelper.longToast(log_in_success);
                        HomeActivity.saveData();

                        if(!MessageSerivice.isRunning ){
                            ChatActivity.sender_id="";
                            MsgHelper.startMsgSerivice(HomeActivity.getHome_context());
                        }
                        if (HomeActivity.login_ch) {
                            HomeActivity.login_ch = false;
                            Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    GeneralHelper.closeLoadingProgress();
                    ToastHelper.errorToast(log_in_failed);
                    ////System.out.println("error = [" + error + "]");
                    HomeActivity.getCurrent_user().setLoggedin(false);
                }
            });
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_login)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_login));
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelled = true;
    }
}
