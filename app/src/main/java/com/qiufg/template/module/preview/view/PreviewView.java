package com.qiufg.template.module.preview.view;

import com.qiufg.template.module.base.IView;

/**
 * Created by fengguang.qiu on 2019/09/29 16:28.
 * <p>
 * Desc：照片预览View接口
 */
public interface PreviewView extends IView {

    void downloadPhotoSuccess(String path);

    void downloadPhotoFail();

    void setWallpaperSuccess();

    void setWallpaperFail();

    void setLockWrapperSuccess();

    void setLockWrapperFail();
}
