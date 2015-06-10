package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.request;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutActivityFragment extends Fragment {

    Button bookingBtn;
    ImageView thumbnail;
    TextView title;
    TextView duration;
    TextView booking_date_1;
    TextView booking_time_1;
    TextView booking_date_2;
    TextView booking_time_2;
    TextView booking_date_3;
    TextView booking_time_3;
    TextView booking_price_and_person;
    TextView booking_price_and_person_amt;
    NumberPicker np;
    static int guests = 1;
    static String price_s = null;
    static Double price_i = null;

    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");

    public CheckoutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        title = (TextView) view.findViewById(R.id.booking_title);
        thumbnail = (ImageView) view.findViewById(R.id.booking_thumbnail);
        duration = (TextView) view.findViewById(R.id.booking_duration);
        booking_date_1 = (TextView) view.findViewById(R.id.booking_date_txt1);
        booking_date_2 = (TextView) view.findViewById(R.id.booking_date_txt2);
        booking_date_3 = (TextView) view.findViewById(R.id.booking_date_txt3);
        booking_time_1 = (TextView)view.findViewById(R.id.booking_time_txt1);
        booking_time_2 = (TextView)view.findViewById(R.id.booking_time_txt2);
        booking_time_3 = (TextView)view.findViewById(R.id.booking_time_txt3);
        booking_price_and_person = (TextView) view.findViewById(R.id.booking_price_and_person_txt);
        booking_price_and_person_amt = (TextView) view.findViewById(R.id.booking_price_total_amt_txt);
        np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMinValue(1);
        np.setMaxValue(50);
        np.setWrapSelectorWheel(false);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                guests = newVal;
                booking_price_and_person.setText("$"+ price_s + " AUD x "+ guests+" pp");
                booking_price_and_person_amt.setText(REAL_FORMATTER.format(price_i*guests));
            }
        });

        bookingBtn = (Button) view.findViewById(R.id.booking_bottom_Btn);
        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PaymentActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        if(CheckoutActivity.position != 999){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint("http://adventure007.cloudapp.net/")
                    .build();
            ApiService apiService = restAdapter.create(ApiService.class);
            ToastHelper.longToast("Contacting Server...");
            Gson gson = new Gson();
            request req = new request(CheckoutActivity.position);
            apiService.getExpDetails(req, new Callback<Experience_Detail>() {
                @Override
                public void success(Experience_Detail experience_detail, Response response) {
                    CheckoutActivity.experience_to_book = experience_detail;
                    updateDetails();
                }
                @Override
                public void failure(RetrofitError error) {
                    ToastHelper.errorToast("Error occurred");
                }
            });
        }
        return view;
    }

    public void updateDetails(){
        Experience_Detail temp_detail_exp = CheckoutActivity.experience_to_book;
        if(temp_detail_exp != null){
            Glide.with(HomeActivity.getHome_context()).load(ExpDetailActivityFragment.BASE_URL+
                    "thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(thumbnail);
            title.setText(temp_detail_exp.getExperience_title());
            duration.setText(temp_detail_exp.getExperience_duration().toString()+" hrs . ");
            // ### option to change language in future
            //TextView language = (TextView) view.findViewById(R.id.booking_language);
            //title.setText(CheckoutActivity.experience_to_book.ge);
            if(temp_detail_exp.getAvailable_options().size() >= 3){
                booking_date_1.setText(temp_detail_exp.getAvailable_options().get(0).getDate_string());
                booking_date_2.setText(temp_detail_exp.getAvailable_options().get(1).getDate_string());
                booking_date_3.setText(temp_detail_exp.getAvailable_options().get(2).getDate_string());
                booking_time_1.setText(temp_detail_exp.getAvailable_options().get(0).getTime_string());
                booking_time_2.setText(temp_detail_exp.getAvailable_options().get(1).getTime_string());
                booking_time_3.setText(temp_detail_exp.getAvailable_options().get(2).getTime_string());
            }
            price_i = temp_detail_exp.getExperience_price();
            price_s = REAL_FORMATTER.format(temp_detail_exp.getExperience_price());
            booking_price_and_person.setText("$"+ price_s+ " AUD x "+ guests+" pp");
            booking_price_and_person_amt.setText(REAL_FORMATTER.format(price_i*guests));
        }
    }

}
