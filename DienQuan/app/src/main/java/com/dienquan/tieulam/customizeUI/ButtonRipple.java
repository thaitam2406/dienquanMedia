package com.dienquan.tieulam.customizeUI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.leftorright.lor.R;


/**
 * Created by Tam Huynh on 3/31/2015.
 */

public class ButtonRipple extends Button {

    private float mRadius;

    private Paint mPaint;
    private Paint mRectPaint;

    private Coord mCoord;

    public ButtonRipple(final Context context) {
        super(context);
        init();
    }

    public ButtonRipple(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonRipple(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.color_blue_default));

        mRectPaint = new Paint();
        mRectPaint.setColor(Color.argb(0, 84, 110, 122));

        mCoord = new Coord(0, 0);
    }

    @Override
    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            float mCenterX = (getTranslationX() + getWidth()) / 2.0f;
            float mCenterY = (getTranslationY() + getHeight()) / 2.0f;

            // reliable? http://stackoverflow.com/q/1410885
            mCoord.setX(event.getX());
            mCoord.setY(event.getY());

            Interpolator interpolator = new LinearInterpolator();
            long duration = 300;

            ObjectAnimator animRadius = ObjectAnimator.ofFloat(this, "radius", 10f, getWidth() / 3f);
            animRadius.setInterpolator(interpolator);
            animRadius.setDuration(duration);

            ObjectAnimator animAlpha = ObjectAnimator.ofInt(mPaint, "alpha", 200, 0);
            animAlpha.setInterpolator(interpolator);
            animAlpha.setDuration(duration);

            ObjectAnimator animX = ObjectAnimator.ofFloat(mCoord, "x", mCoord.x, mCenterX);
            animX.setInterpolator(interpolator);
            animX.setDuration(duration);

            ObjectAnimator animY = ObjectAnimator.ofFloat(mCoord, "y", mCoord.y, mCenterY);
            animY.setInterpolator(interpolator);
            animY.setDuration(duration);

            ObjectAnimator animRectAlpha = ObjectAnimator.ofInt(mRectPaint, "alpha", 0, 100, 0);
            animRectAlpha.setInterpolator(interpolator);
            animRectAlpha.setDuration(duration);

            AnimatorSet animSetAlphaRadius = new AnimatorSet();
            animSetAlphaRadius.playTogether(animRadius, animAlpha, animX, animY, animRectAlpha);
            animSetAlphaRadius.start();

        }
        invalidate();
        return super.onTouchEvent(event);
    }

    public void setRadius(final float radius) {
        mRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(@NonNull final Canvas canvas) {
        // before super.onDraw so it goes under the text
        canvas.drawCircle(mCoord.x, mCoord.y, mRadius, mPaint);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mRectPaint);

        super.onDraw(canvas);
    }

    private class Coord {
        public float x;
        public float y;

        public Coord(float xValue, float yValue) {
            this.x = xValue;
            this.y = yValue;
        }

        private void setX(float value) {
            this.x = value;
        }

        private void setY(float value) {
            this.y = value;
        }
    }
}