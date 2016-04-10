package com.dienquan.tieulam.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.dienquan.tieulam.Application;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;

//import com.google.android.gms.analytics.Tracker;
//import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Tam Huynh on 5/29/15.
 */
abstract class BaseActivity extends FragmentActivity {

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

        Application.Instance().getDefaultTracker();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Application.isDebug) {
            RefWatcher refWatcher = Application.getRefWatcher(this);
            refWatcher.watch(this);
        }
        ButterKnife.unbind(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged();
    }
}
