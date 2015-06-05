package tripalocal.com.au.tripalocalbeta.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.MyProfile_result;


public class NavigationFragment extends Fragment {

    private static MyProfile_result result;
    private static final String BASE_URL ="https://www.tripalocal.com/images/";

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

        if(HomeActivity.getCurrent_user().isLoggedin()) {
            view = inflater.inflate(R.layout.fragment_navigation, container, false);
            getProfileDetails(view);
        }
        else
        {
            view = inflater.inflate(R.layout.default_navigation, container, false);
            loginBtn = (Button) view.findViewById(R.id.nav_normal_login);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragHelper.replace(HomeActivity.getFrag_manager(), new LoginFragment());
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
            });
        }
        return view;
    }

    private void prepareProfile(View view) {
        CircleImageView profile_img = (CircleImageView) view.findViewById(R.id.nav_profile_image);
        EditText localno = (EditText) view.findViewById(R.id.profile_local_no);
        EditText roamingno = (EditText) view.findViewById(R.id.profile_roaming_no);
        EditText address1 = (EditText) view.findViewById(R.id.profile_Address_line_1);
        EditText address2 = (EditText) view.findViewById(R.id.profile_Address_line_2);
        EditText suburb = (EditText) view.findViewById(R.id.profile_Address_suburb);
        EditText postcode = (EditText) view.findViewById(R.id.profile_Address_postcode);

        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+result.getImage()).fitCenter().into(profile_img);
        localno.setText(result.getPhone_number());
        roamingno.setText("##########");
        address1.setText("##########");
        address2.setText("##########");
        suburb.setText("##########");
        postcode.setText("##########");
    }

    public void getProfileDetails(final View view){

        final String temp_token = "73487d0eb131a6822e08cd74612168cf6e0755dc";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net")// https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        //request.addHeader("Authorization", "Token " + HomeActivity.getCurrent_user().getLogin_token());
                        request.addHeader("Authorization", "Token " + temp_token);
                    }
                })
                .build();

        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getMyProfileDetails(new Callback<MyProfile_result>() {
            @Override
            public void success(MyProfile_result res, Response response) {
                result = res;
                prepareProfile(view);
                ToastHelper.shortToast("success from my profile");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = [" + error + "]");
                ToastHelper.shortToast("error from my profile");
            }
        });
    }


}
