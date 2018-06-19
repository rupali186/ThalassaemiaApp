package com.example.rupali.thalassaemiaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email_edittext;
    EditText password_edittext;
    Button signIn;
    Button signUp;
    String email_text;
    String password_text;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email_edittext=findViewById(R.id.login_email);
        password_edittext=findViewById(R.id.login_password);
        signIn=findViewById(R.id.signin_button);
        signUp=findViewById(R.id.signup_button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInToYourAccount();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpToYourAccount();
            }
        });




    }

    private void signUpToYourAccount() {
        email_text=email_edittext.getText().toString();
        password_text=password_edittext.getText().toString();
        if(!email_text.isEmpty()&&!password_text.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Authentication", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else if(email_text.isEmpty()&&password_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email and password are required",Toast.LENGTH_SHORT).show();
        }
        else if(email_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"password is required",Toast.LENGTH_SHORT).show();
        }
    }

    private void signInToYourAccount() {
        email_text=email_edittext.getText().toString();
        password_text=password_edittext.getText().toString();
        if(!email_text.isEmpty()&&!password_text.isEmpty()){
            mAuth.signInWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Authentication", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent=new Intent();
                                setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                                finish();
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Authentication", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else if(email_text.isEmpty()&&password_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email and password are required",Toast.LENGTH_SHORT).show();
        }
        else if(email_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"password is required",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


//    private void updateUI(FirebaseUser currentUser) {
//    }
}
