package com.tripalocal.bentuke.Services;

/**
 * Created by chenf_000 on 14/07/2015.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.Views.MsgListFragment;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.MsgHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.helpers.dbHelper.ChatMsgDataSource;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;
import com.tripalocal.bentuke.models.network.Profile_result;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.sql.SQLException;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    public static boolean connected;

    @Override
    public void onCreate() {
        isRunning = true;
        handler = new Handler();
        connected=false;
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
                Thread.sleep(6000);
                    try {
                        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                            .setHost(getResources().getString(R.string.msg_host))
                            .setServiceName(getResources().getString(R.string.msg_server_name))
                            .setPort(5222)
                            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                            .build();
                        connection = new XMPPTCPConnection(config);
                        connection.connect();
                        SharedPreferences settings_l = getSharedPreferences(HomeActivity.PREFS_NAME_L, Context.MODE_PRIVATE);
                        username=settings_l.getString("user_id","test123123");
                        try {
                            connection.login(username, username);
                            connected=true;
                        }catch(Exception e){
                            //System.out.println("connection error:"+e.getMessage().toString());
                            MsgHelper.registerUserXMPP(username);
                            Thread.sleep(1000);
                            try {
                                connection.login(username, username);
                                connected=true;
                            }catch (Exception e1){
                                //System.out.println("connection error2:"+e1.getMessage().toString());
                            }
                        }

                        Presence presence = new Presence(Presence.Type.available);
                        presence.setMode(Presence.Mode.available);
                        connection.sendPacket(presence);
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
                                    //System.out.println("message body" + msg_body);final HashMap<String,String> map=new HashMap<String,String>();
                                    final String tooken_en = "804db40bac2e17f35932693dd4925b930be6925e";
                                    //System.out.println("msg body : "+ msg_body+chat.getParticipant());
                                    RestAdapter restAdapter = new RestAdapter.Builder()
                                        .setLogLevel(RestAdapter.LogLevel.FULL)
                                        .setEndpoint(HomeActivity.getHome_context().getResources().getString(R.string.server_url))//https://www.tripalocal.com
                                        .setRequestInterceptor(new RequestInterceptor() {
                                            @Override
                                            public void intercept(RequestFacade request) {
                                                request.addHeader("Accept", "application/json");
                                                request.addHeader("Authorization", "Token " + tooken_en);
                                            }
                                        })
                                        .build();
                                    ApiService apiService = restAdapter.create(ApiService.class);

                                    apiService.getPublicProfile(partiticipant_id, new Callback<Profile_result>() {
                                        @Override
                                        public void success(Profile_result result, Response response) {
                                            GeneralHelper.closeLoadingProgress();
                                            final HashMap<String, String> map = new HashMap<String, String>();
                                            map.put("name", result.getFirst_name() + " " + result.getLast_name());
                                            map.put("image", result.getImage());
                                            ChatListDataSource dataSource = new ChatListDataSource(getApplicationContext());
                                            ChatList_model model = new ChatList_model();
                                            model.setSender_id(partiticipant_id);
                                            model.setSender_name(map.get("name"));
                                            model.setLast_msg_content(msg_body);
                                            model.setLast_msg_date(GeneralHelper.getDateTime());
                                            model.setSender_img(map.get("image"));

                                            ChatMsgDataSource msgDataSource = new ChatMsgDataSource(getApplicationContext());
                                            ChatMsg_model msgModel = new ChatMsg_model();
                                            msgModel.setReceiver_id(partiticipant_id);
                                            msgModel.setReceiver_name(map.get("name"));
                                            msgModel.setMsg_date(GeneralHelper.getDateTime());
                                            msgModel.setMsg_content(msg_body);
                                            msgModel.setMsg_type(ChatActivity.receiver_flag);
                                            msgModel.setReceiver_img(map.get("image"));
                                            try {
                                                dataSource.open();
                                                dataSource.createNewChat(model);
                                                dataSource.close();

                                                msgDataSource.open();
                                                msgDataSource.addNewMsg(msgModel);
                                                msgDataSource.close();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                                //System.out.println("Exception here " + e.getMessage().toString());
                                            }
                                            if (ChatActivity.sender_id.equals(partiticipant_id) && !ChatActivity.sender_id.equals("")) {

                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                    //update UI elements
                                                    ChatActivity.addTextToListStatic(msg_body, ChatActivity.receiver_flag, map.get("image"),GeneralHelper.getDateTime());
                                                    ChatActivity.notifAdapterStatic();
                                                    if(MsgListFragment.getAdapter()!=null) {
                                                        MsgListFragment.getAdapter().refreshData();
                                                    }
                                                    }
                                                });
                                            } else {
                                                NotificationHelper.msg_notification(partiticipant_id, map.get("name"), map.get("image"), msg_body, getApplicationContext());
                                                runOnUiThread(new Runnable() {
                                                    public void run() {
                                                    NotificationHelper.addBadge();
                                                    if(MsgListFragment.getInit()) {
                                                        MsgListFragment.getAdapter().refreshData();
                                                    }
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            GeneralHelper.closeLoadingProgress();
                                            //System.out.println("ERROR test :" + error + "\n Tooken is " + HomeActivity.getCurrent_user().getLogin_token());
                                        }
                                    });
                                }
                                }
                            });
                            }
                        });
                    }catch(Exception e){
                        //System.out.println("service erro1:"+e.getMessage().toString());
                    }
            }catch(Exception e){
                //System.out.println("service error2"+e.getMessage().toString());
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

    //private messageTask extends AsyncTask<String,Stirng,Stirng>(){}

    private void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }
    @Override
    public IBinder onBind(Intent arg0) {
        //Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
    }
}

