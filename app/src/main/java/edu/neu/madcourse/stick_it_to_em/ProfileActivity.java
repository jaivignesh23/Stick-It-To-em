package edu.neu.madcourse.stick_it_to_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    String userID;
    ImageView imageView1, imageView2, imageView3, imageView4;
    TextView sCount1, sCount2, sCount3, sCount4;

    FirebaseDatabase database;
    DatabaseReference dbReference;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("userID");

            // Check for notifications
//            new RTDBNotificationListener().checkForNotifications(userID, this);
        }
        //userID = getIntent().getStringExtra("senderID");


        imageView1 = findViewById(R.id.profileImageView1);
        imageView1.setImageResource(R.mipmap.sticker2_round);
        imageView1.setScaleX(1.7f);
        imageView1.setScaleY(1.7f);

        imageView2 = findViewById(R.id.profileImageView2);
        imageView2.setImageResource(R.mipmap.sticker1_round);
        imageView2.setScaleX(1.7f);
        imageView2.setScaleY(1.7f);

        imageView3 = findViewById(R.id.profileImageView3);
        imageView3.setImageResource(R.mipmap.sticker3_round);
        imageView3.setScaleX(1.7f);
        imageView3.setScaleY(1.7f);

        imageView4 = findViewById(R.id.profileImageView4);
        imageView4.setImageResource(R.mipmap.sticker4_round);
        imageView4.setScaleX(1.7f);
        imageView4.setScaleY(1.7f);

        database = FirebaseDatabase.getInstance();
        dbReference = database.getReferenceFromUrl(
                "https://stickittoem-83164-default-rtdb.firebaseio.com/");

        sCount1 = findViewById(R.id.sCount1);
        sCount2 = findViewById(R.id.sCount2);
        sCount3 = findViewById(R.id.sCount3);
        sCount4 = findViewById(R.id.sCount4);

        dbReference.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s1Count = snapshot.child("sCount1").getValue().toString();
                String s2Count = snapshot.child("sCount2").getValue().toString();
                String s3Count = snapshot.child("sCount3").getValue().toString();
                String s4Count = snapshot.child("sCount4").getValue().toString();
                sCount1.setText("Sent " + s1Count + " time(s)");
                sCount2.setText("Sent " + s2Count + " time(s)");
                sCount3.setText("Sent " + s3Count + " time(s)");
                sCount4.setText("Sent " + s4Count + " time(s)");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}