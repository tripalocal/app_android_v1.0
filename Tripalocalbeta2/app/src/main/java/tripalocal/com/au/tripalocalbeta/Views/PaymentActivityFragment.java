package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import tripalocal.com.au.tripalocalbeta.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentActivityFragment extends Fragment{

     TextView alipay_checkout;
    Button btn;
    public PaymentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
//        alipay_checkout.setText(PaymentActivity.price+"guest:"+PaymentActivity.guest);
        alipay_checkout = (TextView) view.findViewById(R.id.Alipay_checkout_text);
        alipay_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AlipayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("price", PaymentActivity.price);
                intent.putExtra("guests",PaymentActivity.guests);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });
                return view;

    }

}
