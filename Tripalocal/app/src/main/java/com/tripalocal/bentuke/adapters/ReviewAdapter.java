package com.tripalocal.bentuke.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.models.Tripalocal;
import com.tripalocal.bentuke.models.exp_detail.ExperienceReview;

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
        simpleViewHolder.reviewerName.setText(Tripalocal.getFullName(review_to_display.getReviewer_firstname(),
                review_to_display.getReviewer_lastname()));
        if(review_to_display.getReviewer_image() != null && !review_to_display.getReviewer_image().isEmpty()) {
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + review_to_display.getReviewer_image()).fitCenter()
                    .into(simpleViewHolder.profileImage);
        }
        simpleViewHolder.reviewContent.setText(review_to_display.getReview_comment());
        simpleViewHolder.itemView.setTag(i);
    }

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
