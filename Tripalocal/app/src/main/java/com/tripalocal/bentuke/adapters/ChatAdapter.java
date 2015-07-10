package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripalocal.bentuke.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatAdapter extends BaseAdapter{
    Context context=null;
    ArrayList<HashMap<String,Object>> chatList=null;
    int[] layout;

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
        public ImageView imageView=null;
        public TextView textView=null;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder=null;
        int who=(Integer)chatList.get(position).get("person");

            convertView= LayoutInflater.from(context).inflate(layout[who],null);
;
        holder=new ViewHolder();
        if(who==0){
            holder.textView=(TextView)convertView.findViewById(R.id.msg_content_receive);
        }else{
            holder.textView=(TextView)convertView.findViewById(R.id.msg_content_receive);

        }
        holder.textView.setText("This is a test");
//        holder.imageView=(ImageView)convertView.findViewById(to[who*2+0]);
//        holder.textView=(TextView)convertView.findViewById(to[who*2+1]);
//
//
//        System.out.println(holder);
//        System.out.println("WHYWHYWHYWHYW");
//        System.out.println(holder.imageView);
//        holder.imageView.setBackgroundResource((Integer)chatList.get(position).get(from[0]));
//        holder.textView.setText(chatList.get(position).get(from[1]).toString());
        return convertView;
    }
}
