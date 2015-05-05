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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.ExpDetailActivity;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.Experience;
import tripalocal.com.au.tripalocalbeta.models.Search_Result;

/**
 * Created by naveen on 4/18/2015.
 */
public class ExperienceListAdapter extends RecyclerView.Adapter<ExperienceListAdapter.ListViewHolder> {

    //public static List<Search_Result> search_result;
    public static List<Experience> all_experiences = new ArrayList<Experience>();;
    public static Context mContext;
    private static final String BASE_URL ="https://www.tripalocal.com/images/";
    public static final String INT_EXTRA = "POSITION";
    public static int current_city;


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
        holder.bannerTxt.setText("from $" + exp_to_display.getPrice()+ "AUD/person");
        /*holder.bannerTxt2.setText("$" + exp_to_display.getPrice());
        holder.bannerTxt3.setText("AUD/person");*/
        holder.titleTxt.setText(exp_to_display.getTitle());
        holder.infoTxt.setText(exp_to_display.getDescription());
        // todo @naveen check if the experience in favoutites and set the image
        //holder.wishimage.setImageResource(R.drawable.abc_btn_check_to_on_mtrl_000);
        holder.durationTxt.setText(". " + exp_to_display.getDuration().toString()+"hrs . ");
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
        //public TextView bannerTxt2;
        //public TextView bannerTxt3;
        public TextView titleTxt;
        public TextView infoTxt;
        public CircleImageView profileImage;
        public ImageView wishimage;
        public TextView wishTxt;
        public TextView durationTxt;
        public TextView languageTxt;
        public ImageView bannerImage;
        public boolean wish = false;
        public FrameLayout bannerContainer;


        public ListViewHolder(View itemView) {
            super(itemView);
            bannerImage = (ImageView) itemView.findViewById(R.id.exp_list_banner_image);
            bannerTxt = (TextView) itemView.findViewById(R.id.exp_list_banner_txt);
           // bannerTxt2 = (TextView) itemView.findViewById(R.id.exp_list_banner_txt2);
           // bannerTxt3 = (TextView) itemView.findViewById(R.id.exp_list_banner_txt3);
            titleTxt = (TextView) itemView.findViewById(R.id.exp_list_title_txt);
            infoTxt = (TextView) itemView.findViewById(R.id.exp_list_info_txt);
            profileImage = (CircleImageView) itemView.findViewById(R.id.exp_list_profile_image);
            wishimage = (ImageView) itemView.findViewById(R.id.exp_list_wish_image);
            wishimage.setImageResource(R.drawable.unwishlisted);
            wishTxt = (TextView) itemView.findViewById(R.id.exp_list_wish_text);
            durationTxt = (TextView) itemView.findViewById(R.id.exp_list_duration);
            languageTxt = (TextView) itemView.findViewById(R.id.exp_list_language);
            bannerContainer = (FrameLayout) itemView.findViewById(R.id.banner_container);
            bannerContainer.setOnClickListener(this);
            wishimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(wish) {
                        wishimage.setImageResource(R.drawable.unwishlisted);
                        wish = false;
                        wishTxt.setText("Save to wishlist");
                        ToastHelper.shortToast("removed from wishlist");
                    }
                    else{
                        wish = true;
                        wishimage.setImageResource(R.drawable.heart);
                        wishTxt.setText("Saved to wishlist");
                        ToastHelper.shortToast("saved to wishlist");
                    }

                }
            });
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
