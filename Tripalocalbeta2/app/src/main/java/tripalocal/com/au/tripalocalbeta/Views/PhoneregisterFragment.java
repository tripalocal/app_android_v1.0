package tripalocal.com.au.tripalocalbeta.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tripalocal.com.au.tripalocalbeta.R;

public class PhoneregisterFragment extends Fragment {


    public PhoneregisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phoneregister, container, false);
        return view;
    }
}
