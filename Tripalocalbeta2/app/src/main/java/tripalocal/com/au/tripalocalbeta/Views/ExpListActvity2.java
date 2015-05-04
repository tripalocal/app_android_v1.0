package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;


public class ExpListActvity2 extends ActionBarActivity {

    private static final String city[] = {"melbourne", "sydney"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            int position = intent.getIntExtra(INT_EXTRA,0);
            displayListFrag(city[position]);
        }
        setContentView(R.layout.activity_exp_list_actvity2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exp_list_actvity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayListFrag(final String city){
        StringBuilder temp = new StringBuilder();
        //Food&Wine,Education,History&Culture,Architecture,For Couples,Photography Worthy,Liveability Research,Kids Friendly,Outdoor&Nature,Shopping,Sports&Leisure,Host with Car,Extreme Fun,Events,Health&Beauty
        temp.append("Shopping");
        temp.append(",");
        temp.append("Education");
        temp.append(",");
        temp.append("Architecture");
        temp.append(",");
        temp.append("Food&Wine");
        temp.append(",");
        temp.append("History&Culture");
        temp.append(",");
        temp.append("For Couples");
        temp.append(",");
        temp.append("Photography Worthy");
        temp.append(",");
        temp.append("Liveability Research");
        temp.append(",");
        temp.append("Kids Friendly");
        temp.append(",");
        temp.append("Outdoor&Nature");
        temp.append(",");

        SearchRequest req_obj = new SearchRequest("2015-05-08", "2015-05-11",
                city,"2",temp.toString());
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
                FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment(), R.id.exp_list_fragment_container);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("HomeActivityFragment.failure");
                System.out.println("error = [" + error + "]");
            }
        });
    }
}
