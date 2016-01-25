package com.tripalocal.bentuke.Views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.MyTripAdapter;
import com.tripalocal.bentuke.models.MyTrip;

/**
 * Created by naveen on 6/17/2015.
 */
public class MyTripFragment extends Fragment {

    private RecyclerView rv;
    private Button upcomingTripButton;
    private Button previousTripButton;
    private Button exploreButton;
    private int category = 0;//0:upcoming, 1:past
    private TableLayout tl,tl_none;
    public static TextView msgTxt;

    public static ArrayList<MyTrip> upcomingTrip = new ArrayList<>();
    public static ArrayList<MyTrip> previousTrip = new ArrayList<>();

    private float initialX, initialY;

    public MyTripFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.title_activity_my_trip));
        View view =inflater.inflate(R.layout.fragment_my_trip, container, false);
        getMyTrip(HomeActivity.getCurrent_user().getLogin_token());
        upcomingTripButton = (Button) view.findViewById(R.id.my_trip_upcoming);
        upcomingTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category==1)
                {
                    MyTripAdapter.myTrip = upcomingTrip;
                    category=0;
                    upcomingTripButton.setTextColor(getResources().getColor(R.color.tripalocal_light_grey));
                    upcomingTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_green_blue));
                    previousTripButton.setTextColor(getResources().getColor(R.color.tripalocal_green_blue));
                    previousTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_light_grey));
                    rv.setAdapter(new MyTripAdapter(HomeActivity.getHome_context()));
                }
            }
        });

        previousTripButton = (Button) view.findViewById(R.id.my_trip_previous);
        previousTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category == 0) {
                    MyTripAdapter.myTrip = previousTrip;
                    category = 1;
                    upcomingTripButton.setTextColor(getResources().getColor(R.color.tripalocal_green_blue));
                    upcomingTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_light_grey));
                    previousTripButton.setTextColor(getResources().getColor(R.color.tripalocal_light_grey));
                    previousTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_green_blue));
                    rv.setAdapter(new MyTripAdapter(HomeActivity.getHome_context()));
                }
            }
        });

        exploreButton = (Button) view.findViewById(R.id.my_trip_explore_button);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeActivityFragment()).addToBackStack("mytrip").commit();
            }
        });

        tl_none = (TableLayout)view.findViewById(R.id.my_trip_table_none);

        tl = (TableLayout) view.findViewById(R.id.my_trip_table);
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
                            rv.setAdapter(new MyTripAdapter(HomeActivity.getHome_context()));
                        }

                        if (initialX > finalX) {
                            //Log.d(TAG, "Right to Left swipe performed");
                            MyTripAdapter.myTrip = upcomingTrip;
                            rv.setAdapter(new MyTripAdapter(HomeActivity.getHome_context()));
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
        rv = (RecyclerView) view.findViewById(R.id.my_trip_recycle_view);
        rv.setHasFixedSize(true);
        LinearLayoutManager LLM = new LinearLayoutManager(HomeActivity.getHome_context());
        rv.setLayoutManager(LLM);
        msgTxt = (TextView) getActivity().findViewById(R.id.blank_msg);
        getActivity().invalidateOptionsMenu();

        return view;
    }

    private void getMyTrip(final String token)
    {
        GeneralHelper.showLoadingProgress(getActivity());

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getResources().getString(R.string.server_url))//https://www.tripalocal.com
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
                if (!upcomingTrip.isEmpty()) {
                    MyTripAdapter.myTrip = upcomingTrip;
                    category = 0;
                    upcomingTripButton.setTextColor(getResources().getColor(R.color.white));
                    upcomingTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_green_blue));
                    previousTripButton.setTextColor(getResources().getColor(R.color.tripalocal_green_blue));
                    previousTripButton.setBackgroundColor(getResources().getColor(R.color.white));
                    tl_none.setVisibility(View.GONE);
                } else if (!previousTrip.isEmpty()) {
                    MyTripAdapter.myTrip = previousTrip;
                    category = 1;
                    previousTripButton.setTextColor(getResources().getColor(R.color.white));
                    previousTripButton.setBackgroundColor(getResources().getColor(R.color.tripalocal_green_blue));
                    upcomingTripButton.setTextColor(getResources().getColor(R.color.tripalocal_green_blue));
                    upcomingTripButton.setBackgroundColor(getResources().getColor(R.color.white));
                    tl_none.setVisibility(View.GONE);
                } else {
                    if (upcomingTrip.isEmpty()) {
                        MyTripAdapter.upcoming_flag = true;
                    } else MyTripAdapter.previous_flag = true;
                    upcomingTripButton.setVisibility(View.INVISIBLE);
                    previousTripButton.setVisibility(View.INVISIBLE);
                    MyTripAdapter.myTrip = new ArrayList<MyTrip>();
                }
                rv.setAdapter(new MyTripAdapter(HomeActivity.getHome_context()));
                GeneralHelper.closeLoadingProgress();

            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                ////System.out.println("ERROR MYTRIP :" + error);
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
                ////System.out.println(pe.toString());
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_myTrip)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_myTrip));
    }
}
