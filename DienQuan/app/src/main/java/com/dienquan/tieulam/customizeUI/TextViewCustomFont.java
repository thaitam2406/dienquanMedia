package com.dienquan.tieulam.customizeUI;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.leftorright.lor.R;
import com.leftorright.lor.util.AssetsUtils;

/**
 * Created by tamhuynh on 3/20/16.
 */

public class TextViewCustomFont extends TextView {
    private static final String TAG = "TextView";

    public TextViewCustomFont(Context context) {
        super(context);
    }

    public TextViewCustomFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TextViewCustomFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String customFont = a.getString(R.styleable.CustomTextView_customFont);
        if (customFont != null && !customFont.equals(""))
            AssetsUtils.setFont(ctx, this, customFont);
        a.recycle();
    }


}
