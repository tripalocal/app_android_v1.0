package tripalocal.com.au.tripalocalbeta.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tripalocal.com.au.tripalocalbeta.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentActivityFragment extends Fragment {

    public PaymentActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }
}
