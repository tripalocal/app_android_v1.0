package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.network.SignupRequest;

public class PhoneregisterFragment2 extends Fragment {


    Button confirm_btn;
    EditText last_name,first_name,password_1,password_2,email;
    public PhoneregisterFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister_2, container, false);

        initView(view);
        initControllers();
        return view;
    }

    public void invisibleButton(Boolean visible){

    }

    public void initView(View view){
        confirm_btn=(Button)view.findViewById(R.id.confirm_btn);
        last_name=(EditText)view.findViewById(R.id.last_name_edit);
        first_name=(EditText)view.findViewById(R.id.first_name_edit);
        password_1=(EditText)view.findViewById(R.id.password_edit_1);
        password_2=(EditText)view.findViewById(R.id.password_edit_2);
        email=(EditText)view.findViewById(R.id.email_edit);
    }

    public void initControllers(){
            confirm_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signupUser();
                }
            });
    }


    public void signupUser(){
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
    ApiService apiService = restAdapter.create(ApiService.class);
    /

    apiService.signupUser(new SignupRequest("djaks@gmail.com", "dasdas", "dasda", "dasd","13370883322"), new Callback<Login_Result>() {
        @Override
        public void success(Login_Result result, Response response) {
//            ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_signup_success));
           System.out.println("success");
            System.out.println("s = [" + result.toString() + "], response = [" + response + "]");
            Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }

        @Override
        public void failure(RetrofitError error) {
//            ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_signup_failure));
         System.out.println("failure");
            System.out.println("error = [" + error + "]");
        }
    });
}
}
