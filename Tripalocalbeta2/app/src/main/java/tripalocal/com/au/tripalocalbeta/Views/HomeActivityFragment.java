package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;
import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    private static final String[] bg_urls = {"https://www.tripalocal.com/images/mobile/home/Melbourne.jpg",
            "https://www.tripalocal.com/images/mobile/home/Sydney.jpg"};



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
        ImageView gc_vic = (ImageView) view.findViewById(R.id.home_greater_reg_vic);
        ImageView gc_nsw = (ImageView) view.findViewById(R.id.home_greater_reg_nsw);
        ImageView gc_qld = (ImageView) view.findViewById(R.id.home_greater_reg_qld);


        TextView melb_txt = (TextView) view.findViewById(R.id.home_melbourne_text);
        TextView syd_txt = (TextView) view.findViewById(R.id.home_sydney_text);
        TextView bris_txt = (TextView) view.findViewById(R.id.home_brisbane_text);
        TextView cairnstxt = (TextView) view.findViewById(R.id.home_cairns_text);
        TextView goldcoasttxt = (TextView) view.findViewById(R.id.home_gold_coast_text);
        TextView hobart_txt = (TextView) view.findViewById(R.id.home_hobart_text);
        TextView adelaidetxt = (TextView) view.findViewById(R.id.home_adelaide_text);
        TextView grtvictxt = (TextView) view.findViewById(R.id.home_greater_reg_vic_text);
        TextView grtqldtxt = (TextView) view.findViewById(R.id.home_greater_reg_qld_text);
        TextView grtnswtxt = (TextView) view.findViewById(R.id.home_greater_reg_nsw_text);


        melb_txt.setText(HomeActivity.poi_data[0]);
        syd_txt.setText(HomeActivity.poi_data[1]);
        bris_txt.setText(HomeActivity.poi_data[2]);
        cairnstxt.setText(HomeActivity.poi_data[3]);
        goldcoasttxt.setText(HomeActivity.poi_data[4]);
        hobart_txt.setText(HomeActivity.poi_data[5]);
        adelaidetxt.setText(HomeActivity.poi_data[6]);
        grtvictxt.setText(HomeActivity.poi_data[7]);
        grtqldtxt.setText(HomeActivity.poi_data[8]);
        grtnswtxt.setText(HomeActivity.poi_data[9]);


        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[1]).centerCrop().crossFade().into(syd);
        /*Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).crossFade().into(brisb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).crossFade().into(tas);*/

        melb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 0;
                        displayListFrag2(0);
            }
        });
        syd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(1);
            }
        });
        brisb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(2);
            }
        });
        cairns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(3);
            }
        });
        goldcoast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(4);
            }
        });
        hobart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(5);
            }
        });
        adelaide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(6);
            }
        });
        gc_vic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(7);
            }
        });
        gc_qld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(8);
            }
        });
        gc_nsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperienceListAdapter.current_city = 1;
                displayListFrag2(9);
            }
        });
        return view;
    }

    public void displayListFrag2(int position) {
        Intent intent = new Intent(HomeActivity.getHome_context(), ExpListActvity2.class);
        intent.putExtra(INT_EXTRA,position);
        startActivity(intent);
    }
}
