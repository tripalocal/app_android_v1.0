package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.AvailableOption;
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
    TextView language_txt;
    TextView booking_date_1;
    TextView booking_time_1;
    TextView booking_date_2;
    TextView booking_time_2;
    TextView booking_date_3;
    TextView booking_time_3;
    View booking_row_3;
    View booking_row_4;
    View booking_row_5;
    TextView booking_price;
    TextView booking_guest_number;
    TextView booking_price_and_person_amt;
    TextView refund;
    Spinner date_spin;
    Spinner time_spin;
    NumberPicker np;
    static int guests = 1;
    static String price_s = null;
    static Double price_i = null;
    static int date_sel = 0;
    static int time_sel = 0;
    static Experience_Detail temp_detail_exp;


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
        language_txt = (TextView) view.findViewById(R.id.booking_language);
        date_spin = (Spinner) view.findViewById(R.id.booking_Select_Date_spinner);
        time_spin = (Spinner) view.findViewById(R.id.booking_Select_Time_spinner);
        final View time_container_1 = view.findViewById(R.id.checkout_time_1_container);
        final View time_container_2 = view.findViewById(R.id.checkout_time_2_container);
        final View time_container_3 = view.findViewById(R.id.checkout_time_3_container);
        booking_row_3 = view.findViewById(R.id.booking_row_3);
        booking_row_4 = view.findViewById(R.id.booking_row_4);
        booking_row_5 = view.findViewById(R.id.booking_row_5);
        booking_date_1 = (TextView) view.findViewById(R.id.booking_date_txt1);
        booking_date_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date_sel != 0) {
                    if(date_sel == 1){
                        booking_date_2.setBackgroundResource(R.color.white);
                        time_container_2.setBackgroundResource(R.color.white);
                    }else{
                        booking_date_3.setBackgroundResource(R.color.white);
                        time_container_3.setBackgroundResource(R.color.white);
                    }
                    booking_date_1.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_container_1.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    date_sel = 0;
                    np.setMaxValue(temp_detail_exp.getAvailable_options().get(0).getAvailable_seat());
                }
            }
        });
        booking_date_2 = (TextView) view.findViewById(R.id.booking_date_txt2);
        booking_date_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date_sel != 1) {
                    if(date_sel == 2){
                        booking_date_3.setBackgroundResource(R.color.white);
                        time_container_3.setBackgroundResource(R.color.white);
                    }else{
                        booking_date_1.setBackgroundResource(R.color.white);
                        time_container_1.setBackgroundResource(R.color.white);
                    }
                    booking_date_2.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_container_2.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    date_sel = 1;
                    np.setMaxValue(temp_detail_exp.getAvailable_options().get(1).getAvailable_seat());
                }
            }
        });
        booking_date_3 = (TextView) view.findViewById(R.id.booking_date_txt3);
        booking_date_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(date_sel != 2) {
                    if(date_sel == 0){
                        booking_date_1.setBackgroundResource(R.color.white);
                        time_container_1.setBackgroundResource(R.color.white);
                    }else {
                        booking_date_2.setBackgroundResource(R.color.white);
                        time_container_2.setBackgroundResource(R.color.white);
                    }
                    booking_date_3.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_container_3.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    date_sel = 2;
                    np.setMaxValue(temp_detail_exp.getAvailable_options().get(2).getAvailable_seat());
                }
            }
        });
        booking_time_1 = (TextView)view.findViewById(R.id.booking_time_txt1);
        booking_time_2 = (TextView)view.findViewById(R.id.booking_time_txt2);
        booking_time_3 = (TextView)view.findViewById(R.id.booking_time_txt3);
        time_container_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_sel != 0) {
                    if(time_sel == 1){
                        booking_date_2.setBackgroundResource(R.color.white);
                        time_container_2.setBackgroundResource(R.color.white);
                }else {
                        booking_date_3.setBackgroundResource(R.color.white);
                        time_container_3.setBackgroundResource(R.color.white);
                    }
                    booking_date_1.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_container_1.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_sel = 0;
                }
            }
        });

        time_container_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_sel != 1) {
                    if(time_sel == 2){
                        booking_date_3.setBackgroundResource(R.color.white);
                        time_container_3.setBackgroundResource(R.color.white);
                    }else {
                        booking_date_1.setBackgroundResource(R.color.white);
                        time_container_1.setBackgroundResource(R.color.white);
                    }
                    time_container_2.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    booking_date_2.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_sel = 1;
                }
            }
        });
        time_container_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time_sel != 2) {
                    if(time_sel == 0){
                        booking_date_1.setBackgroundResource(R.color.white);
                        time_container_1.setBackgroundResource(R.color.white);
                    }else {
                        time_container_2.setBackgroundResource(R.color.white);
                        booking_date_2.setBackgroundResource(R.color.white);
                    }
                    booking_date_3.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_container_3.setBackgroundResource(R.color.tripalocal_selection_highlight);
                    time_sel = 2;
                }
            }
        });

        booking_price = (TextView) view.findViewById(R.id.booking_price);
        booking_guest_number = (TextView) view.findViewById(R.id.booking_guest_number);
        booking_price_and_person_amt = (TextView) view.findViewById(R.id.booking_price_total_amt_txt);
        np = (NumberPicker) view.findViewById(R.id.numberPicker1);
        np.setMinValue(1);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                guests = newVal;
                booking_price.setText(price_s);
                booking_guest_number.setText(String.valueOf(guests));
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
        refund = (TextView) view.findViewById(R.id.booking_refund_txt);

        if(CheckoutActivity.position != 999){
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(getResources().getString(R.string.server_url))
                    .build();
            ApiService apiService = restAdapter.create(ApiService.class);
            ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_contacting));
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
                    ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_error));
                }
            });
        }
        return view;
    }

    public void updateDetails(){
        temp_detail_exp = CheckoutActivity.experience_to_book;
        if(temp_detail_exp != null){
            Glide.with(HomeActivity.getHome_context()).load(ExpDetailActivityFragment.BASE_URL+
                    "thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(thumbnail);
            title.setText(temp_detail_exp.getExperience_title());
            duration.setText(temp_detail_exp.getExperience_duration().toString()+" hrs . ");
            String[] language = temp_detail_exp.getLanguage()!=null?temp_detail_exp.getLanguage().split(";"):new String[1];
            String l= "";
            for(int i=0;language!=null && i<language.length;i++)
            {
                switch(language[i]) {
                    case "english": l = "English";
                    case "english;mandarin": l = "English / 中文";
                }
            }
            language_txt.setText(l);

            if(temp_detail_exp.getAvailable_options().get(0).isInstantBooking()){
                if(temp_detail_exp.getAvailable_options().size() >= 3){
                    booking_date_1.setText(temp_detail_exp.getAvailable_options().get(0).getDate_string());
                    booking_date_2.setText(temp_detail_exp.getAvailable_options().get(1).getDate_string());
                    booking_date_3.setText(temp_detail_exp.getAvailable_options().get(2).getDate_string());
                    booking_time_1.setText(temp_detail_exp.getAvailable_options().get(0).getTime_string());
                    booking_time_2.setText(temp_detail_exp.getAvailable_options().get(1).getTime_string());
                    booking_time_3.setText(temp_detail_exp.getAvailable_options().get(2).getTime_string());
                }
            }else{
                booking_row_3.setVisibility(View.GONE);
                booking_row_4.setVisibility(View.GONE);
                booking_row_5.setVisibility(View.GONE);
            }
            price_i = temp_detail_exp.getExperience_price();
            price_s = REAL_FORMATTER.format(temp_detail_exp.getExperience_price());
            booking_price.setText(price_s);
            booking_guest_number.setText(String.valueOf(guests));
            booking_price_and_person_amt.setText(REAL_FORMATTER.format(price_i*guests));
            np.setMaxValue(temp_detail_exp.getAvailable_options().get(0).getAvailable_seat());
            List<String> temp_dates = new ArrayList<>();
            List<String> temp_times = new ArrayList<>();
            for(AvailableOption option : temp_detail_exp.getAvailable_options()){
                temp_dates.add(option.getDate_string());
                temp_times.add(option.getTime_string());
            }
            ArrayAdapter<String> date_adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_tp, temp_dates);
            ArrayAdapter<String> time_adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_tp, temp_times);
            date_spin.setAdapter(date_adapter);
            time_spin.setAdapter(time_adapter);
            refund.setMovementMethod(LinkMovementMethod.getInstance());
            String text = "<a href='https://www.tripalocal.com/refundpolicy'>"+getResources().getString(R.string.checkout_refund_link)+" </a>";
            refund.setText(Html.fromHtml(text));
        }
    }

}
