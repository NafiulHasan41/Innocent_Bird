package com.self.nafsoft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText email,password;
    Button singIn;
    TextView singUp,forgotPass;
    ProgressBar progressBar;

    Drawable drawable;
    FirebaseAuth auth;

    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null && firebaseUser.isEmailVerified())
        {
            Intent i= new Intent(Login.this,MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);
        singIn =findViewById(R.id.signIn);
        singUp = findViewById(R.id.toSignUP);
        forgotPass = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progressSignIn);



        drawable = getResources().getDrawable(R.drawable.baseline_error_24,null);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());


        auth = FirebaseAuth.getInstance();


        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String inputEmail=email.getText().toString().trim();
                String inputPass = password.getText().toString().trim();
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



                if(!inputPass.isEmpty() && !inputEmail.isEmpty())
                {

                    progressBar.setVisibility(View.VISIBLE);
                    UserSignIn(inputEmail,inputPass);
                }


            }
        });

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });


        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Login.this,forgotPassword.class);
               startActivity(i);
            }
        });

    }

    public void UserSignIn(String inputEmail,String inputPassword)
    {
        auth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = auth.getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                        Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        progressBar.setVisibility(View.INVISIBLE);
                        email.setError("Email is not Verified ",drawable);
                        email.requestFocus();
                    }

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
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "Opps! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
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