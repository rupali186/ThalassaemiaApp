package com.example.rupali.thalassaemiaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PaymentResultListener{
    TextView toolbarTitle;
    private FirebaseAuth mAuth;
    ImageView profileImage;
    TextView profileName;
    TextView logInOrSignUp;
    FirebaseUser currentUser;
    GoogleSignInAccount account;
    boolean loggedIn=false;
    GoogleSignInClient mGoogleSignInClient;
    private boolean exit=false;
    TextView emailVerification;
    boolean isEmailVerified=false;
    boolean islogInWithGoogle=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.main_act_toolbar_text);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Thalassaemia");
        mAuth=FirebaseAuth.getInstance();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        profileName=(TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
        profileImage=(ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_image);
        logInOrSignUp=(TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_login_text);
        emailVerification=(TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_head_email_verification);
        if(isEmailVerified){
            emailVerification.setVisibility(View.GONE);
        }
        else if(loggedIn){
            emailVerification.setVisibility(View.VISIBLE);
        }
        emailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationEmail();
            }
        });
        logInOrSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loggedIn) {
                    logout();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,Constants.LOGIN_ACTIVITY_REQUEST_CODE);
                }
            }
        });
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Checkout.preload(getApplicationContext());

        subscribeToPushService();
        toolbarTitle.setText("Home");
        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_main, homeFragment,"HOME").commit();
        exit=false;

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(currentUser!=null) {
            updateUI(currentUser);
        }
        else if(account!=null) {
            updateGoogleUI(account);
        }
    }
    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("global");

        Log.d("AndroidBash", "Subscribed");
       // Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                Log.d("AndroidBash", newToken);
                //Toast.makeText(MainActivity.this, newToken, Toast.LENGTH_SHORT).show();

            }
        });

        // Log and toast

    }
    private void updateGoogleUI(GoogleSignInAccount account) {
        if(account!=null){
            String email=account.getEmail();
            String name=account.getDisplayName();
            Uri photoUrl=account.getPhotoUrl();
            profileName.setText("Welcome "+name+"!");
            if(photoUrl!=null){
                Picasso.get().load(photoUrl).into(profileImage);
            }
            loggedIn=true;
            islogInWithGoogle=true;
            logInOrSignUp.setText("Log Out!");
        }else {
            profileImage.setImageDrawable(null);
            profileImage.setBackgroundResource(R.drawable.profile_i);
            profileName.setText("Welcome !");
            logInOrSignUp.setText("Log In Or Sign Up!");
            islogInWithGoogle=false;
            loggedIn=false;
        }
        isEmailVerified=true;
        emailVerification.setVisibility(View.GONE);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();
            profileName.setText(email);
            // Check if user's email is verified
             isEmailVerified = currentUser.isEmailVerified();
            if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify email to accesss all the features...",Toast.LENGTH_SHORT).show();
                isEmailVerified=false;
                emailVerification.setVisibility(View.VISIBLE);
            }
            else{
                isEmailVerified=true;
                emailVerification.setVisibility(View.GONE);
            }

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getUid();
            islogInWithGoogle=false;
            loggedIn=true;
            logInOrSignUp.setText("Log Out!");
        }else {
            profileImage.setImageDrawable(null);
            profileImage.setBackgroundResource(R.drawable.profile_i);
            profileName.setText("Welcome !");
            logInOrSignUp.setText("Log In Or Sign Up!");
            islogInWithGoogle=false;
            isEmailVerified=true;
            emailVerification.setVisibility(View.GONE);
            loggedIn=false;
        }
    }
    private void logout() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to Log Out?");
        builder.setTitle("Confirm Log Out !");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(currentUser!=null) {
                    mAuth.signOut();
                    currentUser=mAuth.getCurrentUser();
                    updateUI(currentUser);
                }
                else if(account!=null){
                    mGoogleSignInClient.signOut();
                    account=GoogleSignIn.getLastSignedInAccount(MainActivity.this);
                    updateGoogleUI(account);
                }
                dialogInterface.dismiss();

            }
        });
        builder.show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            if (exit) {
                super.onBackPressed();
                return;
            }

            try {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("HOME");
                if (fragment != null) {
                    if (fragment.isVisible()) {
                        this.exit = true;
                        Toast.makeText(this, "Press Back again to Exit", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    fragment = ThalassaemiaFragment.class.newInstance();
                    getFragmentManager().popBackStack();
                    fragmentManager.beginTransaction().replace(R.id.container_main, fragment, "HOME").commit();
                }
            } catch (Exception e) {

            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notification_icon) {
            Intent intent = new Intent(this,NotificationsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Constants.LOGIN_ACTIVITY_REQUEST_CODE&&resultCode==Constants.LOGIN_ACTIVITY_RESULT_CODE){
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void sendVerificationEmail()
    {
        FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent


                            // after email is sent just logout the user and finish this activity
                            mAuth.signOut();
                            currentUser = mAuth.getCurrentUser();
                            updateUI(currentUser);
                            Toast.makeText(MainActivity.this,"Verification email sent. Verify your email" +
                                    " and sign in again to continue...",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            Toast.makeText(MainActivity.this,"Email Verification pending! ",Toast.LENGTH_SHORT).show();
                            //restart this activity
//                            overridePendingTransition(0, 0);
//                            finish();
//                            overridePendingTransition(0, 0);
//                            startActivity(getIntent());

                        }
                    }
                });
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            HomeFragment homeFragment= new HomeFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_main,homeFragment,"HOME").commit();
            toolbarTitle.setText("Home");
            exit=false;
        } else if (id == R.id.nav_thalassaemia) {
            ThalassaemiaFragment thalassaemiaFragment= new ThalassaemiaFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_main,thalassaemiaFragment).commit();
            toolbarTitle.setText("Thalassaemia");
            exit=false;
        } else if (id == R.id.nav_be_a_blood_donor) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                BeABloodDonorFragment beABloodDonorFragment = new BeABloodDonorFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, beABloodDonorFragment).commit();
                toolbarTitle.setText("Be A Donor");
                exit = false;
            }
        } else if (id == R.id.nav_regis_patients) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            } else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else{
                toolbarTitle.setText("Thalassaemics Reg");
                ThalassaemicsRegistrationFragment thalassaemicsRegistrationFragment = new ThalassaemicsRegistrationFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main,thalassaemicsRegistrationFragment).commit();
                exit=false;
            }

        } else if (id == R.id.nav_be_a_support) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            } else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                BeASupportFragment beASupportFragment = new BeASupportFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, beASupportFragment).commit();
                toolbarTitle.setText("Be A Support");
                exit = false;
            }

        } else if (id == R.id.nav_explore) {
            toolbarTitle.setText("Explore");
            ExploreFragment exploreFragment = new ExploreFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_main, exploreFragment).commit();
            exit=false;

        } else if (id == R.id.nav_about) {
            AboutFragment aboutFragment= new AboutFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_main,aboutFragment).commit();
            toolbarTitle.setText("About Us");
            exit=false;

        } else if (id == R.id.nav_contact_us) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:fa.thalassaemia@gmail.com"));
            //intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback or subject");
            startActivity(intent);

        }
        else if (id == R.id.nav_report_a_bug) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:rupalichawla186@gmail.com"));
            //intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback or subject");
            startActivity(intent);

        }
        else if (id == R.id.nav_share) {

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Share our app and spread awareness :" +
                    " https://play.google.com/store/apps/apps/details?456=thalassaemia.456");
            intent.setType("text/plain");
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Log.d("RazopayPayment", "Razorpay payment success");
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.d("RazopayPayment", "Razorpay Payment failed code: "+code+" response: "+response);
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show();

    }
}
