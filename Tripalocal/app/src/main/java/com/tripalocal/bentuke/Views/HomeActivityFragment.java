package com.tripalocal.bentuke.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.umeng.analytics.MobclickAgent;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.models.Tripalocal;

import org.json.JSONObject;


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
            Tripalocal.getServerUrl() + "images/mobile/home/Adelaide.jpg"};
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
        //ImageView gc_vic = (ImageView) view.findViewById(R.id.home_greater_reg_vic);
        //ImageView gc_nsw = (ImageView) view.findViewById(R.id.home_greater_reg_nsw);
        //ImageView gc_qld = (ImageView) view.findViewById(R.id.home_greater_reg_qld);


        TextView melb_txt = (TextView) view.findViewById(R.id.home_melbourne_text);
        TextView syd_txt = (TextView) view.findViewById(R.id.home_sydney_text);
        TextView bris_txt = (TextView) view.findViewById(R.id.home_brisbane_text);
        TextView cairnstxt = (TextView) view.findViewById(R.id.home_cairns_text);
        TextView goldcoasttxt = (TextView) view.findViewById(R.id.home_gold_coast_text);
        TextView hobart_txt = (TextView) view.findViewById(R.id.home_hobart_text);
        TextView adelaidetxt = (TextView) view.findViewById(R.id.home_adelaide_text);


        melb_txt.setText(HomeActivity.poi_data[0]);
        syd_txt.setText(HomeActivity.poi_data[1]);
        bris_txt.setText(HomeActivity.poi_data[2]);
        cairnstxt.setText(HomeActivity.poi_data[3]);
        goldcoasttxt.setText(HomeActivity.poi_data[4]);
        hobart_txt.setText(HomeActivity.poi_data[5]);
        adelaidetxt.setText(HomeActivity.poi_data[6]);


        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[1]).centerCrop().crossFade().into(syd);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[2]).centerCrop().crossFade().into(brisb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[3]).centerCrop().crossFade().into(cairns);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[4]).centerCrop().crossFade().into(goldcoast);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[5]).centerCrop().crossFade().into(hobart);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[6]).centerCrop().crossFade().into(adelaide);

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
                MobclickAgent.onEvent(getActivity().getApplicationContext(), getActivity().getResources().getString(R.string.youmeng_event_expLocation_adelaide));

            }
        });
        getActivity().setTitle(getResources().getString(R.string.app_name));
        getActivity().invalidateOptionsMenu();
//        addMixPanelData();
        GeneralHelper.addMixPanelData(this.getActivity(),this.getResources().getString(R.string.mixpanel_event_viewHomePage));
        return view;

    }

    public void displayListFrag2(int position) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        exp_list_frag.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_home)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_home));
    }


}
