package com.dienquan.tieulam.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leftorright.lor.LorRApplication;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

//import com.google.android.gms.analytics.Tracker;
//import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Tam Huynh on 5/29/15.
 */
abstract class BaseActivityCompat extends AppCompatActivity {

    protected boolean isLandscape;
    protected Bundle bundle;
    private int xml;
//    private Tracker mTracker;

    public abstract int setLayout();

    public abstract void initUI();

    public abstract void initData();

    public abstract void initListener();

    public abstract void onConfigurationChanged();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xml = setLayout();
        setContentView(xml);
        bundle = savedInstanceState;

        initUI();
        initListener();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LorRApplication.Instance().getDefaultTracker();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (isStickyAvailable()) {
                EventBus.getDefault().registerSticky(this);
            } else {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {

        } catch (Exception e) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (LorRApplication.isDebug) {
            RefWatcher refWatcher = LorRApplication.getRefWatcher(this);
            refWatcher.watch(this);
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged();
    }

    protected boolean isStickyAvailable() {
        return false;
    }

    protected BaseActivityCompat getActivity() {
        return this;
    }
}
