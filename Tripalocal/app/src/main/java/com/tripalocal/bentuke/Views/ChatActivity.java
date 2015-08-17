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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.ChatAdapter;

import com.tripalocal.bentuke.adapters.MyTripAdapter;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.helpers.dbHelper.ChatMsgDataSource;
import com.tripalocal.bentuke.models.Experience;
import com.tripalocal.bentuke.models.MyTrip;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;
import com.tripalocal.bentuke.models.network.Conversation_Result;
import com.tripalocal.bentuke.models.network.Conversation_msg_api;
import com.tripalocal.bentuke.models.network.MsgListModel;
import com.tripalocal.bentuke.models.network.Profile_result;
import com.tripalocal.bentuke.models.network.Update_Conversation_Request;
import com.tripalocal.bentuke.models.network.Update_Conversation_Result;
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

import java.sql.SQLException;
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

        updateChat();

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
//
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        updateServer();

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
//        updateChat();
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
                    addTextToList(text, sender_flag, sender_img, GeneralHelper.getDateTime());
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



    protected void addTextToList(String text,int person,String image,String time){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        map.put("dateTime",time);
        map.put("image", image);
        chatListMap.add(map);
        ArrayList<ChatMsg_model> lists=new ArrayList<ChatMsg_model>();
        try {
            //add to datasource
            chatMsg_datasource=new ChatMsgDataSource(getApplicationContext());
            chatMsg_datasource.open();
            System.out.println("msg date from addTextToList"+text);

            chatMsg_datasource.addNewMsg(new ChatMsg_model(sender_id, sender_name, text,time, ChatActivity.sender_flag,
                    image));
            chatMsg_datasource.close();

            //add to data list
            ChatListDataSource dataSource=new ChatListDataSource(getApplicationContext());
            ChatList_model model=new ChatList_model();
            model.setSender_id(sender_id);
            model.setSender_name(sender_name);
            model.setLast_msg_content(text);
            model.setLast_msg_date(time);
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

    public static void addTextToListStatic(String text,int person,String image,String time){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        map.put("dateTime", time);
        map.put("image", image);
//        System.out.println("image url on chatActivity " + image);

        chatListMap.add(map);
    }

    public  void addTextToListNoRecord(String text,int person,String image,String time){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", person);
        map.put("text", text);
        map.put("dateTime",time);
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


    public void updateChat(){
        GeneralHelper.showLoadingProgress(chatActivity_context);
        final String tooken = HomeActivity.getCurrent_user().getLogin_token();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + tooken);
                    }
                })
                .build();
        final int receiver_id=Integer.parseInt(sender_id);
        int last_chat_id=0;
        try {
            ChatMsgDataSource dataSource=new ChatMsgDataSource(HomeActivity.getHome_context());
            dataSource.open();
            last_chat_id=dataSource.getLastConversationGlobalId(receiver_id);
//            dataSource.RemoveAlldataWithoutGlobalId(receiver_id);
            dataSource.close();
        }catch (Exception e){
            Log.i("CONVERSATION ",e.getMessage().toString());
        }
        Log.i("CONVERSATION ",last_chat_id+"");

        ApiService apiService = restAdapter.create(ApiService.class);
        apiService.getConversationById(receiver_id,last_chat_id, new Callback<ArrayList<Conversation_Result>>() {
            @Override
            public void success(ArrayList<Conversation_Result> conversation_results, Response response) {
                Log.i("Conversation ", "amount " + conversation_results.size());
                try {
                    ChatMsgDataSource datesource=new ChatMsgDataSource(HomeActivity.getHome_context());
                    datesource.open();
                    for(Conversation_Result result : conversation_results){
                        ChatMsg_model model=new ChatMsg_model();
                        model.setGlobal_id(result.getId() + "");
//                        model.setMsg_type();
                        model.setMsg_content(result.getMsg_content());
                        model.setMsg_date(GeneralHelper.getLocalTime(result.getMsg_date()));
                       if((result.getSender_id()+"").equals(sender_id)) {
                            model.setMsg_type(ChatActivity.receiver_flag);
                            model.setReceiver_name(sender_name);
                            model.setReceiver_id(sender_id);
                            model.setReceiver_img(sender_img);
                           addTextToListNoRecord(result.getMsg_content(),receiver_flag,sender_img,model.getMsg_date());
                        }else{
                            model.setMsg_type(ChatActivity.sender_flag);
                            model.setReceiver_name(sender_name);
                            model.setReceiver_id(sender_id);
                            model.setReceiver_img(HomeActivity.user_img);
                           addTextToListNoRecord(result.getMsg_content(), sender_flag, model.getReceiver_img(), model.getMsg_date());

                       }
                        System.out.println("Receiver name is " + model.getReceiver_name());
                        System.out.println("msg date from update chat" + model.getMsg_content());
                        datesource.addNewMsg(model);
//                        addTextToListNoRecord(model.getMsg_content(), model.getMsg_type(), model.getReceiver_img(), model.getMsg_date());
                    }

                    
                    datesource.close();
                }catch (Exception e){
                    Log.i("Conv" +
                            "ersation ", "Exception for the chatActivity" + e.getMessage().toString());
                }

//                initData();
                Log.i("Chat Size ",chatListMap.size()+"" );

                notifAdapter();
                GeneralHelper.closeLoadingProgress();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("chat error",error.getMessage().toString());
                GeneralHelper.closeLoadingProgress();
            }
        });

    }

    public void updateServer() {
    GeneralHelper.showLoadingProgress(this);
        final String tooken = HomeActivity.getCurrent_user().getLogin_token();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + tooken);
                    }
                })
                .build();
        ChatMsgDataSource dataSource=new ChatMsgDataSource(this);
        Update_Conversation_Request request=new Update_Conversation_Request();

        try {
            dataSource.open();
            ArrayList<ChatMsg_model> chatsData= dataSource.getUnsyncMsgs(Integer.parseInt(sender_id));
            for(ChatMsg_model m:chatsData){
                Conversation_msg_api item=new Conversation_msg_api();

                item.setMsg_date(m.getMsg_date());
                item.setMsg_content(m.getMsg_content());
                item.setLocal_id(m.getMsg_id());
                item.setReceiver_id(Integer.parseInt(m.getReceiver_id()));
                System.out.println("receiver id outside is " + m.getReceiver_id());
                String receiverId=m.getReceiver_id();
                if(m.getMsg_type()==ChatActivity.sender_flag){
                    System.out.println("receiver id inside is " + m.getReceiver_id());
                    request.addToList(item);

                }
            }
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("duihua","exception "+e.getMessage().toString());

        }
        Gson json=new Gson();
        String test=json.toJson(request);
        System.out.println("update request "+test);
      if(request.getMessages().size()!=0) {
            ApiService apiService = restAdapter.create(ApiService.class);
            apiService.updateConversation(request, new Callback<ArrayList<Update_Conversation_Result>>() {
                @Override
                public void success(ArrayList<Update_Conversation_Result> update_conversation_results, Response response) {
                    Log.i("Conversation ", "update size " + update_conversation_results.size());
                    try {
                        ChatMsgDataSource dataSource=new ChatMsgDataSource(HomeActivity.getHome_context());
                        dataSource.open();
                        for(Update_Conversation_Result result : update_conversation_results){
                            dataSource.UpdateGlobalId(result.getLocal_id(),result.getLocal_id());
                            System.out.println("11local id is " + result.getLocal_id() + "global id is " + result.getGlobal_id());

                        }
                        dataSource.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Msg update Exception here "+ e.getMessage().toString());
                    }
                    GeneralHelper.closeLoadingProgress();

                }

                @Override
                public void failure(RetrofitError error) {
                    Log.i("Conversation ", "errorc is " + error.getMessage().toString());
                    GeneralHelper.closeLoadingProgress();
                }
            });
        }
    }





}
