package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

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
    private Button previousTripButton;
    private Button exploreButton;
    private int category = 0;//0:upcoming, 1:past
    private TableLayout tl,tl_none;

    public static ArrayList<MyTrip> upcomingTrip = new ArrayList<>();
    public static ArrayList<MyTrip> previousTrip = new ArrayList<>();

    private float initialX, initialY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);
        getMyTrip(getUserToken());

        upcomingTripButton = (Button)findViewById(R.id.my_trip_upcoming);
        upcomingTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category==1)
                {
                    MyTripAdapter.myTrip = upcomingTrip;
                    category=0;
                    upcomingTripButton.setTextColor(Color.parseColor("#f7f7f7"));
                    upcomingTripButton.setBackgroundColor(Color.parseColor("#33cccc"));
                    previousTripButton.setTextColor(Color.parseColor("#33cccc"));
                    previousTripButton.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    rv.setAdapter(new MyTripAdapter(getApplicationContext()));
                }
            }
        });

        previousTripButton = (Button)findViewById(R.id.my_trip_previous);
        previousTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 0) {
                    MyTripAdapter.myTrip = previousTrip;
                    category = 1;
                    upcomingTripButton.setTextColor(Color.parseColor("#33cccc"));
                    upcomingTripButton.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    previousTripButton.setTextColor(Color.parseColor("#f7f7f7"));
                    previousTripButton.setBackgroundColor(Color.parseColor("#33cccc"));
                    rv.setAdapter(new MyTripAdapter(getApplicationContext()));
                }
            }
        });

        exploreButton = (Button)findViewById(R.id.my_trip_explore_button);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyTripActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        tl_none = (TableLayout)findViewById(R.id.my_trip_table_none);

        tl = (TableLayout)findViewById(R.id.my_trip_table);
        tl.setOnTouchListener(new View.OnTouchListener() {
            //http://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        initialX = event.getX();
                        initialY = event.getY();

                        //Log.d(TAG, "Action was DOWN");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //Log.d(TAG, "Action was MOVE");
                        break;

                    case MotionEvent.ACTION_UP:
                        float finalX = event.getX();
                        float finalY = event.getY();

                        //Log.d(TAG, "Action was UP");

                        if (initialX < finalX) {
                            //Log.d(TAG, "Left to Right swipe performed");
                            MyTripAdapter.myTrip = previousTrip;
                            rv.setAdapter(new MyTripAdapter(getApplicationContext()));
                        }

                        if (initialX > finalX) {
                            //Log.d(TAG, "Right to Left swipe performed");
                            MyTripAdapter.myTrip = upcomingTrip;
                            rv.setAdapter(new MyTripAdapter(getApplicationContext()));
                        }

                        if (initialY < finalY) {
                            //Log.d(TAG, "Up to Down swipe performed");
                        }

                        if (initialY > finalY) {
                            //Log.d(TAG, "Down to Up swipe performed");
                        }

                        break;

                    case MotionEvent.ACTION_CANCEL:
                        //Log.d(TAG,"Action was CANCEL");
                        break;

                    case MotionEvent.ACTION_OUTSIDE:
                        //Log.d(TAG, "Movement occurred outside bounds of current screen element");
                        break;
                }

                return true;
            }
        });

        rv = (RecyclerView) findViewById(R.id.my_trip_recycle_view);
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
        return "73487d0eb131a6822e08cd74612168cf6e0755dc";//"ca1188e53130b1af884918f797bac9aeb89ef7d2";//
    }

    private void getMyTrip(final String token)
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net")// https://www.tripalocal.com
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
                if(!upcomingTrip.isEmpty()) {
                    MyTripAdapter.myTrip = upcomingTrip;
                    category = 0;
                    upcomingTripButton.setTextColor(Color.parseColor("#f7f7f7"));
                    upcomingTripButton.setBackgroundColor(Color.parseColor("#33cccc"));
                    previousTripButton.setTextColor(Color.parseColor("#33cccc"));
                    previousTripButton.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tl_none.setVisibility(View.GONE);
                }
                else if(!previousTrip.isEmpty())
                {
                    MyTripAdapter.myTrip = previousTrip;
                    category = 1;
                    previousTripButton.setTextColor(Color.parseColor("#f7f7f7"));
                    previousTripButton.setBackgroundColor(Color.parseColor("#33cccc"));
                    upcomingTripButton.setTextColor(Color.parseColor("#33cccc"));
                    upcomingTripButton.setBackgroundColor(Color.parseColor("#f7f7f7"));
                    tl_none.setVisibility(View.GONE);
                }
                else
                {
                    upcomingTripButton.setVisibility(View.INVISIBLE);
                    previousTripButton.setVisibility(View.INVISIBLE);
                }

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
        upcomingTrip.clear();
        previousTrip.clear();
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
                previousTrip.add(result);
            }
        }
    }
}
