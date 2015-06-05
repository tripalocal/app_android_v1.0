package tripalocal.com.au.tripalocalbeta.Views;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckoutActivityFragment extends Fragment {



    public CheckoutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        Button bookingBtn = (Button) view.findViewById(R.id.booking_bottom_Btn);
        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.shortToast("Making booking request now...");
                String data = "{\"itinerary_string\":[{\"id\":\"1\",\"date\":\"2015/04/17\"," +
                        "\"time\":\"4:00 - 6:00\",\"guest_number\":\"2\"},{\"id\":\"20\",\"date\":" +
                        "\"2015/04/17\",\"time\":\"17:00 - 20:00\",\"guest_number\":\"2\"}]," +
                        "\"card_number\":\"4242424242424242\",\"expiration_month\":10," +
                        "\"expiration_year\":2015,\"cvv\":123}";
                RestAdapter restAdapter = new RestAdapter.Builder()
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setEndpoint("http://adventure007.cloudapp.net")//https://www.tripalocal.com"
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade request) {
                                request.addHeader("Accept", "application/json");
                                request.addHeader("Authorization", "Token " + HomeActivity.getCurrent_user().getLogin_token());
                            }
                        })
                        .build();
                ApiService apiService = restAdapter.create(ApiService.class);
                apiService.bookExperience(data, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        ToastHelper.shortToast("Booking Success!!");
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ToastHelper.warnToast("Booking Failed!!");
                    }
                });
            }
        });


        ImageView thumbnail = (ImageView) view.findViewById(R.id.booking_thumbnail);
        Glide.with(HomeActivity.getHome_context()).load(ExpDetailActivityFragment.BASE_URL+"thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(thumbnail);
        TextView title = (TextView) view.findViewById(R.id.booking_title);
        title.setText(CheckoutActivity.experience_to_book.getExperience_title().toString());
        TextView duration = (TextView) view.findViewById(R.id.booking_duration);
        title.setText(CheckoutActivity.experience_to_book.getExperience_duration().toString());
        // ### option to change language in future
        //TextView language = (TextView) view.findViewById(R.id.booking_language);
        //title.setText(CheckoutActivity.experience_to_book.ge);
        return view;
    }
}
