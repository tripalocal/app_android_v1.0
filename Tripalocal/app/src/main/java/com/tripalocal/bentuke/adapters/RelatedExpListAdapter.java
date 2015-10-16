package com.tripalocal.bentuke.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.models.exp_detail.RelatedExperience;

import java.util.ArrayList;

/**
 * Created by chenfang on 14/10/2015.
 */
public class RelatedExpListAdapter extends BaseAdapter {
    private ArrayList<String> exp_list;
    private Context context;

    public RelatedExpListAdapter(Context context){
        this.exp_list=new ArrayList<String>();
        exp_list.add("test");
        exp_list.add("test2");
        this.context=context;
        System.out.println("exp list size "+exp_list.size());
    }
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public String getItem(int position) {
        return exp_list.get(0);
}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.msg_send_card,null);
//        TextView relatedExp_price,relatedExp_duration,relatedExp_language;
//        ImageView relatedExp_img;
//        relatedExp_price=(TextView)convertView.findViewById(R.id.relatedExp_price);
//        relatedExp_duration=(TextView)convertView.findViewById(R.id.relatedExp_duration);
//        relatedExp_language=(TextView)convertView.findViewById(R.id.relatedExp_language);
//        relatedExp_img=(ImageView)convertView.findViewById(R.id.relatedExp_img);
//
//        relatedExp_price.setText(exp_list.get(position).getPrice()+"");
//        relatedExp_duration.setText(exp_list.get(position).getDuration()+"");
//        relatedExp_language.setText(exp_list.get(position).getLanguage()+"");
//        System.out.println("price is " + exp_list.get(position).getPrice() + "");
//        System.out.println("duration is "+ exp_list.get(position).getDuration()+"");
//        System.out.println("language is "+ exp_list.get(position).getLanguage()+"");

        return convertView;
    }
}