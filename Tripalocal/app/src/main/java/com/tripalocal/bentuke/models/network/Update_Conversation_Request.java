package com.tripalocal.bentuke.models.network;

import java.util.ArrayList;

/**
 * Created by Frank on 12/08/2015.
 */
public class Update_Conversation_Request {
    public ArrayList<Conversation_msg_api> messages;

    public Update_Conversation_Request(ArrayList<Conversation_msg_api> messages){
        this.messages=messages;
    }

    public Update_Conversation_Request(){
        this.messages=new ArrayList<Conversation_msg_api>();
    }
    public ArrayList<Conversation_msg_api> getMessages() {
        return messages;
    }

    public void addToList(Conversation_msg_api item){
        messages.add(item);
    }
}


