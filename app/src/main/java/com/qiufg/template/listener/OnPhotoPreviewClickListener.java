package com.qiufg.template.listener;

/**
 * Created by fengguang.qiu on 2019/09/30 09:53.
 * <p>
 * Desc：照片预览页点击事件回调
 */
public interface OnPhotoPreviewClickListener {

    void onClickListener();

    void onLongClickListener(String url);
}
