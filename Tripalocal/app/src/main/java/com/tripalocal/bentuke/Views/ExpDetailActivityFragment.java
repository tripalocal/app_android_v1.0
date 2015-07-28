package com.tripalocal.bentuke.Views;

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
import com.squareup.okhttp.OkHttpClient;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.helpers.dbHelper.ChatMsgDataSource;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;
import com.umeng.analytics.MobclickAgent;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ReviewAdapter;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.Tripalocal;
import com.tripalocal.bentuke.models.exp_detail.ExperienceReview;
import com.tripalocal.bentuke.models.exp_detail.Experience_Detail;
import com.tripalocal.bentuke.models.exp_detail.request;

import static com.tripalocal.bentuke.adapters.ExperienceListAdapter.INT_EXTRA;

/**
 * A placeholder fragment containing a simple view.
 */
public class ExpDetailActivityFragment extends Fragment {


    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";
    private static Experience_Detail exp_to_display;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");
    public static OkHttpClient ok_client;

    request req;
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
    Button request_to_book_btn,send_msg_btn;
    TextView food_info;
    TextView transport_info;
    TextView tickets_info;
    View review_container;



    public ExpDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateYoumeng();
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
        send_msg_btn=(Button)view.findViewById(R.id.send_msg_btn);
        request_to_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HomeActivity.getCurrent_user().isLoggedin()){
                    Intent intent = new Intent(HomeActivity.getHome_context(), CheckoutActivity.class);
                    intent.putExtra(INT_EXTRA,ExpDetailActivity.position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    HomeActivity.getHome_context().startActivity(intent);
                }else{
                    /*
                    getFragmentManager().beginTransaction().addToBackStack("login")
                            .replace(R.id.detail_frag_container, new LoginFragment()).commit();
                    Intent intent=new Intent(HomeActivity.getHome_context(),PhoneregisterActivity.class);
                    intent.putExtra(INT_EXTRA,ExpDetailActivity.position);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    HomeActivity.getHome_context().startActivity(intent);*/
                    ToastHelper.warnToast(getResources().getString(R.string.exp_detail_log_in_msg));
                }
            }
        });

        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(HomeActivity.getCurrent_user().isLoggedin()){
                    Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    ChatActivity.sender_id=exp_to_display.getHost_id();//set exp id
//                    ChatActivity.sender_name=exp_to_display.getHost_firstname();//set exp name
//                    ChatActivity.sender_img=exp_to_display.getHost_image();
                    intent.putExtra(ChatActivity.COL_SENDER_ID,exp_to_display.getHost_id());
                    intent.putExtra(ChatActivity.COL_SENDER_NAME,exp_to_display.getHost_firstname());
                    intent.putExtra(ChatActivity.COL_SENDER_IMG,exp_to_display.getHost_image());

//                    ChatActivity
                    HomeActivity.getHome_context().startActivity(intent);
                }else{

                    ToastHelper.warnToast(getResources().getString(R.string.exp_detail_log_in_msg));
                }


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
        review_container = view.findViewById(R.id.exp_detail_review_container);
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
                if (host_info_less.getVisibility() == View.GONE) {
                    host_info_less.setVisibility(View.VISIBLE);
                    host_info_more.setVisibility(View.GONE);
                    host_more_btn.setText(getResources().getString(R.string.view_more));
                } else {
                    host_info_less.setVisibility(View.GONE);
                    host_info_more.setVisibility(View.VISIBLE);
                    host_more_btn.setText(getResources().getString(R.string.view_less));
                }
            }
        });
        request_to_book_btn.setEnabled(false);
        send_msg_btn.setEnabled(false);

        getExpDetails(ExpDetailActivity.position);

        return view;
    }


    public void getExpDetails(int exp_id){
        GeneralHelper.showLoadingProgress(getActivity());
        ok_client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(ok_client))
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
//        ToastHelper.longToast(getResources().getString(R.string.toast_contacting));
        Gson gson = new Gson();
        req = new request(exp_id);
        apiService.getExpDetails(req, new Callback<Experience_Detail>() {
            @Override
            public void success(Experience_Detail experience_detail, Response response) {
                GeneralHelper.closeLoadingProgress();
                if(req.getExperience_id() > 0) {
                    exp_to_display = experience_detail;
                    fillDetails();
                    request_to_book_btn.setEnabled(true);
                    send_msg_btn.setEnabled(true);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

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

        exp_host_name.setText(" " + Tripalocal.getFullName(exp_to_display.getHost_firstname(), exp_to_display.getHost_lastname()));
        String[] language = exp_to_display.getLanguage()!=null?exp_to_display.getLanguage().split(";"):new String[1];
        String l= "";
        for(int i=0;language!=null && i<language.length;i++)
        {
            switch(language[i]) {
                case "english": l = "English";break;
                case "mandarin": l += " / 中文";break;
            }
        }
        exp_detail_lang.setText(l);

        //price_title.setText(REAL_FORMATTER.format(exp_to_display.getExperience_price()));
        //if guest_number_min <= 4 && guest_number_max >= 4, show the price for group of size 4;
        //if guest_number_max < 4 , show the price for group of size guest_number_max;
        //if guest_number_min > 4, show the price for group of size guest_number_min

        Float[] temp_dp = exp_to_display.getExperience_dynamic_price();
        int min_number = exp_to_display.getExperience_guest_number_min();
        int max_number = exp_to_display.getExperience_guest_number_max();

        if(temp_dp.length > 0 && max_number >= min_number && temp_dp.length == max_number-min_number+1){
            if (min_number <= 4 && max_number >= 4) {
                if(max_number - min_number >= 3) {
                    price_title.setText(REAL_FORMATTER.format(temp_dp[3]));
                }
                else {
                    price_title.setText(REAL_FORMATTER.format(temp_dp[max_number - min_number]));
                }
            } else if (max_number < 4) {
                price_title.setText(REAL_FORMATTER.format(temp_dp[max_number]));
            } else if (min_number > 4) {
                price_title.setText(REAL_FORMATTER.format(temp_dp[0]));
            }
        }else price_title.setText(REAL_FORMATTER.format(exp_to_display.getExperience_price()));


        price_hours.setText(REAL_FORMATTER.format(exp_to_display.getExperience_duration()));
        info_title.setText(exp_to_display.getExperience_title());
        info_less.setText(exp_to_display.getExperience_description()+"\n\n"+exp_to_display.getExperience_activity()+"\n\n"+exp_to_display.getExperience_interaction());
        info_more.setText(exp_to_display.getExperience_description()+"\n\n"+exp_to_display.getExperience_activity()+"\n\n"+exp_to_display.getExperience_interaction());
        host_title.setText(Tripalocal.getFullName(exp_to_display.getHost_firstname(), exp_to_display.getHost_lastname()));
        host_info_less.setText(exp_to_display.getHost_bio());
        host_info_more.setText(exp_to_display.getHost_bio());
        if(exp_to_display.getHost_bio().length()<120)
        {
            host_more_btn.setVisibility(View.INVISIBLE);
        }
        review_title.setText(String.valueOf(exp_to_display.getExperience_reviews().size()));
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
            review_username.setText(Tripalocal.getFullName(top_review.getReviewer_firstname(), top_review.getReviewer_lastname()));
            review_content_less.setText(top_review.getReview_comment());
        }else{
            review_container.setVisibility(View.GONE);
        }
        if(exp_to_display.isIncludedFood()){
            food_info.setText(exp_to_display.getIncluded_food_detail());
        }
        if(exp_to_display.isIncludedTransport()){
            transport_info.setText(exp_to_display.getIncluded_transport_detail());
        }
        if(exp_to_display.isIncludedTicket()){
            tickets_info.setText(exp_to_display.getIncluded_ticket_detail());
        }
        getActivity().setTitle(exp_to_display.getExperience_title());
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_expDetail)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_expDetail));
    }

    public void onStop() {
        super.onStop();
        if(ok_client != null) {
            req.setExperience_id(-1);
            ok_client.cancel(req);
        }
    }

    public void updateYoumeng(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(getResources().getString(R.string.youmeng_event_item_cityId),ExperiencesListFragment.city_position+"");
        map.put(getResources().getString(R.string.youmeng_event_item_expId),ExpDetailActivity.position+"");
        MobclickAgent.onEvent(getActivity().getApplicationContext(), getResources().getString(R.string.youmeng_event_title_viewExp), map);
    }


}
