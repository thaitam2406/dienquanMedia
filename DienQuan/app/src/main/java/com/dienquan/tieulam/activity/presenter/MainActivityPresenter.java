package com.dienquan.tieulam.activity.presenter;

/**
 * Created by tamhuynh on 2/24/16.
 */
public interface MainActivityPresenter {
    void checkShowFooter(String tag);

    void checkShowHeader(String tag);

    void chooseImageFromGallery(boolean isLeft);
}
