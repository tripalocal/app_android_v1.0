package tripalocal.com.au.tripalocalbeta.Views;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.models.Login_Request;

import static tripalocal.com.au.tripalocalbeta.R.*;


public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_home);
        HomeActivityFragment homeFrag = new HomeActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFrag).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
            Toast.makeText(getApplicationContext(), "menu called", Toast.LENGTH_SHORT).show();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("http://www.tripalocal.com")
                    .build();
            ApiService apiService = restAdapter.create(ApiService.class);
            Login_Request log_req = new Login_Request("ravnav44@gmail.com" , "omegastar");
            Gson gson = new Gson();
            String log_json = gson.toJson(log_req);
            apiService.loginUser(log_json , new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    System.out.println("s = [" + s + "], response = [" + response + "]");
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println("error = [" + error + "]");
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayExperiecesList(){
        ExperiencesListFragment expFrag = new ExperiencesListFragment();
        getSupportFragmentManager() .beginTransaction().replace(R.id.fragment_container, expFrag).commit();
    }

}
