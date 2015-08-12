package com.tripalocal.bentuke.models.network;

import java.util.ArrayList;

/**
 * Created by Frank on 12/08/2015.
 */
public class Conversation_Result {
   public int id,receiver_id,sender_id;
    public String msg_content;

    public int getId() {
        return id;
    }

    public Conversation_Result(int id, int receiver_id, int sender_id, String msg_content, String msg_date) {
        this.id = id;
        this.receiver_id = receiver_id;
        this.sender_id = sender_id;
        this.msg_content = msg_content;
        this.msg_date = msg_date;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public String msg_date;
}
