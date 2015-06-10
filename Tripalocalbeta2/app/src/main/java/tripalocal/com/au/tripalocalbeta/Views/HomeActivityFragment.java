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
        TextView melb_txt = (TextView) view.findViewById(R.id.home_melbourne_text);
        TextView syd_txt = (TextView) view.findViewById(R.id.home_sydney_text);
        melb_txt.setText("Melbourne");
        syd_txt.setText("Sydney");
       /* ImageView brisb = (ImageView) view.findViewById(R.id.home_brisbane);
        ImageView tas = (ImageView) view.findViewById(R.id.home_tasmania);*/
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).centerCrop().crossFade().into(melb);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[1]).centerCrop().crossFade().into(syd);
      /*  Glide.with(HomeActivity.getHome_context()).load(bg_urls[0]).crossFade().into(brisb);
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
        return view;
    }

    public void displayListFrag2(int position) {
        Intent intent = new Intent(HomeActivity.getHome_context(), ExpListActvity2.class);
        intent.putExtra(INT_EXTRA,position);
        startActivity(intent);
    }
}
