package com.tripalocal.bentuke.helpers.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tripalocal.bentuke.models.database.ChatList_model;

import org.jivesoftware.smack.chat.Chat;
import org.w3c.dom.Comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatListDataSource {
    private SQLiteDatabase database;
    private ChatList_db_Helper dbHelper;
    private String[] allColumns={
      ChatList_db_Helper.COLUMN_LAST_MSG_CONTENT,
            ChatList_db_Helper.COLUMN_LAST_MSG_DATE,
            ChatList_db_Helper.COLUMN_SENDER_ID,
            ChatList_db_Helper.COLUMN_SENDER_NAME,
            ChatList_db_Helper.COLUMN_SENDER_IMAGE
    };

    public ChatListDataSource(Context context){
        dbHelper=new ChatList_db_Helper(context);
    }

    public void open() throws SQLException {
        database=dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void createNewChat(ChatList_model model){
        Boolean checkReuslt=checkChat(model.getSender_id().trim());
        ContentValues values=new ContentValues();
        values.put(dbHelper.COLUMN_LAST_MSG_CONTENT,model.getLast_msg_content());
        values.put(dbHelper.COLUMN_LAST_MSG_DATE,model.getLast_msg_date());
        values.put(dbHelper.COLUMN_SENDER_ID,model.getSender_id().trim().trim());
        values.put(dbHelper.COLUMN_SENDER_NAME,model.getSender_name());
        values.put(dbHelper.COLUMN_SENDER_IMAGE,model.getSender_img());
//        if(checkReuslt) {
            long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
//        }else {
//            long insertId = database.update(dbHelper.TABLE_NAME, values, " " + dbHelper.COLUMN_SENDER_ID + "= ? ", new String[]{model.getSender_id()});
//        Cursor cursor=database.query(dbHelper.TABLE_NAME);

//        }
    }

    public void deleteChat(String sender_id){
        database.delete(dbHelper.TABLE_NAME, dbHelper.COLUMN_SENDER_ID + " = " +
                "'" + sender_id +"'",null);
    }

    public List<ChatList_model> getChatList(){
        List<ChatList_model> chats=new ArrayList<ChatList_model>();
        Cursor cursor=database.query(dbHelper.TABLE_NAME,allColumns,null,null,null,null,dbHelper.COLUMN_ID+" DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ChatList_model model=cursorToChatList(cursor);
            chats.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return chats;
    }

    public boolean checkChat(String sender_id){
//        List<ChatList_model> models=new ArrayList<ChatList_model>();
        Cursor cursor=database.query(dbHelper.TABLE_NAME,allColumns,
                dbHelper.COLUMN_SENDER_ID+" = '"+sender_id+"'",null,null,null,null);
        if(cursor.getCount()!=0){
            deleteChat(sender_id);
            return  false;
        }
        return true;
    }

    public ChatList_model cursorToChatList(Cursor cursor){
        ChatList_model model=new ChatList_model();
        model.setLast_msg_content(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_LAST_MSG_CONTENT)));
        model.setLast_msg_date(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_LAST_MSG_DATE)));
        model.setSender_id(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENDER_ID)).trim());
        model.setSender_name(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENDER_NAME)));
        model.setSender_img(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_SENDER_IMAGE)));
        return model;
    }

}


