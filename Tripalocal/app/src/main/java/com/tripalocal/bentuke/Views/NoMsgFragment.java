package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripalocal.bentuke.R;

/**
 * Created by Frank on 4/08/2015.
 */
public class NoMsgFragment extends Fragment {

    public NoMsgFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_message, container, false);

        return view;

    }

}
