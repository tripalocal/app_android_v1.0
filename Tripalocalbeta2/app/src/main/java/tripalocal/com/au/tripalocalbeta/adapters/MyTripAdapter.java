package tripalocal.com.au.tripalocalbeta.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.models.ImageDownloader;
import tripalocal.com.au.tripalocalbeta.models.MyTrip;

/**
 * Created by YiHan on 2015/4/28.
 */
public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.ListViewHolder> {

    public static ArrayList<MyTrip> myTrip;
    public static Context mContext;

    public MyTripAdapter(Context applicationContext)
    {
        mContext = applicationContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_layout, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        MyTrip result =  myTrip.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'");
        Date dt = new Date();
        try {
            dt = sdf.parse(result.getDatetime().substring(0,20));
        }
        catch(ParseException pe)
        {
            System.out.println(pe.toString());
        }

        sdf = new SimpleDateFormat("dd-MM-yyyy");
        if(sdf.format(dt) == sdf.format(Calendar.getInstance().getTime()))
        {
            holder.bookingDate.setText("Today");
            holder.bookingDate.setTextColor(Color.RED);
        }
        else
        {
            holder.bookingDate.setText(sdf.format(dt));
        }
        sdf = new SimpleDateFormat("HH:mm");
        holder.bookingTime.setText(sdf.format(dt));
        holder.expTitle.setText(result.getExperienceTitle());
        holder.expTitle.setTextColor(Color.GREEN);
        holder.guestNumber.setText(Integer.toString(result.getGuestNumber()));
        holder.hostName.setText("with "+result.getHostName());
        holder.hostPhoneNumber.setText(result.getHostPhoneNumber());

        String st = result.getStatus();
        if(result.getStatus().equalsIgnoreCase("accepted"))
        {
            st="Confirmed";
            holder.status.setBackgroundColor(Color.parseColor("#89e052"));
            holder.status.setTextColor(Color.WHITE);
        }
        else if(result.getStatus().equalsIgnoreCase("paid"))
        {
            st="Requested";
            holder.status.setBackgroundColor(Color.parseColor("#08ae56"));
            holder.status.setTextColor(Color.WHITE);
        }
        else if(result.getStatus().equalsIgnoreCase("rejected"))
        {
            st="Cancelled";
            holder.status.setBackgroundColor(Color.LTGRAY);
            holder.status.setTextColor(Color.WHITE);
        }
        else
        {
            //TODO
        }
        holder.status.setText(st);
        holder.meetupSpot.setText(result.getMeetupSpot());

        new ImageDownloader(holder.expImage).execute("http://adventure007.cloudapp.net/images/thumbnails/experiences/experience"
                +result.getExperienceId()+"_1.jpg");
        new ImageDownloader(holder.profileImage).execute("http://adventure007.cloudapp.net/images/"
                +result.getHostImage());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if(myTrip!=null)
        {
            return myTrip.size();
        }
        else
        {
            return  0;
        }
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
        public TextView meetupSpot;

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
            meetupSpot = (TextView) itemView.findViewById(R.id.my_trip_meetup_instruction);
        }
    }
}
