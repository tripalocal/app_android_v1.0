package com.tripalocal.bentuke.Views;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ChatAdapter;
import com.tripalocal.bentuke.adapters.ExperienceListAdapter;
import com.tripalocal.bentuke.adapters.TPSuggestionsAdapter;
import com.tripalocal.bentuke.helpers.ToastHelper;
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

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatActivity extends AppCompatActivity {

    Chat chat;
    Fragment fragment;
    XMPPTCPConnection connection;
    private  static ListView chatListView;
    public static ArrayList<HashMap<String,Object>> chatListMap=null;
    private static ChatAdapter adapter;
    public static Activity chatActivity_context;
    int[] layouts;
    Button chat_send_btn;
    public static EditText inputText;
    public final static int receiver_flag=0;
    public final static int sender_flag=1;
    public static String sender_id,sender_name;
    private  ChatManager chatManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        connection= MessageSerivice.connection;
        if(connection==null){
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connection= MessageSerivice.connection;
        }
        chatManager=ChatManager.getInstanceFor(connection);
        String title_t=getResources().getString(R.string.msg_chat_title).replace("somebody",sender_id);
        setTitle(title_t);
        chatListView=(ListView)findViewById(R.id.chat_list);
        chatListMap=new ArrayList<HashMap<String,Object>>();
        layouts=new int[]{R.layout.msg_send_card,R.layout.msg_receive_card};
        chat_send_btn=(Button)findViewById(R.id.chat_send_btn);
        inputText=(EditText)findViewById(R.id.chat_input_text);
        setChatListener();
        adapter=new ChatAdapter(this,chatListMap,layouts);
        chatListView.setAdapter(adapter);
        chatActivity_context=this;
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        chat=null;
        sender_id="";

    }

    @Override
    public void onStop(){
        super.onStop();
        chat=null;
        sender_id="";
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        chat=null;
        sender_id="";
    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg_detail, menu);

        return true;
    }



    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }






    public void setChatListener(){
        chat_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = inputText.getText().toString();
                if (!text.trim().equals("")) {
                    addTextToList(text, sender_flag);
                    notifAdapter();
                    try {
                        chat = chatManager.createChat(sender_id + "@" + getResources().getString(R.string.msg_server_nick_name));
                        chat.sendMessage(text);
                        notifAdapter();
                    } catch (Exception e) {
                        System.out.println("errors here" + e.getMessage().toString());
                    }
                }
            }
        });
    }



    protected void addTextToList(String text,int person){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        chatListMap.add(map);
    }

    public void notifAdapter(){
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
        inputText.setText("");
    }
    public static void notifAdapterStatic(){
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
        inputText.setText("");
    }

    public static void addTextToListStatic(String text,int person){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        chatListMap.add(map);
    }







}
