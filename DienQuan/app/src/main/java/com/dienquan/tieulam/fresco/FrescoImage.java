package com.dienquan.tieulam.fresco;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.leftorright.lor.R;

/**
 * Created by tamhuynh on 1/29/16.
 */
public class FrescoImage extends LinearLayout {

    private SimpleDraweeView simpleDraweeView;
    private ImageRequest request;

    public FrescoImage(Context context) {
        super(context);
        initUI(context);
    }

    public FrescoImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI(context);
    }

    public FrescoImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context);
    }

    private void initUI(Context context) {
        View viewRoot = LayoutInflater.from(context).inflate(R.layout.image_fresco, this);
        simpleDraweeView = (SimpleDraweeView) viewRoot.findViewById(R.id.fresco_image_view);
    }

    public void setCircleImage(boolean isCircle) {
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
        roundingParams.setRoundAsCircle(isCircle);
        simpleDraweeView.getHierarchy().setRoundingParams(roundingParams);
    }

    public void setBackgroundImage(String url) {
        Uri uri = Uri.parse(url);
        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        simpleDraweeView.setImageURI(uri);
    }

    public void setImageUri(String url) {
        Uri uri = Uri.parse(url);
        int width = 50, height = 50;
        request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);

        simpleDraweeView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        simpleDraweeView.setImageURI(uri, ContentResolver.SCHEME_ANDROID_RESOURCE);
    }

    public void setPlaceHolder(int id){
        simpleDraweeView.getHierarchy().setPlaceholderImage(id);
    }
    public Bitmap getBitmap(){
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, getContext());
        final Bitmap[] out = new Bitmap[1];

            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(@Nullable Bitmap bitmap) {
                    if (bitmap != null) {
                        out[0] = bitmap;
                    }else
                        Log.d("HAULX", "Bitmap data source returned success, but bitmap null.");
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {

                }
            }, CallerThreadExecutor.getInstance());

        return out[0];

    }
}
