package com.qiufg.mvp.module.base;

/**
 * Created by fengguang.qiu on 2019/08/12 15:43.
 * <p>
 * Desc：MVP模板模式Activity
 */
public abstract class MVPActivity<P extends IPresenter> extends BaseActivity implements IView {

    protected P mPresenter;

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

    }
}
