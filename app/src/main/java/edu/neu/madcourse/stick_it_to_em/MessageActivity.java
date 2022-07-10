package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageActivity extends AppCompatActivity {

    private String senderID, receiverID;
    FirebaseDatabase database;
    DatabaseReference dbReference;
    private int numChildren;
    String currentImage;
    Integer currentImageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        senderID = getIntent().getStringExtra("senderID");
        receiverID = getIntent().getStringExtra("receiverID");

        ImageView imageView1, imageView2, imageView3, imageView4, imageView5;

        imageView1 = findViewById(R.id.imageView1);
        imageView1.setImageResource(R.mipmap.sticker1_round);
        imageView1.setScaleX(1.7f);
        imageView1.setScaleY(1.7f);

        imageView2 = findViewById(R.id.imageView2);
        imageView2.setImageResource(R.mipmap.sticker2_round);
        imageView2.setScaleX(1.7f);
        imageView2.setScaleY(1.7f);

        imageView3 = findViewById(R.id.imageView3);
        imageView3.setImageResource(R.mipmap.sticker3_round);
        imageView3.setScaleX(1.7f);
        imageView3.setScaleY(1.7f);

        imageView4 = findViewById(R.id.imageView4);
        imageView4.setImageResource(R.mipmap.sticker4_round);
        imageView4.setScaleX(1.7f);
        imageView4.setScaleY(1.7f);

        imageView1.setOnClickListener(
                v -> {
                    sendDataToRTDB(1, senderID, receiverID);
                    openChatActivity();
                    finish();
                }
        );

        imageView2.setOnClickListener(
                v -> {
                    sendDataToRTDB(2, senderID, receiverID);
                    openChatActivity();
                    finish();
                }
        );

        imageView3.setOnClickListener(
                v -> {
                    sendDataToRTDB(3, senderID, receiverID);
                    openChatActivity();
                    finish();
                }
        );

        imageView4.setOnClickListener(
                v -> {
                    sendDataToRTDB(4, senderID, receiverID);
                    openChatActivity();
                    finish();
                }
        );
    }

    public void openChatActivity() {


        Intent intent = new Intent(this, ChatActivity.class);

        //intent.putExtra("recipientUserFullName", chatActivityData.getFriendFullName());
        intent.putExtra("recipientUserName", receiverID);
        intent.putExtra("senderUserName", senderID);
        startActivity(intent);
    }

    @SuppressLint("DefaultLocale")
    private void sendDataToRTDB(int imageID, String senderID, String receiverID) {
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReferenceFromUrl(
                "https://stickittoem-83164-default-rtdb.firebaseio.com/");

        // TODO: get current number of conversations
        dbReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {
                numChildren = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot,
                                       @Nullable String previousChildName) {
                numChildren = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                numChildren = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot,
                                     @Nullable String previousChildName) {
                numChildren = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Add entry in conversations key.
        int conversationCount = numChildren;
        DatabaseReference conversationsReference = dbReference.child("conversations");
        DatabaseReference notificationsReference = dbReference.child("notifications");
        conversationsReference.push().setValue(new Conversation(
                conversationCount + 1, senderID, receiverID, imageID));
        notificationsReference.push().setValue(new Notification(
                conversationCount + 1, senderID, receiverID, imageID));

        // Update Count of data
        currentImage = String.format("sCount%d", imageID);
        Log.i("sCount", currentImage);
        // TODO: get current number of stickers
        dbReference.child("users").child(senderID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentImageCount = Integer.parseInt(snapshot.child(currentImage).getValue().toString());
                currentImageCount++;
                dbReference.child("users").child(senderID).child(currentImage).setValue(currentImageCount.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.i("Conversation", "Added Message");
    }


}