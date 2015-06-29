package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.umeng.analytics.MobclickAgent;

import android.widget.*;
import tripalocal.com.au.tripalocalbeta.R;

/**
 * Created by user on 15/06/2015.
 */
public class AlipayActivityFragment extends Fragment {

    TextView price_aud_1;
    TextView price_aud_2;
    TextView price_rmb_2;
    TextView refund;

    public AlipayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_alipay, container, false);
        price_aud_1=(TextView)view.findViewById(R.id.price_aud_1);
        price_aud_2=(TextView)view.findViewById(R.id.price_aud_2);
        price_rmb_2=(TextView)view.findViewById(R.id.price_rmb_2);
//        refund = (TextView) view.findViewById(R.id.booking_refund_txt);

        setText();
        return view;
    }

    public void setText(){

        price_aud_1.setText(CheckoutActivity.price_label_1);
        price_aud_2.setText(CheckoutActivity.price_label_2);
        price_rmb_2.setText("￥"+Integer.parseInt(CheckoutActivity.total_price)*5+"RMB");
//        refund.setMovementMethod(LinkMovementMethod.getInstance());
//        String text = "<a href='" + getActivity().getResources().getString(R.string.server_url) + "refundpolicy'>"+getResources().getString(R.string.checkout_refund_link)+" </a>";
//        refund.setText(Html.fromHtml(text));
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_alipay)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_alipay));
    }



}