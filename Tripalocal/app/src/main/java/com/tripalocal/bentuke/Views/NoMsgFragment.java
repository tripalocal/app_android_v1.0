package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tripalocal.bentuke.R;

/**
 * Created by Frank on 4/08/2015.
 */
public class NoMsgFragment extends Fragment {

    Button btn;
    public NoMsgFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_message, container, false);
        btn=(Button)view.findViewById(R.id.explore_exp_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment home_fragment = new HomeActivityFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home_fragment).addToBackStack("home").commit();
            }
        });
        return view;
    }
}
