package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity implements FriendsListSelectItem {

    TextView currentUsername;
    TextView currentEmail;
    TextView name;

    TextView friendListHeading;

//    Recycle View of the list
    RecyclerView recyclerViewFriendsList;
    List<FriendsListData> friendsList;

//    Firebase initial setup
    FirebaseDatabase fireBasedatabase;
    DatabaseReference myRefFireBase;

    String intentUsername;
    String intentUserFullName;

    Button profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);





//        currentUsername = findViewById(R.id.friendListusername);
//        currentEmail = findViewById(R.id.friendListEmail);
//        name = findViewById(R.id.fromRegisterPage);
//
        friendListHeading = findViewById(R.id.friendListHeading);
        profileButton = findViewById(R.id.profileButton);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intentUsername = extras.getString("username");
            String intentEmail = extras.getString("email");
            boolean isFromRegister = extras.getBoolean("comingFromRegister");
            intentUserFullName = extras.getString("user_full_name");

            // Check for notifications
//            new RTDBNotificationListener().checkForNotifications(intentUsername, this);
            checkForNotifications(intentUsername, null);
//            if (isFromRegister) {
//                name.setText("COMING FROM THE REGISTER PAGE!!!");
//            }
//
//            currentUsername.setText(intentUsername);
//            currentEmail.setText(intentEmail);

            friendListHeading.setText("Hello " + intentUserFullName);

//            Create a new instance and preserve the state of the list.
            friendsList = new ArrayList<>();

//            Calling db(firebase) to populate the list.
            this.getFriendsList();

        }

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(FriendsList.this, ProfileActivity.class);
                intent.putExtra("userID", intentUsername);
                startActivity(intent);
            }
        });
    }

    private void getFriendsList() {

        // Connect to the firebase.
        fireBasedatabase = FirebaseDatabase.getInstance();
        myRefFireBase = fireBasedatabase.getReferenceFromUrl("https://stickittoem-83164-default-rtdb.firebaseio.com/");

        // Iterate the child - users
        myRefFireBase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Iterate over all the users(key) in the child users in the db
                for(DataSnapshot userValue : snapshot.getChildren()) {

                    // Avoid adding the logged in user to the friends list
                    if(!userValue.getKey().equals(intentUsername)
                            && userValue.getValue() != null) {

                        friendsList.add(new FriendsListData(
                                userValue.child("email").getValue().toString(),
                                userValue.child("full_name").getValue().toString(),
                                userValue.child("username").getValue().toString(),
                                Integer.valueOf(userValue.child("sCount1").getValue().toString()),
                                Integer.valueOf(userValue.child("sCount2").getValue().toString()),
                                Integer.valueOf(userValue.child("sCount3").getValue().toString()),
                                Integer.valueOf(userValue.child("sCount4").getValue().toString()))
                        );

                    }

                }

                // Set the adapter to the list created
                recyclerViewFriendsList = findViewById(R.id.recyclerViewFriendsList);
                recyclerViewFriendsList.setHasFixedSize(true);
                recyclerViewFriendsList.setLayoutManager(new LinearLayoutManager(FriendsList.this));
                recyclerViewFriendsList.setAdapter(new FriendsListAdapter(friendsList, FriendsList.this, FriendsList.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public void onSelectFriendToSendMessage(FriendsListData friendData) {


        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("recipientEmail", friendData.getFriendEmail());
        intent.putExtra("recipientUserFullName", friendData.getFriendFullName());
        intent.putExtra("recipientUserName", friendData.getFriendUserName());
        intent.putExtra("recipientUserS1Count", friendData.getFriendStickerCount1());
        intent.putExtra("recipientUserS2Count", friendData.getFriendStickerCount2());
        intent.putExtra("recipientUserS3Count", friendData.getFriendStickerCount3());
        intent.putExtra("recipientUserS4Count", friendData.getFriendStickerCount4());

        intent.putExtra("senderUserName", intentUsername);
        intent.putExtra("senderUserFullName", intentUserFullName);

        startActivity(intent);
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
                            notifyUser(stickerID, senderID, receiverID, currentNotification.toString(), getApplicationContext());

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Message",
                    NotificationManager.IMPORTANCE_HIGH
            );

            int sticker = R.mipmap.sticker4_round;

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Create Notification
        android.app.Notification.Builder notificationBuilder = new Notification.Builder(context,
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
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_IMMUTABLE);
        notificationBuilder.setContentIntent(pIntent);

        Log.d("Notify", notificationBuilder.toString());

        NotificationManagerCompat.from(context).notify(1, notificationBuilder.build());
    }
}