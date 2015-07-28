package com.tripalocal.bentuke.helpers;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ChatActivity;
import com.tripalocal.bentuke.Views.HomeActivity;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by chenf_000 on 17/07/2015.
 */
public class NotificationHelper {
    public static  int badgeCount = 1;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public final static void msg_notification(String id,String name,String image, String msg_detail,Context context){

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(name);
        mBuilder.setContentText(msg_detail);//details msg inside the notifcation bar
        mBuilder.setTicker(msg_detail);//notification msg on the top
        mBuilder.setSmallIcon(R.drawable.msg_notification_icon);
        mBuilder.setAutoCancel(true);//important here, when you start a notificaiotn from
//        the service, it will not auto cancel, you must add this
        Intent intent=new Intent(context, ChatActivity.class);
        intent.putExtra(ChatActivity.COL_NOTIFICATION_ID,id);
        intent.putExtra(ChatActivity.COL_SENDER_ID,id);
        intent.putExtra(ChatActivity.COL_SENDER_NAME,name);
        intent.putExtra(ChatActivity.COL_SENDER_IMG,image);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ChatActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent=stackBuilder.getPendingIntent(
                Integer.parseInt(id),PendingIntent.FLAG_ONE_SHOT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager myNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        ChatActivity.sender_id=title;
//        ChatActivity.sender_name=name;
//        ChatActivity.sender_img=image;
        myNotificationManager.notify(Integer.parseInt(id), mBuilder.build());

    }

    public static void addBadge(){
        ShortcutBadger.with(HomeActivity.getHome_context()).count(badgeCount++);
    }

    public static void clearBadge(){
        badgeCount=1;
        ShortcutBadger.with(HomeActivity.getHome_context()).count(0);

    }

}
