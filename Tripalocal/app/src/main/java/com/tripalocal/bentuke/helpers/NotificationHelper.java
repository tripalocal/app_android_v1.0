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

/**
 * Created by chenf_000 on 17/07/2015.
 */
public class NotificationHelper {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public final static void msg_notification(String title,String msg_detail,Context context){
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(msg_detail);//details msg inside the notifcation bar
        mBuilder.setTicker(msg_detail);//notification msg on the top
        mBuilder.setSmallIcon(R.drawable.icon_app_notification);
        Intent resultIntent=new Intent(context, ChatActivity.class);
        resultIntent.putExtra("notificationId", 1);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ChatActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent=stackBuilder.getPendingIntent(
                0,PendingIntent.FLAG_ONE_SHOT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager myNotificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        ChatActivity.sender_id=title;
        ChatActivity.sender_name=title;
        myNotificationManager.notify(1, mBuilder.build());

    }

}
