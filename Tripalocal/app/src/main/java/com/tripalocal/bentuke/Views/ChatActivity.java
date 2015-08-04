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
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ChatAdapter;

import com.tripalocal.bentuke.adapters.MyTripAdapter;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.helpers.dbHelper.ChatMsgDataSource;
import com.tripalocal.bentuke.models.MyTrip;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;
import com.tripalocal.bentuke.models.network.Profile_result;
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

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by chenfang on 10/07/2015.
 */
public class ChatActivity extends AppCompatActivity {

    Chat chat;
    Fragment fragment;
    XMPPTCPConnection connection;
    private  static ListView chatListView;
    public static ArrayList<HashMap<String,Object>> chatListMap=new ArrayList<HashMap<String,Object>>();;
    private static ChatAdapter adapter=null;
    public static Activity chatActivity_context;
    int[] layouts;
    Button chat_send_btn;
    public static EditText inputText;
    public final static int receiver_flag=1;
    public final static int sender_flag=0;
    public static String sender_id="",sender_name="",sender_img="",notification_id="";
    private  ChatManager chatManager;
    private ChatMsgDataSource chatMsg_datasource;
    public static String COL_SENDER_ID="SENDER_ID",COL_SENDER_NAME="SENDER_NAME",COL_SENDER_IMG="SENDER_IMG",COL_NOTIFICATION_ID="NOTIFICATION_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initExtra();
       initComponenets();
    System.out.println("chat activity start");
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NotificationHelper.clearBadge();

    }

    public void initComponenets(){
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
        String title_t=getResources().getString(R.string.msg_chat_title).replace("somebody",sender_name);
        setTitle(title_t);
        chatListView=(ListView)findViewById(R.id.chat_list);
//        chatListMap=new ArrayList<HashMap<String,Object>>();
        layouts=new int[]{R.layout.msg_send_card,R.layout.msg_receive_card};
        chat_send_btn=(Button)findViewById(R.id.chat_send_btn);
        inputText=(EditText)findViewById(R.id.chat_input_text);
        setChatListener();
        initData();

        adapter=new ChatAdapter(this,chatListMap,layouts);
        chatListView.setAdapter(adapter);
        chatListView.setSelection(chatListMap.size() - 1);

        chatActivity_context=this;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        finish();
        this.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(!notification_id.equals("")) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
        finish();
//        super.onBackPressed();
        }
        chat=null;
//        sender_id="";

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
        System.out.println("start on resume method on chat Activity");
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
                    initExtra();
                    addTextToList(text, sender_flag, sender_img);
//                    System.out.println("sender images shows here " + sender_img);
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



    protected void addTextToList(String text,int person,String image){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        map.put("dateTime",GeneralHelper.getDateTime());
        map.put("image", image);
        chatListMap.add(map);
        ArrayList<ChatMsg_model> lists=new ArrayList<ChatMsg_model>();
        try {
            //add to datasource
            chatMsg_datasource=new ChatMsgDataSource(getApplicationContext());
            chatMsg_datasource.open();
            chatMsg_datasource.addNewMsg(new ChatMsg_model(sender_id, sender_name, text, GeneralHelper.getDateTime(), ChatActivity.sender_flag,
                    image));
            chatMsg_datasource.close();

            //add to data list
            ChatListDataSource dataSource=new ChatListDataSource(getApplicationContext());
            ChatList_model model=new ChatList_model();
            model.setSender_id(sender_id);
            model.setSender_name(sender_name);
            model.setLast_msg_content(text);
            model.setLast_msg_date(GeneralHelper.getDateTime());
            model.setSender_img(image);

            dataSource.open();
            dataSource.createNewChat(model);
            dataSource.close();
//            System.out.println("add text finish");
        }catch (Exception e){
            System.out.println("exception"+e.getMessage().toString());
        }
//        System.out.println("add text to list end");


    }

    public void notifAdapter(){
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
        inputText.setText("");
    }
    public static void notifAdapterStatic(){
        if(adapter==null){

        }
        adapter.notifyDataSetChanged();
        chatListView.setSelection(chatListMap.size() - 1);
        inputText.setText("");
    }

    public static void addTextToListStatic(String text,int person,String image){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        map.put("dateTime",GeneralHelper.getDateTime());
        map.put("image", image);
//        System.out.println("image url on chatActivity " + image);

        chatListMap.add(map);
    }

    public void initExtra(){
        Bundle extras = getIntent().getExtras();
        sender_id="";
        if (extras != null) {
            sender_id=extras.getString(ChatActivity.COL_SENDER_ID,"");
            sender_name= extras.getString(ChatActivity.COL_SENDER_NAME,"");
            sender_img=extras.getString(ChatActivity.COL_SENDER_IMG,"");
            notification_id=extras.getString(ChatActivity.COL_NOTIFICATION_ID,"");
        }
    }

    public void initData(){
        chatListMap = new ArrayList< HashMap<String,Object>>();
        ArrayList<ChatMsg_model> lists=new ArrayList<ChatMsg_model>();
        ChatMsgDataSource chatMsg_datasource1=new ChatMsgDataSource(getApplicationContext());
        try {
            chatMsg_datasource1.open();
            lists =(ArrayList<ChatMsg_model>)chatMsg_datasource1.getChatMsgs(Integer.parseInt(sender_id));
//            System.out.println("retrieve data succesffully");
        }catch (Exception e){
            System.out.println("exception123:"+e.getMessage().toString());
        }finally {
            chatMsg_datasource1.close();

        }
        for(ChatMsg_model model :lists){
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("person", model.getMsg_type());
            System.out.println("person type on initData" + model.getMsg_type());
            map.put("text", model.getMsg_content());
            map.put("dateTime",model.getMsg_date());
            map.put("image",model.getReceiver_img());

            chatListMap.add(map);
        }
    }



}
