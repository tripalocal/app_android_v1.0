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

    public Boolean getSendMsgFlag(){
        return sendMsgFlag;
    }
    String sender,receiver,datetime_sent,datetime_delieverd,
            datetime_read,status,content;
    Boolean sendMsgFlag;
    public Message(String sender,String receiver,String datetime_sent,
                   String datetime_delieverd,String datetime_read,
                   String status,String content,Boolean sendMsgFlag){
        this.sender=sender;
        this.receiver=receiver;
        this.datetime_sent=datetime_sent;
        this.datetime_delieverd=datetime_delieverd;
        this.datetime_read=datetime_read;
        this.status=status;
        this.content=content;
        this.sendMsgFlag=sendMsgFlag;
    }

    public  Message(String sender,String datetime_read,String content,boolean sendMsgFlag){
        this.sender=sender;
        this.datetime_read=datetime_read;
        this.content=content;
        this.sendMsgFlag=sendMsgFlag;
    }

    public String test(){
        return "this si a a test";
    }
}
