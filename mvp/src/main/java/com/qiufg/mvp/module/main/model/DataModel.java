package com.qiufg.mvp.module.main.model;

import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.net.HttpClient;
import com.qiufg.mvp.net.respond.ResultObject;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:11.
 * <p>
 * Desc：
 */
public class DataModel {

    private DataApi mApi;

    public DataModel() {
        mApi = HttpClient.getInstance().createApi(DataApi.class);
    }

    public void getData(int number, int page, Consumer<ResultObject> consumer) {
        Flowable<ResultObject> flowable = mApi.getData(number, page);
        Disposable subscribe = flowable.subscribe(consumer, new ErrorAction());
    }
}
