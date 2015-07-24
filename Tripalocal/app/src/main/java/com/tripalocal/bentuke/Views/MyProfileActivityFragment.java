package com.tripalocal.bentuke.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.umeng.analytics.MobclickAgent;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.MyProfile_result;
import com.tripalocal.bentuke.models.network.profileUpdateRequest;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyProfileActivityFragment extends Fragment {

    private static MyProfile_result result;
    private CircleImageView profile_img;
    private EditText localno;
    private EditText roamingno;
    private TextView hostname;
    private Button saveBtn;
    private static String profile_saved;
    private static String profile_error;

    public MyProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.myprofile_navigation, container, false);
        getProfileDetails(view);
        getActivity().setTitle(getResources().getString(R.string.my_profile));
        profile_saved = getResources().getString(R.string.profile_saved);
        profile_error = getResources().getString(R.string.error_saving_profile);
        return view;
    }

   /* @Override
    public void onStop() {
        saveProfile();
        super.onStop();
    }*/

    private void saveProfile() {
        GeneralHelper.showLoadingProgress(getActivity());
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
        apiService.saveMyProfileDetails(new profileUpdateRequest(localno.getText().toString()), new Callback<MyProfile_result>() {
            @Override
            public void success(MyProfile_result myProfile_result, Response response) {
                GeneralHelper.closeLoadingProgress();
                ToastHelper.shortToast(profile_saved);
            }

            @Override
            public void failure(RetrofitError error) {

                GeneralHelper.closeLoadingProgress();
                ToastHelper.errorToast(profile_error);
            }
        });
    }

    public void getProfileDetails(final View view){
        GeneralHelper.showLoadingProgress(getActivity());
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
                GeneralHelper.closeLoadingProgress();
                result = res;
                prepareProfile(view);
                ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_success));
            }
            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();
                //System.out.println("error = [" + error + "]");
                ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_error));
            }
        });
    }

    private void prepareProfile(View view) {
        profile_img = (CircleImageView) view.findViewById(R.id.nav_drawer_host_profile_image);
        hostname = (TextView) view.findViewById(R.id.nav_drawer_host_name);
        localno = (EditText) view.findViewById(R.id.nav_drawer_local_no);
        localno.setText(result.getPhone_number());
        saveBtn = (Button) view.findViewById(R.id.my_profile_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

    Glide.with(HomeActivity.getHome_context()).load(NavigationFragment.BASE_URL+result.getImage()).fitCenter().into(profile_img);
    hostname.setText(result.getFirst_name() + " " + result.getLast_name().substring(0, 1) + ".");
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_myProfile)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_myProfile));
    }
}
