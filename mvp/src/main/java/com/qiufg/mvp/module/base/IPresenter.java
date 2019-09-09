package com.qiufg.mvp.module.base;

/**
 * Created by fengguang.qiu on 2019/08/14 14:55.
 * <p>
 * Desc：基类IPresenter接口
 */
public interface IPresenter<V extends IView> {

    void attach(V view);

    void detach();
}
