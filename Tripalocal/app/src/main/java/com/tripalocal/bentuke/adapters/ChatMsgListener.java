package com.tripalocal.bentuke.adapters;

/**
 * Created by chenf_000 on 14/07/2015.
 */

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

public class ChatMsgListener implements ChatManagerListener {
    @Override
    public void chatCreated(Chat arg0, boolean arg1) {
        arg0.addMessageListener(new ChatMessageListener() {

            @Override
            public void processMessage(Chat arg0, Message arg1) {
                if (null != arg1.getBody()) {
                    String from = arg1.getFrom().substring(0, arg1.getFrom().indexOf("@"));
                    System.out.println("from " + from + " : " + arg1.getBody());
                }
            }
        });
    }
}
