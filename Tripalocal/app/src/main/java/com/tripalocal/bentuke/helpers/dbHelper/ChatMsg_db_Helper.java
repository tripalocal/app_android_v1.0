package com.tripalocal.bentuke.helpers.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tripalocal.bentuke.Views.HomeActivity;

/**
 * Created by chenf_000 on 22/07/2015.
 */
public class ChatMsg_db_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="chatMsg"+Integer.parseInt(HomeActivity.getCurrent_user().getUser_id())+".db";
    private static final int DATABASE_VERSION=1;
    public static final String TABLE_NAME="ChatMsg_table";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_RECEIVER_ID="receiver_id";
    public static final String COLUMN_RECEIVER_NAME="receiver_name";
    public static final String COLUMN_MSG_CONTENT="msg_content";
    public static final String COLUMN_MSG_DATE="msg_date";
    public static final String COlUMN_MSG_TYPE="msg_type";
    public static final String COLUMN_RECEIVER_IMAGE="receiver_image";
    public static final String COLUMN_GLOBAL_ID="global_id";

    private static final String DATABASE_CREATE="create table if not exists "
            +TABLE_NAME+ "( "+
            COLUMN_ID +" INTEGER PRIMARY KEY, "+
            COLUMN_RECEIVER_ID+" TEXT NOT NULL, " +
            COLUMN_RECEIVER_NAME+" TEXT NOT NULL, "+
            COLUMN_MSG_CONTENT+" TEXT NOT NULL, "+
            COlUMN_MSG_TYPE+" TEXT NOT NULL, "+
            COLUMN_RECEIVER_IMAGE+" TEXT NOT NULL, "+
            COLUMN_GLOBAL_ID+" TEXT NOT NULL, "+
            COLUMN_MSG_DATE+ " TEXT NOT NULL)";

    public ChatMsg_db_Helper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
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

