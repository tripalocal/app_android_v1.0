package com.tripalocal.bentuke.Views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection= MessageSerivice.connection;
        if(connection==null){
            try {
                Thread.sleep(5000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connection= MessageSerivice.connection;

        }
        String title_t=getResources().getString(R.string.msg_chat_title);
        setTitle(title_t);
        setContentView(R.layout.activity_chat);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                msgHandler();
            }
        }).start();

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

    }
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_msg_detail, menu);
//        if(menu_ref == null)
//            menu_ref= menu;
//        if(getCurrent_user().isLoggedin()){
//            menu.findItem(R.id.action_login).setTitle(getResources().getString(R.string.logout));
//        }
//
//        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
//            @Override
//            public boolean onSuggestionSelect(int position) {
//                //ToastHelper.shortToast("sugg select "+position);
//                return false;
//            }
//
//            @Override
//            public boolean onSuggestionClick(int position) {
//                //ToastHelper.shortToast("sugg click "+position +" : "+ db_poi_data[position]);
//                Fragment exp_list_frag = new ExperiencesListFragment();
//                Bundle args = new Bundle();
//                args.putInt(ExperienceListAdapter.INT_EXTRA, position);
//                exp_list_frag.setArguments(args);
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exp_list_frag).addToBackStack("home_sugg").commit();
//                return false;
//            }
//        });
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


    public void msgHandler(){
        try{

//                    System.out.println("User:"+ connection.getUser());
            ChatManager chatManager= ChatManager.getInstanceFor(connection);
            chat=chatManager.createChat("frankcf329@frank");
            System.out.println("here comes 1");
            chatManager.addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean createdLocally) {
                    chat.addMessageListener(new ChatMessageListener() {

                        @Override
                        public void processMessage(Chat arg0, Message arg1) {
                            System.out.println("here comes 1");

                            if (null != arg1.getBody()) {
                                String from = arg1.getFrom().substring(0, arg1.getFrom().indexOf("@"));
                                System.out.println("from " + from + " : " + arg1.getBody());
//                                try {
//                                    fragment.addTextToList(arg1.getBody());
//                                    fragment.notifAdapter();
//                                    ToastHelper.errorToast("test");
//                                }catch (Exception e){
//                                    System.out.println("Exception here: "+e.getMessage().toString());
//                                }
                                System.out.println("goes inside");
//                                fragment.test();
                            }
                        }
                    });
                }
            });
            System.out.println("here comes 3");

            while (true) ;
        }catch(Exception e){
            System.out.println(""+e.getMessage().toString());
        }
    }


}
