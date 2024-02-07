package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputEditText email,password,userName;
    Button signUp;
    ProgressBar progressBar;
    Drawable drawable;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email =findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        userName = findViewById(R.id.signUpUserName);
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.progressSignUp);

        drawable = getResources().getDrawable(R.drawable.baseline_error_24,null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        auth = FirebaseAuth.getInstance();

        //used the url because default url was for usa region

        database = FirebaseDatabase.getInstance(
                "https://innocent-bird-default-rtdb.asia-southeast1.firebasedatabase.app");

        reference = database.getReference();





        //button for signUp
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validDataRegistration();
            }
        });
    }

    public void validDataRegistration()
    {
        String inputEmail=email.getText().toString().trim();
        String inputPass = password.getText().toString().trim();
        String name = userName.getText().toString();

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

        if(inputPass.isEmpty())
        {
            password.setError("Enter Password",drawable);
            password.requestFocus();
        }
        else if(inputPass.length()<7)
        {
            password.setError("Password is too Short!",drawable);
            password.requestFocus();
        }

        if(name.isEmpty())
        {
            userName.setError("Enter Your userName",drawable);
            userName.requestFocus();
        }

        if(!inputPass.isEmpty() && !inputEmail.isEmpty() && !name.isEmpty())
        {

            progressBar.setVisibility(View.VISIBLE);
            register(inputEmail,inputPass,name);
        }


    }

    private void register(String inputEmail, String inputPass,String name) {

        auth.createUserWithEmailAndPassword(inputEmail,inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {


                    sendVerificationEmail(inputEmail,name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthUserCollisionException)
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    email.setError("Email is Already Registered ",drawable);
                    email.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignUp.this, "Opps! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationEmail(String inputEmail,String name) {
        if(auth.getCurrentUser()!=null)
        {
            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        reference.child("Users").child(auth.getUid()).child("userName").setValue(name);
                        reference.child("Users").child(auth.getUid()).child("userScore").setValue(0);
                        reference.child("Users").child(auth.getUid()).child("userEmail").setValue(inputEmail);

                        Toast.makeText(getApplicationContext(), "A verification Email has been sent to your Your email" +
                                "address! ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(SignUp.this, Login.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Opps! Failed to send verification email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}