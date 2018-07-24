package com.example.rupali.thalassaemiaapp;

public class Constants {
    static final int LOGIN_ACTIVITY_REQUEST_CODE=1;
    static final int LOGIN_ACTIVITY_RESULT_CODE=2;
    static final int RC_SIGN_IN=3;
    static final int FB_SIGN_IN =4;
    static final String DB_NAME="noti_db";
    static final int VERSION=10;
    final static String SPINNER_POS="spinnerPosition";
    static final int THALASSAEMIA_CARRIER_TEST=1;
    static final int BONE_MARROW_MATCHING=2;
    static final int STEM_CELLS_DONATION=3;
    static final String MAIL_PASSWORD="";
    static final String MAIL_EMAIL="thalassaemia12@gmail.com";
    static final String USERNAME="thalassaemia12@gmail.com";

    public class LoginSharedPref{
        final static String SHARED_PREF_NAME="loginInfo";
        final static String PREVIOUSLY_STARTED="previouslyStarted";
        final static String LOGIN_EMAIL="previouslyStarted";
        final static String LOGIN_URENAME="previouslyStarted";
        final static String PROFILE_URL="previouslyStarted";
        final static String LOGGED_IN="previouslyStarted";
        final static String LOGGED_IN_WITH_GOOGLE="previouslyStarted";
        final static String IS_EMAIL_VERIFIED="isEmailVerified";
    }
    public class NotifiactionActivityConstantts{
        static final String IMAGE_URI="image";
        static final String TITLE="title";
        static final String MESSAGE="message";
    }
    public class NotiTAble{
        static final String TABLE_NAME="noti_table";
        static final String TITLE="Title";
        static final String NOTI_ID="Id";
        static final String MESSAGE="Message";
        static final String IMAGE_URL="image_uri";
    }

}
