package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.network.SignupRequest;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

public class PhoneregisterFragment extends Fragment {
    Button login_btn,verfication_btn,confirm_btn;

    EditText phone_no_edit,verfi_code_edit;
    private String verfication_code_confirm="5213";

    public PhoneregisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister, container, false);

        initView(view);
        initControllers();
        return view;
    }

    public void invisibleButton(Boolean visible){
        if(visible) {
            verfi_code_edit.setVisibility(View.INVISIBLE);
            confirm_btn.setVisibility(View.INVISIBLE);
        }else{
            verfi_code_edit.setVisibility(View.VISIBLE);
            confirm_btn.setVisibility(View.VISIBLE);
        }

    }

    public void initView(View view){
         login_btn = (Button) view.findViewById(R.id.signup_login);
        verfication_btn=(Button)view.findViewById(R.id.verfication_btn);
        phone_no_edit=(EditText)view.findViewById(R.id.phone_no_edit);
        confirm_btn=(Button)view.findViewById(R.id.confirm_btn);
        verfi_code_edit=(EditText)view.findViewById(R.id.verfi_code_edit);
    }

    public void initControllers(){

        invisibleButton(true);
        verfication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleButton(false);
                //set the text
                if(validationInput()) {
                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            verfication_btn.setText(getResources().getString(R.string.verfi_countdown) + millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            verfication_btn.setText(getResources().getString(R.string.verfication_expire));
                        }
                    }.start();
                }
            }
        });
//        verfication_btn.setVisibility(View.INVISIBLE);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.phone_reg_container, new LoginFragment()).commit();
                getFragmentManager().beginTransaction().addToBackStack("login")
                        .replace(R.id.detail_frag_container, new TestFragment()).commit();
                System.out.println("this is asdasa test");
            }
        });

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_no=phone_no_edit.getText().toString();
                String verification_code=verfi_code_edit.getText().toString();
                if(verification_code.equals(verfication_code_confirm)){
                    Intent intent = new Intent(getActivity().getApplicationContext(), PhoneregisterActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phone_no",phone_no_edit.getText().toString());
                    getActivity().getApplicationContext().startActivity(intent);
                }

            }
        });
    }


    public boolean validationInput(){
        String phone_no_s=phone_no_edit.getText().toString();
        if(phone_no_s.length()!=11){
            System.out.println(getResources().getString(R.string.phone_no_error));
//            Toast.makeText(this.getActivity().getApplicationContext(),getResources().getString(R.string.phone_no_error),Toast.LENGTH_LONG).show();
                return false;
        }else{
            return true;
        }
    }


}
