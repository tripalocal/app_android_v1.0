package com.tripalocal.bentuke.models;

/**
 * Created by chenfang on 8/07/2015.
 */

public class Message {
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDatetime_sent() {
        return datetime_sent;
    }

    public String getDatetime_delieverd() {
        return datetime_delieverd;
    }

    public String getDatetime_read() {
        return datetime_read;
    }

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    String sender,receiver,datetime_sent,datetime_delieverd,
            datetime_read,status,content;
    public Message(String sender,String receiver,String datetime_sent,
                   String datetime_delieverd,String datetime_read,
                   String status,String content){
        this.sender=sender;
        this.receiver=receiver;
        this.datetime_sent=datetime_sent;
        this.datetime_delieverd=datetime_delieverd;
        this.datetime_read=datetime_read;
        this.status=status;
        this.content=content;
    }

    public  Message(String sender,String datetime_read,String content){
        this.sender=sender;
        this.datetime_read=datetime_read;
        this.content=content;
    }

    public String test(){
        return "this si a a test";
    }
}
