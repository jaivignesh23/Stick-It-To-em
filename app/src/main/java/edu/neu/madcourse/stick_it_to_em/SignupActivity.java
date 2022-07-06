package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignupActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReferenceFromUrl("https://stickittoem-83164-default-rtdb.firebaseio.com/");

    Button cancelSignUp;
    Button registerUser;

    EditText username;
    EditText email;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        cancelSignUp = findViewById(R.id.signupCancel);
        cancelSignUp.setOnClickListener(v -> finish());

        username = findViewById(R.id.signupUsernameInput);
        email = findViewById(R.id.signupEmailInput);
        name = findViewById(R.id.signupFullNameInput);

        registerUser = findViewById(R.id.signupRegisterButton);
        registerUser.setOnClickListener(v -> {
            if (validateEmail() && validateUsername() && validateName()) {

                myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(username.getText().toString())) {
                            Toast.makeText(SignupActivity.this, "Username already exists, please choose another one",Toast.LENGTH_LONG).show();
                        }
                        else{
                            myRef.child("users").child(username.getText().toString()).child("userEmail").setValue(email.getText().toString());
                            myRef.child("users").child(username.getText().toString()).child("userFullName").setValue(name.getText().toString());
                            openFriendsListActivity();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            //Toast.makeText(SignupActivity.this, "Registering this new user", Toast.LENGTH_SHORT).show();
        });
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
     * If the name is empty, then the user cannot register
     * @return true if the user enters a non-empty username, false otherwise
     */
    private boolean validateName() {
        return !name.getText().toString().equals("");
    }

    /**
     * Opens a new activity w/ a recycler view of all
     * the friends of a particular user
     */
    private void openFriendsListActivity() {
        Intent intent = new Intent(this, FriendsList.class);
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("comingFromRegister", true);
        username.setText("");
        email.setText("");
        name.setText("");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}