package com.dienquan.tieulam.customizeUI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Tam Huynh on 12/22/2014.
 */

public class ImageViewOptimize extends ImageView {
    private static int mCorners = 10;
    private boolean makeRequest;

    public ImageViewOptimize(Context context) {
        super(context);
        makeRequest = true;
    }

    public ImageViewOptimize(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageViewOptimize(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        makeRequest = true;
    }

    public void setCorner(int corners) {
        mCorners = corners;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        makeRequest = false;
        super.setImageBitmap(bm);
        makeRequest = true;
    }

    @Override
    public void setImageResource(int resId) {
        makeRequest = false;
        super.setImageResource(resId);
        makeRequest = true;
    }

    @Override
    public void setImageURI(Uri uri) {
        makeRequest = false;
        super.setImageURI(uri);
        makeRequest = true;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        makeRequest = false;
        super.setImageDrawable(drawable);
        makeRequest = true;
    }

    @Override
    public void requestLayout() {
        if (makeRequest)
            super.requestLayout();
    }

}
