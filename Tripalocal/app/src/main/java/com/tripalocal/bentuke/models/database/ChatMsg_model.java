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

    public ChatMsg_model (String receiver_id, String receiver_name, String msg_content, String msg_date,int msg_type,
                               String receiver_img) {
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.msg_content = msg_content;
        this.msg_date = msg_date;
        this.msg_type=msg_type;
        this.receiver_img=receiver_img;
        this.global_id="0";
    }
    public ChatMsg_model (String receiver_id, String receiver_name, String msg_content, String msg_date,int msg_type,
                          String receiver_img,String global_id) {
        this.receiver_id = receiver_id;
        this.receiver_name = receiver_name;
        this.msg_content = msg_content;
        this.msg_date = msg_date;
        this.msg_type=msg_type;
        this.receiver_img=receiver_img;
        this.global_id=global_id;
    }
    public ChatMsg_model(){
        this.global_id="0";

    }

    public String getReceiver_img() {
        return receiver_img;
    }

    public void setReceiver_img(String receiver_img) {
        this.receiver_img = receiver_img;
    }

    public String getMsg_date() {

        return msg_date;
    }

    public String getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(String global_id) {
        this.global_id = global_id;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    private String receiver_id,receiver_name, msg_content,msg_date,receiver_img,global_id;

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    private int msg_id,msg_type;

}
