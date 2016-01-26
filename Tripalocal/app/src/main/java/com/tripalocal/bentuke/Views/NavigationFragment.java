package com.tripalocal.bentuke.Views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.network.MsgListModel;
import com.umeng.analytics.MobclickAgent;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.network.MyProfile_result;
import com.tripalocal.bentuke.models.Tripalocal;

import java.util.ArrayList;

public class NavigationFragment extends Fragment {

    public static MyProfile_result result=null;
    //private static boolean my_profile = false;

    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";

    private CircleImageView profile_img;
    private EditText localno;
    private EditText roamingno;
    private TextView hostname;
    TextView tos_txt;
    TextView privacy_txt;
    TextView about_txt;
    TextView logout_txt;
    private static ImageView notification_red_icon;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        Button loginBtn;
        view = inflater.inflate(R.layout.user_sidebar_navigation, container, false);
        initialContent(view);
        logout_txt=(TextView)view.findViewById(R.id.nav_logout_text);
        if(HomeActivity.getCurrent_user().isLoggedin()) {
            logout_txt.setVisibility(View.VISIBLE);
            getProfileDetails(view);
        }
        else
        {
            //    view = inflater.inflate(R.layout.default_navigation, container, false);
            //    loginBtn = (Button) view.findViewById(R.id.nav_normal_login);
            //    loginBtn.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
            //    FragHelper.addReplace(HomeActivity.getFrag_manager(), new LoginFragment());
            //    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            //    drawer.closeDrawer(GravityCompat.START);
            //}
            //    });
            logout_txt.setVisibility(View.INVISIBLE);
            initNonLoginContent(view);
        }

