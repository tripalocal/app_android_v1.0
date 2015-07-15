package com.tripalocal.bentuke.adapters;

/**
 * Created by chenf_000 on 14/07/2015.
 */


import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatMsgListener implements ChatManagerListener {

    private ArrayList<HashMap<String,Object>> chatListMap=null;

    @Override
    public void chatCreated(Chat arg0, boolean arg1) {
        arg0.addMessageListener(new ChatMessageListener() {

            @Override
            public void processMessage(Chat arg0, Message arg1) {
                if (null != arg1.getBody()) {
                    String from = arg1.getFrom().substring(0, arg1.getFrom().indexOf("@"));
                    System.out.println("from " + from + " : " + arg1.getBody());
//                    addTextToList(arg1.getBody());
//                    ChatFragment.chatActivity_context.findViewById(R.id.chat_list).SET

                }
            }
        });
    }

    protected void addTextToList(String text){
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("person", 1);
        map.put("text", text);


    }
}
