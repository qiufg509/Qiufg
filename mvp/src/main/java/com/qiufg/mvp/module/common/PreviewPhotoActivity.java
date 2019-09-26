package com.qiufg.mvp.module.common;

import com.gyf.immersionbar.ImmersionBar;
import com.qiufg.mvp.R;
import com.qiufg.mvp.module.base.BaseActivity;

/**
 * Created by fengguang.qiu on 2019/09/26 17:49.
 * <p>
 * Desc：照片预览页
 */
public class PreviewPhotoActivity extends BaseActivity {

    @Override
    protected int createView() {
        return R.layout.activity_preview_photo;
    }

    @Override
    protected void viewCreated() {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .statusBarColor(R.color.black)
                .statusBarDarkFont(false)
                .navigationBarEnable(true)
                .navigationBarColor(R.color.black)
                .init();

    }
}
