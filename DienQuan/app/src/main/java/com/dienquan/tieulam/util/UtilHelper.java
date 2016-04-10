package com.dienquan.tieulam.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tam Huynh on 2/2/2015.
 */
public class UtilHelper {

    public static String SCREEN_SIZE = "xlarge";
    public static final String SCREEN_XLARGE = "xlarge";
    public static final String SCREEN_LARGE = "large";
    public static final String SCREEN_NORMAL = "normal";
    public static final String SCREEN_SMALL = "small";
    public static float DEVICE_DPI = 0;

    public static int setNumOfIssuesPerRow(boolean isLandscape, boolean isMobile, boolean isTab7, boolean isTab10, boolean isTab12) {
        int columnCount = 0;
        if (isMobile) {
            columnCount = 2;
        } else {
            if (isLandscape) {
                if (isTab12 || isTab10) {
                    columnCount = 6;
                } else {
                    columnCount = 5;
                }
            } else {
                if (isTab12 || isTab10) {
                    columnCount = 4;
                } else {
                    columnCount = 3;
                }
            }
        }
        return columnCount;
    }

    public static boolean isMobile(Context mContext) {
        return getScreenSize(mContext).equals(SCREEN_NORMAL)
                || getScreenSize(mContext).equals(SCREEN_SMALL);
    }

    public static boolean isTab7(Context mContext) {
        return getScreenSize(mContext).equals(SCREEN_LARGE);
    }

    public static boolean isTab10(Context mContext) {
        return getScreenSize(mContext).equals(SCREEN_XLARGE);
    }

    public static boolean isTab12(Context context) {
        return getDimension(context) >= 12.0d ? true : false;
    }

    private static double getDimension(Context context) {
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        // since SDK_INT = 1;
        int mWidthPixels = displayMetrics.widthPixels;
        int mHeightPixels = displayMetrics.heightPixels;

        // includes window decorations (status bar/menu bar)
        try {
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
        } catch (Exception ignored) {
        }

        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d("debug", "Screen inches : " + screenInches);
        return screenInches;
    }

    public static String getScreenSize(Context mContext) {
        Configuration configuration = mContext.getResources().getConfiguration();
        if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            return SCREEN_XLARGE;
        else if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE)
            return SCREEN_LARGE;
        else if ((configuration.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
            return SCREEN_NORMAL;
        else
            return SCREEN_SMALL;

    }

    public static boolean checkInternetConnection(Context mContext) {
        ConnectivityManager conMgr = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static int getIdentifier(Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType,
                context.getPackageName());
    }

    public static String getFileName(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public static String SHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        Log.i("PDF Key", sb.toString());

        return sb.toString();
    }

    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        DEVICE_DPI = metrics.density;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Point size = new Point();
            display.getSize(size);
            return size;
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            return new Point(width, height);
        }
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
