package com.example.rupali.thalassaemiaapp.JavaClass;

import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class Constants {
    public static String TAG="myLogTag";
    public static final int LOGIN_ACTIVITY_REQUEST_CODE=1;
    public static final int LOGIN_ACTIVITY_RESULT_CODE=2;
    public static final int RC_SIGN_IN=3;
    public static final int FB_SIGN_IN =4;
    public static final String DB_NAME="noti_db";
    public  static final int VERSION=10;
    public final static String SPINNER_POS="spinnerPosition";
    public static final int THALASSAEMIA_CARRIER_TEST=1;
    public static final int BONE_MARROW_MATCHING=2;
    public static final int STEM_CELLS_DONATION=3;
    public static final String MAIL_PASSWORD="";
    public static final String MAIL_EMAIL="thalassaemia12@gmail.com";
    public static final String POLICY_STRING_HEAD="privacyPolicyhead";
    public static final int PRIVACY_POLICY=1;
    public static final int TERMS_OF_SERVICE=2;
    public static final int OPEN_SOURCE_LIBRARIES=3;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;
    public static GoogleSignInClient mGoogleSignInClient;
    public static GoogleSignInOptions gso;
    public static GoogleSignInAccount account;
    public static FirebaseMessaging firebaseMessaging;
    public static FirebaseDatabase database;
    public static DatabaseReference myRef;

    public class LoginSharedPref{
        public final static String SHARED_PREF_NAME="loginInfo";
        public final static String PREVIOUSLY_STARTED="previouslyStarted";
        public final static String LOGIN_EMAIL="loginEmail";
        public final static String LOGIN_URENAME="loginUsername";
        public final static String PROFILE_URL="loginProfileUrl";
        public final static String LOGGED_IN="is_logged_in";
      //  public final static String LOGGED_IN_WITH_GOOGLE="is_logged_in_with_google";
        public final static String IS_EMAIL_VERIFIED="isEmailVerified";
    }
    public class NotifiactionActivityConstantts{
        public static final String IMAGE_URI="image";
        public static final String TITLE="title";
        public static final String MESSAGE="message";
    }
    public class NotiTAble{
        public static final String TABLE_NAME="noti_table";
        public static final String TITLE="Title";
        public static final String NOTI_ID="Id";
        public  static final String MESSAGE="Message";
        public static final String IMAGE_URL="image_uri";
    }

}
