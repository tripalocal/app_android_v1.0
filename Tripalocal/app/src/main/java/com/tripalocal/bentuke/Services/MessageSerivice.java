package com.tripalocal.bentuke.Services;

/**
 * Created by chenf_000 on 14/07/2015.
 */

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.Views.MsgListFragment;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.helpers.dbHelper.ChatMsgDataSource;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.sql.SQLException;

/**
 * Created by chenf_000 on 14/07/2015.
 */
public class MessageSerivice extends Service {
    private static final String TAG = "HelloService";

    public static boolean isRunning  = false;
    public static Chat chat;
    public static XMPPTCPConnection connection;
    public static String username;
    Handler handler;


    @Override
    public void onCreate() {

        isRunning = true;
        handler = new Handler();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new ChatTask().execute();
        return Service.START_STICKY;
    }

    private class ChatTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try{
                        try {

                            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                                    .setHost(getResources().getString(R.string.msg_host))
                                    .setServiceName(getResources().getString(R.string.msg_server_name))
                                    .setPort(5222)
                                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                                    .build();
                            connection = new XMPPTCPConnection(config);
                            connection.connect();
                            try {
                                connection.login(username, username);
                            }catch(Exception e){
                                System.out.println("connection error:"+e.getMessage().toString());
                            }
                            HomeActivity.connection = connection;
                            ChatManager chatManager= ChatManager.getInstanceFor(connection);
                            chatManager.addChatListener(new ChatManagerListener() {
                                @Override
                                public void chatCreated(Chat chat, boolean createdLocally) {
                                    chat.addMessageListener(new ChatMessageListener() {
                                        @Override
                                        public void processMessage(Chat chat, Message message) {
                                            if (message.getBody() != null) {
                                                final String partiticipant_id = chat.getParticipant().split("@")[0];
                                                final String msg_body = message.getBody().toString();
//                                                System.out.println("message body" + msg_body);
                                                ChatListDataSource dataSource=new ChatListDataSource(getApplicationContext());
                                                ChatList_model model=new ChatList_model();
                                                model.setSender_id(partiticipant_id);
                                                model.setSender_name("test" + partiticipant_id);
                                                model.setLast_msg_content(msg_body);
                                                model.setLast_msg_date("dsad");

                                                ChatMsgDataSource msgDataSource=new ChatMsgDataSource(getApplicationContext());
                                                ChatMsg_model msgModel=new ChatMsg_model();
                                                msgModel.setReceiver_id(partiticipant_id);
                                                msgModel.setReceiver_name(partiticipant_id + "test");
                                                msgModel.setMsg_date("data");
                                                msgModel.setMsg_content(msg_body);
                                                msgModel.setMsg_type(ChatActivity.receiver_flag);
                                                try {
                                                    dataSource.open();
                                                    dataSource.createNewChat(model);
                                                    dataSource.close();

                                                    msgDataSource.open();
                                                    msgDataSource.addNewMsg(msgModel);
                                                    msgDataSource.close();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                    System.out.println("Exception here " + e.getMessage().toString());
                                                }
                                                if(ChatActivity.sender_id.equals(partiticipant_id)){
                                                    runOnUiThread(new Runnable() {
                                                        public void run() {
                                                            //update UI elements
                                                            ChatActivity.addTextToListStatic(msg_body, ChatActivity.receiver_flag);
                                                            ChatActivity.notifAdapterStatic();
                                                            MsgListFragment.notfiChangeOfAdapter();
                                                        }
                                                    });
                                                }else{
                                                    NotificationHelper.msg_notification(partiticipant_id, msg_body, getApplicationContext());

                                                }

                                            }
                                        }
                                    });
                                }
                            });
                        }catch(Exception e){
                            System.out.println("service erro1:"+e.getMessage().toString());
                        }


                while (true) ;
            }catch(Exception e){
                System.out.println("service error2"+e.getMessage().toString());
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            //System.out.println("task finished");

        }
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

//    private messageTask extends AsyncTask<String,Stirng,Stirng>(){}

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;

    }





}

