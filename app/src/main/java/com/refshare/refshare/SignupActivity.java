package com.refshare.refshare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private Button signup;
    private Button signin;
    private EditText inputmail;
    private EditText inputpassword;
    private EditText inputname;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("RefShare - SignUp");
        auth = FirebaseAuth.getInstance();

        signup = (Button)findViewById(R.id.signup_button);
        signin = (Button)findViewById(R.id.signin_button);
        inputname = (EditText)findViewById(R.id.edittext_username);
        inputmail = (EditText)findViewById(R.id.edittext_email);
        inputpassword = (EditText)findViewById(R.id.edittext_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputmail.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter a valid E-mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter a Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password to short", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                        else if(!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signinfunction();
            }
        });
    }
    protected void signinfunction() {
        Intent in = new Intent(this.getApplicationContext(), LoginActivity.class);
        startActivity(in);
    }
}
