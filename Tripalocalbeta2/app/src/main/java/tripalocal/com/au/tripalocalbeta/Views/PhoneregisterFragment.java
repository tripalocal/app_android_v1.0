package tripalocal.com.au.tripalocalbeta.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;

public class PhoneregisterFragment extends Fragment {
    Button login_btn;


    public PhoneregisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister, container, false);
        Button login_btn = (Button) view.findViewById(R.id.signup_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment signUpFragment = new SignUpFragment();

//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, signUpFragment).commit();
            }
        });
        return view;
    }
}
