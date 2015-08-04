package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.squareup.okhttp.OkHttpClient;
import com.tripalocal.bentuke.helpers.GeneralHelper;
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
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.helpers.SearchRequest;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.Search_Result;


public class ExperiencesListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static RecyclerView rv;
    public static int city_position;
    public static OkHttpClient ok_client;
    SearchRequest req_obj;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager LLM;
    ExperienceListAdapter adapter;
    public ExperiencesListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null)
            city_position = getArguments().getInt(ExperienceListAdapter.INT_EXTRA);
        if(city_position != 9999){
            displayListFrag(city_position);
            getActivity().setTitle(HomeActivity.poi_data[city_position]);
            ExperienceListAdapter.all_experiences.clear();
        }else{
            getActivity().setTitle(getResources().getString(R.string.your_wishlist));
        }

        View view = inflater.inflate(R.layout.fragment_experiences_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycle_view_exp_list);
         LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        adapter=new ExperienceListAdapter(getActivity().getApplicationContext());
        rv.setAdapter(adapter);
        if(CheckoutActivity.experience_to_book != null)
            CheckoutActivity.experience_to_book = null;
        getActivity().invalidateOptionsMenu();
                rv.setLayoutManager(LLM);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = LLM.getChildCount();
                totalItemCount = LLM.getItemCount();
                pastVisiblesItems = LLM.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
//                        GeneralHelper.showLoadingProgress(getActivity());

                        System.out.println("here comes to the end ");
//                        adapter.all_experiences.add();
//                        displayListFrag(ExperienceListAdapter.current_city);

                        //prepare for paging also need to modify this method.
                    }

                }
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != ExperienceListAdapter.current_city ) {
            //displayListFrag(city[position]);
            ((RecyclerView) getActivity().findViewById(R.id.recycle_view_exp_list)).getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void displayListFrag(final int position){
        GeneralHelper.showLoadingProgress(getActivity());
        String keywords = getResources().getString(R.string.tags);
        Calendar cal = new GregorianCalendar();
        Date today = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH,1);
        Date tommorow = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        req_obj = new SearchRequest(dateFormat.format(tommorow), dateFormat.format(tommorow),
                HomeActivity.db_poi_data[position],"0", keywords);
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
                //System.out.println("search_results = " + search_results);
                //ToastHelper.shortToast(getResources().getString(R.string.toast_showing_exp) + HomeActivity.poi_data[position]);
                rv.getAdapter().notifyDataSetChanged();
                //FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment(), R.id.exp_list_fragment_container);
            }
            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                //System.out.println("HomeActivityFragment.failure");
                //System.out.println("error = [" + error + "]");
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if(ok_client != null) {
            ok_client.cancel(req_obj);
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_expList)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_expList));
    }
}