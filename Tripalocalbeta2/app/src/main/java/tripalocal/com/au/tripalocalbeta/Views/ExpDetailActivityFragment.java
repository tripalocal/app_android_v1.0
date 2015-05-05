package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.ExperienceReview;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.request;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpDetailActivityFragment extends Fragment {

    private static final String BASE_URL ="https://www.tripalocal.com/images/";
    private static Experience_Detail exp_to_display;
    ImageView exp_bg;
    CircleImageView profileImage;
    CircleImageView profileHostImage;
    TextView exp_title;
    TextView price_title;
    TextView price_hours;
    TextView info_less;
    TextView info_more;
    TextView host_name;
    TextView host_info_less;
    TextView review_title;
    CircleImageView reviewProfileImage;
    TextView review_username;
    TextView review_content_less;
    ImageView expenses_banner_img;

    public ExpDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exp_detail, container, false);
        exp_bg = (ImageView) view.findViewById(R.id.exp_detail_bg);
         profileImage = (CircleImageView) view.findViewById(R.id.exp_detail_profile_image);
         profileHostImage = (CircleImageView) view.findViewById(R.id.exp_detail_host_profile_image);
         exp_title = (TextView) view.findViewById(R.id.exp_detail_title);
         price_title = (TextView) view.findViewById(R.id.exp_detail_price2);
         price_hours = (TextView) view.findViewById(R.id.exp_detail_price_person);
          info_less = (TextView) view.findViewById(R.id.exp_detail_info_content_less);
          info_more = (TextView) view.findViewById(R.id.exp_detail_info_content_more);
         host_info_less = (TextView) view.findViewById(R.id.exp_detail_host_info_less);
         host_name = (TextView) view.findViewById(R.id.exp_detail_hostname);
         review_title = (TextView) view.findViewById(R.id.exp_detail_review_title);
         reviewProfileImage = (CircleImageView) view.findViewById(R.id.exp_detail_review_profile_image);
         review_username = (TextView) view.findViewById(R.id.exp_detail_review_reviewername);
         review_content_less = (TextView) view.findViewById(R.id.exp_detail_review_content_less);
         expenses_banner_img = (ImageView) view.findViewById(R.id.exp_detail_add_expenses_banner);

        getExpDetails(ExpDetailActivity.position);
        return view;
    }

    public void getExpDetails(int exp_id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://adventure007.cloudapp.net/")
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        ToastHelper.longToast("Contacting Server...");
        Gson gson = new Gson();
        request req = new request(exp_id);
        apiService.getExpDetails(req, new Callback<Experience_Detail>() {
            @Override
            public void success(Experience_Detail experience_detail, Response response) {
                exp_to_display = experience_detail;
                fillDetails();
            }

            @Override
            public void failure(RetrofitError error) {
                ToastHelper.errorToast("Error occurred");
            }
        });
    }

    //public void fillDetails(ImageView exp_bg, CircleImageView profileImage, CircleImageView profileHostImage, TextView exp_title, TextView price_title, TextView price_hours, final TextView info_less, final TextView info_more, TextView host_info_less, TextView review_title, CircleImageView reviewProfileImage, TextView review_username, TextView review_content_less, ImageView expenses_banner_img, Experience_Detail exp_to_display) {
    public void fillDetails(){
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+"thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(exp_bg);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+"thumbnails/experiences/experience" + ExpDetailActivity.position+ "_2.jpg").fitCenter().into(expenses_banner_img);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileImage);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileHostImage);

        exp_title.setText(exp_to_display.getExperience_title());
        price_title.setText(" $"+exp_to_display.getExperience_price());
        if (exp_to_display.getExperience_duration() > 1)
        price_hours.setText("per person for "+exp_to_display.getExperience_duration()+"hrs");
        else
        price_hours.setText("per person for "+exp_to_display.getExperience_duration()+"hr");

        info_less.setText(exp_to_display.getExperience_description());
        /*info_more.setText(exp_to_display.getExperience_description());
        info_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info_less.setVisibility(View.INVISIBLE);
                info_more.setVisibility(View.VISIBLE);
            }
        });*/

        host_name.setText(exp_to_display.getHost_firstname());
        host_info_less.setText(exp_to_display.getHost_bio());
        review_title.setText(exp_to_display.getExperience_reviews().size()+" Reviews");
        ExperienceReview top_review  = exp_to_display.getExperience_reviews().get(0);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+top_review.getReviewer_image()).fitCenter().into(reviewProfileImage);
        review_username.setText(top_review.getReviewer_firstname());
        review_content_less.setText(top_review.getReview_comment());
    }
}
