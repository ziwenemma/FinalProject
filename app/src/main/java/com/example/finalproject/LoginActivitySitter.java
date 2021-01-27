package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivitySitter extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button loginButton;
    Button signupButton;
    FirebaseAuth fAuth;
    TextView forgotTextLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sitter);

        mEmail = findViewById(R.id.email_sitter);
        mPassword = findViewById(R.id.password_sitter);
        loginButton = (Button) findViewById(R.id.loginButton_sitter);
        signupButton = (Button) findViewById(R.id.signupButton_sitter);
        fAuth = FirebaseAuth.getInstance();
        forgotTextLink=findViewById(R.id.forgotPassword_sitter);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("password is required.");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("password mush be more than 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivitySitter.this, "Login in successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainPageSitter.class));
                        } else {
                            Toast.makeText(LoginActivitySitter.this, "Error login!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivitySitter.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(LoginActivitySitter.this,"Reset Link Sent To Your Email", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivitySitter.this,"Error ! Reset Link is Not Sent"+e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                passwordResetDialog.create().show();
            }
        });
    }



}
