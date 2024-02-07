package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class forgotPassword extends AppCompatActivity {

    TextInputEditText email;
    ProgressBar progressBar;
    Drawable drawable;
    TextView recover;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgotEmail);
        recover=findViewById(R.id.toRecover);
        progressBar =findViewById(R.id.progressRecover);


        drawable = getResources().getDrawable(R.drawable.baseline_error_24,null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        auth = FirebaseAuth.getInstance();

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputEmail=email.getText().toString().trim();
                if(inputEmail.isEmpty())
                {

                    email.setError("Enter Email Address",drawable);
                    email.requestFocus();
                }
                else if(inputEmail.length()<10)
                {
                    email.setError("Enter Valid Email Address",drawable);
                    email.requestFocus();
                }
               else
                {
        
                    progressBar.setVisibility(View.VISIBLE);
                    passwordReset(inputEmail);

                }
            }
        });




    }

    private void passwordReset(String inputEmail) {


        Query query = FirebaseDatabase.getInstance("https://innocent-bird-default-rtdb.asia-southeast1" +
                ".firebasedatabase.app").getReference("Users").orderByChild("userEmail").equalTo(inputEmail);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    auth.sendPasswordResetEmail(inputEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(forgotPassword.this, "Please check your Email!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                            else
                            {
                                Toast.makeText(forgotPassword.this, "Opps! something went wrong", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(!(e instanceof FirebaseAuthUserCollisionException))
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                email.setError("Email is not Registered ",drawable);
                                email.requestFocus();
                            }
                        }
                    });
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Email is not Registered ",drawable);
                    Toast.makeText(forgotPassword.this, "Opps! something went wrong", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}