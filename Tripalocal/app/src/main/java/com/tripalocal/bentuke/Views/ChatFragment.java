package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ChatAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatFragment extends Fragment {


    private ListView chatListView;
    private ArrayList<HashMap<String,Object>> chatListMap=null;
    private ChatAdapter adapter;
    int[] layouts;
    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatListView=(ListView)view.findViewById(R.id.chat_list);
        chatListMap=new ArrayList<HashMap<String,Object>>();
        layouts=new int[]{R.layout.msg_send_card,R.layout.msg_receive_card};
        initData();
        adapter=new ChatAdapter(this.getActivity(),chatListMap,layouts);

        chatListView.setAdapter(adapter);
        return view;

    }


    public void initData(){
        addTextToList("this is from me",1);
        addTextToList("this is from 3",0);
        addTextToList("this is from other texts",1);

    }

    public void initAdapter(){

    }
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_payment)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_payment));
    }

    protected void addTextToList(String text, int who){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person",who );
        map.put("text", text);
        chatListMap.add(map);
    }

}
