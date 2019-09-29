package com.qiufg.mvp.module.preview.view;

import com.qiufg.mvp.module.base.IView;

/**
 * Created by fengguang.qiu on 2019/09/29 16:28.
 * <p>
 * Desc：照片预览View接口
 */
public interface PreviewView extends IView {

    void downloadPhotoSuccess(String path);

    void downloadPhotoFail();

    void setWallpaperSuccess();

    void setLockWrapperSuccess();
}
