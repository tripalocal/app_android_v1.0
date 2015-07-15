package com.tripalocal.bentuke.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ChatAdapter;
import com.tripalocal.bentuke.adapters.ChatMsgListener;
import com.umeng.analytics.MobclickAgent;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatFragment extends Fragment {

    Chat chat;
    private static ListView chatListView;
    public static ArrayList<HashMap<String,Object>> chatListMap=null;
    private static ChatAdapter adapter;
    public static View view;
    public static Activity chatActivity_context;
    int[] layouts;
    Button chat_send_btn;
    EditText inputText;
    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatListView=(ListView)view.findViewById(R.id.chat_list);
        chatListMap=new ArrayList<HashMap<String,Object>>();
        layouts=new int[]{R.layout.msg_send_card,R.layout.msg_receive_card};
        chat_send_btn=(Button)view.findViewById(R.id.chat_send_btn);
        inputText=(EditText)view.findViewById(R.id.chat_input_text);
        initData();
        setChatListener();
        adapter=new ChatAdapter(this.getActivity(),chatListMap,layouts);
        chatListView.setAdapter(adapter);
        chatActivity_context=getActivity();

        System.out.println("start chat fragment");
        return view;

    }



    public static void notfityChange(){
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
    }

    public void setChatListener(){
        chat_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputText.getText().toString();
                if (!text.trim().equals("")) {
                    addTextToList(text);
                    notifAdapter();
                    try {
                        MessageSerivice.chat.sendMessage(text);
                    } catch (Exception e) {
                        System.out.println("errors here" + e.getMessage().toString());
                    }
                }
            }
        });
    }

    public void initData(){
        addTextToList("this is from me");
        addTextToList("this is from 3");
        addTextToList("this is from other texts");

    }


    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_payment)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_payment));
    }

    protected void addTextToList(String text){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person",0 );
        map.put("text", text);
        chatListMap.add(map);
    }

    public void notifAdapter(){
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
        inputText.setText("");
    }

    public void test(){
        chat_send_btn.setText("this is a tets");
    }




}
