package com.tripalocal.bentuke.Views;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.okhttp.OkHttpClient;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.helpers.FragHelper;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.tripalocal.bentuke.helpers.SearchRequest;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.LoginFBRequest;
import com.tripalocal.bentuke.models.network.Search_Result;
import com.umeng.analytics.MobclickAgent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class ItinerariesFragment extends Fragment {
    SearchRequest req_obj;

    public TextView three_mel_one,three_mel_all,three_syn_one,three_syn_all,seven_one,seven_all,ten_one,ten_all;
    public static final String INT_EXTRA = "POSITION";
    public LinearLayout search_to_host_layout,search_to_local_layout;
    public static OkHttpClient ok_client;

    public ItinerariesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view =  inflater.inflate(R.layout.itineraries_item, container, false);
        initContent(view);
        getActivity().invalidateOptionsMenu();
        getActivity().setTitle(getResources().getString(R.string.nav_itineraries));

        return view;
    }

    public void displayListFrag2(int position,String exp_type) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        ExperiencesListFragment.experience_type=exp_type;
        exp_list_frag.setArguments(args);
        ExperiencesListFragment.ac.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }
    public void initContent(View view){
        three_mel_one=(TextView)view.findViewById(R.id.three_mel_one);
        three_mel_all=(TextView)view.findViewById(R.id.three_mel_all);
        three_syn_one=(TextView)view.findViewById(R.id.three_syn_one);
        three_syn_all=(TextView)view.findViewById(R.id.three_syn_all);
        seven_one=(TextView)view.findViewById(R.id.seven_one);
        seven_all=(TextView)view.findViewById(R.id.seven_all);
        ten_one=(TextView)view.findViewById(R.id.ten_one);
        ten_all=(TextView)view.findViewById(R.id.ten_all);
        search_to_host_layout=(LinearLayout)view.findViewById(R.id.search_to_host_layout_i);
        search_to_local_layout=(LinearLayout)view.findViewById(R.id.search_to_local_layout_i);
        search_to_local_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0,ExperiencesListFragment.exp_newPro);//change here

            }
        });
        search_to_host_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0, ExperiencesListFragment.exp_private);//change here
            }
        });
        setActionListener(three_mel_one,651);
        setActionListener(three_mel_all,701);
        setActionListener(three_syn_one,661);
        setActionListener(three_syn_all,711);
        setActionListener(seven_one,681);
        setActionListener(seven_all,731);
        setActionListener(ten_one,691);
        setActionListener(ten_all,771);


    }

    public void setActionListener(TextView text,int exp_id){
        final int id=exp_id;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.getHome_context(), ExpDetailActivity.class);
                intent.putExtra(INT_EXTRA, id);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            }
        });

    }
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_login)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_login));
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public void displayListFrag(){
        GeneralHelper.showLoadingProgress(getActivity());
        String keywords = getResources().getString(R.string.tags);
        Calendar cal = new GregorianCalendar();
        Date today = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH,1);
        Date tommorow = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int position=0;
        req_obj = new SearchRequest(dateFormat.format(tommorow), dateFormat.format(tommorow),
                HomeActivity.db_poi_data[position]
                ,"0", "",ExperiencesListFragment.exp_itinerary);


        ok_client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(ok_client))
                .setEndpoint(getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
//        ToastHelper.longToast(getResources().getString(R.string.toast_contacting));
        apiService.getSearchResults(req_obj, new Callback<List<Search_Result>>() {
            @Override
            public void success(List<Search_Result> search_results, Response response) {
                GeneralHelper.closeLoadingProgress();
                ExperienceListAdapter.prepareSearchResults(search_results);
                ////System.out.println("search_results = " + search_results);
                //ToastHelper.shortToast(getResources().getString(R.string.toast_showing_exp) + HomeActivity.poi_data[position]);
//                rv.getAdapter().notifyDataSetChanged();
                //FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment(), R.id.exp_list_fragment_container);
            }
            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                ////System.out.println("HomeActivityFragment.failure");
                ////System.out.println("error = [" + error + "]");
            }
        });
    }
}
