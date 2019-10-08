package com.qiufg.template.module.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by fengguang.qiu on 2019/08/22 09:33.
 * <p>
 * Desc：基类IPresenter
 */
public class BasePresenter<V extends IView> implements IPresenter<V> {

    protected V mView;
    protected CompositeDisposable mDisposable;

    @Override
    public void attach(V view) {
        this.mView = view;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void detach() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        this.mView = null;
    }
}
