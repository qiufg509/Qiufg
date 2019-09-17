package com.qiufg.mvp.module.home.model;

import com.qiufg.mvp.bean.BannerBean;
import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.bus.RxSchedulers;
import com.qiufg.mvp.exception.ErrorAction;
import com.qiufg.mvp.net.HttpClient;
import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by fengguang.qiu on 2019/08/12 17:11.
 * <p>
 * Desc：首页Model
 */
public class HomeModel {

    private HomeApi mApi;

    public HomeModel() {
        mApi = HttpClient.getInstance().createApi(HomeApi.class);
    }

    public Disposable getGirlData(int number, int page, Consumer<List<GirlsBean>> consumer, ErrorAction error) {
        return mApi.getGirlData(number, page)
                .map(ResultArray::getResults)
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }

    public Disposable getBannerData(Consumer<List<String>> consumer, ErrorAction error) {
        return mApi.getBannerData(ServiceUrls.SERVER_GITHUB + ServiceUrls.API_BANNER_IMAGES)
                .map(bannerBeans -> {
                    List<String> strings = new ArrayList<>(bannerBeans.size());
                    for (BannerBean bannerBean : bannerBeans) {
                        strings.add(bannerBean.getDownload_url());
                    }
                    return strings;
                })
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }
}
