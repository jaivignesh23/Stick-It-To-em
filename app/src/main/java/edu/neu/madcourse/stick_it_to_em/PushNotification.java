package edu.neu.madcourse.stick_it_to_em;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

// https://firebase.google.com/docs/cloud-messaging/android/receive
public class PushNotification extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        String msgHeader = message.getNotification().getTitle();
        String msgContent = message.getNotification().getBody();
        String channelId = "A8MSG";

        NotificationChannel notificationChannel = new NotificationChannel(channelId,"Notification Message", NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        Notification.Builder notificationSetting = new Notification.Builder(this,channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(msgHeader)
                .setContentText(msgContent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManage = NotificationManagerCompat.from(this);
        // // notificationId is a unique int for each notification that you must define
        notificationManage.notify(1, notificationSetting.build());

        super.onMessageReceived(message);


    }
}
