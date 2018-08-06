package com.example.rupali.thalassaemiaapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    EditText email_edittext;
    EditText password_edittext;
    Button signIn;
    Button signUp;
    String email_text;
    String password_text;
    TextView resetPassword;
    SignInButton googleLogin;
    TextView toolbarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.login_act_toolbar_text);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("LogIn Or SignUp");
        email_edittext=findViewById(R.id.login_email);
        password_edittext=findViewById(R.id.login_password);
        resetPassword=findViewById(R.id.reset_password_text);
        googleLogin=findViewById(R.id.login_button_google);
        signIn=findViewById(R.id.signin_button);
        signUp=findViewById(R.id.signup_button);
        googleLogin.setSize(SignInButton.SIZE_STANDARD);

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        if(Constants.gso==null) {
            Log.d(Constants.TAG,"gso new Instance");
            Constants.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail().requestProfile()
                    .build();
        }
        // Build a GoogleSignInClient with the options specified by gso.
        if(Constants.mGoogleSignInClient==null) {
            Log.d(Constants.TAG,"mGoogleSignInClient new Instance");
            Constants.mGoogleSignInClient = GoogleSignIn.getClient(this, Constants.gso);
        }
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
    private void sendVerificationEmail(FirebaseUser user)
    {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            // after email is sent just logout the user and finish this activity
                            if(Constants.mAuth==null){
                                Log.d(Constants.TAG,"mAuth new Instance");
                                Constants.mAuth=FirebaseAuth.getInstance();
                            }
                            Constants.mAuth.signOut();
                            Toast.makeText(LoginActivity.this,"Verification email sent. Verify" +
                                    " your email and sign in again to continue...",Toast.LENGTH_LONG).show();
                            password_edittext.setText("");
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Toast.makeText(LoginActivity.this,"Email Verification pending! ",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                            finish();
                        }
                    }
                });
    }

    private void signInWithGoogle() {
        Intent signInIntent = Constants.mGoogleSignInClient.getSignInIntent();
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
            Constants.account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(Constants.account);
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(Constants.TAG, "google signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
       // Log.d(Constants.TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if(Constants.mAuth==null){
            Log.d(Constants.TAG,"mAuth new Instance");
            Constants.mAuth=FirebaseAuth.getInstance();
        }
        Constants.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constants.TAG, "signInResult:google success");
                            Toast.makeText(LoginActivity.this, "you are signed in successfully. ",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(Constants.TAG, "signInResult:google with firebase failure", task.getException());
                            Toast.makeText(LoginActivity.this, "An error occured while signing in. Check your network connection ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void resetPassword() {
        String emailAddress = email_edittext.getText().toString();
        if(!emailAddress.isEmpty()) {
            if(Constants.mAuth==null){
                Log.d(Constants.TAG,"mAuth new Instance");
                Constants.mAuth=FirebaseAuth.getInstance();
            }
            Constants.mAuth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "A link has been sent to your email. " +
                                        "Please use it to reset your password.", Toast.LENGTH_SHORT).show();
                                Log.d(Constants.TAG, "password reset Email sent.");
                            }
                        }
                    });
        }
    }

    private void signUpToYourAccount() {
        email_text=email_edittext.getText().toString();
        password_text=password_edittext.getText().toString();
       // phone_no=phone_no_edittext.getText().toString();
        if(!email_text.isEmpty()&&!password_text.isEmpty()){
            if(Constants.mAuth==null){
                Constants.mAuth=FirebaseAuth.getInstance();
            }
            Constants.mAuth.createUserWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(Constants.TAG, "createUserWithEmail:success");
                                Constants.user=Constants.mAuth.getCurrentUser();
                                if (Constants.user != null) {
                                    // User is signed in
                                    // NOTE: this Activity should get onpen only when the user is not signed in, otherwise
                                    // the user will receive another verification email.
                                    Toast.makeText(LoginActivity.this,"New account created, verify your email to" +
                                            " continue...",Toast.LENGTH_SHORT).show();
                                    sendVerificationEmail(Constants.user);
                                } else {
                                    Toast.makeText(LoginActivity.this,"A problem occured while signing in. Sign in again" +
                                            " to continue." ,Toast.LENGTH_SHORT).show();
                                    // User is signed out
                                }
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(Constants.TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "An error occured while creating account. Use reset password in case you already have an account  ",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        else if(email_text.isEmpty()&&password_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email and password are required",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email_text.isEmpty()){
            Toast.makeText(LoginActivity.this,"Email is required",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(LoginActivity.this,"password is required",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void signInToYourAccount() {
        email_text = email_edittext.getText().toString();
        password_text = password_edittext.getText().toString();
        if (!email_text.isEmpty() && !password_text.isEmpty()) {
            if (Constants.mAuth == null) {
                Log.d(Constants.TAG,"mAuth new Instance");
                Constants.mAuth = FirebaseAuth.getInstance();
            }
            Constants.mAuth.signInWithEmailAndPassword(email_text, password_text)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(Constants.TAG, "signInWithEmail:success");
                                Toast.makeText(LoginActivity.this, "Logged in successfully .",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(Constants.LOGIN_ACTIVITY_RESULT_CODE);
                                finish();
//                            updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d(Constants.TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed. use reset Password in case you forgot your password.",
                                        Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                            }

                            // ...
                        }
                    });
        } else if (email_text.isEmpty() && password_text.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            return;
        } else if (email_text.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(LoginActivity.this, "password is required", Toast.LENGTH_SHORT).show();
            return;
        }

    }
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            profileName.setText(email);
            // Check if user's email is verified
            isEmailVerified = currentUser.isEmailVerified();
            Uri photoUrl=currentUser.getPhotoUrl();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            Log.d(Constants.TAG," Photo Uri send to Picasso: "+photoUrl.toString());
            if(photoUrl!=null){
                Picasso.get().load(photoUrl).into(profileImage,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(Constants.TAG," Photo Uri send to Picasso: success");

                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.d(Constants.TAG," Photo Uri send to Picasso: failure exp: "+ex.toString());

                    }
                });
                editor.putString(Constants.LoginSharedPref.PROFILE_URL,photoUrl.toString());
            }
            editor.putBoolean(Constants.LoginSharedPref.LOGGED_IN,true);
            editor.putBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,isEmailVerified);
            editor.putString(Constants.LoginSharedPref.LOGIN_EMAIL,email);
            editor.putString(Constants.LoginSharedPref.LOGIN_URENAME,name);
            editor.commit();
            if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify email to accesss all the features...",Toast.LENGTH_SHORT).show();
                isEmailVerified=false;
                emailVerification.setVisibility(View.VISIBLE);
            }
            else{
                isEmailVerified=true;
                emailVerification.setVisibility(View.GONE);
            }
            loggedIn=true;
            logInOrSignUp.setText("Log Out!");
        }else {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(Constants.LoginSharedPref.LOGGED_IN,false);
            editor.putBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,false);
            editor.putString(Constants.LoginSharedPref.LOGIN_EMAIL,"");
            editor.putString(Constants.LoginSharedPref.LOGIN_URENAME,"");
            editor.putString(Constants.LoginSharedPref.PROFILE_URL,"");
            editor.commit();
            profileImage.setImageDrawable(null);
            profileImage.setBackgroundResource(R.drawable.profile_i);
            profileName.setText("Welcome !");
            logInOrSignUp.setText("Log In Or Sign Up!");
            isEmailVerified=false;
            emailVerification.setVisibility(View.GONE);
            Constants.account=null;
            Constants.user=null;
            loggedIn=false;
        }
    }
}
