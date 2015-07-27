package com.tripalocal.bentuke.helpers.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tripalocal.bentuke.Views.HomeActivity;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatList_db_Helper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="chat"+Integer.parseInt(HomeActivity.getCurrent_user().getUser_id())+".db";
    private static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="ChatList_table";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_SENDER_ID="sender_id";
    public static final String COLUMN_SENDER_NAME="sender_name";
    public static final String COLUMN_LAST_MSG_CONTENT="last_msg_content";
    public static final String COLUMN_LAST_MSG_DATE="last_msg_date";
    public static final String COLUMN_SENDER_IMAGE="sender_image";

    private static final String DATABASE_CREATE="create table if not exists "
            +TABLE_NAME+ "( "+
            COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_SENDER_ID+" TEXT NOT NULL, " +
            COLUMN_SENDER_NAME+" TEXT NOT NULL, "+
            COLUMN_LAST_MSG_CONTENT+" TEXT NOT NULL, "+
            COLUMN_SENDER_IMAGE+" TEXT NOT NULL, "+
            COLUMN_LAST_MSG_DATE+ " TEXT NOT NULL);";


    public ChatList_db_Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_CREATE);

    }

}
