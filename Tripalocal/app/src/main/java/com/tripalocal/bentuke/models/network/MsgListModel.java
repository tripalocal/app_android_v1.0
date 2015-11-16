package com.tripalocal.bentuke.models.network;

/**
 * Created by Frank on 12/08/2015.
 */
public class MsgListModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMsg_date() {
        return msg_date;
    }

    public void setMsg_date(String msg_date) {
        this.msg_date = msg_date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }

    public MsgListModel(String sender_image, String msg_content, String sender_name, String msg_date, int sender_id, int id) {
        this.sender_image = sender_image;
        this.msg_content = msg_content;
        this.sender_name = sender_name;
        this.msg_date = msg_date;
        this.sender_id = sender_id;
        this.id = id;

    }

    public MsgListModel(int id, int sender_id, int receiver_id, String msg_date, String sender_name, String msg_content, String sender_image) {
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.msg_date = msg_date;
        this.sender_name = sender_name;
        this.msg_content = msg_content;
        this.sender_image = sender_image;
    }

    public int id,sender_id,receiver_id;
    public String msg_date,sender_name,msg_content,sender_image;

}
