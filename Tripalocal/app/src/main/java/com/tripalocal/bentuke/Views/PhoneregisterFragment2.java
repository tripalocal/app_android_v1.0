package com.tripalocal.bentuke.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.umeng.analytics.MobclickAgent;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.SignupRequest;

public class PhoneregisterFragment2 extends Fragment {


    Button confirm_btn;
    EditText last_name, first_name, password_1, password_2, email;
    public static boolean cancelled = false;

    public PhoneregisterFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister_2, container, false);

        initView(view);
        initControllers();
        cancelled = false;

        return view;
    }

    public void invisibleButton(Boolean visible) {

    }

    public void initView(View view) {
        confirm_btn = (Button) view.findViewById(R.id.confirm_btn);
        last_name = (EditText) view.findViewById(R.id.last_name_edit);
        first_name = (EditText) view.findViewById(R.id.first_name_edit);
        password_1 = (EditText) view.findViewById(R.id.password_edit_1);
        password_2 = (EditText) view.findViewById(R.id.password_edit_2);
        email = (EditText) view.findViewById(R.id.email_edit);
    }

    public void initControllers() {
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationInput()) {
                    signupUser();
                }
            }
        });
    }


    public void signupUser() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                    }
                })
                .build();
        GeneralHelper.addMixPanelData(getActivity(), getActivity().getString(R.string.mixpanel_event_signup));

        ApiService apiService = restAdapter.create(ApiService.class);
       final String email_s=email.getText().toString();
       final String password_s=password_1.getText().toString();
        String first_name_s=first_name.getText().toString();
        String last_name_s=last_name.getText().toString();
        if( GeneralHelper.EmptyCheck(new String[]{email_s,password_s,first_name_s,last_name_s})

                &&GeneralHelper.validateEmail(email_s) && GeneralHelper.validatePwd(password_s)
                ) {
            GeneralHelper.showLoadingProgress(getActivity());
            apiService.signupUser(new SignupRequest(email_s, password_s, first_name_s, last_name_s, PhoneregisterActivity2.phone_no), new Callback<Login_Result>() {
                @Override
                public void success(Login_Result result, Response response) {
                    GeneralHelper.closeLoadingProgress();
                    final Login_Result result1 = result;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MsgHelper.registerUserXMPP(result1.getUser_id());//need id here
                            //System.out.println("running here" + result1.getUser_id());
                            HomeActivity.user_id = result1.getUser_id();

                        }
                    }).start();
                    loginUser(email_s, password_s);

                }

                @Override
                public void failure(RetrofitError error) {
                    GeneralHelper.closeLoadingProgress();
                    ToastHelper.errorToast(getResources().getString(R.string.toast_signup_failure), getActivity());

                }
            });
        }
    }

    public void loginUser(String username,String pwd){
        GeneralHelper.addMixPanelData(getActivity(), getActivity().getString(R.string.mixpanel_event_signin));

        GeneralHelper.showLoadingProgress(getActivity());
//        ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_contacting));
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        GeneralHelper.recordEmail(username);
        apiService.loginUser(username, pwd, new Callback<Login_Result>() {
            @Override
            public void success(Login_Result result, Response response) {
                try {
                    Thread.sleep(5000);

                    if (!cancelled) {
                        //set login
                        HomeActivity.getCurrent_user().setLogin_token(result.getToken());
                        HomeActivity.getCurrent_user().setLoggedin(true);
                        HomeActivity.login_flag = true;
                        HomeActivity.getCurrent_user().setUser_id(result.getUser_id());
                        getActivity().invalidateOptionsMenu();
                        getActivity().onBackPressed();
//                    ToastHelper.longToast();
                        HomeActivity.saveData();

                        if (!MessageSerivice.isRunning) {
                            ChatActivity.sender_id = "";
                            MsgHelper.startMsgSerivice(HomeActivity.getHome_context());
                        }
                        if (HomeActivity.login_ch) {
                            HomeActivity.login_ch = false;
                            Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    GeneralHelper.closeLoadingProgress();

                }
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();
//                ToastHelper.errorToast(log_in_failed);
                ////System.out.println("error = [" + error + "]");
                HomeActivity.getCurrent_user().setLoggedin(false);
            }
        });
    }

    public boolean validationInput() {
        String last_name_s = last_name.getText().toString();
        String first_name_s = first_name.getText().toString();
        String password_1_s = password_1.getText().toString();
        String password_2_s = password_2.getText().toString();
        String email_s = email.getText().toString();
        if (last_name_s.equals("") || first_name_s.equals("") || password_1_s.equals("")
                || password_2_s.equals("") || email_s.equals("")) {
            ToastHelper.errorToast(getResources().getString(R.string.empty_field_error),getActivity());

            return false;
        }
        if(!password_1_s.equals(password_2_s)){
            ToastHelper.errorToast(getResources().getString(R.string.password_error), getActivity());

            return false;
        }


        return true;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_phoneReg2)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_phoneReg2));
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelled = true;
    }
}
