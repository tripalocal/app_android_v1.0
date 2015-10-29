package com.tripalocal.bentuke.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.Views.MsgListFragment;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.Tripalocal;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenfang on 8/07/2015.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    public static List<ChatList_model> messages;
    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";
    public static  RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> adapter;
   public MessageListAdapter(List<ChatList_model> messages){
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_list_card_layout, viewGroup, false);
        MessageViewHolder pvh = new MessageViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder msgViewHolder, int i) {
        adapter=this;
        msgViewHolder.msg_sender.setText(messages.get(i).getSender_name());
        msgViewHolder.msg_brief.setText(messages.get(i).getLast_msg_content());
        String msg_time= GeneralHelper.getTimeClp(messages.get(i).getLast_msg_date());
        msgViewHolder.msg_time.setText(msg_time);
        String image=messages.get(i).getSender_img();
        if(!image.equals("")) {
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + image).fitCenter()
                    .into(msgViewHolder.imageView);
        }

        System.out.println("here images:"+BASE_URL+image);
        String sender_name=messages.get(i).getSender_name();
        String sender_id=messages.get(i).getSender_id();
        msgViewHolder.msg_sender.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
        msgViewHolder.msg_brief.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
        msgViewHolder.msg_time.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
        msgViewHolder.imageView.setOnClickListener(new msglistlistener(sender_name, sender_id, image));

//        msgViewHolder.msg_sender.setOnLongClickListener(new msgOnLongClickListener(sender_id));
//        msgViewHolder.msg_brief.setOnLongClickListener(new msgOnLongClickListener(sender_id));
//        msgViewHolder.msg_time.setOnLongClickListener(new msgOnLongClickListener(sender_id));
//        msgViewHolder.imageView.setOnLongClickListener(new msgOnLongClickListener(sender_id));

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView msg_sender;
        TextView msg_brief;
        TextView msg_time;


        public MessageViewHolder(View itemView) {
            super(itemView);
            msg_sender = (TextView)itemView.findViewById(R.id.msg_sender);
            msg_brief = (TextView)itemView.findViewById(R.id.msg_brief);
            msg_time = (TextView)itemView.findViewById(R.id.msg_time);
            imageView=(CircleImageView)itemView.findViewById(R.id.msg_list_image);


        }


    }

    static class msglistlistener implements View.OnClickListener{
        String name,id,image;
        msglistlistener(String name,String id,String image){
            this.name=name;
            this.id=id;
            this.image=image;
        }
        @Override
        public void onClick(View view) {
            System.out.println("on click goes here");
            Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
            intent.putExtra(ChatActivity.COL_SENDER_ID,id);
            System.out.println(name + "'s id is " +id);
            intent.putExtra(ChatActivity.COL_SENDER_NAME, name);
            intent.putExtra(ChatActivity.COL_SENDER_IMG, image);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            HomeActivity.getHome_context().startActivity(intent);
            System.out.println("Onclick finish here");
        }
    }

    static class msgOnLongClickListener implements View.OnLongClickListener{

        final String sender_id;
        msgOnLongClickListener(String sender_id){
            this.sender_id=sender_id;
        }
        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

            alertDialog.setTitle(HomeActivity.getHome_context().getResources().getString(R.string.dialog_delete_conversation));
//            alertDialog.setIcon(R.drawable.icon);

            alertDialog.setPositiveButton(
                    HomeActivity.getHome_context().getResources().getString(R.string.dialog_option_delete),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                ChatListDataSource dataSource = new ChatListDataSource(HomeActivity.getHome_context());
                                dataSource.open();
                                dataSource.deleteChat(sender_id);
                                dataSource.close();
                                for(ChatList_model model : messages){
                                        if(model.getSender_id().equals(sender_id)){
                                            messages.remove(model);
                                        }
                                }
                                adapter.notifyDataSetChanged();

                            } catch (Exception e) {

                            }
                        }
                    }
            );
            alertDialog.setNegativeButton(
                    HomeActivity.getHome_context().getResources().getString(R.string.dialog_option_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }
            );


        alertDialog.show();

            return false;
        }
    }


    public static void refreshData(){
        messages = new ArrayList<>();
        ArrayList<ChatList_model> lists=new ArrayList<ChatList_model>();
        ChatListDataSource chatList_db_source=new ChatListDataSource(HomeActivity.getHome_context());
        try {

            chatList_db_source.open();
            lists =(ArrayList<ChatList_model>)chatList_db_source.getChatList();
            chatList_db_source.close();
        }catch (Exception e){
            System.out.println("exception"+e.getMessage().toString());
        }
        for(ChatList_model model :lists){
            messages.add(model);
        }
//        NotificationHelper.clearBadge();
        adapter.notifyDataSetChanged();
    }
}