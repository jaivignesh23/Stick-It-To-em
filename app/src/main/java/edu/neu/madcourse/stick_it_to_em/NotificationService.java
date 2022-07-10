package edu.neu.madcourse.stick_it_to_em;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;

public class NotificationService extends FirebaseMessagingService{

    String TAG = "Notification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String notificationTitle = remoteMessage.getNotification().getTitle();

        // Text should have userName and stickerID
        String notificationText[] = remoteMessage.getNotification().getBody().split(";");
        String messengerUser = notificationText[0];
        String messengerStickerID = notificationText[1];
        String conversationID = notificationText[2];

        // Unique for each conversation
        final String NOTIFICATION_CHANNEL_ID = conversationID;

        NotificationChannel notificationChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Message",
                NotificationManager.IMPORTANCE_HIGH
        );

        getSystemService(NotificationManager.class).createNotificationChannel(notificationChannel);

        int sticker = R.mipmap.sticker4_round;

        // Create Notification
        Notification.Builder notificationBuilder = new Notification.Builder(this,
                NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setContentText("Message from " + messengerUser);
        notificationBuilder.setContentTitle(notificationTitle);
        notificationBuilder.setAutoCancel(true);

        try {
            InputStream ims = getAssets().open("sticker1.jpeg");
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            notificationBuilder.setSmallIcon(Icon.createWithBitmap(bitmap));
        } catch (IOException e) {
            e.printStackTrace();
        }

          // PendingIntent intent = new PendingIntent.OnFinished();
          // notificationBuilder.setContentIntent(intent)

        if (/* Only if user matches*/true)
            NotificationManagerCompat.from(this).notify(1, notificationBuilder.build());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        super.onMessageReceived(remoteMessage);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
