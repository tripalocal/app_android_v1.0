package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tripalocal.com.au.tripalocalbeta.R;

/**
 * Created by user on 15/06/2015.
 */
public class CreditCardActivityFragment extends Fragment {

    TextView price_aud_1;
    TextView price_aud_2;
    TextView refund;
    public CreditCardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_creditcard, container, false);
        price_aud_1=(TextView)view.findViewById(R.id.price_aud_1);
        price_aud_2=(TextView)view.findViewById(R.id.price_aud_2);
//        refund = (TextView) view.findViewById(R.id.booking_refund_txt);

        setText();
        return view;
    }

    public void setText(){
        int price=Integer.parseInt(PaymentActivity.price);
        int guests=Integer.parseInt(PaymentActivity.guests);
        int total=price*guests;
        price_aud_1.setText("$"+price+" AUD * "+guests+ "");
        price_aud_2.setText("$" + total + " AUD");
//        refund.setMovementMethod(LinkMovementMethod.getInstance());
//        String text = "<a href='" + getActivity().getResources().getString(R.string.server_url) + "refundpolicy'>"+getResources().getString(R.string.checkout_refund_link)+" </a>";
//        refund.setText(Html.fromHtml(text));

    }




}