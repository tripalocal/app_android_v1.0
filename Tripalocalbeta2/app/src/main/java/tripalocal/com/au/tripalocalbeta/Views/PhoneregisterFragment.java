package tripalocal.com.au.tripalocalbeta.Views;


import android.content.Intent;
import android.os.AsyncTask;
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

import com.bcloud.msg.http.HttpSender;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;

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
    Button login_btn, verfication_btn, confirm_btn;

    EditText phone_no_edit, verfi_code_edit;
    private String verfication_code_confirm = "";

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

    public void invisibleButton(Boolean visible) {
        if (visible) {
            verfi_code_edit.setVisibility(View.INVISIBLE);
            confirm_btn.setVisibility(View.INVISIBLE);
        } else {
            verfi_code_edit.setVisibility(View.VISIBLE);
            confirm_btn.setVisibility(View.VISIBLE);
        }

    }

    public void initView(View view) {
        login_btn = (Button) view.findViewById(R.id.signup_login);
        verfication_btn = (Button) view.findViewById(R.id.verfication_btn);
        phone_no_edit = (EditText) view.findViewById(R.id.phone_no_edit);
        confirm_btn = (Button) view.findViewById(R.id.confirm_btn);
        verfi_code_edit = (EditText) view.findViewById(R.id.verfi_code_edit);
    }

    public void initControllers() {

        invisibleButton(true);
        verfication_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set the text
                if (validationInput()) {
                    verfication_btn.setEnabled(false);
                    new SendMsg().execute("");


                }
            }
        });
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
                String phone_no = phone_no_edit.getText().toString();
                String verification_code = verfi_code_edit.getText().toString();
                if (verification_code.equals(verfication_code_confirm)) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), PhoneregisterActivity2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("phone_no", phone_no_edit.getText().toString());
                    getActivity().getApplicationContext().startActivity(intent);
                }else{
                    ToastHelper.errorToast(getResources().getString(R.string.verfication_code_error), getActivity());
                }

            }
        });
    }


    public boolean validationInput() {
        String phone_no_s = phone_no_edit.getText().toString();
        if (phone_no_s.length() != 11) {
            ToastHelper.errorToast(getResources().getString(R.string.phone_no_error), getActivity());
            return false;
        } else {
            return true;
        }
    }


    public Boolean sendVerfiMsg(String code) {
        Boolean success=true;
        System.out.println("verification code is "+code);
        verfication_code_confirm = code;
        String uri = "http://222.73.117.158/msg/";//应用地址
        String account = "jiekou-clcs-01";//账号
        String pswd = "Tch111888";//密码
        String mobiles = phone_no_edit.getText().toString();//手机号码，多个号码使用","分割
        System.out.println("phone no:" + mobiles);
        String content = getResources().getString(R.string.msg_content).replace("code",code);  //短信内容
        boolean needstatus = true;//是否需要状态报告，需要true，不需要false
        String product = null;//产品ID
        String extno = null;//扩展码

        try {
            System.out.println("start event");
            String returnString = HttpSender.batchSend(uri, account, pswd, mobiles, content, needstatus, product, extno);
            String[] returnCodeArray=(returnString.split("\n")[0]).split(",");
            String returnCode=returnCodeArray[1];

            System.out.println("return1 length:"+returnCode.length()+"return code:"+returnCode);
            if(returnCode.equals("0")){
            }else{
                success=false;
            }
            System.out.println("++++++++++++++");
        } catch (Exception e) {
            e.printStackTrace();
            success=false;
        }
        System.out.println("end event");
        return success;
    }


    private class SendMsg extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            return sendVerfiMsg(getVerificationCode());

        }

        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("task finished");
            if(result){
            invisibleButton(false);

            new CountDownTimer(60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    verfication_btn.setText(getResources().getString(R.string.verfi_countdown) + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    verfication_btn.setText(getResources().getString(R.string.verfication_expire));
                    verfication_btn.setEnabled(true);

                }
            }.start();}
            else{
                ToastHelper.errorToast(getResources().getString(R.string.send_msg_failure),getActivity());
                verfication_btn.setEnabled(true);

            }
        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public String getVerificationCode() {
        String code = "";
        Random ran = new Random();
        for (int i = 0; i < 5; i++) {
            ran.nextInt(10);
            code += ran.nextInt(10);
        }

        return code;


    }


}