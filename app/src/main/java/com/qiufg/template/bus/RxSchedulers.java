package com.qiufg.template.bus;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fengguang.qiu on 2019/3/19 19:40.
 * <p>
 * Desc：RxJava2线程切换
 */
public class RxSchedulers {

    /**
     * 流线程切换（io线程/主线程）
     *
     * @param <T> Observable操作的数据类型
     * @return Observable
     */
    public static <T> ObservableTransformer<T, T> ioSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 流线程切换（事件循环或和回调处理线程/主线程）
     *
     * @param <T> Observable操作的数据类型
     * @return Observable
     */
    public static <T> ObservableTransformer<T, T> normalSchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
