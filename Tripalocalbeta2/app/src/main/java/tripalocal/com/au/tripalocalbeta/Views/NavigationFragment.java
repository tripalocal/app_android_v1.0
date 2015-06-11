package tripalocal.com.au.tripalocalbeta.Views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
   // private static boolean my_profile = false;

    public static final String BASE_URL ="https://www.tripalocal.com/images/";


    private CircleImageView profile_img;
    private EditText localno;
    private EditText roamingno;
    private TextView hostname;


    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        Button loginBtn;

        if(HomeActivity.getCurrent_user().isLoggedin()) {
            /*if(my_profile)
                view = inflater.inflate(R.layout.myprofile_navigation, container, false);
            else*/
            view = inflater.inflate(R.layout.user_sidebar_navigation, container, false);
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

        profile_img = (CircleImageView) view.findViewById(R.id.nav_drawer_host_profile_image);
        hostname = (TextView) view.findViewById(R.id.nav_drawer_host_name);
       /// if(!my_profile){
            view.findViewById(R.id.nav_home_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }
            });
            view.findViewById(R.id.nav_my_trips_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyTripActivity.class);
                    startActivity(intent);
                }
            });
            view.findViewById(R.id.nav_wishlist_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastHelper.warnToast("Feature coming soon..");
                /*Intent intent = new Intent(getActivity().getApplicationContext(), .class);
                startActivity(intent);*/
                }
            });
            view.findViewById(R.id.nav_my_messages_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastHelper.warnToast("Feature coming soon..");

                }
            });
            view.findViewById(R.id.nav_my_profile_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToastHelper.shortToast("my profile nav");
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawers();
                    //my_profile = true;
                    /*Fragment current_nav = getFragmentManager().findFragmentById(R.id.nav_drawer);
                    getFragmentManager().beginTransaction().replace(R.id.nav_drawer, current_nav).commit();*/
                /*Fragment current_nav = getFragmentManager().findFragmentById(R.id.nav_drawer);
                getFragmentManager().beginTransaction().detach(current_nav).attach(current_nav).commit();*/
                    //drawerLayout.openDrawer(drawerLayout);
                     Intent intent = new Intent(getActivity().getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
                }
            });
            view.findViewById(R.id.nav_my_account_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastHelper.warnToast("Feature coming soon..");
                /*Intent intent = new Intent(getActivity().getApplicationContext(), .class);
                startActivity(intent);*/
                }
            });
      //  }else{
            /*localno = (EditText) view.findViewById(R.id.nav_drawer_local_no);
            localno.setText(result.getPhone_number());
            roamingno = (EditText) view.findViewById(R.id.nav_drawer_roaming_no);
            roamingno.setText(result.getPhone_number());*/
       // }

        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+result.getImage()).fitCenter().into(profile_img);
        hostname.setText(result.getFirst_name() + result.getLast_name());

    }

    public void getProfileDetails(final View view){

        //final String temp_token = "73487d0eb131a6822e08cd74612168cf6e0755dc";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net")// https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + HomeActivity.getCurrent_user().getLogin_token());
                        //request.addHeader("Authorization", "Token " + temp_token);
                    }
                })
                .build();

        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getMyProfileDetails(new Callback<MyProfile_result>() {
            @Override
            public void success(MyProfile_result res, Response response) {
                result = res;
                prepareProfile(view);
                ToastHelper.shortToast("success for profile");
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = [" + error + "]");
                ToastHelper.shortToast("error getting profile");
            }
        });
    }


}
