package com.qiufg.mvp.module.base;

/**
 * Created by fengguang.qiu on 2019/08/14 14:55.
 * <p>
 * Desc：
 */
public interface IPresenter<V extends IView> {

    void attach(V view);

    void detach();
}
