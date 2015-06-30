package tripalocal.com.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.R;
import tripalocal.com.helpers.FragHelper;
import tripalocal.com.models.Tripalocal;
import tripalocal.com.models.network.MyProfile_result;

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
