package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    Button cancel;
    Button logUserIn;

    EditText email;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cancel = findViewById(R.id.cancelLogin);
        logUserIn = findViewById(R.id.logUserIn);
        email = findViewById(R.id.loginEmailInput);
        username = findViewById(R.id.loginUsernameInput);


        cancel.setOnClickListener(v -> finish());
        logUserIn.setOnClickListener(v -> {
            // Only if the user has entered a valid username and email do we
            // open up the friends list activity
            if (validateEmail() && validateUsername()) {

                // check w/ database and make sure that the user exists

                // if the user exists, open the new activity
                openFriendsListActivity();
                // else: make a toast saying wrong information
            }

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
     * Opens a new activity w/ a recycler view of all
     * the friends of a particular user
     */
    private void openFriendsListActivity() {
        Intent intent = new Intent(this, FriendsList.class);
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("email", email.getText().toString());
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