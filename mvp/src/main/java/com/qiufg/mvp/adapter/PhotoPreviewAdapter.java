package com.qiufg.mvp.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.qiufg.mvp.R;
import com.qiufg.mvp.util.ToastUtils;

import java.io.File;
import java.util.List;

/**
 * Created by fengguang.qiu on 2019/09/27 16:09.
 * <p>
 * Desc：照片预览页适配器
 */
public class PhotoPreviewAdapter extends PagerAdapter {

    private Activity mActivity;
    private List<String> mUrls;

    public PhotoPreviewAdapter(Activity activity, List<String> urls) {
        mActivity = activity;
        mUrls = urls;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.item_photo_preview, container, false);
        SubsamplingScaleImageView scaleImageView = inflate.findViewById(R.id.photo_view);
        scaleImageView.setDoubleTapZoomDuration(500);
        scaleImageView.setMinScale(1);
        scaleImageView.setMaxScale(8);
        scaleImageView.setDoubleTapZoomScale(3);
        scaleImageView.setOnClickListener(v -> mActivity.finish());


        Glide.with(mActivity).asFile().load(mUrls.get(position))
                .into(new CustomViewTarget<SubsamplingScaleImageView, File>(scaleImageView) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        ToastUtils.toast("图片加载失败");
                    }

                    @Override
                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                        view.setImage(ImageSource.uri(Uri.fromFile(resource)));
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                    }
                });
        container.addView(inflate);
        return inflate;
    }

    @Override
    public int getCount() {
        return mUrls == null ? 0 : mUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            container.removeView((View) object);
            Glide.get(mActivity).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
