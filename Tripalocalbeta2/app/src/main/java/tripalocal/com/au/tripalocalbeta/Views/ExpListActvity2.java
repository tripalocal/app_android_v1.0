package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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


public class ExpListActvity2 extends AppCompatActivity {

    public static int city_position;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getIntExtra(INT_EXTRA,0) != 9999) {
                city_position = intent.getIntExtra(INT_EXTRA, 0);
                displayListFrag(city_position);
                setTitle(HomeActivity.poi_data[city_position]);
            }else{
                displayWishList();
                setTitle(getResources().getString(R.string.wishlist_title));
            }
        }
        if(CheckoutActivity.experience_to_book != null)
            CheckoutActivity.experience_to_book = null;
        setContentView(R.layout.activity_exp_list_actvity2);
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }

    private void displayWishList() {
        ToastHelper.shortToast("Showing your wishlist");
        FragHelper.replace(getSupportFragmentManager(), new ExperiencesListFragment(), R.id.exp_list_fragment_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exp_detail, menu);
        if(HomeActivity.getCurrent_user().isLoggedin()){
            menu.findItem(R.id.action_login).setTitle("Log Out");
        }
        return true;
    }
    @Override
    protected void onStop() {
        super.onStop();
        HomeActivity.saveData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if(HomeActivity.getCurrent_user().isLoggedin()){
                HomeActivity.getCurrent_user().setLogin_token(null);
                HomeActivity.getCurrent_user().setLoggedin(false);
                HomeActivity.setAccessToken(null);
                SharedPreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_l = settings_l.edit();
                editor_l.clear();
                editor_l.apply();
                ToastHelper.shortToast("Logged out");
            }else {
                getSupportFragmentManager().beginTransaction().addToBackStack("login")
                        .replace(R.id.exp_list_fragment_container, new LoginFragment()).commit();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayListFrag(final int position){
        String keywords = "Food&Wine,Education,History&Culture,Architecture,For Couples," +
                "Photography Worthy,Liveability Research,Kids Friendly,Outdoor&Nature,Shopping,Sports&Leisure," +
                "Host with Car,Extreme Fun,Events,Health&Beauty";
        Calendar cal = new GregorianCalendar();
        Date today = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH,1);
        Date tommorow = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SearchRequest req_obj = new SearchRequest(dateFormat.format(today), dateFormat.format(tommorow),
                HomeActivity.db_poi_data[position],"2", keywords);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net/")
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        ToastHelper.longToast("Contacting Server...");
        apiService.getSearchResults(req_obj, new Callback<List<Search_Result>>() {
            @Override
            public void success(List<Search_Result> search_results, Response response) {
                ExperienceListAdapter.prepareSearchResults(search_results);
                System.out.println("search_results = " + search_results);
                ToastHelper.shortToast("Showing experiences for " + HomeActivity.poi_data[position]);
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
