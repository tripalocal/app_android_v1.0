package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Search_Result;


public class ExperiencesListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    RecyclerView rv;

    public static final String keywords = "Food&Wine,Education,History&Culture,Architecture,For Couples," +
            "Photography Worthy,Liveability Research,Kids Friendly,Outdoor&Nature,Shopping,Sports&Leisure," +
            "Host with Car,Extreme Fun,Events,Health&Beauty";

    private static final String city[] = {"melbourne", "sydney"};
    private int spinner_last_selection = 99;


    public ExperiencesListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experiences_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycle_view_exp_list);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new ExperienceListAdapter(getActivity().getApplicationContext()));
        /*spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       // if(spinner_last_selection != 99 && spinner_last_selection != position){
        if(position != ExperienceListAdapter.current_city ) {
            displayListFrag(city[position]);
            ((RecyclerView) getActivity().findViewById(R.id.recycle_view_exp_list)).getAdapter().notifyDataSetChanged();
        }
       //     spinner_last_selection = position;
        //}
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void displayListFrag(final String city){

        SearchRequest req_obj = new SearchRequest("2015-05-08", "2015-05-11",
                city,"2",keywords);
        Gson gson = new Gson();
        String json = gson.toJson(req_obj);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net/")
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getSearchResults(req_obj, new Callback<List<Search_Result>>() {

            @Override
            public void success(List<Search_Result> search_results, Response response) {
                ExperienceListAdapter.prepareSearchResults(search_results);
                System.out.println("######################################");
                System.out.println("search_results = " + search_results);
                ToastHelper.shortToast("spinner select :" + city);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("HomeActivityFragment.failure");
                System.out.println("error = [" + error + "]");
            }
        });
    }
}