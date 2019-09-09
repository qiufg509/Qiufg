package com.qiufg.mvp.module.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qiufg.mvp.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by fengguang.qiu on 2019/08/12 15:44.
 * <p>
 * Desc：基类Fragment
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {

    protected P mPresenter;
    protected View mRootView;
    private Unbinder mBinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (createView() != 0) {
            mRootView = inflater.inflate(createView(), container, false);
        }
        mPresenter = initPresenter();
        if (mPresenter == null) {
            throw new IllegalStateException("Please call mPresenter in BaseFragment(initPresenter) to onCreateView!");
        } else {
            //noinspection unchecked
            mPresenter.attach(this);
        }
        mBinder = ButterKnife.bind(this, mRootView);
        viewCreated(mRootView);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        if (mBinder != null) {
            mBinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getRefWatcher().watch(this);
    }

    protected abstract @LayoutRes
    int createView();

    protected abstract P initPresenter();

    protected abstract void viewCreated(View view);

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
