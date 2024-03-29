package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import com.tripalocal.bentuke.helpers.FragHelper;
import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.SignupRequest;

public class SignUpFragment extends Fragment {

    public static boolean cancelled = false;
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.title_signup_fragment));
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Button signupBtn = (Button) view.findViewById(R.id.normal_signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        Button signupLoginBtn = (Button) view.findViewById(R.id.signup_login);
        signupLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FragHelper.replace(getFragmentManager(), new LoginFragment());
            //view = inflater.inflate(R.layout.fragment_login, container, false);
            }
        });
        cancelled = false;
        return view;
    }

    public void signup(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                    }
                })
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        //Login_Request log_req = new Login_Request("ravnav44@gmail.com" , "omegastar");
        // Gson gson = new Gson();
        //String log_json = gson.toJson(log_req);

        EditText edit_email = (EditText) getActivity().findViewById(R.id.signup_email);
        EditText edit_pwd = (EditText) getActivity().findViewById(R.id.signup_password);
        EditText edit_firstname = (EditText) getActivity().findViewById(R.id.signup_firstname);
        EditText edit_lastname = (EditText) getActivity().findViewById(R.id.signup_lastname);

        String email = edit_email.getText().toString();
        String pwd = edit_pwd.getText().toString();
        String first_name = edit_firstname.getText().toString();
        String last_name = edit_lastname.getText().toString();
        GeneralHelper.recordEmail(email);
        GeneralHelper.addMixPanelData(getActivity(), getActivity().getString(R.string.mixpanel_event_signup));

        if(GeneralHelper.EmptyCheck(new String[]{email,pwd,first_name,last_name}) &&
            GeneralHelper.validateEmail(email) && GeneralHelper.validatePwd(pwd))
        {
            GeneralHelper.showLoadingProgress(getActivity());

            apiService.signupUser(new SignupRequest(email, pwd, first_name, last_name), new Callback<Login_Result>() {
                @Override
                public void success(Login_Result result, Response response) {
                    GeneralHelper.closeLoadingProgress();
                    final Login_Result result1 = result;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MsgHelper.registerUserXMPP(result1.getUser_id());//need id here
                            //System.out.println("running here");
                            HomeActivity.user_id = result1.getUser_id();
                        }
                    }).start();

                    if (!cancelled) {
                        ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_signup_success));
                        HomeActivity.getCurrent_user().setLoggedin(true);
                        HomeActivity.getCurrent_user().setLogin_token(result.getToken());
                        HomeActivity.getCurrent_user().setUser_id(result.getUser_id());
                        HomeActivity.saveData();
                        HomeActivity.login_flag = true;
                        Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    GeneralHelper.closeLoadingProgress();
                    ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_signup_failure));
                    //System.out.println("error = [" + error + "]");
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_signup)); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_signup));
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelled = true;
    }
}