package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.Views.MsgListFragment;
import com.tripalocal.bentuke.Views.MyTripFragment;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.ImageDownloader;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.MyTrip;
import com.tripalocal.bentuke.models.database.ChatList_model;

/**
 * Created by YiHan on 2015/4/28.
 */
public class MyTripAdapter extends RecyclerView.Adapter<MyTripAdapter.ListViewHolder> {

    public static ArrayList<MyTrip> myTrip;
    public static Context mContext;
    public static boolean upcoming_flag = false;
    public static boolean previous_flag = false;
    public static MyTrip result_mytrip;


    public MyTripAdapter(Context applicationContext)
    {
        mContext = applicationContext;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(myTrip!=null && myTrip.isEmpty()){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_layout, parent, false);
            MyTripFragment.msgTxt = (TextView) view.findViewById(R.id.blank_msg);
        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_trip_layout, parent, false);
        }
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if(myTrip != null && !myTrip.isEmpty()) {
            final MyTrip result = myTrip.get(position);
            result_mytrip=myTrip.get(position);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'");
            Date dt = new Date();
            try {
                dt = sdf.parse(result.getDatetime().substring(0, 20));
            } catch (ParseException pe) {
                //System.out.println(pe.toString());
            }

            sdf = new SimpleDateFormat(mContext.getResources().getString(R.string.mytrip_date_format));
            if (sdf.format(dt).equalsIgnoreCase(sdf.format(Calendar.getInstance().getTime()))) {
                holder.bookingDate.setText(mContext.getResources().getString(R.string.mytrip_date_today));
                holder.bookingDate.setTextColor(Color.RED);
            } else {
                holder.bookingDate.setText(sdf.format(dt));
            }
            sdf = new SimpleDateFormat("HH:mm");
            holder.bookingTime.setText(sdf.format(dt));
            holder.expTitle.setText(result.getExperienceTitle());
            holder.expTitle.setTextColor(mContext.getResources().getColor(R.color.tripalocal_green_blue));
            holder.guestNumber.setText(Integer.toString(result.getGuestNumber()));
            holder.hostName.setText(result.getHostName());
            holder.hostPhoneNumber.setText(result.getHostPhoneNumber());

            String st = result.getStatus();
            if (result.getStatus().equalsIgnoreCase("accepted")) {
                st = mContext.getString(R.string.mytrip_status_confirmed);
                holder.status.setBackground(mContext.getResources().getDrawable(R.drawable.my_trip_status_confirmed_shape));
                holder.status.setTextColor(Color.WHITE);
            } else if (result.getStatus().equalsIgnoreCase("paid")) {
                st = mContext.getString(R.string.mytrip_status_requested);
                holder.status.setBackground(mContext.getResources().getDrawable(R.drawable.my_trip_status_requested_shape));
                holder.status.setTextColor(Color.WHITE);
            } else if (result.getStatus().equalsIgnoreCase("rejected")) {
                st = mContext.getString(R.string.mytrip_status_cancelled);
                holder.status.setBackground(mContext.getResources().getDrawable(R.drawable.my_trip_status_cancelled_shape));
                holder.status.setTextColor(Color.WHITE);
            } else {
                //TODO
            }
            holder.status.setText(st);
            holder.meetupSpot.setText(result.getMeetupSpot());
            new ImageDownloader(holder.expImage).execute((mContext.getResources().getString(R.string.server_url) + "images/"+result.getExperience_photo()));
//            System.out.println("Mytrip photo over there "+(mContext.getResources().getString(R.string.server_url) + "images/"+result.getExperience_photo());
            new ImageDownloader(holder.profileImage).execute(mContext.getResources().getString(R.string.server_url) + "images/"
                    + result.getHostImage());

            holder.itemView.setTag(position);

            holder.callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    TextView b = ((TextView) ((View) v.getParent().getParent()).findViewById(R.id.my_trip_host_phone_number));
                    intent.setData(Uri.parse("tel:" + b.getText()));
                    mContext.startActivity(intent);
                }
            });

            holder.messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(HomeActivity.getCurrent_user().isLoggedin()){
                        if (MessageSerivice.connection!=null) {
                            String id=MyTripAdapter.result_mytrip.getHost_id();  //set exp id
                            String name=MyTripAdapter.result_mytrip.getHostName();//set exp name
                            String image=MyTripAdapter.result_mytrip.getHostImage();

                            Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ChatActivity.COL_SENDER_ID,id);
                            intent.putExtra(ChatActivity.COL_SENDER_NAME,name);
                            intent.putExtra(ChatActivity.COL_SENDER_IMG, image);
                            HomeActivity.getHome_context().startActivity(intent);         }else{
                            ToastHelper.shortToast(mContext.getResources().getString(R.string.msg_connecting));
                        }

                    }else{

                        ToastHelper.warnToast(mContext.getResources().getString(R.string.exp_detail_log_in_msg));
                    }
                }
            });
        }else{
            /*if(upcoming_flag)
            MyTripFragment.msgTxt.setText("You do not have any upcoming booking");
            else
            MyTripFragment.msgTxt.setText("You do not have any past booking");*/
        }
    }

    @Override
    public int getItemCount() {
        if(myTrip!=null)
        {
            return myTrip.size();
        }
        else
        {
            return  1;
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
        public Button callButton;
        public Button messageButton;

        public ListViewHolder(View itemView) {
            super(itemView);
            expTitle = (TextView) itemView.findViewById(R.id.my_trip_exp_title);
            expImage = (ImageView) itemView.findViewById(R.id.my_trip_exp_image);
            bookingDate = (TextView) itemView.findViewById(R.id.my_trip_booking_date);
            bookingTime = (TextView) itemView.findViewById(R.id.my_trip_booking_time);
            guestNumber = (TextView) itemView.findViewById(R.id.my_trip_guest_number);
            status = (TextView) itemView.findViewById(R.id.my_trip_booking_status);
            hostName = (TextView) itemView.findViewById(R.id.my_trip_host_name);
            hostPhoneNumber = (TextView) itemView.findViewById(R.id.my_trip_host_phone_number);
            profileImage = (CircleImageView) itemView.findViewById(R.id.my_trip_profile_image);
            meetupSpot = (TextView) itemView.findViewById(R.id.my_trip_meetup_instruction);
            callButton = (Button)itemView.findViewById(R.id.my_trip_call_button);
            messageButton = (Button)itemView.findViewById(R.id.my_trip_message_button);
        }
    }
}
