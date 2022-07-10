package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReferenceFromUrl("https://stickittoem-83164-default-rtdb.firebaseio.com/");

    Button cancel;
    Button logUserIn;

    EditText email;
    EditText username;

    // local string variable to store the current logged-in user full name
    private String loggedIn_User_Full_Name;

    private String uniqueToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancel = findViewById(R.id.cancelLogin);
        logUserIn = findViewById(R.id.logUserIn);
        email = findViewById(R.id.loginEmailInput);
        username = findViewById(R.id.loginUsernameInput);
        loggedIn_User_Full_Name = "";
        uniqueToken = "";

        // Unique toke for a device

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        uniqueToken = token;

//                    }
//                });



        cancel.setOnClickListener(v -> finish());
        logUserIn.setOnClickListener(v -> {
            // Only if the user has entered a valid username and email do we
            // open up the friends list activity
            if (validateEmail() && validateUsername()) {

                // check w/ database and make sure that the user exists

                // if the user exists, open the new activity

                myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(username.getText().toString())) {
                            loggedIn_User_Full_Name = snapshot.child(username.getText().toString())
                                    .child("full_name")
                                    .getValue().toString();

                            //update the userToken

                            myRef.child("users").child(username.getText().toString())
                                    .child("email").setValue("xyz2@sdd.com");

                            openFriendsListActivity();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "User not registered",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //openFriendsListActivity();
                // else: make a toast saying wrong information
            }

        });


        // Push notification to the recipient user
        PushNotificationFCM.sendNotifications(LoginActivity.this,"dukwkZp2QiKosgBVzhjWw4:APA91bHrD_e6pT6pI_yFrlEghTPnKNh28npTuypV_M9G3i0X7KlTI-asmXMzIY8mQQWUvVOEnR9R9FoGZmNzg_hzuASSGOdkKe03vNjF7jCzRYwKL_fbVOoBGe-5jjFpMPQAYhzUmydM",
                "Welcome","hi beo!!!!");
    }

    /**
     * Method to validate the username
     * @return true if valid username
     */
    private boolean validateUsername() {
        if (username.getText().toString().equals("")) {
            username.setError("Cannot have empty username");
        }
        return true;
    }

    /**
     * Validate the email address passed in by the user
     * @return true if the user has a valid email, false otherwise
     */
    private boolean validateEmail() {

        String emailString = email.getText().toString();

        // if the email is empty
        if (emailString.equals("")) {
            email.setError("Email cannot be empty");
            return false;
        }
        if (!emailString.contains("@")) {
            email.setError("Invalid email address");
            return false;
        }
        return true;
    }

    /**
     * Opens a new activity w/ a recycler view of all
     * the friends of a particular user
     */
    private void openFriendsListActivity() {
        Intent intent = new Intent(this, FriendsList.class);
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("user_full_name", loggedIn_User_Full_Name);
        intent.putExtra("comingFromRegister", false);
        username.setText("");
        email.setText("");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}