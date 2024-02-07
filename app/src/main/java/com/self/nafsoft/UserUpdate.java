package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserUpdate extends AppCompatActivity {


    TextInputEditText userName;
    ProgressBar progressBar;
    Drawable drawable;
    TextView recover;
    FirebaseAuth auth;


    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        userName = findViewById(R.id.forgotEmailUpdate);
        recover=findViewById(R.id.toRecoveUpdate);
        progressBar =findViewById(R.id.progressUpdate);


        drawable = getResources().getDrawable(R.drawable.baseline_error_24,null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());



        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=userName.getText().toString().trim();
                if(name.isEmpty())
                {
                    userName.setError("Enter Your userName",drawable);
                    userName.requestFocus();
                }
                else
                {

                    progressBar.setVisibility(View.VISIBLE);
                    updateUserName(name);

                }
            }
        });





    }

    private void updateUserName(String userName) {

        auth = FirebaseAuth.getInstance();
       FirebaseUser user = auth.getCurrentUser();

        reference = FirebaseDatabase.getInstance("https://innocent-bird-default-rtdb.asia-southeast1" +
                ".firebasedatabase.app").getReference();
        if(user != null)
        {
            reference.child("Users").child(user.getUid()).child("userName").setValue(userName);
            Toast.makeText(this, "UserName Updated Successfully", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "OPPS! something went wrong", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressBar.setVisibility(View.INVISIBLE);
            }
        },1200);




    }


}