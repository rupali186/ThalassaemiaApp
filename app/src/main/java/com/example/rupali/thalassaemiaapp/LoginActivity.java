package com.example.rupali.thalassaemiaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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
    TextView resetPassword;
    SignInButton googleLogin;
    Button facebookLogin;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email_edittext=findViewById(R.id.login_email);
        password_edittext=findViewById(R.id.login_password);
        resetPassword=findViewById(R.id.reset_password_text);
        googleLogin=findViewById(R.id.login_button_google);
        facebookLogin=findViewById(R.id.login_button_facebook);
        signIn=findViewById(R.id.signin_button);
        signUp=findViewById(R.id.signup_button);
        googleLogin.setSize(SignInButton.SIZE_STANDARD);
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInToYourAccount();
            }
        });
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpToYourAccount();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == Constants.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w("Authentication", "signInResult:google success");
            Intent intent=new Intent();
            setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
            finish();
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Authentication", "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
    private void resetPassword() {
        String emailAddress = email_edittext.getText().toString();

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,"Email sent",Toast.LENGTH_SHORT).show();
                            Log.d("Authentication", "Email sent.");
                        }
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
                                Intent intent=new Intent();
                                setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                                finish();
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
