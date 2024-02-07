package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    private TextView score,highScore,home,playAgain;


    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    String scoreH;
   private int currentScore;
    private int temp;
   private int highestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        score = findViewById(R.id.scoreResult);
        highScore = findViewById(R.id.scoreH);
        home = findViewById(R.id.homeGame);
        playAgain = findViewById(R.id.playAgain);

        auth = FirebaseAuth.getInstance();

        //used the url because default url was for usa region

        database = FirebaseDatabase.getInstance(
                "https://innocent-bird-default-rtdb.asia-southeast1.firebasedatabase.app");

        reference = database.getReference();


        //checking which on is highest value
        getHighestScore1();




        currentScore = getIntent().getIntExtra("scoreCurrent",0);
        score.setText(""+currentScore);

        setUserHigestScore();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getHighestScore2();
            }
        },100);



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, GameActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void setUserHigestScore() {

        reference = database.getReference();

        FirebaseUser user = auth.getCurrentUser();

        reference.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                 temp = snapshot.child("userScore").getValue(Integer.class);


               if(temp==0)
               {
                   reference.child("Users").child(user.getUid()).child("userScore").setValue(currentScore);
               }

               else if(currentScore > temp)
                {
                    reference.child("Users").child(user.getUid()).child("userScore").setValue(currentScore);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    public void getHighestScore1()
    {
        reference = database.getReference();

        reference.child("Users").child("highestScore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                highestScore = snapshot.getValue(Integer.class);


                if(currentScore > highestScore)
                {
                    reference = database.getReference();
                    reference.child("Users").child("highestScore").setValue(currentScore);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getHighestScore2()
    {
        reference = database.getReference();


        reference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String scoreHighest = snapshot.child("highestScore").getValue().toString();
                highScore.setText(scoreHighest);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {


        Intent i = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}