package com.tripalocal.bentuke.models.database;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatList_model {

    public ChatList_model(String sender_id, String sender_name, String last_msg_date, String last_msg_content) {
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.last_msg_date = last_msg_date;
        this.last_msg_content = last_msg_content;
    }

    public ChatList_model(){

    }
    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getLast_msg_date() {
        return last_msg_date;
    }

    public void setLast_msg_date(String last_msg_data) {
        this.last_msg_date = last_msg_data;
    }

    public String getLast_msg_content() {
        return last_msg_content;
    }

    public void setLast_msg_content(String last_msg_content) {
        this.last_msg_content = last_msg_content;
    }

    private String sender_id,sender_name,last_msg_date,last_msg_content;

}
