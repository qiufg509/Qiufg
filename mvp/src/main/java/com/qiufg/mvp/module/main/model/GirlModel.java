package com.qiufg.mvp.module.main.model;

import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.bus.RxSchedulers;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.net.HttpClient;
import com.qiufg.mvp.net.respond.ResultArray;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:11.
 * <p>
 * Desc：
 */
public class GirlModel {

    private GirlApi mApi;

    public GirlModel() {
        mApi = HttpClient.getInstance().createApi(GirlApi.class);
    }

    public Disposable getData(int number, int page, Consumer<ResultArray<GirlsBean>> consumer, ErrorAction error) {
        return mApi.getData(number, page).compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }
}
