package tripalocal.com.au.tripalocalbeta.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.ExpDetailActivity;
import tripalocal.com.au.tripalocalbeta.helpers.Search_Result;

/**
 * Created by naveen on 4/18/2015.
 */
public class ExperienceListAdapter extends RecyclerView.Adapter<ExperienceListAdapter.ListViewHolder> {

    public static Search_Result[] searchResult;
    public static Context mContext;

    public ExperienceListAdapter(Context applicationContext) {
        mContext = applicationContext;
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_list_card_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        /*Search_Result result =  searchResult[position];
        holder.bannerTxt.setText(result.);
        holder.titleTxt.setText("title of the result");
        holder.infoTxt.setText("info of the resultss.....some more info..more info???");
        holder.itemView.setTag(position);*/

        /*holder.bannerTxt.setText("banner title of the results");
        holder.titleTxt.setText("title of the result");
        holder.infoTxt.setText("info of the resultss.....some more info..more info???");*/
        holder.itemView.setTag(position);
    }


    @Override
    public int getItemCount() {
        //return searchResult.length;
        return 5;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bannerTxt;
        public TextView titleTxt;
        public TextView infoTxt;
        public CircleImageView profileImage;


        public ListViewHolder(View itemView) {
            super(itemView);
            bannerTxt = (TextView) itemView.findViewById(R.id.banner_txt);
            titleTxt = (TextView) itemView.findViewById(R.id.title_txt);
            infoTxt = (TextView) itemView.findViewById(R.id.info_txt);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
            bannerTxt.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ExpDetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

    }
}
