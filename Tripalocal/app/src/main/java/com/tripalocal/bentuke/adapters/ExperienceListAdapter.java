package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ExpDetailActivity;
import com.tripalocal.bentuke.Views.ExperiencesListFragment;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.Views.ItinerariesFragment;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.Experience;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.network.Search_Result;
import com.tripalocal.bentuke.models.Tripalocal;
import com.tripalocal.bentuke.models.network.WishList_update_Request;
import com.tripalocal.bentuke.models.network.Wishlist_Update_Result;

/**
 * Created by naveen on 4/18/2015.
 */
public class ExperienceListAdapter extends RecyclerView.Adapter<ExperienceListAdapter.ListViewHolder> {

    //public static List<Search_Result> search_result;
    public static List<Experience> all_experiences = new ArrayList<Experience>();
    //public static Context mContext;
    private static final String BASE_URL = Tripalocal.getServerUrl() + "images/";
    public static final String INT_EXTRA = "POSITION";
    public static int current_city = 0;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");
    private Context mContext;
    public void displayListFrag2(int position,String exp_type) {
        Fragment exp_list_frag = new ExperiencesListFragment();
        Bundle args = new Bundle();
        args.putInt(ExperienceListAdapter.INT_EXTRA, position);
        ExperiencesListFragment.experience_type=exp_type;
        exp_list_frag.setArguments(args);
        ExperiencesListFragment.ac.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home").commit();
    }
    public ExperienceListAdapter(Context applicationContext) {
        mContext = applicationContext;

    }


