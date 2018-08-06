package com.example.rupali.thalassaemiaapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
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

import com.example.rupali.thalassaemiaapp.Fragments.AnimationFragment;
import com.example.rupali.thalassaemiaapp.Fragments.MoreInfoFragment;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.Fragments.AboutFragment;
import com.example.rupali.thalassaemiaapp.Fragments.BeABloodDonorFragment;
import com.example.rupali.thalassaemiaapp.Fragments.BeASupportFragment;
import com.example.rupali.thalassaemiaapp.Fragments.ExploreFragment;
import com.example.rupali.thalassaemiaapp.Fragments.HomeFragment;
import com.example.rupali.thalassaemiaapp.R;
import com.example.rupali.thalassaemiaapp.Fragments.RegisterationFragment;
import com.example.rupali.thalassaemiaapp.Fragments.ThalassaemiaFragment;
import com.example.rupali.thalassaemiaapp.Fragments.ThalassaemicsRegistrationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PaymentResultListener{
    TextView toolbarTitle;
    ImageView profileImage;
    TextView profileName;
    TextView logInOrSignUp;
    boolean loggedIn=false;
    private boolean exit=false;
    boolean isEmailVerified=false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.main_act_toolbar_text);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Thalassaemia");
        //mAuth=FirebaseAuth.getInstance();
        // Obtain the FirebaseAnalytics instance.
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
        logInOrSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loggedIn) {
                    logout();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent, Constants.LOGIN_ACTIVITY_REQUEST_CODE);
                }
            }
        });
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        Checkout.preload(getApplicationContext());
        toolbarTitle.setText("Home");
        HomeFragment homeFragment = new HomeFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_main, homeFragment,"HOME").commit();
        exit=true;

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        sharedPreferences=getSharedPreferences(Constants.LoginSharedPref.SHARED_PREF_NAME,MODE_PRIVATE);
        loggedIn =sharedPreferences.getBoolean(Constants.LoginSharedPref.LOGGED_IN,false);
        if(loggedIn){
            updateUIFromDb();
        } else{
            updateUINULL(null);
        }
    }
    private void updateUINULL(FirebaseUser user){
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
//        emailVerification.setVisibility(View.GONE);
        Constants.account=null;
        Constants.user=null;
        loggedIn=false;

    }

    private void updateUIFromDb() {
        String email=sharedPreferences.getString(Constants.LoginSharedPref.LOGIN_EMAIL,"");
        String name=sharedPreferences.getString(Constants.LoginSharedPref.LOGIN_URENAME,"");
        String profileUriString=sharedPreferences.getString(Constants.LoginSharedPref.PROFILE_URL,"");
        isEmailVerified=sharedPreferences.getBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,false);

        if(!email.isEmpty()){
            profileName.setText(email);
        }else{
            profileName.setText("Welcome "+name+"!");
        }
        if(!profileUriString.isEmpty()){
            Uri photoUrl=Uri.parse(profileUriString);
            Log.d(Constants.TAG," Photo Uri send to Picasso: "+photoUrl.toString());
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
        }else {
            Log.d(Constants.TAG," Photo Uri empty so not set");
            profileImage.setImageDrawable(null);
            profileImage.setBackgroundResource(R.drawable.profile_i);
        }
        // Check if user's email is verified
        if(!isEmailVerified){
            Toast.makeText(MainActivity.this,"Verify email to accesss all the features...",Toast.LENGTH_SHORT).show();
            isEmailVerified=false;
//            emailVerification.setVisibility(View.VISIBLE);
        }
        else{
            isEmailVerified=true;
//            emailVerification.setVisibility(View.GONE);
        }
        loggedIn=true;
        logInOrSignUp.setText("Log Out!");

    }


    private void logout() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to Logout?");
        builder.setTitle("Confirm Logout !");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Constants.user!=null) {
                    if(Constants.mAuth==null){
                        Constants.mAuth=FirebaseAuth.getInstance();
                        Log.d(Constants.TAG,"mAuth new Instance");
                    }
                    Constants.mAuth.signOut();
                    Constants.user=Constants.mAuth.getCurrentUser();
                }
