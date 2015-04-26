package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Request;

public class LoginFragment extends Fragment {

    private CallbackManager callbackManager;
    private static Boolean login_status = false;

    public static Boolean isLogin_status() {
        return login_status;
    }

    public static void setLogin_status(Boolean login_status) {
        LoginFragment.login_status = login_status;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ToastHelper.longToast("FB Login success");
                setLogin_status(true);
            }

            @Override
            public void onCancel() {
                ToastHelper.warnToast("FB Login cancelled");
                setLogin_status(false);
            }

            @Override
            public void onError(FacebookException exception) {
                ToastHelper.errorToast("FB Login error!");
                setLogin_status(false);
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
         RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("https://www.tripalocal.com")
                    .build();
            ApiService apiService = restAdapter.create(ApiService.class);
            Login_Request log_req = new Login_Request("ravnav44@gmail.com" , "omegastar");
            Gson gson = new Gson();
            //String log_json = gson.toJson(log_req);
        //String log_json = "{ \"email\" : \"ravnav44@gmail.com\",\"password\":\"omegastar\" }";
            //apiService.login_user(log_json , new Callback<String>() {
            apiService.loginUser("ravnav44@gmail.com", "omegastar", new Callback<Login_Result>() {
                @Override
                public void success(Login_Result result, Response response) {
                    ToastHelper.shortToast("log in success");
                    System.out.println("result = [" + result + "], response = [" + response + "]");
                }

                @Override
                public void failure(RetrofitError error) {
                    ToastHelper.errorToast("log in failed");
                    System.out.println("error = [" + error + "]");
                }
            });
    }
}
