package tripalocal.com.au.tripalocalbeta.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.models.MyTrip;

/**
 * Created by YiHan on 2015/4/28.
 */
public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.ListViewHolder> {

    public static MyTrip[] myTrip;
    public static Context mContext;

    public MyTripAdapter(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        //return searchResult.length;
        return 5;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder{
        public TextView expTitle;
        public ImageView expImage;
        public TextView bookingDate;
        public TextView bookingTime;
        public TextView guestNumber;
        public TextView status;
        public TextView hostName;
        public TextView hostPhoneNumber;
        public CircleImageView profileImage;

        public ListViewHolder(View itemView) {
            super(itemView);
            expTitle = (TextView) itemView.findViewById(R.id.exp_title);
            expImage = (ImageView) itemView.findViewById(R.id.exp_image);
            bookingDate = (TextView) itemView.findViewById(R.id.booking_date);
            bookingTime = (TextView) itemView.findViewById(R.id.booking_time);
            guestNumber = (TextView) itemView.findViewById(R.id.guest_number);
            status = (TextView) itemView.findViewById(R.id.booking_status);
            hostName = (TextView) itemView.findViewById(R.id.host_name);
            hostPhoneNumber = (TextView) itemView.findViewById(R.id.host_phone_number);
            profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}
