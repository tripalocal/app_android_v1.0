package com.tripalocal.bentuke.models.network;

/**
 * Created by Frank on 13/08/2015.
 */
public class Conversation_msg_api {

    public int receiver_id,local_id;

    public Conversation_msg_api() {

    }

    public Conversation_msg_api(int receiver_id, int local_id, String msg_content, String msg_date) {
        this.receiver_id = receiver_id;
        this.local_id = local_id;
        this.msg_content = msg_content;
        this.msg_date = msg_date;
    }

    public String msg_content,msg_date;

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
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
}
