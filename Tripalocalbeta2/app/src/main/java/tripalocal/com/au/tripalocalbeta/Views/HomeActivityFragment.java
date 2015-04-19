package tripalocal.com.au.tripalocalbeta.Views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.SimpleRecycleAdapter;
import tripalocal.com.au.tripalocalbeta.models.SearchRequest;


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
        return view;
    }

    public void displayListFrag(String city){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://www.tripalocal.com")
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        //ArrayList<String> temp = new ArrayList<String>();
        StringBuilder temp = new StringBuilder();
        temp.append("Sports");
        temp.append("Arts");
        temp.append("Food");
        SearchRequest req_obj = new SearchRequest("2015-04-19", "2015-05-22",
                city,"2",temp.toString());
        Gson gson = new Gson();
        String json = gson.toJson(req_obj);
        apiService.getSearchResults(req_obj, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                System.out.println("HomeActivityFragment.success");
                System.out.println("s = [" + s + "], response = [" + response + "]");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("HomeActivityFragment.failure");
                System.out.println("error = [" + error + "]");
            }
        });
        android.support.v4.app.FragmentManager fragManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fragManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new ExperiencesListFragment());
        transaction.addToBackStack("homeTOlist");
        transaction.commit();
    }

}
