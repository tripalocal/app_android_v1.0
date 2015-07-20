package com.tripalocal.bentuke.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Services.MessageSerivice;
import com.tripalocal.bentuke.Views.HomeActivity;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;

/**
 * Created by chenf_000 on 20/07/2015.
 */
public class MsgHelper {

    public static void registerUserXMPP(String userId){

        XMPPTCPConnection connection;

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setHost(HomeActivity.getHome_context().getResources().getString(R.string.msg_host))
                .setServiceName(HomeActivity.getHome_context().getString(R.string.msg_server_name))
                .setPort(5222)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();
        connection = new XMPPTCPConnection(config);
        try {
            connection.connect();
            String admin_username=HomeActivity.getHome_context().getResources().getString(R.string.msg_admin_username);
            String admin_password=HomeActivity.getHome_context().getResources().getString(R.string.msg_admin_password);
            connection.login(admin_username, admin_password);
            AccountManager accountManager=AccountManager.getInstance(connection);
            accountManager.createAccount(userId,userId);
        }catch(Exception e){
            System.out.println("connection error:"+e.getMessage().toString());
        }finally{
            connection.disconnect();
        }
    }

    public static void startMsgSerivice(Context context){
        if(!MessageSerivice.isRunning && HomeActivity.getCurrent_user().isLoggedin()) {
            MessageSerivice.username=HomeActivity.getCurrent_user().getUser_id();
            Intent intent = new Intent(context, MessageSerivice.class);
            context.startService(intent);
            System.out.println("user id is "+HomeActivity.getCurrent_user().getUser_id());
        }
    }
}
