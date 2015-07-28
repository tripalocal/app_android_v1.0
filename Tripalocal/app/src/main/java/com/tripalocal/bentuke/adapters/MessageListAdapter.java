package com.tripalocal.bentuke.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.models.Tripalocal;
import com.tripalocal.bentuke.models.database.ChatList_model;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenfang on 8/07/2015.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    List<ChatList_model> messages;
    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";

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
        msgViewHolder.msg_sender.setText(messages.get(i).getSender_name()+messages.get(i).getSender_id());
        msgViewHolder.msg_brief.setText(messages.get(i).getLast_msg_content());
        String msg_time=messages.get(i).getLast_msg_date();
        msgViewHolder.msg_time.setText(msg_time);
        String image=messages.get(i).getSender_img();
        if(!image.equals("")) {
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + image).fitCenter()
                    .into(msgViewHolder.imageView);
        }
        String sender_name=messages.get(i).getSender_name();
        String sender_id=messages.get(i).getSender_id();
        msgViewHolder.msg_sender.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
        msgViewHolder.msg_brief.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
        msgViewHolder.msg_time.setOnClickListener(new msglistlistener(sender_name,sender_id,image));
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
            Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
//            ChatActivity.sender_id=id;
//            ChatActivity.sender_name=name;
//            ChatActivity.sender_img=image;
            intent.putExtra(ChatActivity.COL_SENDER_ID,id);
            intent.putExtra(ChatActivity.COL_SENDER_NAME,name);
            intent.putExtra(ChatActivity.COL_SENDER_IMG,image);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HomeActivity.getHome_context().startActivity(intent);
        }
    }
}