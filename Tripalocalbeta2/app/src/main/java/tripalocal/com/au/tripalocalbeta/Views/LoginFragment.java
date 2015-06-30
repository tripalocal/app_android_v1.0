package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.umeng.analytics.MobclickAgent;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

public class LoginFragment extends Fragment {

    String log_in_success;
    String log_in_failed;
    String fb_log_in_success;
    String fb_log_in_failed;


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
                loginButton.setVisibility(View.GONE);
        }else{
            loginButton.setVisibility(View.VISIBLE);
        }
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                HomeActivity.setAccessToken(loginResult.getAccessToken());
                //HomeActivity.setCurrent_userid("9900"); //id for FB login
                HomeActivity.getCurrent_user().setLoggedin(true);
                getActivity().onBackPressed();
                getActivity().onBackPressed();
                ToastHelper.longToast(fb_log_in_success);
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
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.warnToast(getActivity().getResources().getString(R.string.toast_service_down));
            }
        });
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
                if(HomeActivity.login_ch) {
                    Intent intent =new Intent(getActivity().getApplicationContext(), PhoneregisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    FragHelper.addReplace(getActivity().getSupportFragmentManager(), new SignUpFragment());
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginUser(){
        ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_contacting));
         RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                    .build();
            ApiService apiService = restAdapter.create(ApiService.class);

        String username = ((EditText) getActivity().findViewById(R.id.login_email)).getText().toString();
        String pwd = ((EditText) getActivity().findViewById(R.id.login_password)).getText().toString();

            apiService.loginUser(username, pwd, new Callback<Login_Result>() {
                @Override
                public void success(Login_Result result, Response response) {
                    HomeActivity.getCurrent_user().setLogin_token(result.getToken());
                    //HomeActivity.setCurrent_userid(result.getUser_id());
                    HomeActivity.getCurrent_user().setLoggedin(true);
                    HomeActivity.login_flag = true;
                    //System.out.println("result = [" + result + "], response = [" + response + "]");
                    getActivity().invalidateOptionsMenu();
                    getActivity().onBackPressed();
                    ToastHelper.longToast(log_in_success);
                    HomeActivity.saveData();
                    if(HomeActivity.login_ch){
                        HomeActivity.login_ch=false;
                        Intent intent =new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    ToastHelper.errorToast(log_in_failed);
                    //System.out.println("error = [" + error + "]");
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
}
