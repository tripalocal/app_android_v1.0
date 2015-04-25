package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tripalocal.com.au.tripalocalbeta.R;

public class ExperienceDetailFragment extends Fragment{

    public ExperienceDetailFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_experience_detail,container,false);
        return view;
    }
}