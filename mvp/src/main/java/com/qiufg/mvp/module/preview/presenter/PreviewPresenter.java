package com.qiufg.mvp.module.preview.presenter;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qiufg.mvp.App;
import com.qiufg.mvp.module.base.BasePresenter;
import com.qiufg.mvp.module.preview.view.PreviewView;
import com.qiufg.mvp.util.FileUtils;

import java.io.File;

/**
 * Created by fengguang.qiu on 2019/09/29 16:30.
 * <p>
 * Desc：照片预览Presenter
 */
public class PreviewPresenter extends BasePresenter<PreviewView> {


    public void downloadPhoto(String url) {
        Glide.with(App.getInstance()).asFile().load(url).into(new CustomTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                String savePath = FileUtils.saveFile2Album(resource);
                if (savePath == null) {
                    mView.downloadPhotoFail();
                } else {
                    mView.downloadPhotoSuccess(savePath);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
            }
        });
    }
}
