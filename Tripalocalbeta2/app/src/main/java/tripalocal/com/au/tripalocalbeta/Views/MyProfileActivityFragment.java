package tripalocal.com.au.tripalocalbeta.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.network.MyProfile_result;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyProfileActivityFragment extends Fragment {

    private static MyProfile_result result;
    private CircleImageView profile_img;
    private EditText localno;
    private EditText roamingno;
    private TextView hostname;

    public MyProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.myprofile_navigation, container, false);
        getProfileDetails(view);
        getActivity().setTitle(getResources().getString(R.string.my_profile));
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        saveProfile();
    }

    private void saveProfile() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))// https://www.tripalocal.com
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
        apiService.saveMyProfileDetails(localno.getText().toString(), new Callback<MyProfile_result>() {
            @Override
            public void success(MyProfile_result myProfile_result, Response response) {
                ToastHelper.shortToast("profile saved");
            }

            @Override
            public void failure(RetrofitError error) {
                ToastHelper.errorToast("unable to save profile");
            }
        });
    }

    public void getProfileDetails(final View view){

        //final String temp_token = "73487d0eb131a6822e08cd74612168cf6e0755dc";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))// https://www.tripalocal.com
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
                ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_success));
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = [" + error + "]");
                ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_error));
            }
        });
    }

    private void prepareProfile(View view) {
        profile_img = (CircleImageView) view.findViewById(R.id.nav_drawer_host_profile_image);
        hostname = (TextView) view.findViewById(R.id.nav_drawer_host_name);
        localno = (EditText) view.findViewById(R.id.nav_drawer_local_no);
        localno.setText(result.getPhone_number());
        /*roamingno = (EditText) view.findViewById(R.id.nav_drawer_roaming_no);
        roamingno.setText(result.getPhone_number());*/

    Glide.with(HomeActivity.getHome_context()).load(NavigationFragment.BASE_URL+result.getImage()).fitCenter().into(profile_img);
    hostname.setText(result.getFirst_name() + " " + result.getLast_name().substring(0,1) + ".");
    }
}
