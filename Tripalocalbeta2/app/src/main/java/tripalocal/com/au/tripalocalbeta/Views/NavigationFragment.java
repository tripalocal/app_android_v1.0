package tripalocal.com.au.tripalocalbeta.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tripalocal.com.au.tripalocalbeta.R;



public class NavigationFragment extends Fragment {


    public static NavigationFragment newInstance(String param1, String param2) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        Button loginBtn;

        if(HomeActivity.getCurrent_user().isLoggedin())
        view = inflater.inflate(R.layout.fragment_navigation, container, false);
        else
        {
            view = inflater.inflate(R.layout.default_navigation, container, false);
            loginBtn = (Button) view.findViewById(R.id.nav_normal_login);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity.callLoginFrag();
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.END);
                }
            });
        }


        return view;
    }
}
