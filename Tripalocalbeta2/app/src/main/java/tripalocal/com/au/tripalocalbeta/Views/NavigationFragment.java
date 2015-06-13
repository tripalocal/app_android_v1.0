package tripalocal.com.au.tripalocalbeta.Views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
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
    TextView tos_txt;
    TextView privacy_txt;
    TextView about_txt;


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
                    FragHelper.addReplace(HomeActivity.getFrag_manager(), new LoginFragment());
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
        tos_txt = (TextView) view.findViewById(R.id.nav_tos_txt);
        tos_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String tos_text_content = "<a href='https://www.tripalocal.com/termsofservice'>"+getResources().getString(R.string.nav_terms_of_service_link)+" </a>";
        tos_txt.setText(Html.fromHtml(tos_text_content));
        privacy_txt = (TextView) view.findViewById(R.id.nav_privacy_txt);
        privacy_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String priv_text_content = "<a href='https://www.tripalocal.com/privacypolicy'>"+getResources().getString(R.string.nav_privacy_policy)+" </a>";
        privacy_txt.setText(Html.fromHtml(priv_text_content));
        about_txt = (TextView) view.findViewById(R.id.nav_about_us_txt);
        about_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String about_text_content = "<a href='https://www.tripalocal.com/aboutus'>"+getResources().getString(R.string.nav_about_us)+" </a>";
        about_txt.setText(Html.fromHtml(about_text_content));
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
                    ExperienceListAdapter.all_experiences.clear();
                    ExperienceListAdapter.all_experiences.addAll(HomeActivity.wish_map.values());
                    Intent intent = new Intent(getActivity().getApplicationContext(), ExpListActvity2.class);
                    intent.putExtra(ExperienceListAdapter.INT_EXTRA,9999);
                    startActivity(intent);
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyProfileActivity.class);
                    startActivity(intent);
                }
            });
            view.findViewById(R.id.nav_my_account_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastHelper.warnToast("Feature coming soon..");
                }
            });

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
