package com.tripalocal.bentuke.models.database;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatList_model {

    public ChatList_model(String sender_id, String sender_name, String last_msg_date, String last_msg_content,String sender_img,
                          String global_id) {
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.last_msg_date = last_msg_date;
        this.last_msg_content = last_msg_content;
        this.sender_img=sender_img;
        this.global_id=global_id;
    }

    public ChatList_model(String sender_id, String sender_name, String last_msg_date, String last_msg_content,String sender_img
                          ) {
        this.sender_id = sender_id;
        this.sender_name = sender_name;
        this.last_msg_date = last_msg_date;
        this.last_msg_content = last_msg_content;
        this.sender_img=sender_img;
        this.global_id="0";
    }

    public ChatList_model(){
        this.global_id="0";

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

    public String getSender_img() {
        return sender_img;
    }

    public void setSender_img(String sender_img) {
        this.sender_img = sender_img;
    }

    public String getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(String global_id) {
        this.global_id = global_id;
    }

    public String getLast_msg_content() {
        return last_msg_content;
    }

    public void setLast_msg_content(String last_msg_content) {
        this.last_msg_content = last_msg_content;
    }

    private String sender_id,sender_name,last_msg_date,last_msg_content,sender_img,global_id;

}
