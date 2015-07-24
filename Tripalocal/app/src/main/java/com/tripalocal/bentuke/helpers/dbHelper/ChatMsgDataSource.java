package com.tripalocal.bentuke.helpers.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatMsgDataSource{
    private SQLiteDatabase database;
    private ChatMsg_db_Helper dbHelper;
    private String[] allColumns={
            ChatMsg_db_Helper.COLUMN_ID,
            ChatMsg_db_Helper.COLUMN_MSG_CONTENT,
            ChatMsg_db_Helper.COLUMN_MSG_DATE,
            ChatMsg_db_Helper.COLUMN_RECEIVER_ID,
            ChatMsg_db_Helper.COLUMN_RECEIVER_NAME,
            ChatMsg_db_Helper.COlUMN_MSG_TYPE,
            ChatMsg_db_Helper.COLUMN_RECEIVER_IMAGE
    };

    public ChatMsgDataSource(Context context){
        dbHelper=new ChatMsg_db_Helper(context);
    }

    public void open() throws SQLException {
        database=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void addNewMsg(ChatMsg_model model){
        ContentValues values=new ContentValues();
        values.put(dbHelper.COLUMN_MSG_CONTENT,model.getMsg_content());
        values.put(dbHelper.COLUMN_MSG_DATE,model.getMsg_date());
        values.put(dbHelper.COLUMN_RECEIVER_ID,model.getReceiver_id());
        values.put(dbHelper.COLUMN_RECEIVER_NAME,model.getReceiver_name());
        values.put(dbHelper.COlUMN_MSG_TYPE,model.getMsg_type());
        values.put(dbHelper.COLUMN_RECEIVER_IMAGE,model.getReceiver_img());

        long insertId=database.insert(dbHelper.TABLE_NAME,null,values);
        System.out.println("added to database successfully"+model.getMsg_type()+values.get(dbHelper.COlUMN_MSG_TYPE));
//        System.out.println("person type on add new msg");

    }

    public List<ChatMsg_model> getChatMsgs(int receiverId){
        List<ChatMsg_model> chats=new ArrayList<ChatMsg_model>();
        System.out.println("sender id + "+ receiverId);
        Cursor cursor=database.query(dbHelper.TABLE_NAME,allColumns,dbHelper.COLUMN_RECEIVER_ID+" = "+receiverId,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ChatMsg_model model=cursorToChatMsg(cursor);
            chats.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return chats;
    }


    public ChatMsg_model cursorToChatMsg(Cursor cursor){
        ChatMsg_model model=new ChatMsg_model();
        model.setMsg_type(cursor.getInt(cursor.getColumnIndex(dbHelper.COlUMN_MSG_TYPE)));
        model.setMsg_content(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_MSG_CONTENT)));
        model.setMsg_date(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_MSG_DATE)));
        model.setReceiver_id(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_ID)));
        model.setReceiver_name(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_NAME)));
        model.setReceiver_img(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_IMAGE)));


        return model;
    }
}
