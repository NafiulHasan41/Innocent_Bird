package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//for drawer implemented something
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView volume;
    private Button startGame;

    private TextView score,userScore,userEmail,userName;

    private MediaPlayer mediaPlayer;

    boolean volumeStatus = false;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    // for drawer layout
    private DrawerLayout drawerLayout;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volume = findViewById(R.id.volume);
        startGame = findViewById(R.id.startGame);
        score = findViewById(R.id.highest_score);
        userScore = findViewById(R.id.highest_scoreUser);



        //For Drawer start

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //finding the The buttons
        NavigationView navigationViewUser = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        userEmail = headerView.findViewById(R.id.drawerUserMail);
        userName = headerView.findViewById(R.id.drawerNameUser);


        //end of drawer

        auth = FirebaseAuth.getInstance();

        //used the url because default url was for usa region

        database = FirebaseDatabase.getInstance(
                "https://innocent-bird-default-rtdb.asia-southeast1.firebasedatabase.app");

        reference = database.getReference();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getHighestScore();
            }
        },100);



    }

    //for drawer


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       int getUid=item.getItemId();

       if(getUid == R.id.nav_update)
       {
           mediaPlayer.reset();
           volume.setImageResource(R.drawable.valume_on);
           Intent i = new Intent(MainActivity.this,UserUpdate.class);
           startActivity(i);
       }
       else if(getUid == R.id.nav_gameRule)
       {
           mediaPlayer.reset();
           volume.setImageResource(R.drawable.valume_on);
           Intent i = new Intent(MainActivity.this,GameRule.class);
           startActivity(i);
       }
       else if(getUid == R.id.nav_passwordChange)
       {
           mediaPlayer.reset();
           volume.setImageResource(R.drawable.valume_on);
           Intent i = new Intent(MainActivity.this,ResetPassword.class);
           startActivity(i);
       }
       else if(getUid == R.id.nav_creator)
       {
           mediaPlayer.reset();
           volume.setImageResource(R.drawable.valume_on);
           Intent i = new Intent(MainActivity.this,AboutUs.class);
           startActivity(i);
       }
       else if(getUid == R.id.nav_logOutDrawer)
       {
           mediaPlayer.reset();
           volume.setImageResource(R.drawable.valume_on);
           auth.signOut();
           Intent i = new Intent(MainActivity.this, Login.class);
           startActivity(i);
           finish();
           Toast.makeText(MainActivity.this, "Log Out successfully", Toast.LENGTH_SHORT).show();
       }
       drawerLayout.closeDrawer(GravityCompat.START);
       return true;
    }


    //end drawer

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.musicgame);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!volumeStatus)
                {
                    mediaPlayer.setVolume(0,0);
                    volume.setImageResource(R.drawable.volume_off);
                    volumeStatus = true;
                }
                else
                {
                    mediaPlayer.setVolume(1,1);
                    volume.setImageResource(R.drawable.valume_on);
                    volumeStatus = false;

                }

            }
        });

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mediaPlayer.reset();
               volume.setImageResource(R.drawable.valume_on);

                Intent i = new Intent(MainActivity.this, GameActivity.class);
                startActivity(i);
                finish();

            }
        });




    }

    public void getHighestScore()
    {



                reference.child("Users").child("highestScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String scoreHighest = snapshot.getValue().toString();
                        score.setText(scoreHighest);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                reference = database.getReference();
                FirebaseUser user1 = auth.getCurrentUser();
                reference.child("Users").child(user1.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot!=null)
                        {
                            String mail = snapshot.child("userEmail").getValue().toString();
                            String name = snapshot.child("userName").getValue().toString();
                            String scoreHH = snapshot.child("userScore").getValue().toString();
                            userEmail.setText(mail);
                            userName.setText(name);
                            userScore.setText(scoreHH);
                        }
                        else
                        {

                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });










    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.reset();
        volume.setImageResource(R.drawable.valume_on);
    }

    @Override
    public void onBackPressed() {

        //for drawer
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are You sure you want to QUIT GAME?");
            builder.setCancelable(false);

            builder.setNegativeButton("QUIT GAME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });

            builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
        }

    }


}