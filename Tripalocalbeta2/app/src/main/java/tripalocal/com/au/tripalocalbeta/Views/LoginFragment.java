package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
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

    private CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                HomeActivity.setAccessToken(loginResult.getAccessToken());
                HomeActivity.setCurrent_userid("9900"); //id for FB login
                HomeActivity.getCurrent_user().setLoggedin(true);
                Intent intent = new Intent(HomeActivity.getHome_context(), HomeActivity.class);
                startActivity(intent);
<<<<<<< HEAD
                ToastHelper.longToast("FB Login success");
=======
                /*View nav_view = getActivity().findViewById(R.id.nav_drawer);
                nav_view.invalidate();*/
                ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_fb_login_success));
>>>>>>> origin/master
                HomeActivity.tpDrawer.openDrawer(GravityCompat.START);
            }

            @Override
            public void onCancel() {
                ToastHelper.warnToast(getActivity().getResources().getString(R.string.toast_fb_login_cancel));
                HomeActivity.getCurrent_user().setLoggedin(false);
            }

            @Override
            public void onError(FacebookException exception) {
                ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_fb_login_failure));
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
                FragHelper.addReplace(getActivity().getSupportFragmentManager(), new SignUpFragment());
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
                    HomeActivity.setCurrent_userid(result.getUser_id());
                    HomeActivity.getCurrent_user().setLoggedin(true);
                    System.out.println("result = [" + result + "], response = [" + response + "]");
<<<<<<< HEAD
                    /*Intent intent = new Intent(HomeActivity.getHome_context(), HomeActivity.class);
                    startActivity(intent);*/
                    ToastHelper.longToast("log in success");
                    getActivity().onBackPressed();
                    //HomeActivity.tpDrawer.openDrawer(GravityCompat.START);
=======
                    Intent intent = new Intent(HomeActivity.getHome_context(), HomeActivity.class);
                    startActivity(intent);
                    ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_login_success));
                    HomeActivity.tpDrawer.openDrawer(GravityCompat.START);
>>>>>>> origin/master
                }

                @Override
                public void failure(RetrofitError error) {
                    ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_login_failure));
                    System.out.println("error = [" + error + "]");
                    HomeActivity.getCurrent_user().setLoggedin(false);
                }
            });
    }
}
