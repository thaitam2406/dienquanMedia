package com.dienquan.tieulam.activity.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dienquan.tieulam.activity.views.MainActivityView;
import com.dienquan.tieulam.util.AppContants;

/**
 * Created by tamhuynh on 2/24/16.
 */
public class MainActivityPresenterImpl implements MainActivityPresenter {
    private Context context;
    private MainActivityView mainActivityView;

    public MainActivityPresenterImpl(Context context, MainActivityView mainActivityView) {
        this.context = context;
        this.mainActivityView = mainActivityView;
    }

    /**
     * check show/hide footer appropriate fragment
     *
     * @param tag
     */
    @Override
    public void checkShowFooter(String tag) {
    }

    @Override
    public void checkShowHeader(String tag) {
    }

    @Override
    public void chooseImageFromGallery(boolean isLeft) {
        pickImage(isLeft);
    }

    public void pickImage(boolean isLeft) {
        int id = AppContants.PICK_PHOTO_RIGHT;
        if (isLeft) {
            id = AppContants.PICK_PHOTO_LEFT;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, id);
    }
}