    public static void prepareSearchResults(List<Search_Result> results){
        all_experiences.clear();
        for(Search_Result res : results)
        {
                all_experiences.addAll(res.getPrivateExperiences());

        }
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_list_card_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Experience exp_to_display = all_experiences.get(position);
        ////System.out.println("exp host photo : " + BASE_URL + "" + exp_to_display.getHostImage());
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getPhoto_url()).fitCenter().into(holder.bannerImage);
        if(ExperiencesListFragment.experience_type==ExperiencesListFragment.exp_private){
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHostImage()).fitCenter().into(holder.profileImage);
            holder.profileImage.setVisibility(View.VISIBLE);

        }else{
            holder.profileImage.setVisibility(View.GONE);
        }
        if(position==0){
            holder.search_icon_bar.setVisibility(View.VISIBLE);
            if(ExperiencesListFragment.experience_type.equals(ExperiencesListFragment.exp_private)){
                holder.search_host_img.setImageResource(R.drawable.local_o);
                holder.search_local_img.setImageResource(R.drawable.hot);
                holder.search_bar_host_txt.setTextColor(HomeActivity.getHome_context().getResources().getColor(R.color.tripalocal_green_blue));
            }else if(ExperiencesListFragment.experience_type.equals(ExperiencesListFragment.exp_newPro)){
                holder.search_host_img.setImageResource(R.drawable.local);
                holder.search_local_img.setImageResource(R.drawable.hot_o);
                holder.search_bar_local_txt.setTextColor(HomeActivity.getHome_context().getResources().getColor(R.color.tripalocal_green_blue));

            }
        }else{
            holder.search_icon_bar.setVisibility(View.GONE);

        }
        holder.bannerTxt.setText(REAL_FORMATTER.format(exp_to_display.getPrice()));
        holder.titleTxt.setText(exp_to_display.getTitle());
        holder.infoTxt.setText(exp_to_display.getDescription());
        holder.dataTxt.setText(exp_to_display.getId().toString());
        holder.dataTxt.setText(exp_to_display.getId().toString());
        if(HomeActivity.getCurrent_user().isLoggedin() && HomeActivity.wish_map != null){
            if(HomeActivity.wish_map.containsKey(exp_to_display.getId().toString())){
                holder.wishimage.setImageResource(R.drawable.heart_lr);
                holder.smallwishimage.setImageResource(R.drawable.heart_sr);
                holder.wishTxt.setText(R.string.saved_to_wishlist);
            }else{
                holder.wishimage.setImageResource(R.drawable.heart_ldg);
                holder.smallwishimage.setImageResource(R.drawable.heart_sw);
                holder.wishTxt.setText(R.string.save_to_wishlist);
            }
        }
        if(exp_to_display.getDuration().intValue() == exp_to_display.getDuration())
        {
            holder.durationTxt.setText(Integer.toString(exp_to_display.getDuration().intValue()));
        }
        else
        {
            holder.durationTxt.setText(exp_to_display.getDuration().toString());
        }
        String[] language = exp_to_display.getLanguage()!=null?exp_to_display.getLanguage().split(";"):new String[1];
        String l= "";
        for(int i=0;language!=null && i<language.length;i++)
        {
            switch(language[i].toLowerCase()) {
                case "english": l = "English";break;
                case "mandarin": l += " / 中文";break;
            }
        }
        holder.languageTxt.setText(l);
        holder.bannerContainer.setTag(exp_to_display.getId());
        holder.titleTxt.setTag(exp_to_display.getId());

        //set onclick listener for the top search items btn
        holder.search_to_host_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0,ExperiencesListFragment.exp_private);//change here
            }
        });

        holder.search_to_local_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_newPro;
                ExperienceListAdapter.current_city = 0;
                displayListFrag2(0,ExperiencesListFragment.exp_newPro);//change here

            }
        });

        holder.search_to_itinerary_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExperiencesListFragment.experience_type=ExperiencesListFragment.exp_private;
                ExperiencesListFragment.experience_type= ExperiencesListFragment.exp_itinerary;

                Fragment loginFragment = new ItinerariesFragment();
                ExperiencesListFragment.ac.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).addToBackStack("loginFragment").commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        //return searchResult.length;
        return all_experiences.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bannerTxt;
        public TextView titleTxt;
        public TextView infoTxt;
        public CircleImageView profileImage;
        public ImageView wishimage;
        public TextView wishTxt;
        public ImageView smallwishimage;
        public TextView durationTxt;
        public TextView languageTxt;
        public ImageView bannerImage;
        public FrameLayout bannerContainer;
        public TextView dataTxt;
        public LinearLayout search_icon_bar;
        public ImageView search_host_img,search_local_img,search_itineraries_img;
        public TextView search_bar_host_txt,search_bar_local_txt,search_bar_itinerary_txt;
        public LinearLayout search_to_local_layout,search_to_host_layout,search_to_itinerary_layout;
        public ListViewHolder(final View itemView) {
            super(itemView);
            bannerImage = (ImageView) itemView.findViewById(R.id.exp_list_banner_image);
            bannerTxt = (TextView) itemView.findViewById(R.id.exp_list_banner_txt);
            titleTxt = (TextView) itemView.findViewById(R.id.exp_list_title_txt);
            titleTxt.setOnClickListener(this);
            infoTxt = (TextView) itemView.findViewById(R.id.exp_list_info_txt);
            profileImage = (CircleImageView) itemView.findViewById(R.id.exp_list_profile_image);
            wishimage = (ImageView) itemView.findViewById(R.id.exp_list_wish_image);
            smallwishimage = (ImageView) itemView.findViewById(R.id.exp_list_small_wish);
            dataTxt = (TextView) itemView.findViewById(R.id.data_txt);
            wishTxt = (TextView) itemView.findViewById(R.id.exp_list_wish_text);
            durationTxt = (TextView) itemView.findViewById(R.id.exp_list_duration);
            languageTxt = (TextView) itemView.findViewById(R.id.exp_list_language);
            bannerContainer = (FrameLayout) itemView.findViewById(R.id.banner_container);
            bannerContainer.setOnClickListener(this);
            search_icon_bar=(LinearLayout)itemView.findViewById(R.id.search_icon_bar);
            search_host_img=(ImageView)itemView.findViewById(R.id.search_host_img);
            search_local_img=(ImageView)itemView.findViewById(R.id.search_local_img);
            search_itineraries_img=(ImageView)itemView.findViewById(R.id.search_itineraries_img);
            search_bar_host_txt=(TextView)itemView.findViewById(R.id.search_bar_host_txt);
            search_bar_local_txt=(TextView)itemView.findViewById(R.id.search_bar_local_txt);
            search_bar_itinerary_txt=(TextView)itemView.findViewById(R.id.search_bar_itinerary_txt);
            search_to_local_layout=(LinearLayout)itemView.findViewById(R.id.search_to_local_layout);
            search_to_host_layout=(LinearLayout)itemView.findViewById(R.id.search_to_host_layout);
            search_to_itinerary_layout=(LinearLayout)itemView.findViewById(R.id.search_to_itinerary_layout);

            wishimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (HomeActivity.getCurrent_user().isLoggedin()) {
                        String test = dataTxt.getText().toString();
                        //if (HomeActivity.wish_list.contains(test)) {
                        if (HomeActivity.wish_map.containsKey(test)) {
                            wishimage.setImageResource(R.drawable.heart_ldg);
                            smallwishimage.setImageResource(R.drawable.heart_sw);
                            wishTxt.setText(HomeActivity.getHome_context().getString(R.string.wishlist_save));
                            //HomeActivity.wish_list.remove(test);
                            HomeActivity.wish_map.remove(test);
                            updateListMap(Integer.parseInt(HomeActivity.getCurrent_user().getUser_id()), Integer.parseInt(test), WishList_update_Request.added_true);

                            ToastHelper.shortToast(HomeActivity.getHome_context().getString(R.string.wishlist_removed));
                        } else {
                            wishimage.setImageResource(R.drawable.heart_lr);
                            smallwishimage.setImageResource(R.drawable.heart_sr);
                            wishTxt.setText(HomeActivity.getHome_context().getString(R.string.wishlist_saved));
                            //HomeActivity.wish_list.add(test);
                            Experience exp = getExperience(Integer.parseInt(test));
                            if(exp != null)
                            {
                                HomeActivity.wish_map.put(test, exp);
                                updateListMap(Integer.parseInt(HomeActivity.getCurrent_user().getUser_id()), Integer.parseInt(test), WishList_update_Request.added_false);
                                ToastHelper.shortToast(HomeActivity.getHome_context().getString(R.string.wishlist_saved));

                            }
                            else{
                                ToastHelper.errorToast(HomeActivity.getHome_context().getString(R.string.wishlist_error));
                            }
                        }
                    }
                    else{
                        ToastHelper.warnToast(HomeActivity.getHome_context().getString(R.string.wish_log_in_msg));
                    }
                }
            });
        }

        public Experience getExperience(int id){
            for(Experience exp : all_experiences){
                if(exp.getId() == id){
                    return exp;
                }
            }
            return null;
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(HomeActivity.getHome_context(), ExpDetailActivity.class);
            intent.putExtra(INT_EXTRA, (Integer) v.getTag());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HomeActivity.getHome_context().startActivity(intent);
        }
    }


    public static void updateListMap(int user_id,int experience_id,String added) {

        final HashMap<String, String> map = new HashMap<String, String>();
        final String tooken = HomeActivity.getCurrent_user().getLogin_token();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + tooken);
                        request.addHeader("Content-Type", "application/json");
                    }
                })
                .build();

        WishList_update_Request request = new WishList_update_Request();
        request.setUser_id(user_id);
        request.setExperience_id(experience_id);
        request.setAdded(added);
        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.UpdateWishList(request, new Callback<Wishlist_Update_Result>() {
            @Override
            public void success(Wishlist_Update_Result wishlist_update_result, Response response) {
                //System.out.println("success" + wishlist_update_result.getAdded());
            }

            @Override
            public void failure(RetrofitError error) {
                //System.out.println("failure" + error.getMessage().toString());
            }
        });
    }}



