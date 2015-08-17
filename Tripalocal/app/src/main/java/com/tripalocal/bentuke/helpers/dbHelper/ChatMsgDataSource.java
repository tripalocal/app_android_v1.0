package com.tripalocal.bentuke.helpers.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tripalocal.bentuke.Views.HomeActivity;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.database.ChatMsg_model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
            ChatMsg_db_Helper.COLUMN_RECEIVER_IMAGE,
            ChatMsg_db_Helper.COLUMN_GLOBAL_ID

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
        values.put(dbHelper.COLUMN_MSG_DATE,GeneralHelper.getUTCTime(model.getMsg_date()));
        values.put(dbHelper.COLUMN_RECEIVER_ID,model.getReceiver_id().trim());
        values.put(dbHelper.COLUMN_RECEIVER_NAME,model.getReceiver_name());
        values.put(dbHelper.COlUMN_MSG_TYPE,model.getMsg_type());
        values.put(dbHelper.COLUMN_RECEIVER_IMAGE,model.getReceiver_img());
        values.put(dbHelper.COLUMN_GLOBAL_ID,model.getGlobal_id());

        System.out.println("msg date :"+model.getMsg_date());
        try {
            long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
            Log.i("Conversation ","add global id is "+model.getGlobal_id());

        }catch (Exception e){
            System.out.println("insert exception here "+ e.getMessage().toString());
        }
//        System.out.println("person type on add new msg");

    }


    public int getLastConversationGlobalId(int receiverId){
        Cursor cursor=database.query(dbHelper.TABLE_NAME,allColumns,dbHelper.COLUMN_RECEIVER_ID+" = "+receiverId
                +" OR " +dbHelper.COLUMN_RECEIVER_ID +" = "+ HomeActivity.getCurrent_user().getUser_id(),null,null,null,null);
        int last_conversation_id=0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ChatMsg_model model=cursorToChatMsg(cursor);
            int temp=Integer.parseInt(model.getGlobal_id());
            if(temp>last_conversation_id){
                last_conversation_id=temp;
                Log.i("Conversation ","global id inside is "+temp);
            }
            Log.i("Conversation ","global id outside is "+temp);

            cursor.moveToNext();
        }
        return last_conversation_id;
    }
    public List<ChatMsg_model> getChatMsgs(int receiverId){
        List<ChatMsg_model> chats=new ArrayList<ChatMsg_model>();
        Map<Date,ChatMsg_model> map = new HashMap<Date,ChatMsg_model>();

        System.out.println("sender id + " + receiverId);
        Cursor cursor=database.query(dbHelper.TABLE_NAME,allColumns,dbHelper.COLUMN_RECEIVER_ID+" = "+receiverId,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ChatMsg_model model=cursorToChatMsg(cursor);
            map.put(GeneralHelper.getDateByString(model.getMsg_date()),model);
//            chats.add(model);
            cursor.moveToNext();
        }
        Map<Date, ChatMsg_model> sortedMap = new TreeMap<Date, ChatMsg_model>(map);
        for(ChatMsg_model m:sortedMap.values()){
            chats.add(m);
        }
        cursor.close();
        return chats;
    }

    public ArrayList<ChatMsg_model> getUnsyncMsgs(int receiverId){
        List<ChatMsg_model> chats=getChatMsgs(receiverId);
        ArrayList<ChatMsg_model> reChats=new ArrayList<ChatMsg_model>();

        for(ChatMsg_model m:chats){
            if(m.getGlobal_id().equals("0") && !m.getReceiver_id().equals(HomeActivity.getCurrent_user().getUser_id())){
                reChats.add(m);
            }
        }
        return reChats;
    }

    public void UpdateGlobalId(int local_id,int global_id){
        ContentValues args = new ContentValues();
        args.put(dbHelper.COLUMN_GLOBAL_ID, global_id +"");
        database.update(dbHelper.TABLE_NAME, args, dbHelper.COLUMN_ID+" =" + local_id, null);

    }

    public void RemoveAlldataWithoutGlobalId(int receiverId){
        List<ChatMsg_model> chats=getChatMsgs(receiverId);

        for(ChatMsg_model m:chats){
            System.out.println("on the delete option ,global id is "+m.getGlobal_id() +
            "and the receiver id is "+m.getReceiver_id());
            if(m.getGlobal_id().equals("0") ){
//                reChats.add(m);
                String whereClause = dbHelper.COLUMN_ID + "=?";
                String[] whereArgs = new String[] {m.getMsg_id()+""};
                database.delete(dbHelper.TABLE_NAME,whereClause,whereArgs);
                System.out.println("delete record here ");
            }
        }
    }



    public ChatMsg_model cursorToChatMsg(Cursor cursor){
        ChatMsg_model model=new ChatMsg_model();
        model.setMsg_type(cursor.getInt(cursor.getColumnIndex(dbHelper.COlUMN_MSG_TYPE)));
        model.setMsg_content(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_MSG_CONTENT)));
        model.setMsg_date(GeneralHelper.getLocalTime(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_MSG_DATE))));
        model.setReceiver_id(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_ID)));
        model.setReceiver_name(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_NAME)));
        model.setReceiver_img(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_RECEIVER_IMAGE)));
        model.setGlobal_id(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_GLOBAL_ID)));
        model.setMsg_id(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)));
        Log.i("MESSAGE","retrieve msg id : "+cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_ID)));
        return model;
    }
}
