package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripalocal.bentuke.R;

/**
 * Created by user on 23/06/2015.
 */
public class TestFragment extends Fragment {



    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

           View view = inflater.inflate(R.layout.fragment_test, container, false);


        return view;
    }
}
