package com.dienquan.tieulam;

import android.content.Context;

import com.dienquan.tieulam.factory.FactoryImageLoader;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by tamhuynh on 2/3/16.
 */
public class Application extends android.app.Application {
    public static GoogleAnalytics analytics;
    public static boolean isDebug = false;
    private static Application application;
    private String googleAnalytisKey = "UA-74684815-1";
    private String accessToken;
    private String userId = "";
    //Google Analytics
    private Tracker mTracker;
    /**
     * init Leak Canary
     */
    private RefWatcher refWatcher;

    public Application() {
        super();
        application = this;

    }

    public static Application Instance() {
        return application;
    }

    public static RefWatcher getRefWatcher(Context context) {
        Application application = (Application) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize SDK
        initSDK();
        initData();
    }

    private void initData() {
        if (userId != null && !userId.isEmpty()) {
            setUserId(userId);
        }
    }

    /**
     * Gets the default {@link Tracker} for this {@link }.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.setLocalDispatchPeriod(1800);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(googleAnalytisKey); // Replace with actual tracker id
            mTracker.enableExceptionReporting(true);
            mTracker.enableAdvertisingIdCollection(true);
            mTracker.enableAutoActivityTracking(true);
        }
        return mTracker;
    }

    /**
     * initialize some SDK : Fresco (Image Loader)
     */
    private void initSDK() {
        Fresco.initialize(this);
        FactoryImageLoader.initImageLoaderUniversal(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (isDebug) {
            refWatcher = LeakCanary.install(this);
        }
    }


    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
//        this.accessToken = "EeymOSxDgZuLwWGVeaepW0BfXl1cQ8Yh";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
