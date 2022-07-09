package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

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
}