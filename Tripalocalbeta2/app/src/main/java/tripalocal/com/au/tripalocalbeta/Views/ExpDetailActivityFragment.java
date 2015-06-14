package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.adapters.ReviewAdapter;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.ExperienceReview;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.request;

import static tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter.INT_EXTRA;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpDetailActivityFragment extends Fragment {


    public static final String BASE_URL ="https://www.tripalocal.com/images/";
    private static Experience_Detail exp_to_display;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");


    ImageView exp_bg;
    CircleImageView profileImage;
    CircleImageView profileHostImage;
    TextView exp_host_name;
    TextView exp_detail_lang;
    TextView price_title;
    TextView price_hours;
    TextView info_title;
    TextView info_less;
    TextView info_more;
    Button info_view_more_btn;
    TextView host_title;
    TextView host_info_less;
    TextView host_info_more;
    TextView review_title;
    ImageView rating_str_1;
    ImageView rating_str_2;
    ImageView rating_str_3;
    ImageView rating_str_4;
    ImageView rating_str_5;
    CircleImageView reviewProfileImage;
    TextView review_username;
    TextView review_content_less;
    Button review_more_btn;
    Button host_more_btn;
    ImageView expenses_banner_img;
    Button request_to_book_btn;
    TextView food_info;
    TextView transport_info;
    TextView tickets_info;



    public ExpDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exp_detail, container, false);
        exp_bg = (ImageView) view.findViewById(R.id.exp_detail_bg);
        profileImage = (CircleImageView) view.findViewById(R.id.exp_detail_profile_image);
        profileHostImage = (CircleImageView) view.findViewById(R.id.exp_detail_host_profile_image);
        exp_host_name = (TextView) view.findViewById(R.id.exp_host_name);
        exp_detail_lang = (TextView) view.findViewById(R.id.exp_detail_lang);
        price_title = (TextView) view.findViewById(R.id.exp_detail_price2);
        price_hours = (TextView) view.findViewById(R.id.exp_detail_price_person);
        info_title = (TextView) view.findViewById(R.id.exp_detail_info_title);
        info_less = (TextView) view.findViewById(R.id.exp_detail_info_content_less);
        info_more = (TextView) view.findViewById(R.id.exp_detail_info_content_more);
        host_info_less = (TextView) view.findViewById(R.id.exp_detail_host_info_less);
        host_info_more = (TextView) view.findViewById(R.id.exp_detail_host_info_more);
        host_title = (TextView) view.findViewById(R.id.exp_detail_about_host_title);
        review_title = (TextView) view.findViewById(R.id.exp_detail_review_title);
        reviewProfileImage = (CircleImageView) view.findViewById(R.id.exp_detail_review_profile_image);
        review_username = (TextView) view.findViewById(R.id.exp_detail_review_reviewername);
        review_content_less = (TextView) view.findViewById(R.id.exp_detail_review_content_less);
        rating_str_1 = (ImageView) view.findViewById(R.id.exp_detail_review_star_1);
        rating_str_2 = (ImageView) view.findViewById(R.id.exp_detail_review_star_2);
        rating_str_3 = (ImageView) view.findViewById(R.id.exp_detail_review_star_3);
        rating_str_4 = (ImageView) view.findViewById(R.id.exp_detail_review_star_4);
        rating_str_5 = (ImageView) view.findViewById(R.id.exp_detail_review_star_5);
        expenses_banner_img = (ImageView) view.findViewById(R.id.exp_detail_add_expenses_banner);
        food_info = (TextView) view.findViewById(R.id.exp_detail_grid_food_info);
        transport_info = (TextView) view.findViewById(R.id.exp_detail_grid_transport_info);
        tickets_info = (TextView) view.findViewById(R.id.exp_detail_grid_ticket_info);
        request_to_book_btn = (Button) view.findViewById(R.id.exp_detail_booking_btn);


        request_to_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.getHome_context(), CheckoutActivity.class);
                intent.putExtra(INT_EXTRA,ExpDetailActivity.position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            }
        });

        info_view_more_btn = (Button) view.findViewById(R.id.exp_detail_info_view_more_btn);
        info_view_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(info_less.getVisibility() == View.GONE){
                    info_view_more_btn.setText(getActivity().getResources().getString(R.string.view_more));
                    info_less.setVisibility(View.VISIBLE);
                    info_more.setVisibility(View.GONE);
                }else{
                    info_view_more_btn.setText(getActivity().getResources().getString(R.string.view_less));
                    info_less.setVisibility(View.GONE);
                    info_more.setVisibility(View.VISIBLE);
                }
            }
        });

        review_more_btn = (Button) view.findViewById(R.id.exp_detail_review_view_more_btn);
        review_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ReviewAdapter.reviewsList = (ArrayList<ExperienceReview>) exp_to_display.getExperience_reviews();
                    Intent intent = new Intent(getActivity().getApplicationContext(),ReviewActivity.class);
                    startActivity(intent);
            }
        });


        host_more_btn = (Button) view.findViewById(R.id.exp_detail_host_view_more_btn);
        host_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(host_info_less.getVisibility() == View.GONE){
                   host_info_less.setVisibility(View.VISIBLE);
                   host_info_more.setVisibility(View.GONE);
                   host_more_btn.setText(getResources().getString(R.string.view_more));
               }else{
                   host_info_less.setVisibility(View.GONE);
                   host_info_more.setVisibility(View.VISIBLE);
                   host_more_btn.setText(getResources().getString(R.string.view_less));
               }
            }
        });
        /*if(CheckoutActivity.experience_to_book != null){
            exp_to_display = CheckoutActivity.experience_to_book;
            fillDetails();
        }else*/
            getExpDetails(ExpDetailActivity.position);
        return view;
    }


    public void getExpDetails(int exp_id){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        ToastHelper.longToast(getActivity().getResources().getString(R.string.toast_contacting));
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
                ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_error));
            }
        });
    }

    //public void fillDetails(ImageView exp_bg, CircleImageView profileImage, CircleImageView profileHostImage, TextView exp_host_name, TextView price_title, TextView price_hours, final TextView info_less, final TextView info_more, TextView host_info_less, TextView review_title, CircleImageView reviewProfileImage, TextView review_username, TextView review_content_less, ImageView expenses_banner_img, Experience_Detail exp_to_display) {
    public void fillDetails(){
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+"thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(exp_bg);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+"thumbnails/experiences/experience" + ExpDetailActivity.position+ "_1.jpg").fitCenter().into(expenses_banner_img);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileImage);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileHostImage);

        exp_host_name.setText(" " + exp_to_display.getHost_firstname() + " " + exp_to_display.getHost_lastname().substring(0, 1) + ".");
        String[] language = exp_to_display.getLanguage()!=null?exp_to_display.getLanguage().split(";"):new String[1];
        String l= "";
        for(int i=0;language!=null && i<language.length;i++)
        {
            switch(language[i]) {
                case "english": l = "English";
                case "english;mandarin": l = "English / 中文";
            }
        }
        exp_detail_lang.setText(l);
        price_title.setText(REAL_FORMATTER.format(exp_to_display.getExperience_price()));
        price_hours.setText(REAL_FORMATTER.format(exp_to_display.getExperience_duration()));
        info_title.setText(exp_to_display.getExperience_title());
        info_less.setText(exp_to_display.getExperience_description());
        info_more.setText(exp_to_display.getExperience_description());
        host_title.setText("About the Host, " +exp_to_display.getHost_firstname());
        host_info_less.setText(exp_to_display.getHost_bio());
        host_info_more.setText(exp_to_display.getHost_bio());
        review_title.setText(exp_to_display.getExperience_reviews().size()+" Reviews");
        if(exp_to_display.getExperience_reviews().isEmpty()){
            review_more_btn.setVisibility(View.INVISIBLE);
        }
        int rate = Math.round(exp_to_display.getExperience_rate());
        if(rate < 5){
            if(rate <=4){
                rating_str_5.setVisibility(View.GONE);
                if(rate <=3) {
                    rating_str_4.setVisibility(View.GONE);
                    if(rate <=2){
                        rating_str_3.setVisibility(View.GONE);
                        if(rate <=1){
                            rating_str_2.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
        if(exp_to_display.getExperience_reviews().size() > 0){
            ExperienceReview top_review  = exp_to_display.getExperience_reviews().get(0);
            if(top_review.getReviewer_image().length() > 2)
                Glide.with(HomeActivity.getHome_context()).load(BASE_URL+top_review.getReviewer_image()).fitCenter().into(reviewProfileImage);
            review_username.setText(top_review.getReviewer_firstname());
            review_content_less.setText(top_review.getReview_comment());
        }
        if(exp_to_display.isIncludedFood()){
            food_info.setText("Food : "+exp_to_display.getIncluded_food_detail());
        }
        if(exp_to_display.isIncludedTransport()){
            transport_info.setText("Transport : "+exp_to_display.getIncluded_transport_detail());
        }
        if(exp_to_display.isIncludedTicket()){
            tickets_info.setText("Tickets : "+exp_to_display.getIncluded_ticket_detail());
        }
        getActivity().setTitle(exp_to_display.getExperience_title());
    }
}
