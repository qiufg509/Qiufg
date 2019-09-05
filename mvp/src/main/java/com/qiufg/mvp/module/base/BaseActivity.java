package com.qiufg.mvp.module.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/**
 * Created by fengguang.qiu on 2019/08/12 15:43.
 * <p>
 * Desc：
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarEnable(false)
                .fitsSystemWindows(true)
                .init();
        mPresenter = initPresenter();
        if (mPresenter == null) {
            throw new IllegalStateException("Please call mPresenter in BaseActivity(initPresenter) to create!");
        } else {
            //noinspection unchecked
            mPresenter.attach(this);
        }
        viewCreated();
    }

    protected abstract @LayoutRes
    int createView();

    protected abstract P initPresenter();

    protected abstract void viewCreated();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
