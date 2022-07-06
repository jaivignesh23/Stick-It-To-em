package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView loginText;
    Button signup;
    Button message;
    String alreadyHaveAccount = "Already have an account?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the signup button
        signup = findViewById(R.id.sign_up);
        signup.setOnClickListener(v -> {
            openSignUpPage();
        });

        // TODO: Move this code later to friends list screen
        message = findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, MessageActivity.class);
                intent.putExtra("senderID", "abc");
                intent.putExtra("receiverID", "def");
                startActivity(intent);
            }
        });

        // set the login text
        loginText = findViewById(R.id.log_in);
        SpannableString spannableString = new SpannableString(alreadyHaveAccount);
        spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
        loginText.setText(spannableString);

        // setting on click listeners
        loginText.setOnClickListener(v -> {
            openLoginPage();
        });
    }

    private void openLoginPage() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSignUpPage() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}