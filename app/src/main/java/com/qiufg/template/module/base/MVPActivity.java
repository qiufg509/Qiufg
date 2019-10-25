package com.qiufg.template.module.base;

import com.qiufg.template.wedget.dialog.LoadingDialog;

/**
 * Created by fengguang.qiu on 2019/08/12 15:43.
 * <p>
 * Desc：MVP模板模式Activity
 */
public abstract class MVPActivity<P extends IPresenter> extends BaseActivity implements IView {

    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void initBeforeView() {
        mPresenter = initPresenter();
        if (mPresenter == null) {
            throw new IllegalStateException("Please call mPresenter in MVPActivity(initPresenter) to create!");
        } else {
            //noinspection unchecked
            mPresenter.attach(this);
        }
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.with(this).show();
        } else if (!mLoadingDialog.isVisible()) {
            mLoadingDialog.show(this);
        }
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && !mLoadingDialog.isHidden()) {
            mLoadingDialog.dismiss();
        }
    }
}
