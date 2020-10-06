package kr.co.song1126.mypush_test01;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCM_Service extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("onreceivedLog", remoteMessage.getFrom());

        String title="";
        String msg="";

        String notiTitle=null;
        // remoteMessage.getNotification().getTitle();
        String notiBody=null;
        // remoteMessage.getNotification().getBody();

        Map<String, String> data;

        if (remoteMessage.getData().size()>0){// map방식으로 반든 data
            Log.d("getMessageData", remoteMessage.getData().toString());
            data=remoteMessage.getData();
            title=data.get("title");
            msg=data.get("msg");
        }




        if (remoteMessage.getNotification()!=null){
            Log.d("getNotification", remoteMessage.getNotification().getBody());
            notiTitle=remoteMessage.getNotification().getTitle();
            notiBody=remoteMessage.getNotification().getBody();

            //유료서비스만 사용가능
            Uri notiImgUri=remoteMessage.getNotification().getImageUrl();

        }

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder=null;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("ch01", "channel01", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            builder=new NotificationCompat.Builder(this, "ch01");
        }else builder=new NotificationCompat.Builder(this, null);

        builder.setSmallIcon(R.drawable.ic_beach_access_black_24dp);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiBody);

        Intent intent=new Intent(this, MainActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("msg", msg);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification notification=builder.build();
        notificationManager.notify(111, notification);


    }
}
