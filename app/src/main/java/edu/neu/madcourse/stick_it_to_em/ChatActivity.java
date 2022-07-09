package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    EditText editText;
    TextView alignText;
    ListView listview;
    //Button f_button;
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

    ImageView selectedImage;
    int images[] = {R.mipmap.sticker1 , R.mipmap.sticker2, R.mipmap.sticker3, R.mipmap.sticker4, R.mipmap.sticker5};

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
        }

        this.getConversationHistory();

        f_button = findViewById(R.id.btnSendMessage);
        f_button.setOnClickListener(v -> {
            Intent intent  = new Intent(ChatActivity.this, MessageActivity.class);
            intent.putExtra("senderID", currentUserName);
            intent.putExtra("receiverID", recipientUserName);
            startActivity(intent);
        });

//        chatList.add(new ChatActivityData("hello brad", "07072022","Jai", "Jv",233,54));
//        chatList.add(new ChatActivityData("hello teddy", "07072022","Jai", "Jv",233,54));
//        chatList.add(new ChatActivityData("hello harry", "07082022","Jai", "Jv",233,54));
//        chatList.add(new ChatActivityData("welcome brad", "07072022","Jai", "Jv",233,54));
//        chatList.add(new ChatActivityData("hola brad", "07072022","Jai", "Jv",233,54
//        ));


        // Set the adapter to the list created



//        editText = findViewById(R.id.txtMessageContent);
//        editText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//
//        alignText = findViewById(R.id.align);
//        //alignText.setText("abcd");
//        alignText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//
//        listview = (ListView) findViewById(R.id.messageListView);
//        f_button = findViewById(R.id.btnSendMessage);
//
//        final List< String > ListElementsArrayList = new ArrayList< String >(Arrays.asList(ListElements));
//        //final List< ImageView > ListElementsArrayList = new ArrayList< ImageView>();
//
//        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
//                (ChatActivity.this, android.R.layout.simple_list_item_1,
//                        ListElementsArrayList);
//
//        listview.setAdapter(adapter);
//
//        f_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //selectedImage.setImageResource(R.mipmap.sticker1);
//                ListElementsArrayList.add(editText.getText().toString());
//                adapter.notifyDataSetChanged();
//            }
//        });

    }

    @Override
    protected void onResume() {
        //super.onStart();
        super.onResume();
        //chatList.clear();
        this.getConversationHistory();

    }

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

        chatList.clear();

        finish();

    }
}
