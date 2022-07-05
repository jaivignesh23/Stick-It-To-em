package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class FriendsList extends AppCompatActivity {

    TextView currentUsername;
    TextView currentEmail;
    TextView name;

    TextView friendListHeading;

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

//            this.setTitle("Hello " + intentUsername);
        }
    }


}