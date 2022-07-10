package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;

public class RTDBNotificationListener extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void checkForNotifications(String currentUserID, Context context) {
        FirebaseDatabase database;
        DatabaseReference notificationsReference;

        database = FirebaseDatabase.getInstance();
        notificationsReference = database.getReferenceFromUrl(
                "https://stickittoem-83164-default-rtdb.firebaseio.com/notifications");

        notificationsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Get the notification Details and notify the user
                    Log.i("Notifications", "Changed");
                    for (DataSnapshot currentNotification : snapshot.getChildren()) {
                        Log.i("Current User", currentNotification.child("receiverID").getValue().toString());
                        Log.i("Current User ID", currentUserID);
                        if (currentNotification.child("receiverID").getValue().toString().equals(currentUserID)) {
                            // Notify the user in the app
                            Log.i("Notify", "Current User");
                            String senderID = currentNotification.
                                    child("senderID").getValue().toString();
                            int stickerID = Integer.parseInt(currentNotification.
                                    child("stickerID").getValue().toString());
                            String receiverID = currentNotification.
                                    child("receiverID").getValue().toString();
                            notifyUser(stickerID, senderID, receiverID, currentNotification.toString(), context);

                            // Delete the entry from the RealTime DB

                        }
                    }
                    notificationsReference.child("dummy").removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*notificationsReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Notification", "Child added");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("Notification", "Child added");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }



    private void notifyUser(int stickerID, String senderName, String recipientUserName,
                            String notificationID, Context context) {

        Log.d("Notify", "notifying user");
        String message = senderName + " sent a new sticker!";

        final String NOTIFICATION_CHANNEL_ID = notificationID;

        NotificationChannel notificationChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Message",
                NotificationManager.IMPORTANCE_HIGH
        );

        int sticker = R.mipmap.sticker4_round;

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);

        // Create Notification
        Notification.Builder notificationBuilder = new Notification.Builder(context,
                NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setContentText("Message from " + senderName);
        notificationBuilder.setContentTitle("New Message");
        notificationBuilder.setAutoCancel(true);

        try {
            InputStream ims = getAssets().open("sticker1.jpeg");
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            notificationBuilder.setSmallIcon(Icon.createWithBitmap(bitmap));
            notificationBuilder.setLargeIcon(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("senderUserName", senderName);
        intent.putExtra("recipientUserName", recipientUserName);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(),
                intent, 0);
        notificationBuilder.setContentIntent(pIntent);

        Log.d("Notify", notificationBuilder.toString());

        NotificationManagerCompat.from(context).notify(1, notificationBuilder.build());
    }
}