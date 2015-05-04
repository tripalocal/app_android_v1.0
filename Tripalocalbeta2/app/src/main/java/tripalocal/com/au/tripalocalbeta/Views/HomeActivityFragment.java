package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.FragHelper;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Search_Result;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private static final String[] bg_urls = {"https://www.tripalocal.com/images/mobile/home/Melbourne.jpg",
            "https://www.tripalocal.com/images/mobile/home/Sydney.jpg"};
    public static final String INT_EXTRA = "POSITION";


    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_v2, container, false);
        ImageView melb = (ImageView) view.findViewById(R.id.home_melbourne);
        ImageView syd = (ImageView) view.findViewById(R.id.home_sydney);
        TextView melb_txt = (TextView) view.findViewById(R.id.home_melbourne_text);
        TextView syd_txt = (TextView) view.findViewById(R.id.home_sydney_text);
        melb_txt.setText("Melbourne");
        syd_txt.setText("Sydney");
       /* ImageView brisb = (ImageView) view.findViewById(R.id.home_brisbane);
        ImageView tas = (ImageView) view.findViewById(R.id.home_tasmania);*/
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[1]).centerCrop().crossFade().into(syd);
      /*  Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).crossFade().into(brisb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).crossFade().into(tas);*/

        melb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 0;
                        displayListFrag("melbourne");
                /*Intent intent = new Intent(getActivity().getApplicationContext(), ExpListActvity2.class);
                intent.putExtra(INT_EXTRA, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);*/
            }
        });
        syd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag("sydney");
                /*Intent intent = new Intent(getActivity().getApplicationContext(), ExpListActvity2.class);
                intent.putExtra(INT_EXTRA, 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);*/
            }
        });

        /*RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new SimpleRecycleAdapter(this));*/
        return view;
    }

    public void displayListFrag(final String city){
        String keywords = "Food&Wine,Education,History&Culture,Architecture,For Couples," +
                "Photography Worthy,Liveability Research,Kids Friendly,Outdoor&Nature,Shopping,Sports&Leisure," +
                "Host with Car,Extreme Fun,Events,Health&Beauty";

        SearchRequest req_obj = new SearchRequest("2015-05-08", "2015-05-11",
                city,"2", keywords);
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
                System.out.println("search_results = " + search_results);
                ToastHelper.shortToast("HOME FRAG :" + city);
                FragHelper.replace(getActivity().getSupportFragmentManager(), new ExperiencesListFragment());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("HomeActivityFragment.failure");
                System.out.println("error = [" + error + "]");
            }
        });
    }
}
