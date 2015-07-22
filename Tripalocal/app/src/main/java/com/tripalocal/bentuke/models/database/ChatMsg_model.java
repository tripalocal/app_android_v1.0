package com.tripalocal.bentuke.models.database;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatMsg_model {
    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public ChatMsg_model (String receiver_id, String receiver_name, String msg_content, String msg_date) {
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.msg_content = msg_content;
        this.msg_date = msg_date;
    }
    public ChatMsg_model(){}

    public String getMsg_date() {

        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    private String receiver_id,receiver_name, msg_content,msg_date;
    private int msg_id;

}
