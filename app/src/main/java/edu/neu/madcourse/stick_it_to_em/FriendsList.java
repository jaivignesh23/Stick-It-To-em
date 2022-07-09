package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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

    Toolbar myChildToolbar =
            (Toolbar) findViewById(R.id.action_profile);
    setSupportActionBar(myChildToolbar);

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            // User chose the "Profile" action
            Intent intent = new Intent(FriendsList.this, ProfileActivity.class);
            intent.putExtra("userID", "abc");
            startActivity(intent);
            return true;
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }

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
            intentUsername = extras.getString("username");
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

        fireBasedatabase = FirebaseDatabase.getInstance();
        myRefFireBase = fireBasedatabase.getReferenceFromUrl("https://stickittoem-83164-default-rtdb.firebaseio.com/");

        myRefFireBase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot userValue : snapshot.getChildren()) {

                    if(!userValue.getKey().equals(intentUsername)
                            && userValue.getValue() != null) {

                        friendsList.add(new FriendsListData(1,
                                userValue.child("full_name").getValue().toString(),
                                userValue.child("email").getValue().toString())
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
        Toast.makeText(this,friendData.getFriendId().toString()+ "\n"
                + friendData.getFriendName()+"\n"
                +friendData.getFriendEmailId(),Toast.LENGTH_LONG).show();
    }
}