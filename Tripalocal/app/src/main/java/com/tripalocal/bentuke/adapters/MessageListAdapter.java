package com.tripalocal.bentuke.adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.Message;

import java.util.List;

/**
 * Created by chenfang on 8/07/2015.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>{

    List<Message> messages;

   public MessageListAdapter(List<Message> messages){
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
        msgViewHolder.msg_sender.setText(messages.get(i).getSender());
        msgViewHolder.msg_brief.setText(messages.get(i).getContent());
        String msg_time=HomeActivity.getHome_context().getResources().getString(R.string.msg_mins_to_now);
        msgViewHolder.msg_time.setText(msg_time);

        String sender_name=messages.get(i).getSender();
        msgViewHolder.msg_sender.setOnClickListener(new msglistlistener(sender_name,sender_name));
        msgViewHolder.msg_brief.setOnClickListener(new msglistlistener(sender_name,sender_name));
        msgViewHolder.msg_time.setOnClickListener(new msglistlistener(sender_name,sender_name));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder{
        CardView msg_list_card_view;
        TextView msg_sender;
        TextView msg_brief;
        TextView msg_time;


        public MessageViewHolder(View itemView) {
            super(itemView);
            msg_sender = (TextView)itemView.findViewById(R.id.msg_sender);
            msg_brief = (TextView)itemView.findViewById(R.id.msg_brief);
            msg_time = (TextView)itemView.findViewById(R.id.msg_time);
          ;

        }


    }

    static class msglistlistener implements View.OnClickListener{
        String name,id;
        msglistlistener(String name,String id){
            this.name=name;
            this.id=id;
        }
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(HomeActivity.getHome_context(), ChatActivity.class);
            ChatActivity.sender_id=id;
            ChatActivity.sender_name=name;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HomeActivity.getHome_context().startActivity(intent);
        }
    }
}