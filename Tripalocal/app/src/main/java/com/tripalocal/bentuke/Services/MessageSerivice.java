package com.tripalocal.bentuke.Services;

/**
 * Created by chenf_000 on 14/07/2015.
 */

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.adapters.ChatMsgListener;

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

/**
 * Created by chenf_000 on 14/07/2015.
 */
public class MessageSerivice extends Service {
    private static final String TAG = "HelloService";

    private boolean isRunning  = false;
    public static Chat chat;
    public static XMPPTCPConnection connection;

    @Override
    public void onCreate() {

        isRunning = true;
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
                /** 获取当前登陆用户 */

                        try {
                            Log.i(TAG, "Service onStartCommand");
                            System.out.println("service statrted");
                            System.out.println("this is a test");
                            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                                    .setHost("10.0.3.2")
                                    .setServiceName("10.0.3.2")
                                    .setPort(5222)
                                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                                    .build();
                            connection = new XMPPTCPConnection(config);
                            connection.connect();
                            connection.login("zhuxiaole", "zhuxiaole");

                            HomeActivity.connection = connection;
                        }catch(Exception e){
                            System.out.println(e.getMessage().toString());
                        }


//                    System.out.println("User:"+ connection.getUser());
                ChatManager chatManager= ChatManager.getInstanceFor(connection);
                chat=chatManager.createChat("frankcf329@frank");
//                chatManager.addChatListener(new ChatMsgListener());
                while (true) ;
            }catch(Exception e){
                System.out.println(""+e.getMessage().toString());
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

