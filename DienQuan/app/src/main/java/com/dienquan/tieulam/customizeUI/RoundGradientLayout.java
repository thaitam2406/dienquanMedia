package com.dienquan.tieulam.customizeUI;

/**
 * Created by Tam Tran on 08-Jun-15.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RoundGradientLayout extends LinearLayout {

    private Paint drawPaint;
    private Paint roundPaint;

    private int mCornerRadius = 5;

    private RectF bounds, bounds1;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RoundGradientLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onInit();
    }

    public RoundGradientLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        onInit();
    }

    public RoundGradientLayout(Context context) {
        super(context);
        onInit();
    }

    protected void onInit() {
        drawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        drawPaint.setColor(0xffffffff);
        drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        roundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        roundPaint.setColor(0xffffffff);

        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw && h != oldh) {
            bounds = new RectF(0, 0, w / 2, h);
            bounds1 = new RectF(w / 2, 0, w, h);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap((int) bounds.width(), (int) bounds.height(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        super.dispatchDraw(c);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);
        Paint paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setShader(shader);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setColor(Color.BLUE);
        canvas.drawRoundRect(bounds, mCornerRadius, mCornerRadius, paint);
        canvas.drawRoundRect(bounds1, mCornerRadius, mCornerRadius, paint1);
//        canvas.drawRoundRect(0,0,this.getWidth()/2 , this.getHeight() , mCornerRadius , mCornerRadius , paint);
    }

}