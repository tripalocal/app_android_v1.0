package tripalocal.com.au.tripalocalbeta.Views;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.MyTripAdapter;
import tripalocal.com.au.tripalocalbeta.models.MyTrip;

public class MyTripActivity extends ActionBarActivity {

    private RecyclerView rv;
    private Button upcomingTripButton;
    private Button pastTripButton;

    public static ArrayList<MyTrip> upcomingTrip = new ArrayList<>();
    public static ArrayList<MyTrip> pastTrip = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
        getMyTrip(getUserToken());

        upcomingTripButton = (Button)findViewById(R.id.my_trip_upcoming);
        upcomingTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTripAdapter.myTrip = upcomingTrip;
                rv.setAdapter(new MyTripAdapter(getApplicationContext()));
            }
        });

        pastTripButton = (Button)findViewById(R.id.my_trip_past);
        pastTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTripAdapter.myTrip = pastTrip;
                rv.setAdapter(new MyTripAdapter(getApplicationContext()));
            }
        });

        rv = (RecyclerView) findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager LLM = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(LLM);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_trip, menu);
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

    private String getUserToken()
    {
        //TODO
        return "ca1188e53130b1af884918f797bac9aeb89ef7d2";
    }

    private void getMyTrip(final String token)
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net")//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + token);
                    }
                })
                .build();

        ApiService apiService = restAdapter.create(ApiService.class);

        apiService.getMyTrip(new Callback<ArrayList<MyTrip>>() {

            @Override
            public void success(ArrayList<MyTrip> my_trip, Response response) {
                classifyTrip(my_trip);
                MyTripAdapter.myTrip = upcomingTrip;
                rv.setAdapter(new MyTripAdapter(getApplicationContext()));
                System.out.println("MyTripActivity.Success");
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("MyTripActivity.failure");
                System.out.println("error = [" + error + "]");
            }
        });
    }

    private void classifyTrip(ArrayList<MyTrip> my_trip)
    {
        for(int i=0;i<my_trip.size();i++)
        {
            MyTrip result =  my_trip.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'");
            Date dt = new Date();
            try {
                dt = sdf.parse(result.getDatetime().substring(0,20));
            }
            catch(ParseException pe)
            {
                System.out.println(pe.toString());
            }
            if(dt.after(new Date()))
            {
                upcomingTrip.add(result);
            }
            else
            {
                pastTrip.add(result);
            }
        }
    }
}