//                else if(account!=null){
//                    mGoogleSignInClient.signOut();
//                    account=GoogleSignIn.getLastSignedInAccount(MainActivity.this);
//                    updateGoogleUI(account);
//                }
                if(Constants.user==null){
                    updateUINULL(Constants.user);
                    Constants.account=null;
                    Toast.makeText(MainActivity.this,"You are Logged out successfully.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"A problem occured" +
                            " while signing out. Please Check your network connection.",Toast.LENGTH_SHORT).show();
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
                    fragment = HomeFragment.class.newInstance();
                    getFragmentManager().popBackStack();
                    toolbarTitle.setText("Home");
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
        if (requestCode == Constants.LOGIN_ACTIVITY_REQUEST_CODE && resultCode == Constants.LOGIN_ACTIVITY_RESULT_CODE) {
            isEmailVerified=sharedPreferences.getBoolean(Constants.LoginSharedPref.IS_EMAIL_VERIFIED,false);
            if (isEmailVerified) {
                updateUIFromDb();
            }else{
                updateUINULL(null);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
//    private void sendVerificationEmail(FirebaseUser user)
//    {
//        if(user==null){
//            Log.d(Constants.TAG,"Email verification user is null");
//            user=Constants.mAuth.getCurrentUser();
//
//        }
//        if(user==null){
//            return;
//        }
//        user.sendEmailVerification()
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // email sent
//                            // after email is sent just logout the user and finish this activity
//                            Constants.mAuth.signOut();
//                            Constants.user = Constants.mAuth.getCurrentUser();
//                            updateUI(Constants.user);
//                            Log.d(Constants.TAG,"Email verification sent");
//                            Toast.makeText(MainActivity.this,"Verification email sent. Verify your email" +
//                                    " and sign in again to continue...",Toast.LENGTH_SHORT).show();
//                        }
//                        else
//                        {
//                            // email not sent, so display message and restart the activity or do whatever you wish to do
//                            Toast.makeText(MainActivity.this,"Email Verification pending! ",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
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
                toolbarTitle.setText("Be A Blood Donor");
                exit = false;
            }
        }else if (id == R.id.popup_bone_marrow) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.SPINNER_POS,2);
                RegisterationFragment registerationFragment = new RegisterationFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, registerationFragment);
                registerationFragment.setArguments(bundle);
                transaction.commit();
                toolbarTitle.setText("Bone Marrow Matching");
                exit = false;
            }
        }else if (id == R.id.popup_carrier) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.SPINNER_POS,1);
                RegisterationFragment registerationFragment = new RegisterationFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, registerationFragment);
                registerationFragment.setArguments(bundle);
                transaction.commit();
                toolbarTitle.setText("Thalassaemia Carrier Test");
                exit = false;
            }
        } else if (id == R.id.popup_stem_cells_donation) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.SPINNER_POS,3);
                RegisterationFragment registerationFragment = new RegisterationFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, registerationFragment);
                registerationFragment.setArguments(bundle);
                transaction.commit();
                toolbarTitle.setText("Stem Cells Donation");
                exit = false;
            }
        }else if (id == R.id.nav_regis_patients) {
            if(!loggedIn){
                Toast.makeText(MainActivity.this,"You need to be logged in to continue! ",Toast.LENGTH_SHORT).show();
            } else if(!isEmailVerified){
                Toast.makeText(MainActivity.this,"Verify your email to continue! ",Toast.LENGTH_SHORT).show();

            }else {
                toolbarTitle.setText("Thalassaemic's Registeration");
                ThalassaemicsRegistrationFragment thalassaemicsRegistrationFragment = new ThalassaemicsRegistrationFragment();
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_main, thalassaemicsRegistrationFragment).commit();
                exit = false;
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

        } else if (id == R.id.nav_info) {
            MoreInfoFragment moreInfoFragment= new MoreInfoFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_main,moreInfoFragment).commit();
            toolbarTitle.setText("More Info");
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
            try {
                ShareCompat.IntentBuilder.from(MainActivity.this)
                        .setType("text/plain")
                        .setChooserTitle("Share via")
                        .setText("Download FAT( the official app of Foundation Against Thalassaemia and get all the " +
                                "information regarding our upcoming events. "+
                                "http://play.google.com/store/apps/details?id=" + getPackageName())
                        .startChooser();
            } catch(Exception e) {
                Log.d(Constants.TAG,"Share Error "+e.toString());
                //e.toString();
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Log.d(Constants.TAG, "Razorpay payment success id: "+razorpayPaymentID);
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.d(Constants.TAG, "Razorpay Payment failed code: "+code+" response: "+response);
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show();

    }


}