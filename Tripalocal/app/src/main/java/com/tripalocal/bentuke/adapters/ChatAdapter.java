package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.models.Tripalocal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatAdapter extends BaseAdapter{
    Context context=null;
    ArrayList<HashMap<String,Object>> chatList=null;
    int[] layout;
    public static final String BASE_URL = Tripalocal.getServerUrl() + "images/";

    public ChatAdapter(Context context,
                       ArrayList<HashMap<String, Object>> chatList, int[] layout
                       ) {
        super();
        this.context = context;
        this.chatList = chatList;
        this.layout = layout;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chatList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    class ViewHolder{
        public CircleImageView imageView=null;
        public TextView textView=null;
        public TextView dateTime_text=null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=null;
        int who=(Integer)chatList.get(position).get("person");

            convertView= LayoutInflater.from(context).inflate(layout[who],null);
;
        holder=new ViewHolder();
        if(who== ChatActivity.sender_flag){
            holder.textView=(TextView)convertView.findViewById(R.id.msg_content_send);
            holder.dateTime_text=(TextView)convertView.findViewById(R.id.msg_time_send);
            holder.imageView=(CircleImageView)convertView.findViewById(R.id.msg_image_send);
        }else{
            holder.textView=(TextView)convertView.findViewById(R.id.msg_content_receive);
            holder.dateTime_text=(TextView)convertView.findViewById(R.id.msg_time_receive);
            holder.imageView=(CircleImageView)convertView.findViewById(R.id.msg_image_receive);
            Glide.with(HomeActivity.getHome_context()).load(BASE_URL + chatList.get(position).get("image")).fitCenter()
                    .into(holder.imageView);
            System.out.println("image on chatAdapter is "+chatList.get(position).get("image" +
                    ""));
        }
        String text=(String)chatList.get(position).get("text");
        String datetime_s=(String)chatList.get(position).get("dateTime");

        holder.textView.setText(text);
        holder.dateTime_text.setText(datetime_s);

        return convertView;
    }
}
