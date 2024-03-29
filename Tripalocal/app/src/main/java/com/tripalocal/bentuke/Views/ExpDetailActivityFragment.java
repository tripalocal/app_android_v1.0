package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.models.exp_detail.Local_Experience_Detail;
import com.tripalocal.bentuke.models.exp_detail.RelatedExperience;
import com.umeng.analytics.MobclickAgent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

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
public class ExpDetailActivityFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mDemoSlider;

    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";
    private static Experience_Detail exp_to_display;
    private static Local_Experience_Detail local_exp_to_display;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");
    public static OkHttpClient ok_client;

    request req;
    ImageView exp_bg;//local
    CircleImageView profileImage;
    CircleImageView profileHostImage;
    TextView exp_host_name;
    TextView exp_detail_lang;
    TextView price_title;
    TextView price_hours;
    TextView exp_header;
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
    LinearLayout exp_reservation_with;
    RelativeLayout exp_detail_host_profile_image_container;
    TextView btn_view_schedule,btn_view_tips,btn_view_include,btn_view_pickup,btn_view_disclaimer,
            btn_view_refund,btn_view_insurance;
    TextView tx_schedule,tx_tips,tx_include,tx_pickup,tx_disclaimer,tx_refund,tx_insurance;
    //TextView exp_detail_pickup,exp_detail_included,exp_detail_tips;
    TextView local_overview,local_description,local_highlights;
    LinearLayout layout_schedule,layout_tips,layout_include,layout_pickup,layout_disclaimer,layout_refund,layout_insurance;
    FrameLayout layout_title_schedule,layout_title_tips,layout_title_include,layout_title_pickup,layout_title_disclaimer,
            layout_title_refund,layout_title_insurance;

    ImageView relatedExp_img_1,relatedExp_img_2,relatedExp_img_3;
    TextView relatedExp_title_1,relatedExp_title_2,relatedExp_title_3,
            relatedExp_price_1,relatedExp_price_2,relatedExp_price_3,
            relatedExp_duration_1,relatedExp_duration_2,relatedExp_duration_3,
            relatedExp_language_1,relatedExp_language_2,relatedExp_language_3;
    LinearLayout related_exp_layout_1,related_exp_layout_2,related_exp_layout_3;
    TextView exp_popularity;
    public ExpDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updateYoumeng();
        View view;
        if(ExperiencesListFragment.experience_type!=ExperiencesListFragment.exp_newPro) {
            view = inflater.inflate(R.layout.fragment_exp_detail, container, false);
            mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
            initialComponenets(view);
            initController();
            getExpDetails(ExpDetailActivity.position);
        }else{
            view = inflater.inflate(R.layout.fragment_local_exp_detail_v2, container, false);
            mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
            initComponentLocal(view);
            initControllerLocal();
            getLocalExpDetails(ExpDetailActivity.position);
        }

        GeneralHelper.addMixPanelData(this.getActivity(), this.getResources().getString(R.string.mixpanel_event_viewExperiencePage));

        return view;
    }

    public void updateImageGallery(ArrayList<String> url_list){

        for (String name : url_list) {
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity().getApplicationContext());
            // initialize a SliderLayout
            textSliderView
                    .image(BASE_URL+name)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setDuration(10000);
        mDemoSlider.addOnPageChangeListener(this);
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
        //ToastHelper.longToast(getResources().getString(R.string.toast_contacting));
        Gson gson = new Gson();
        req = new request(exp_id);
        apiService.getExpDetails(req, new Callback<Object>() {
            @Override
            public void success(Object experience_detail, Response response) {
                GeneralHelper.closeLoadingProgress();
                if (req.getExperience_id() > 0) {
                    try
                    {
                        exp_to_display = new Gson().fromJson(new String(((TypedByteArray) response.getBody()).getBytes(), "UTF-8"), Experience_Detail.class);
                    }
                    catch(java.io.UnsupportedEncodingException ex)
                    {
                        exp_to_display = (Experience_Detail)experience_detail;
                    }
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

    public void getLocalExpDetails(int exp_id){
        GeneralHelper.showLoadingProgress(getActivity());
        ok_client = new OkHttpClient();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(ok_client))
                .setEndpoint(getActivity().getResources().getString(R.string.server_url))
                .build();
        ApiService apiService = restAdapter.create(ApiService.class);
        //ToastHelper.longToast(getResources().getString(R.string.toast_contacting));
        Gson gson = new Gson();
        req = new request(exp_id);
        apiService.getExpDetails(req, new Callback<Object>() {
            @Override
            public void success(Object experience_detail, Response response) {
                GeneralHelper.closeLoadingProgress();
                if (req.getExperience_id() > 0) {
                    try {
                        local_exp_to_display = new Gson().fromJson(new String(((TypedByteArray) response.getBody()).getBytes(), "UTF-8"), Local_Experience_Detail.class);
                    } catch (java.io.UnsupportedEncodingException ex) {
                        local_exp_to_display = (Local_Experience_Detail)experience_detail;
                    }
                    fillLocalDetails();
                    request_to_book_btn.setEnabled(true);
                    //System.out.println("urgent " + local_exp_to_display.getExperience_popularity());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();
                //System.out.println("error exp detail :"+error.getMessage().toString());
                ToastHelper.errorToast(getActivity().getResources().getString(R.string.toast_error));
            }
        });
    }

    public void initialComponenets(View view){
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
        info_view_more_btn = (Button) view.findViewById(R.id.exp_detail_info_view_more_btn);
        review_container = view.findViewById(R.id.exp_detail_review_container);
        review_more_btn = (Button) view.findViewById(R.id.exp_detail_review_view_more_btn);
        host_more_btn = (Button) view.findViewById(R.id.exp_detail_host_view_more_btn);
        exp_reservation_with=(LinearLayout)view.findViewById(R.id.exp_reservation_with);
        exp_detail_host_profile_image_container=(RelativeLayout)view.findViewById(R.id.exp_detail_host_profile_image_container);
    }

    public void initComponentLocal(View view){
        exp_bg = (ImageView) view.findViewById(R.id.exp_detail_bg);
        profileImage = (CircleImageView) view.findViewById(R.id.exp_detail_profile_image);
        exp_detail_lang = (TextView) view.findViewById(R.id.exp_detail_lang);
        price_title = (TextView) view.findViewById(R.id.exp_detail_price2);
        price_hours = (TextView) view.findViewById(R.id.exp_detail_price_person);
        info_title = (TextView) view.findViewById(R.id.exp_detail_info_title);
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
        request_to_book_btn = (Button) view.findViewById(R.id.exp_detail_booking_btn);
        review_container = view.findViewById(R.id.exp_detail_review_container);
        review_more_btn = (Button) view.findViewById(R.id.exp_detail_review_view_more_btn);
        exp_reservation_with=(LinearLayout)view.findViewById(R.id.exp_reservation_with);
        exp_detail_host_profile_image_container=(RelativeLayout)view.findViewById(R.id.exp_detail_host_profile_image_container);
        exp_header = (TextView)view.findViewById((R.id.exp_header));
        local_description=(TextView)view.findViewById(R.id.local_description);
        local_overview=(TextView)view.findViewById(R.id.local_overview);
        local_highlights=(TextView)view.findViewById(R.id.local_highlights);

        btn_view_schedule=(TextView)view.findViewById(R.id.btn_view_schedule);
        btn_view_tips=(TextView)view.findViewById(R.id.btn_view_tips);
        btn_view_include=(TextView)view.findViewById(R.id.btn_view_include);
        btn_view_pickup=(TextView)view.findViewById(R.id.btn_view_pickup);
        btn_view_disclaimer=(TextView)view.findViewById(R.id.btn_view_disclaimer);
        btn_view_refund=(TextView)view.findViewById(R.id.btn_view_refund);
        btn_view_insurance=(TextView)view.findViewById(R.id.btn_view_insurance);

        tx_schedule=(TextView)view.findViewById(R.id.tx_schedule);
        tx_tips=(TextView)view.findViewById(R.id.tx_tips);
        tx_include=(TextView)view.findViewById(R.id.tx_include);
        tx_pickup=(TextView)view.findViewById(R.id.tx_pickup);
        tx_disclaimer=(TextView)view.findViewById(R.id.tx_disclaimer);
        tx_refund=(TextView)view.findViewById(R.id.tx_refund);
        tx_insurance=(TextView)view.findViewById(R.id.tx_insurance);

        layout_schedule=(LinearLayout)view.findViewById(R.id.layout_schedule);
        layout_tips=(LinearLayout)view.findViewById(R.id.layout_tips);
        layout_include=(LinearLayout)view.findViewById(R.id.layout_include);
        layout_pickup=(LinearLayout)view.findViewById(R.id.layout_pickup);
        layout_disclaimer=(LinearLayout)view.findViewById(R.id.layout_disclaimer);
        layout_refund=(LinearLayout)view.findViewById(R.id.layout_refund);
        layout_insurance=(LinearLayout)view.findViewById(R.id.layout_insurance);

        layout_title_schedule=(FrameLayout)view.findViewById(R.id.layout_title_schedule);
        layout_title_tips=(FrameLayout)view.findViewById(R.id.layout_title_tips);
        layout_title_include=(FrameLayout)view.findViewById(R.id.layout_title_include);
        layout_title_pickup=(FrameLayout)view.findViewById(R.id.layout_title_pickup);
        layout_title_disclaimer=(FrameLayout)view.findViewById(R.id.layout_title_disclaimer);
        layout_title_refund=(FrameLayout)view.findViewById(R.id.layout_title_refund);
        layout_title_insurance=(FrameLayout)view.findViewById(R.id.layout_title_insurance);

        //initial related exp content
        relatedExp_img_1=(ImageView)view.findViewById(R.id.relatedExp_img_1);
        relatedExp_title_1=(TextView)view.findViewById(R.id.relatedExp_title_1);
        relatedExp_price_1=(TextView)view.findViewById(R.id.relatedExp_price_1);
        relatedExp_duration_1=(TextView)view.findViewById(R.id.relatedExp_duration_1);
        relatedExp_language_1=(TextView)view.findViewById(R.id.relatedExp_language_1);
        related_exp_layout_1=(LinearLayout)view.findViewById(R.id.related_exp_layout_1);

        relatedExp_img_2=(ImageView)view.findViewById(R.id.relatedExp_img_2);
        relatedExp_title_2=(TextView)view.findViewById(R.id.relatedExp_title_2);
        relatedExp_price_2=(TextView)view.findViewById(R.id.relatedExp_price_2);
        relatedExp_duration_2=(TextView)view.findViewById(R.id.relatedExp_duration_2);
        relatedExp_language_2=(TextView)view.findViewById(R.id.relatedExp_language_2);
        related_exp_layout_2=(LinearLayout)view.findViewById(R.id.related_exp_layout_2);

        relatedExp_img_3=(ImageView)view.findViewById(R.id.relatedExp_img_3);
        relatedExp_title_3=(TextView)view.findViewById(R.id.relatedExp_title_3);
        relatedExp_price_3=(TextView)view.findViewById(R.id.relatedExp_price_3);
        relatedExp_duration_3=(TextView)view.findViewById(R.id.relatedExp_duration_3);
        relatedExp_language_3=(TextView)view.findViewById(R.id.relatedExp_language_3);
        related_exp_layout_3=(LinearLayout)view.findViewById(R.id.related_exp_layout_3);
        exp_popularity=(TextView)view.findViewById(R.id.exp_popularity);
    }

    public void initController(){
        request_to_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (HomeActivity.getCurrent_user().isLoggedin()) {
                Intent intent = new Intent(HomeActivity.getHome_context(), CheckoutActivity.class);
                intent.putExtra(INT_EXTRA, ExpDetailActivity.position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                intent.putExtra(GeneralHelper.login_fragment_extra, 1);//login in
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                //ToastHelper.warnToast(getResources().getString(R.string.exp_detail_log_in_msg));
            }
            }
        });

        send_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(HomeActivity.getCurrent_user().isLoggedin()){
                if (MessageSerivice.connection!=null) {
                    Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(ChatActivity.COL_SENDER_ID,exp_to_display.getHost_id());
                    intent.putExtra(ChatActivity.COL_SENDER_NAME, exp_to_display.getHost_firstname());
                    intent.putExtra(ChatActivity.COL_SENDER_IMG, exp_to_display.getHost_image());

                    //ChatActivity
                    HomeActivity.getHome_context().startActivity(intent); }else{
                    ToastHelper.shortToast(getResources().getString(R.string.msg_connecting));
                }
            }else{
                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                intent.putExtra(GeneralHelper.login_fragment_extra, 1);//login in
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);
                ToastHelper.warnToast(getResources().getString(R.string.exp_detail_log_in_msg));
            }
            }
        });

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

        review_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ReviewAdapter.reviewsList = (ArrayList<ExperienceReview>) exp_to_display.getExperience_reviews();
            Intent intent = new Intent(getActivity().getApplicationContext(), ReviewActivity.class);
            startActivity(intent);
            }
        });

        host_info_more.setVisibility(View.GONE);

        host_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (host_info_less.getMaxLines() != 6) {
                host_info_less.setMaxLines(6);
                host_info_less.setVisibility(View.VISIBLE);
                host_more_btn.setText(getResources().getString(R.string.view_more));
            } else {
                host_info_less.setVisibility(View.VISIBLE);
                host_info_less.setMaxLines(Integer.MAX_VALUE);
                host_more_btn.setText(getResources().getString(R.string.view_less));
            }
            }
        });
        request_to_book_btn.setEnabled(false);
        send_msg_btn.setEnabled(false);

        if(ExperiencesListFragment.experience_type==ExperiencesListFragment.exp_private){
            exp_reservation_with.setVisibility(View.VISIBLE);
            exp_detail_host_profile_image_container.setVisibility(View.VISIBLE);
            profileImage.setVisibility(View.VISIBLE);
            send_msg_btn.setVisibility(View.VISIBLE);
        }else{
            exp_reservation_with.setVisibility(View.GONE);
            exp_detail_host_profile_image_container.setVisibility(View.GONE);
            profileImage.setVisibility(View.GONE);
            send_msg_btn.setVisibility(View.GONE);
        }
    }

    public void initControllerLocal(){
        request_to_book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (HomeActivity.getCurrent_user().isLoggedin()) {
                Intent intent = new Intent(HomeActivity.getHome_context(), CheckoutActivity.class);
                intent.putExtra(INT_EXTRA, ExpDetailActivity.position);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HomeActivity.getHome_context().startActivity(intent);
            } else {
                ToastHelper.warnToast(getResources().getString(R.string.exp_detail_log_in_msg));
            }
            }
        });

        review_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ReviewAdapter.reviewsList = (ArrayList<ExperienceReview>) exp_to_display.getExperience_reviews();
            Intent intent = new Intent(getActivity().getApplicationContext(), ReviewActivity.class);
            startActivity(intent);
            }
        });

        request_to_book_btn.setEnabled(false);

        btn_view_schedule.setOnClickListener(new ViewBtnListener(layout_schedule, btn_view_schedule));
        btn_view_tips.setOnClickListener(new ViewBtnListener(layout_tips,btn_view_tips));
        btn_view_include.setOnClickListener(new ViewBtnListener(layout_include,btn_view_include));
        btn_view_pickup.setOnClickListener(new ViewBtnListener(layout_pickup,btn_view_pickup));
        btn_view_disclaimer.setOnClickListener(new ViewBtnListener(layout_disclaimer,btn_view_disclaimer));
        btn_view_refund.setOnClickListener(new ViewBtnListener(layout_refund,btn_view_refund));
        btn_view_insurance.setOnClickListener(new ViewBtnListener(layout_insurance, btn_view_insurance));
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {
    }

    private class ViewBtnListener implements View.OnClickListener{
        LinearLayout layout_to_toggle;
        TextView text_to_change;
        ViewBtnListener(LinearLayout layout_to_toggle,TextView text_to_change){
            this.layout_to_toggle=layout_to_toggle;
            this.text_to_change=text_to_change;
        }

        @Override
        public void onClick(View v) {
            if(layout_to_toggle.getVisibility()==View.GONE){
                layout_to_toggle.setVisibility(View.VISIBLE);
                text_to_change.setText(HomeActivity.getHome_context().getResources().getString(R.string.hide));
            }else{
                layout_to_toggle.setVisibility(View.GONE);
                text_to_change.setText(HomeActivity.getHome_context().getResources().getString(R.string.view));
            }
        }
    }

    public void setAllDetailsGone(){
        layout_schedule.setVisibility(View.GONE);
    }
    public void fillDetails(){
        //Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getExperience_images().get(0)).fitCenter().into(exp_bg);
        updateImageGallery(exp_to_display.getExperience_images());

        int point =exp_to_display.getExperience_images().size()-1;
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getExperience_images().get(point)).fitCenter().into(expenses_banner_img);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileImage);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHost_image()).fitCenter().into(profileHostImage);

        exp_host_name.setText(" " + Tripalocal.getFullName(exp_to_display.getHost_firstname(), exp_to_display.getHost_lastname()));
        String[] language = exp_to_display.getLanguage()!=null?exp_to_display.getLanguage().split(";"):new String[1];
        String l= "";
        for(int i=0;language!=null && i<language.length;i++)
        {
            switch(language[i].toLowerCase()) {
                case "english": l = "English";break;
                case "mandarin": l += " / 中文";break;
            }
        }
        exp_detail_lang.setText(l);

        //price_title.setText(REAL_FORMATTER.format(exp_to_display.getPrice()));
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
        }
        else
        {
            price_title.setText(REAL_FORMATTER.format(exp_to_display.getPrice()));
        }

        price_hours.setText(REAL_FORMATTER.format(exp_to_display.getDuration()));
        info_title.setText(exp_to_display.getTitle());
        String exper_description=exp_to_display.getExperience_description()+"\n\n"+exp_to_display.getExperience_activity()+"\n\n"+exp_to_display.getExperience_interaction();
        String exper_description_line=exper_description.replace("</strong>","</strong><br/>");
        String exper_description_line_2=exper_description_line.replace("<strong>","<br/><br/><strong>");

        info_less.setText(Html.fromHtml(exper_description_line_2), TextView.BufferType.SPANNABLE);
        info_more.setText(Html.fromHtml(exper_description_line_2), TextView.BufferType.SPANNABLE);
        //info_more.setText(exp_to_display.getExperience_description()+"\n\n"+exp_to_display.getExperience_activity()+"\n\n"+exp_to_display.getExperience_interaction());
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
        getActivity().setTitle(exp_to_display.getTitle());
    }

    public void fillLocalDetails(){
        //Glide.with(HomeActivity.getHome_context()).load(BASE_URL+local_exp_to_display.getExperience_images().get(0)).fitCenter().into(exp_bg);
        updateImageGallery(local_exp_to_display.getExperience_images());
        if(local_exp_to_display.getExperience_popularity()<50) {
            exp_popularity.setText( getResources().getString(R.string.hurry_text).replace("%", "50%"));
        }else{
            exp_popularity.setText(getResources().getString(R.string.hurry_text).replace("%", "80%"));

        }
        int point =local_exp_to_display.getExperience_images().size()-1;
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+local_exp_to_display.getExperience_images().get(point)).fitCenter().into(expenses_banner_img);

        String[] language = local_exp_to_display.getLanguage()!=null?local_exp_to_display.getLanguage().split(";"):new String[1];
        String l= "";
        for(int i=0;language!=null && i<language.length;i++)
        {
            //System.out.println("lanugage is "+language[i]);
            switch(language[i].toLowerCase()) {
                case "english": l = "English";break;
                case "mandarin": l += " / 中文";break;
            }
        }
        exp_detail_lang.setText(l);
        //price_title.setText(REAL_FORMATTER.format(exp_to_display.getPrice()));
        //if guest_number_min <= 4 && guest_number_max >= 4, show the price for group of size 4;
        //if guest_number_max < 4 , show the price for group of size guest_number_max;
        //if guest_number_min > 4, show the price for group of size guest_number_min

        Float[] temp_dp = local_exp_to_display.getExperience_dynamic_price();
        int min_number = local_exp_to_display.getExperience_guest_number_min();
        int max_number = local_exp_to_display.getExperience_guest_number_max();

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
        }
        else
        {
            price_title.setText(REAL_FORMATTER.format(local_exp_to_display.getPrice()));
        }

        Double hour = local_exp_to_display.getDuration();
        String hour_string = "";
        if(hour==hour.intValue())
        {
            hour_string = String.valueOf(hour.intValue());
        }
        else
        {
            hour_string = String.valueOf(hour);
        }
        price_hours.setText(hour_string);
        info_title.setText(local_exp_to_display.getTitle());
        String exper_description=local_exp_to_display.getDescription()+"\n\n";
        String exper_description_line=exper_description.replace("</strong>","</strong><br/>");
        String exper_description_line_2=exper_description_line.replace("<strong>","<br/><br/><strong>");
        exp_header.setText(hour_string +
                            getActivity().getResources().getString(R.string.txt_hrs) +
                            getActivity().getResources().getString(R.string.txt_dot) +
                            local_exp_to_display.getExperience_language());
        local_overview.setText(exper_description_line_2);
        local_description.setText(exper_description_line_2);
        local_highlights.setText(local_exp_to_display.getHighlights());
        review_title.setText(String.valueOf(local_exp_to_display.getExperience_reviews().size()));
        if(local_exp_to_display.getExperience_reviews().isEmpty()){
            review_more_btn.setVisibility(View.INVISIBLE);
        }
        int rate = Math.round(local_exp_to_display.getExperience_rate());
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
        if(local_exp_to_display.getExperience_reviews().size() > 0){
            ExperienceReview top_review  = local_exp_to_display.getExperience_reviews().get(0);
            if(top_review.getReviewer_image().length() > 2)
                Glide.with(HomeActivity.getHome_context()).load(BASE_URL+top_review.getReviewer_image()).fitCenter().into(reviewProfileImage);
            review_username.setText(Tripalocal.getFullName(top_review.getReviewer_firstname(), top_review.getReviewer_lastname()));
            review_content_less.setText(top_review.getReview_comment());
        }else{
            review_container.setVisibility(View.GONE);
        }

        tx_schedule.setText(local_exp_to_display.getSchedule());
        removeEmptyField(layout_title_schedule, local_exp_to_display.getSchedule() + "");
        tx_disclaimer.setText(local_exp_to_display.getDisclamier());
        removeEmptyField(layout_title_disclaimer, local_exp_to_display.getDisclamier() + "");
        if(local_exp_to_display.getDisclamier()==null) {
        layout_title_disclaimer.setVisibility(View.GONE);
        }
        //System.out.println("disclaimer is " + local_exp_to_display.getDisclamier() + "12");

        tx_include.setText(local_exp_to_display.getWhatsincluded());
        removeEmptyField(layout_title_include, local_exp_to_display.getWhatsincluded() + "");

        tx_insurance.setText(local_exp_to_display.getInsurance());
        removeEmptyField(layout_title_insurance, local_exp_to_display.getInsurance() + "");

        tx_pickup.setText(local_exp_to_display.getPickup_detail());
        removeEmptyField(layout_title_pickup, local_exp_to_display.getPickup_detail() + "");

        tx_refund.setText(local_exp_to_display.getRefund_policy());
        removeEmptyField(layout_title_refund, local_exp_to_display.getRefund_policy() + "");

        tx_tips.setText(local_exp_to_display.getTips());
        removeEmptyField(layout_title_tips, local_exp_to_display.getTips() + "");

        getActivity().setTitle(local_exp_to_display.getTitle());
        //System.out.println("total number " + local_exp_to_display.getRelated_experiences().size());

        final ArrayList<RelatedExperience> exp_list=local_exp_to_display.getRelated_experiences();
        //System.out.println("experience list here here " + exp_list.toString());
        if(exp_list.size()>0) {
            relatedExp_title_1.setText(exp_list.get(0).getTitle());
            relatedExp_price_1.setText((int)exp_list.get(0).getPrice() + "");
            try{
                relatedExp_duration_1.setText(Integer.parseInt(exp_list.get(0).getDuration()+"")+"");
            }catch (Exception e){
                relatedExp_duration_1.setText(exp_list.get(0).getDuration()+"");
            }

            relatedExp_language_1.setText(exp_list.get(0).getLanguage());
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + exp_list.get(0).getImage()).fitCenter().into(relatedExp_img_1);
            related_exp_layout_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //host exp:
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;
                ExpDetailActivity.position=exp_list.get(0).getId();
                Intent intent = getActivity().getIntent();
                intent.putExtra(INT_EXTRA,ExpDetailActivity.position);
                getActivity().finish();
                startActivity(intent);
                //System.out.println("onclick listener starts here ");
                }
            });
        }else{
            related_exp_layout_1.setVisibility(View.GONE);
        }
        if(exp_list.size()>1) {
            relatedExp_title_2.setText(exp_list.get(1).getTitle());
            relatedExp_price_2.setText((int)exp_list.get(1).getPrice() + "");
            hour = exp_list.get(1).getDuration();
            if(hour == hour.intValue())
            {
                relatedExp_duration_2.setText(String.valueOf(hour.intValue()));
            }
            else
            {
                relatedExp_duration_2.setText(String.valueOf(hour));
            }
            relatedExp_language_2.setText(exp_list.get(1).getLanguage());
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + exp_list.get(1).getImage()).fitCenter().into(relatedExp_img_2);
            related_exp_layout_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //getLocalExpDetails(exp_list.get(1).getId());
                //local exp
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                ExpDetailActivity.position=exp_list.get(1).getId();
                Intent intent = getActivity().getIntent();
                intent.putExtra(INT_EXTRA, ExpDetailActivity.position);
                getActivity().finish();
                startActivity(intent);
                //System.out.println("onclick listener starts here ");
                }
            });
        }else{
            related_exp_layout_2.setVisibility(View.GONE);
        }
        if(exp_list.size()>2) {
            relatedExp_title_3.setText(exp_list.get(2).getTitle());
            relatedExp_price_3.setText((int)exp_list.get(2).getPrice() + "");
            try{
                relatedExp_duration_3.setText(Integer.parseInt(exp_list.get(2).getDuration()+"")+"");
            }catch (Exception e){
                relatedExp_duration_3.setText(exp_list.get(2).getDuration()+"");
            }
            relatedExp_language_3.setText(exp_list.get(2).getLanguage());
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + exp_list.get(2).getImage()).fitCenter().into(relatedExp_img_3);
            related_exp_layout_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                getLocalExpDetails(exp_list.get(2).getId());
                //System.out.println("onclick listener starts here ");
                }
            });
        }else{
            related_exp_layout_3.setVisibility(View.GONE);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public void removeEmptyField(FrameLayout layout,String text){
        if(text.trim().equals("")){
            layout.setVisibility(View.GONE);
        }
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