package tripalocal.com.au.tripalocalbeta.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.ExpDetailActivity;
import tripalocal.com.au.tripalocalbeta.Views.ExpListActvity2;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Experience;
import tripalocal.com.au.tripalocalbeta.models.Search_Result;

/**
 * Created by naveen on 4/18/2015.
 */
public class ExperienceListAdapter extends RecyclerView.Adapter<ExperienceListAdapter.ListViewHolder> {

    //public static List<Search_Result> search_result;
    public static List<Experience> all_experiences = new ArrayList<Experience>();
    public static Context mContext;
    private static final String BASE_URL ="https://www.tripalocal.com/images/";
    public static final String INT_EXTRA = "POSITION";
    public static int current_city = 0;
    private static DecimalFormat REAL_FORMATTER = new DecimalFormat("0");


    public ExperienceListAdapter(Context applicationContext) {
        mContext = applicationContext;
    }


    public static void prepareSearchResults(List<Search_Result> results){
        all_experiences.clear();
        for(Search_Result res : results)
        {
            all_experiences.addAll(res.getExperiences());
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
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+"thumbnails/experiences/experience" + exp_to_display.getId()+ "_1.jpg").fitCenter().into(holder.bannerImage);
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+exp_to_display.getHostImage()).fitCenter().into(holder.profileImage);
        holder.bannerTxt.setText(REAL_FORMATTER.format(exp_to_display.getPrice()));
        holder.titleTxt.setText(exp_to_display.getTitle());
        holder.infoTxt.setText(exp_to_display.getDescription());
        holder.dataTxt.setText(exp_to_display.getId().toString());
        holder.dataTxt.setText(exp_to_display.getId().toString());
        //if(HomeActivity.wish_list != null) {
        if(HomeActivity.wish_map != null){
            //if (HomeActivity.wish_map.containsKey(String.valueOf(ExpListActvity2.city_position) + String.valueOf(position))) {
            if(HomeActivity.wish_map.containsKey(exp_to_display.getId().toString())){
            //if(HomeActivity.wish_list.contains(exp_to_display.getId().toString())){
                holder.wishimage.setImageResource(R.drawable.heart_sr);
                holder.smallwishimage.setImageResource(R.drawable.heart_sr);
                holder.wishTxt.setText(mContext.getString(R.string.wishlist_saved));
            }else{
                holder.wishimage.setImageResource(R.drawable.heart_sw);
                holder.smallwishimage.setImageResource(R.drawable.heart_sw);
                holder.wishTxt.setText(mContext.getString(R.string.wishlist_save));
            }
        }
        holder.durationTxt.setText(exp_to_display.getDuration().toString());
        // todo @naveen get the language from the experience
        holder.languageTxt.setText("English");
        holder.bannerContainer.setTag(exp_to_display.getId());
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


        public ListViewHolder(final View itemView) {
            super(itemView);
            bannerImage = (ImageView) itemView.findViewById(R.id.exp_list_banner_image);
            bannerTxt = (TextView) itemView.findViewById(R.id.exp_list_banner_txt);
            titleTxt = (TextView) itemView.findViewById(R.id.exp_list_title_txt);
            infoTxt = (TextView) itemView.findViewById(R.id.exp_list_info_txt);
            profileImage = (CircleImageView) itemView.findViewById(R.id.exp_list_profile_image);
            wishimage = (ImageView) itemView.findViewById(R.id.exp_list_wish_image);
            smallwishimage = (ImageView) itemView.findViewById(R.id.exp_list_small_wish);
            dataTxt = (TextView) itemView.findViewById(R.id.data_txt);
            wishimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String test = dataTxt.getText().toString();
                    //if (HomeActivity.wish_list.contains(test)) {
                    if(HomeActivity.wish_map.containsKey(test)){
                        wishimage.setImageResource(R.drawable.heart_sw);
                        smallwishimage.setImageResource(R.drawable.heart_sw);
                        wishTxt.setText(mContext.getString(R.string.wishlist_save));
                        //HomeActivity.wish_list.remove(test);
                        HomeActivity.wish_map.remove(test);
                        ToastHelper.shortToast(mContext.getString(R.string.wishlist_removed));
                    } else {
                        wishimage.setImageResource(R.drawable.heart_sr);
                        smallwishimage.setImageResource(R.drawable.heart_sr);
                        wishTxt.setText(mContext.getString(R.string.wishlist_saved));
                        //HomeActivity.wish_list.add(test);
                        Experience exp = getExperience(Integer.parseInt(test));
                        if(exp != null)
                        {
                            HomeActivity.wish_map.put(test,exp);
                            ToastHelper.shortToast(mContext.getString(R.string.wishlist_saved));
                        }
                        else
                        ToastHelper.errorToast(mContext.getString(R.string.wishlist_error));
                    }
                }
            });
            wishTxt = (TextView) itemView.findViewById(R.id.exp_list_wish_text);
            durationTxt = (TextView) itemView.findViewById(R.id.exp_list_duration);
            languageTxt = (TextView) itemView.findViewById(R.id.exp_list_language);
            bannerContainer = (FrameLayout) itemView.findViewById(R.id.banner_container);
            bannerContainer.setOnClickListener(this);
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
            Intent intent = new Intent(mContext, ExpDetailActivity.class);
            intent.putExtra(INT_EXTRA, (Integer) v.getTag());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

    }
}
