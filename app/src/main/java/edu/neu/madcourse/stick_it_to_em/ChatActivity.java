package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    EditText editText;
    TextView alignText;
    ListView listview;
    //Button f_button;
    TextView emptyChat;
    FloatingActionButton f_button;
    String[] ListElements = new String[] {
    };


    //    Recycle View of the list
    RecyclerView recyclerViewChatList;
    List<ChatActivityData> chatList;

    FirebaseDatabase fireBasedatabase;
    DatabaseReference myRefFireBase;

    String recipientUserName;
    String recipientFullName;
    String recipientEmail;
    String currentUserName;
    String currentUserFullName;

    ChatActivityAdapter chatActivityAdapter;
    ImageView selectedImage;
    int images[] = {R.mipmap.sticker2, R.mipmap.sticker1, R.mipmap.sticker3, R.mipmap.sticker4, R.mipmap.sticker5};

    static class ViewHolder {
        ImageView Image;
        TextView MsgType;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentUserName = extras.getString("senderUserName");
            //currentUserFullName = extras.getString("senderUserFullName");

            recipientUserName = extras.getString("recipientUserName");
            //recipientFullName = extras.getString("recipientUserFullName");

            // Check for notifications
            new RTDBNotificationListener().checkForNotifications(currentUserName, getApplicationContext());
        }

        this.getConversationHistory();

        emptyChat = findViewById(R.id.emptyChat);

        f_button = findViewById(R.id.btnSendMessage);
        f_button.setOnClickListener(v -> {
            Intent intent  = new Intent(ChatActivity.this, MessageActivity.class);
            intent.putExtra("senderID", currentUserName);
            intent.putExtra("receiverID", recipientUserName);
            startActivity(intent);
            finish();
        });
    }

//    @Override
//    protected void onResume() {
//        //super.onStart();
//        super.onResume();
//        //chatList.clear();
//        //recyclerViewChatList.getAdapter().notifyDataSetChanged();
//        this.getConversationHistory();
//
//    }

    private void getConversationHistory() {
        // TODO: Connect to firebase to get the conversation history
        chatList.clear();
        // add the data to chatList
        fireBasedatabase = FirebaseDatabase.getInstance();
        myRefFireBase = fireBasedatabase.getReferenceFromUrl("https://stickittoem-83164-default-rtdb.firebaseio.com/");

        myRefFireBase.child("conversations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currentConversation : snapshot.getChildren()) {
                    if (currentConversation.getValue() != null) {
                        if ( (currentConversation.child("senderID").getValue().toString().equals(currentUserName)
                                && currentConversation.child("receiverID").getValue().toString().equals(recipientUserName))) {
                            System.out.println("YUR");
//                        String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                                .format(new Date(Integer.parseInt(currentConversation.child("timestamp").getValue().toString()) * 1000L));

                            ChatActivityData chatActivityData = new ChatActivityData("HI!!",
                                    "07072022", currentUserName, recipientUserName, Integer.parseInt(currentConversation.child("stickerID").getValue().toString()), 54
                            );
                            chatList.add(chatActivityData);

                        }
                        if ( (currentConversation.child("senderID").getValue().toString().equals(recipientUserName)
                                && currentConversation.child("receiverID").getValue().toString().equals(currentUserName))) {
                            System.out.println("YUR");

//                        String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                                .format(new Date(Integer.parseInt(currentConversation.child("timestamp").getValue().toString()) * 1000L));

                            ChatActivityData chatActivityData = new ChatActivityData("HI!!",
                                    "07072022", recipientUserName, currentUserName, Integer.parseInt(currentConversation.child("stickerID").getValue().toString()), 54
                            );
                            chatList.add(chatActivityData);

                        }
                    }
                }
                recyclerViewChatList = findViewById(R.id.recyclerViewChatList);
                recyclerViewChatList.setHasFixedSize(true);
                recyclerViewChatList.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                recyclerViewChatList.setAdapter(new ChatActivityAdapter(chatList, ChatActivity.this, currentUserName));
                recyclerViewChatList.scrollToPosition(chatList.size() - 1);

                if (chatList.size() == 0) {
                    emptyChat.setText("Send a sticker to start!");
                } else {
                    emptyChat.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // TODO: Get the inserted message from the sticker add view to update here to the list.
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