        return view;
    }

    public void initNonLoginContent(View view){
        view.findViewById(R.id.nav_my_trips_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();
            }
        });
        view.findViewById(R.id.nav_wishlist_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();
            }
        });
        view.findViewById(R.id.nav_my_profile_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();
            }
        });
        view.findViewById(R.id.nav_msg_list_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit(); }
        });
        view.findViewById(R.id.nav_itineraries).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                ExperiencesListFragment.experience_type= ExperiencesListFragment.exp_itinerary;
                Fragment loginFragment = new ItinerariesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit(); }
        });
        view.findViewById(R.id.nav_host_exp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceListAdapter.current_city = 0;
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;

                displayListFrag2(1);
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });
        view.findViewById(R.id.nav_local_exp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceListAdapter.current_city = 0;
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                displayListFrag2(0);//change here
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });
    }

    public void initialContent(View view){
        view.findViewById(R.id.nav_home_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment homefragment = new HomeActivityFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homefragment).addToBackStack("homefragment").commit();
            }
        });
        tos_txt = (TextView) view.findViewById(R.id.nav_tos_txt);
        //tos_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String tos_text_content = "<a href='" + getActivity().getResources().getString(R.string.server_url) + "termsofservice'>"+getResources().getString(R.string.nav_terms_of_service_link)+" </a>";
        tos_txt.setText(Html.fromHtml(tos_text_content));
        privacy_txt = (TextView) view.findViewById(R.id.nav_privacy_txt);
        //privacy_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String priv_text_content = "<a href='" + getActivity().getResources().getString(R.string.server_url) + "privacypolicy'>"+getResources().getString(R.string.nav_privacy_policy)+" </a>";
        privacy_txt.setText(Html.fromHtml(priv_text_content));
        about_txt = (TextView) view.findViewById(R.id.nav_about_us_txt);
        //about_txt.setMovementMethod(LinkMovementMethod.getInstance());
        String about_text_content = "<a href='" + getActivity().getResources().getString(R.string.server_url) + "aboutus'>"+getResources().getString(R.string.nav_about_us)+" </a>";
        about_txt.setText(Html.fromHtml(about_text_content));
        privacy_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                HomeActivity.webViewPage_info = "privacypolicy";
                InfoFragment.title = getResources().getString(R.string.nav_privacy_policy);

                Fragment info_fragment = new InfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info_fragment).commit();

            }
        });
        about_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                HomeActivity.webViewPage_info = "aboutus";
                InfoFragment.title = getResources().getString(R.string.nav_about_us);
                Fragment info_fragment = new InfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info_fragment).commit();

            }
        });
        tos_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                HomeActivity.webViewPage_info = "termsofservice";
                InfoFragment.title = getResources().getString(R.string.nav_terms_of_service_link);

                Fragment info_fragment = new InfoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, info_fragment).commit();

            }
        });
        logout_txt=(TextView)view.findViewById(R.id.nav_logout_text);

        logout_txt.setText(getResources().getString(R.string.logout));
        if(HomeActivity.getCurrent_user().isLoggedin()) {
            logout_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (HomeActivity.getCurrent_user().isLoggedin()) {
                        HomeActivity.getCurrent_user().setLogin_token(null);
                        HomeActivity.getCurrent_user().setLoggedin(false);
                        HomeActivity.getCurrent_user().setUser_id(null);
                        HomeActivity.setAccessToken(null);
                        SharedPreferences settings_l = getActivity().getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor_l = settings_l.edit();
                        editor_l.clear();
                        editor_l.apply();
                        HomeActivity.login_flag = true;
                        getActivity().invalidateOptionsMenu();
                        MessageSerivice.isRunning = false;
                        MessageSerivice.connection.disconnect();
                        //ExperiencesListFragment.rv.getAdapter().notifyDataSetChanged();
                        ToastHelper.shortToast(getResources().getString(R.string.logged_out));
                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("login")
                                .replace(R.id.fragment_container, new LoginFragment()).commit();
                    }
                }
            });
        }
        notification_red_icon=(ImageView)view.findViewById(R.id.notificaiotn_red_icon);
        if(NotificationHelper.haveNotifcation){
            notification_red_icon.setVisibility(View.VISIBLE);
        }else{
            notification_red_icon.setVisibility(View.INVISIBLE);
        }
    }
    private void prepareProfile(View view) {

        profile_img = (CircleImageView) view.findViewById(R.id.nav_drawer_host_profile_image);
        hostname = (TextView) view.findViewById(R.id.nav_drawer_host_name);
        view.findViewById(R.id.nav_itineraries).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                Fragment loginFragment = new ItinerariesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit(); }
        });

            view.findViewById(R.id.nav_my_trips_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawers();
                    Fragment my_trip_fragment = new MyTripFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, my_trip_fragment).addToBackStack("navigation_my_trip").commit();
                }
            });

        view.findViewById(R.id.nav_host_exp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceListAdapter.current_city = 0;
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;

                displayListFrag2(0);
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });

        view.findViewById(R.id.nav_local_exp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExperienceListAdapter.current_city = 0;
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                displayListFrag2(0);//change here
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });
            view.findViewById(R.id.nav_wishlist_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    Fragment exp_list_frag;
                    if(HomeActivity.wish_map.isEmpty()){
                         args.putString(BlankFragment.MSG_EXTRA, getResources().getString(R.string.empty_wishlist_msg));
                         exp_list_frag = new BlankFragment();
                         exp_list_frag.setArguments(args);
                         getActivity().setTitle(getResources().getString(R.string.title_fragment_wishlist));
                    }else{
                    ExperienceListAdapter.all_experiences.clear();
                    ExperienceListAdapter.all_experiences.addAll(HomeActivity.wish_map.values());
                    exp_list_frag = new ExperiencesListFragment();
                    args.putInt(ExperienceListAdapter.INT_EXTRA, 9999);
                    exp_list_frag.setArguments(args);
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("navigation_wish").commit();
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawers();
                }
            });

            view.findViewById(R.id.nav_my_profile_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawers();
                    Fragment exp_list_frag = new MyProfileActivityFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("navigation_my_profile").commit();
                }
            });
            view.findViewById(R.id.nav_msg_list_container).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateDatabase();

                }
            });
        if(!result.getImage().isEmpty()){
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL+result.getImage()).fitCenter().into(profile_img);
        }

        //System.out.println("profile image is "+result.getImage());
        hostname.setText(result.getFirst_name() + " " + result.getLast_name().substring(0, 1) + ".");
        HomeActivity.user_img=result.getImage();
    }

    public void updateDatabase() {
        final ChatListDataSource chatList_db_source=new ChatListDataSource(getActivity());
        GeneralHelper.showLoadingProgress(getActivity());
        final String tooken = HomeActivity.getCurrent_user().getLogin_token();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + tooken);
                    }
                })
                .build();

        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getAllMessageList(new Callback<ArrayList<MsgListModel>>() {
            @Override
            public void success(ArrayList<MsgListModel> msgListModels, Response response) {
                try {
                    //Log.i("chat server test", "step 1" + msgListModels.size());
                    chatList_db_source.open();
                    for (MsgListModel model : msgListModels) {
                        if (chatList_db_source.checkSync(model.getSender_id() + "", model.getId() + "")) {
                            ChatList_model chatModel = new ChatList_model();
                            chatModel.setGlobal_id(model.getId() + "");
                            chatModel.setSender_img(model.getSender_image());
                            chatModel.setSender_id(model.getSender_id() + "");
                            chatModel.setLast_msg_content(model.getMsg_content());
                            chatModel.setLast_msg_date(model.getMsg_date());
                            chatModel.setSender_name(model.getSender_name());
                            chatList_db_source.createNewChat(chatModel);
                            //Log.i("messages count ", "das" + chatList_db_source.getChatList().size());
                            //Log.i("chat server test", "step 2.1");
                        }
                        //Log.i("chat server test", "step 2");
                    }
                    chatList_db_source.close();
                    //Log.i("chat server test", "step 3");

                } catch (Exception e) {
                    //System.out.println("exception" + e.getMessage().toString());
                    //Log.i("chat server test", "step 4");
                }
                GeneralHelper.closeLoadingProgress();
                if (MessageSerivice.connection != null) {
                    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawerLayout.closeDrawers();

                    Fragment exp_list_frag = new MsgListFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("navigation_my_profile").commit();
                } else {
                    ToastHelper.shortToast(getResources().getString(R.string.msg_connecting));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                //System.out.println("fali on new service : " + error.getMessage().toString());
                GeneralHelper.closeLoadingProgress();
                //if (MessageSerivice.connection!=null) {
                //    DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                //    drawerLayout.closeDrawers();
                //
                //    Fragment exp_list_frag = new MsgListFragment();
                //    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("navigation_my_profile").commit();
                //}else{
                //    ToastHelper.shortToast(getResources().getString(R.string.msg_connecting));
                //}
            }
        });
    }

    public void getProfileDetails(final View view){
        GeneralHelper.showLoadingProgress(getActivity());
        //final String temp_token = "73487d0eb131a6822e08cd74612168cf6e0755dc";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))// https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + HomeActivity.getCurrent_user().getLogin_token());
                        //request.addHeader("Authorization", "Token " + temp_token);
                    }
                })
                .build();

        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getMyProfileDetails(new Callback<MyProfile_result>() {
            @Override
            public void success(MyProfile_result res, Response response) {
                GeneralHelper.closeLoadingProgress();
                result = res;
                prepareProfile(view);
                GeneralHelper.recordEmail(result.getEmail());
                //ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_success));
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();
                //System.out.println("error = [" + error + "]");
                ToastHelper.shortToast(getActivity().getResources().getString(R.string.toast_profile_get_error));
            }
        });
    }

    public void onResume() {
        super.onResume();
        //System.out.println("come to resume here ");
        if(notification_red_icon!=null) {
            if (NotificationHelper.haveNotifcation) {
                notification_red_icon.setVisibility(View.VISIBLE);
            } else {
                notification_red_icon.setVisibility(View.GONE);
            }
        }
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_navigation)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_navigation));
    }

    public void displayListFrag2(int position) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        exp_list_frag.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }
}