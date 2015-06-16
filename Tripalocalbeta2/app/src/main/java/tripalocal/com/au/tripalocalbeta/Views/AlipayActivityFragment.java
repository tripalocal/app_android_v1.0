package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import android.util.*;
import com.alipay.sdk.app.PayTask;
import android.widget.*;
import tripalocal.com.au.tripalocalbeta.R;

/**
 * Created by user on 15/06/2015.
 */
public class AlipayActivityFragment extends Fragment {

    TextView price_aud_1;
    TextView price_aud_2;
    TextView price_rmb_2;

    public AlipayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_alipay, container, false);
        price_aud_1=(TextView)view.findViewById(R.id.price_aud_1);
        price_aud_2=(TextView)view.findViewById(R.id.price_aud_2);
        price_rmb_2=(TextView)view.findViewById(R.id.price_rmb_2);
        setText();
        return view;
    }

    public void setText(){
        int price=Integer.parseInt(PaymentActivity.price);
        int guests=Integer.parseInt(PaymentActivity.guests);
        int total=price*guests;
        price_aud_1.setText("$"+price+" AUD * "+guests+ "");
        price_aud_2.setText("$"+total+" AUD");
        price_rmb_2.setText("￥"+total*5+"RMB");

    }



}