package edu.neu.madcourse.stick_it_to_em;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        userID = getIntent().getStringExtra("senderID");


        imageView1 = findViewById(R.id.profileImageView1);
        imageView1.setImageResource(R.mipmap.sticker1_round);
        imageView1.setScaleX(1.7f);
        imageView1.setScaleY(1.7f);

        imageView2 = findViewById(R.id.profileImageView2);
        imageView2.setImageResource(R.mipmap.sticker2_round);
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

        sCount1.setText("100");
        sCount2.setText("100");
        sCount3.setText("100");
        sCount4.setText("100");

        // TODO: Get Count from RTDB
        // sCount1.setText((CharSequence) dbReference.child("user").child(userID).child("sCount1"));
        // sCount2.setText((CharSequence) dbReference.child("user").child(userID).child("sCount2"));
        // sCount3.setText((CharSequence) dbReference.child("user").child(userID).child("sCount3"));
        // sCount4.setText((CharSequence) dbReference.child("user").child(userID).child("sCount4"));
    }
}