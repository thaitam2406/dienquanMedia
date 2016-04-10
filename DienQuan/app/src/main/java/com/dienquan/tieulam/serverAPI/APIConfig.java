package com.dienquan.tieulam.serverAPI;

/**
 * Created by tamhuynh on 1/31/16.
 */
public class APIConfig {

    public static final String apiGetSession = "common.getSession";
    //public static final String apiGetCardIds = "card.getList20";
    public static final String apiGetCardTop20 = "card.getList20";
    public static final String apiGetCardMale = "card.getListMale";
    public static final String apiGetCardFemale = "card.getListFemale";
    public static final String apiGetCardBonus = "card.getListBonus";
    public static final String apiGetListComment = "card.commentList";
    public static final String apiPostComment = "card.comment";
    public static final String apiUploadImage = "http://static.lorr.rocket-vn.com/rest";
    public static final String apiSearchImage = "http://lorr.rocket-vn.com/api/v1/search_images/{keyword}";
    public static final String apiVoteCard = "card.vote";
    public static final String apiPostCard = "card.post";
    public static final String apiUploadMultiImage = "image.multiUpload";
    public static final String apiLoginFacebook = "user.login";
    public static final String apiGetProfile = "user.detail";
    static final String DOMAIN_DEV = "http://lorr.rocket-vn.com/rest/";
    //    public static final String domainAPI = AppContants.isRelease ? DOMAIN_PRO : DOMAIN_DEV;
    public static final String domainAPI = DOMAIN_DEV;
    static final String DOMAIN_STAGING = "http://stg.leftorright.vn/rest/";
    static final String DOMAIN_PRO = "http://leftorright.vn/rest/";
    static final String PRIVATE_KEY_PRO = "eebnw8LN4cwi4SHP";
    static final String PRIVATE_KEY_DEV = "android_2016_02_03";
    public static String appVersion = "1.0";
    public static String apiVersion = "1";
    public static String privateKey = PRIVATE_KEY_DEV ;//AppContants.isRelease ? PRIVATE_KEY_PRO : PRIVATE_KEY_DEV;

}
