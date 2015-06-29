package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Tripalocal;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

/**
 * Created by user on 15/06/2015.
 */
public class PaymentSuccessFragment extends Fragment {
    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";

    CircleImageView host_img;
    TextView payment_success;
    Button btn;

    public PaymentSuccessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_payment_success, container, false);
        host_img= (CircleImageView) view.findViewById(R.id.host_img);
        payment_success=(TextView)view.findViewById(R.id.payment_content);
        btn=(Button)view.findViewById(R.id.view_trip_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("this is a test");
//                Fragment my_trip_fragment = new MyTripFragment();
//                HomeActivity.getFrag_manager().beginTransaction().replace(R.id.fragment_container, my_trip_fragment).addToBackStack("navigation_my_trip").commit();
//                HomeActivity.getFrag_manager().beginTransaction().replace(R.id.fragment_container, my_trip_fragment).commit();

                Intent intent = new Intent(getActivity().getApplicationContext(), MyTriptmpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                HomeActivity.getFrag_manager().beginTransaction().replace(R.id.fragment_container, my_trip_fragment).addToBackStack("navigation_my_trip").commitAllowingStateLoss();

                getActivity().getApplicationContext().startActivity(intent);
            }
        });
        setContent();
        return view;
    }

    public void setContent(){
        Experience_Detail exp=CheckoutActivity.experience_to_book;
        String host_name=exp.getHost_firstname();
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp.getHost_image()).fitCenter().into(host_img);
        String message=getResources().getString(R.string.payment_success_text);
        message=message.replace("somebody",host_name);
        payment_success.setText(message);
    }



}