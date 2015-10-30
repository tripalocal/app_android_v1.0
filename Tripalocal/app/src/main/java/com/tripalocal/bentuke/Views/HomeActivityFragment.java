package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.umeng.analytics.MobclickAgent;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.models.Tripalocal;

import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private static final String[] bg_urls = { Tripalocal.getServerUrl() + "images/mobile/home/Melbourne.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Sydney.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Brisbane.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Cairns.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Goldcoast.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Hobart.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Adelaide.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Darwin.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Alicesprings.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Christchurch.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Queenstown.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Auckland.jpg",
            Tripalocal.getServerUrl() + "images/mobile/home/Wellington.jpg"};
    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_v2, container, false);
        ImageView melb = (ImageView) view.findViewById(R.id.home_melbourne);
        ImageView syd = (ImageView) view.findViewById(R.id.home_sydney);
        ImageView brisb = (ImageView) view.findViewById(R.id.home_brisbane);
        ImageView hobart = (ImageView) view.findViewById(R.id.home_hobart);
        ImageView cairns = (ImageView) view.findViewById(R.id.home_cairns);
        ImageView goldcoast= (ImageView) view.findViewById(R.id.home_gold_coast);
        ImageView adelaide = (ImageView) view.findViewById(R.id.home_adelaide);
        ImageView darwin=(ImageView)view.findViewById(R.id.home_darwin);
        ImageView alicesprings=(ImageView)view.findViewById(R.id.home_alicesprings);
        ImageView christchurch=(ImageView)view.findViewById(R.id.home_christchurch);
        ImageView queenstown=(ImageView)view.findViewById(R.id.home_queenstown);
        ImageView auckland=(ImageView)view.findViewById(R.id.home_auckland);
        ImageView wellington=(ImageView)view.findViewById(R.id.home_wellington);
        ImageView wechat_img = (ImageView) view.findViewById(R.id.wechat_img);
        LinearLayout host_exp_btn=(LinearLayout)view.findViewById(R.id.host_exp_btn);
        LinearLayout local_exp_btn=(LinearLayout)view.findViewById(R.id.local_exp_btn);
        LinearLayout itinerary_btn=(LinearLayout)view.findViewById(R.id.itinerary_btn);

        host_exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 0;
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;
                displayListFrag2(0,ExperiencesListFragment.exp_private);//change here
                displayListFrag2(0);
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
            }
        });

        local_exp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0,ExperiencesListFragment.exp_newPro);//change here
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
//                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//                drawerLayout.closeDrawers();
//                ExperiencesListFragment.experience_type= ExperiencesListFragment.exp_itinerary;
//
//                Fragment loginFragment = new ItinerariesFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();

            }
        });

        itinerary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawers();
                ExperiencesListFragment.experience_type= ExperiencesListFragment.exp_itinerary;

                Fragment loginFragment = new ItinerariesFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();
            }
        });


        if(HomeActivity.getHome_context().getResources().getString(R.string.version_language).equals("English")){
            Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);

            wechat_img.setImageResource(R.drawable.wechat_en);
        }else{
            wechat_img.setImageResource(R.drawable.wechat_cn);

        }
        TextView melb_txt = (TextView) view.findViewById(R.id.home_melbourne_text);
        TextView syd_txt = (TextView) view.findViewById(R.id.home_sydney_text);
        TextView bris_txt = (TextView) view.findViewById(R.id.home_brisbane_text);
        TextView cairnstxt = (TextView) view.findViewById(R.id.home_cairns_text);
        TextView goldcoasttxt = (TextView) view.findViewById(R.id.home_gold_coast_text);
        TextView hobart_txt = (TextView) view.findViewById(R.id.home_hobart_text);
        TextView adelaidetxt = (TextView) view.findViewById(R.id.home_adelaide_text);
        TextView darwin_txt=(TextView)view.findViewById(R.id.home_darwin_text);
        TextView alicesprings_txt=(TextView)view.findViewById(R.id.home_alicesprings_text);
        TextView chirstchurch_txt=(TextView)view.findViewById(R.id.home_christchurch_text);
        TextView queenstown_txt=(TextView)view.findViewById(R.id.home_queenstown_text);
        TextView auckland_txt=(TextView)view.findViewById(R.id.home_auckland_text);
        TextView wellington_txt=(TextView)view.findViewById(R.id.home_wellington_text);

        melb_txt.setText(HomeActivity.poi_data[0].split(",")[0]);
        syd_txt.setText(HomeActivity.poi_data[1].split(",")[0]);
        bris_txt.setText(HomeActivity.poi_data[2].split(",")[0]);
        cairnstxt.setText(HomeActivity.poi_data[3].split(",")[0]);
        goldcoasttxt.setText(HomeActivity.poi_data[4].split(",")[0]);
        hobart_txt.setText(HomeActivity.poi_data[5].split(",")[0]);
        adelaidetxt.setText(HomeActivity.poi_data[6].split(",")[0]);
        darwin_txt.setText(HomeActivity.poi_data[7].split(",")[0]);
        alicesprings_txt.setText(HomeActivity.poi_data[8].split(",")[0]);
        chirstchurch_txt.setText(HomeActivity.poi_data[9].split(",")[0]);
        queenstown_txt.setText(HomeActivity.poi_data[10].split(",")[0]);
        auckland_txt.setText(HomeActivity.poi_data[11].split(",")[0]);
        wellington_txt.setText(HomeActivity.poi_data[12].split(",")[0]);


        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[1]).centerCrop().crossFade().into(syd);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[2]).centerCrop().crossFade().into(brisb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[3]).centerCrop().crossFade().into(cairns);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[4]).centerCrop().crossFade().into(goldcoast);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[5]).centerCrop().crossFade().into(hobart);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[6]).centerCrop().crossFade().into(adelaide);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[7]).centerCrop().crossFade().into(darwin);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[8]).centerCrop().crossFade().into(alicesprings);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[9]).centerCrop().crossFade().into(christchurch);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[10]).centerCrop().crossFade().into(queenstown);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[11]).centerCrop().crossFade().into(auckland);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[12]).centerCrop().crossFade().into(wellington);

        melb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_mel));

            }
        });
        syd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(1);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_syn));
            }
        });
        brisb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 2;
                displayListFrag2(2);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_brisb));

            }
        });
        cairns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 3;
                displayListFrag2(3);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_cairns));

            }
        });
        goldcoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 4;
                displayListFrag2(4);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_goldcoast));

            }
        });
        hobart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 5;
                displayListFrag2(5);
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_hobart));

            }
        });
        adelaide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 6;
                displayListFrag2(6);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        darwin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 7;
                displayListFrag2(7);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        alicesprings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 8;
                displayListFrag2(8);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        christchurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 9;
                displayListFrag2(9);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        queenstown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 10;
                displayListFrag2(10);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        auckland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 11;
                displayListFrag2(11);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        wellington.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 12;
                displayListFrag2(12);
//                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });


        wechat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralHelper.openApp();
            }
        });
        getActivity().setTitle(getResources().getString(R.string.app_name));
        getActivity().invalidateOptionsMenu();
//        addMixPanelData();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        GeneralHelper.addMixPanelData(this.getActivity(),this.getResources().getString(R.string.mixpanel_event_viewHomePage));
        return view;

    }

    public void displayListFrag2(int position) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
        exp_list_frag.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }

    public void displayListFrag2(int position,String exp_type) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        ExperiencesListFragment.experience_type=exp_type;
        exp_list_frag.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_home)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_home));
    }



}
