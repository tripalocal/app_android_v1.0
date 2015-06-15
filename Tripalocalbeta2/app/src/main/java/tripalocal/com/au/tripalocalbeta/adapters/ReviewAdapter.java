package tripalocal.com.au.tripalocalbeta.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;
import tripalocal.com.au.tripalocalbeta.models.Tripalocal;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.ExperienceReview;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.SimpleViewHolder>{

    public static ArrayList<ExperienceReview> reviewsList = new ArrayList<>();
    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_card_layout, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
        ExperienceReview review_to_display = reviewsList.get(i);
        simpleViewHolder.reviewerName.setText(review_to_display.getReviewer_firstname()
                +" " +review_to_display.getReviewer_lastname());
        Glide.with(HomeActivity.getHome_context()).load(BASE_URL+review_to_display.getReviewer_image()).fitCenter()
                .into(simpleViewHolder.profileImage);
        simpleViewHolder.reviewContent.setText(review_to_display.getReview_comment());
        simpleViewHolder.itemView.setTag(i);
    }


    /*public ReviewAdapter(HomeActivityFragment frag) {
        super();
    }
*/
    @Override
    public int getItemCount() {
        return reviewsList.size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        public TextView reviewerName;
        public CircleImageView profileImage;
        public TextView reviewContent;

        public SimpleViewHolder(View item_view){
            super(item_view);
            reviewerName = (TextView) item_view.findViewById(R.id.review_card_name);
            reviewContent = (TextView) item_view.findViewById(R.id.ereview_card_content);
            profileImage = (CircleImageView) item_view.findViewById(R.id.review_card_profile_image);
        }
    }
}
