package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView loginText;
    Button signup;
    Button message, profile;
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