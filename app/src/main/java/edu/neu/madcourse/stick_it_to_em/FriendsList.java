package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

//        currentUsername = findViewById(R.id.friendListusername);
//        currentEmail = findViewById(R.id.friendListEmail);
//        name = findViewById(R.id.fromRegisterPage);
//
        friendListHeading = findViewById(R.id.friendListHeading);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String intentUsername = extras.getString("username");
            String intentEmail = extras.getString("email");
            boolean isFromRegister = extras.getBoolean("comingFromRegister");

//            if (isFromRegister) {
//                name.setText("COMING FROM THE REGISTER PAGE!!!");
//            }
//
//            currentUsername.setText(intentUsername);
//            currentEmail.setText(intentEmail);

            friendListHeading.setText("Hello " + intentUsername);

//            Create a new instance and preserve the state of the list.
            friendsList = new ArrayList<>();

//            Calling db(firebase) to populate the list.
            this.getFriendsList();

        }
    }

    private void getFriendsList() {



        friendsList.add(new FriendsListData(1,"Jai", "jai@gmail.com"));
        friendsList.add(new FriendsListData(2,"Ayush", "ayush@gmail.com"));
        friendsList.add(new FriendsListData(3,"Shreyas", "shreyas@gmail.com"));
        friendsList.add(new FriendsListData(4,"Shrikanth", "ahri@gmail.com"));

        friendsList.add(new FriendsListData(5,"Jai", "jai@gmail.com"));
        friendsList.add(new FriendsListData(6,"Ayush", "ayush@gmail.com"));
        friendsList.add(new FriendsListData(7,"Shreyas", "shreyas@gmail.com"));
        friendsList.add(new FriendsListData(8,"Shrikanth", "ahri@gmail.com"));


        this.setCurrentAdapter();
    }

    private void setCurrentAdapter() {
        recyclerViewFriendsList = findViewById(R.id.recyclerViewFriendsList);
        recyclerViewFriendsList.setHasFixedSize(true);
        recyclerViewFriendsList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFriendsList.setAdapter(new FriendsListAdapter(friendsList, this, this));
    }


    @Override
    public void onSelectFriendToSendMessage(FriendsListData friendData) {
        Toast.makeText(this,friendData.getFriendId().toString()+ "\n"
                + friendData.getFriendName()+"\n"
                +friendData.getFriendEmailId(),Toast.LENGTH_LONG).show();
    }
}