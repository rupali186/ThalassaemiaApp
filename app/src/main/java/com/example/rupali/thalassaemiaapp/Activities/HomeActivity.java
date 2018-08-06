package com.example.rupali.thalassaemiaapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rupali.thalassaemiaapp.Adapters.ViewPagerAdapter;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout llPagerDots;
    private ImageView[] ivArrayDotsPager;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    TextView skip;
    Button loginWithGoogle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        skip=findViewById(R.id.skip);
        loginWithGoogle=findViewById(R.id.home_login_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               skipLogin();
            }
        });
        viewPager=findViewById(R.id.viewPager);
        llPagerDots = (LinearLayout)findViewById(R.id.pager_dots);
        adapter=new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        setupPagerIndidcatorDots();
        ivArrayDotsPager[0].setImageResource(R.drawable.selected_dot);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dot);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected_dot);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        loginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInWithGoogle();
            }
        });
        subscribeToPushService();
    }

    private void skipLogin() {
        SharedPreferences sharedPreferences=getSharedPreferences(Constants.LoginSharedPref.SHARED_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(Constants.LoginSharedPref.PREVIOUSLY_STARTED,true);
        editor.putBoolean(Constants.LoginSharedPref.LOGGED_IN,false);
        editor.putString(Constants.LoginSharedPref.LOGIN_EMAIL,"");
        editor.putString(Constants.LoginSharedPref.LOGIN_URENAME,"");
        editor.putString(Constants.LoginSharedPref.PROFILE_URL,"");
        editor.putBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,false);
        editor.commit();
        Intent intent=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void signInWithGoogle() {
        Intent signInIntent = Constants.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            Log.d(Constants.TAG, "signInResult google:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
       // Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if(Constants.mAuth==null){
            Constants.mAuth= FirebaseAuth.getInstance();
            Log.d(Constants.TAG,"mAuth new Instance");
        }
        Constants.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constants.TAG, "signInResult:google success");
                            Constants.user=Constants.mAuth.getCurrentUser();
                            SharedPreferences sharedPreferences=getSharedPreferences(Constants.LoginSharedPref.SHARED_PREF_NAME,MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean(Constants.LoginSharedPref.PREVIOUSLY_STARTED,true);
                            editor.putBoolean(Constants.LoginSharedPref.LOGGED_IN,true);
                            String email=Constants.user.getEmail();
                            String name=Constants.user.getDisplayName();
                            Uri photoUrl=Constants.user.getPhotoUrl();
                            editor.putString(Constants.LoginSharedPref.LOGIN_EMAIL,email);
                            editor.putString(Constants.LoginSharedPref.LOGIN_URENAME,name);
                            if(photoUrl!=null) {
                                Log.d(Constants.TAG," Photo Url Extract: "+photoUrl.toString());
                                editor.putString(Constants.LoginSharedPref.PROFILE_URL, photoUrl.toString());
                            }
                            editor.putBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,true);
                            editor.commit();
                            Intent intent=new Intent(HomeActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(Constants.TAG, "signInResult:google with firebase failure", task.getException());
                            Toast.makeText(HomeActivity.this, "An error occured while signing in. Check your network connection ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[6];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected_dot);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
//
        }
    }
    private void subscribeToPushService() {
        if(Constants.firebaseMessaging==null){
            Constants.firebaseMessaging= FirebaseMessaging.getInstance();
            Log.d(Constants.TAG,"firebaseMessaging new Instance");

        }
        Constants.firebaseMessaging.subscribeToTopic("global");
    }
}
