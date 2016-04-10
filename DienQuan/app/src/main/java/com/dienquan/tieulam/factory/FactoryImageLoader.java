package com.dienquan.tieulam.factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.widget.ImageView;

import com.dienquan.tieulam.R;
import com.dienquan.tieulam.util.Util;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

/**
 * Created by tamhuynh on 11/18/15.
 */
public class FactoryImageLoader {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    public static String TAG = "FactoryImageLoader";

    public static ImageLoader factoryImageLoader() {

        return ImageLoader.getInstance();
    }

    public static void initImageLoaderUniversal(Context context) {
        // UNIVERSAL IMAGE LOADER SETUP
        final Point point = Util.getScreenResolution(context);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
//                .cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.place_holder_img)
                .showImageOnLoading(R.drawable.place_holder_img)
                .showImageOnFail(R.drawable.place_holder_img)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        return Bitmap.createScaledBitmap(bmp, point.x / 2,
                                point.y / 2, false);
                    }
                })
//                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .threadPoolSize(CPU_COUNT)
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);


        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    public static void displayImage(String url, ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

}
