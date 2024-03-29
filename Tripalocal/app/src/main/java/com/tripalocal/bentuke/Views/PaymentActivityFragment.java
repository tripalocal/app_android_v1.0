package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import com.tripalocal.bentuke.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentActivityFragment extends Fragment{
    TextView alipay_checkout;
    TextView creditCard_checkout;
    Button btn;

    public PaymentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        alipay_checkout = (TextView) view.findViewById(R.id.Alipay_checkout_text);
        alipay_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlipayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        creditCard_checkout=(TextView)view.findViewById(R.id.credit_checkout_text);
        creditCard_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(getActivity().getApplicationContext(), CreditCardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().getApplicationContext().startActivity(intent);
                }

        });
        return view;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_payment)); //统计页面
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_payment));
    }
}