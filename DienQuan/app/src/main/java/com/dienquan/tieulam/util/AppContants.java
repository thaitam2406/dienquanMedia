package com.dienquan.tieulam.util;

import com.leftorright.lor.BuildConfig;

/**
 * Created by nam on 17/02/2016.
 */
public class AppContants {

    public static final boolean isRelease = !BuildConfig.DEBUG;

    public static final int VOTING_SLEEP_DURATION = 1000;

    public static final int USER_TYPE_ANONYMOUS = 0;
    public static final int USER_TYPE_NULL = -1;
    public static final int USER_TYPE_LOGGED_IN = 1;
    public static final String USER_GENDER_MALE = "1";
    public static final String USER_GENDER_FEMALE = "2";


    public static final String VOTE_NONE = "0";
    public static final String VOTE_LEFT = "1";
    public static final String VOTE_RIGHT = "2";
    public static final String LOGIN_FACEBOOK_METHOD = "1";
    public static final String LOGIN_FACEBOOK_USER = "facebook";
    public static final int ONE_PERCENT = 100;

    public static final int PICK_PHOTO_LEFT = 1;
    public static final int PICK_PHOTO_RIGHT = 2;

    public static final String CARD_TOP20 = "CARD_TOP20";
    public static final String CARD_MALE = "CARD_MALE";
    public static final String CARD_FEMALE = "CARD_FEMALE";
    public static final String CARD_BONUS = "CARD_BONUS";
    public static final String PROFILE = "PROFILE";

    //Request Code Login
    public static final int REQUEST_LOGIN = 101;
    public static final int REQUEST_SEARCH = 102;
    public static final int REQUEST_CAM_LEFT = 103;
    public static final int REQUEST_CAM_RIGHT = 104;
    public static final int REQUEST_LOGIN_TO_PROFILE = 105;

    // Footer BTN POS
//    public static final int POS_TOP20 = 1;
//    public static final int POS_MALE = 2;
//    public static final int POS_VOTE = 3;
//    public static final int POS_FEMALE = 4;
//    public static final int POS_PROFILE = 5;

    /*GCM*/
    public final static String SEND_TOKEN_GCM_TO_SERVER = "SEND_TOKEN_GCM_TO_SERVER";
    public static final String RECEIVED_GCM_PUSH = "notification.gcm.received";

    public static final String GCM_PUSH_TOP20 = "Top 20";
    public static final String GCM_PUSH_MALE = "Male";
    public static final String GCM_PUSH_FEMALE = "Female";


    //request key
    public final static String IKEY_REGISTRATION_GCM_COMPLETE = "IKEY_REGISTRATION_GCM_COMPLETE";
    public final static String IKEY_REGISTRATION_GCM_REGID = "IKEY_REGISTRATION_GCM_REGID";
    public final static String IKEY_GCM_PUSHDATA = "IKEY_GCM_PUSHDATA";
    public final static String IKEY_IMG = "IKEY_IMG";
    public static final String IKEY_SIDE = "IKEY_SIDE";
}
