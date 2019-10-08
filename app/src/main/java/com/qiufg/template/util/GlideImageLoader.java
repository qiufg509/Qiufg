package com.qiufg.template.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qiufg.template.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by fengguang.qiu on 2019/09/10 17:47.
 * <p>
 * Desc：轮播图图片加载器
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(R.mipmap.img_default))
                .into(imageView);
    }
}
