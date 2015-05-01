package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
import tripalocal.com.au.tripalocalbeta.adapters.SimpleRecycleAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.models.Search_Result;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new SimpleRecycleAdapter(this));

        //LayoutInflater inflater = getLayoutInflater();

       /* getActivity().getWindow().addContentView(inflater.inflate(R.layout.abyi, null),
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));*/

        return view;
    }

    public void displayListFrag(String city){
        StringBuilder temp = new StringBuilder();
        temp.append("Sports");
        temp.append(",");
        temp.append("Arts");
        temp.append(",");
        temp.append("Food");
        SearchRequest req_obj = new SearchRequest("2015-05-30", "2015-06-3",
                city,"2",temp.toString());
        Gson gson = new Gson();
        String json = gson.toJson(req_obj);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net/")
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        //ArrayList<String> temp = new ArrayList<String>();


        apiService.getSearchResults(req_obj, new Callback<List<Search_Result>>() {

            @Override
            public void success(List<Search_Result> search_results, Response response) {
                ExperienceListAdapter.search_result = search_results;
                System.out.println("search_results = " + search_results);

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("HomeActivityFragment.failure");
                System.out.println("error = [" + error + "]");
            }
        });
        FragHelper.replace(getActivity().getSupportFragmentManager(), new ExperienceDetailFragment());
    }
}
