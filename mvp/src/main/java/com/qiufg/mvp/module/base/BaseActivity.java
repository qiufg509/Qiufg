package com.qiufg.mvp.module.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by fengguang.qiu on 2019/08/12 15:43.
 * <p>
 * Desc：
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createView());

        mPresenter = createPresenter();
        if (mPresenter == null) {
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        } else {
            mPresenter.attach(this);
        }

        viewCreated();
    }

    protected abstract void viewCreated();

    protected abstract T createPresenter();

    protected abstract View createView();

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
