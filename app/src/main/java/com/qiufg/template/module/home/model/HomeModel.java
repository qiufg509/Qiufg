package com.qiufg.template.module.home.model;

import com.qiufg.template.Const;
import com.qiufg.template.bean.BannerBean;
import com.qiufg.template.bean.GirlsBean;
import com.qiufg.template.bus.RxSchedulers;
import com.qiufg.template.exception.ErrorAction;
import com.qiufg.template.exception.QiufgCode;
import com.qiufg.template.exception.QiufgException;
import com.qiufg.template.net.HttpClient;
import com.qiufg.template.net.ServiceUrls;

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

    public Disposable getGirlData(String type, int number, int page, Consumer<List<GirlsBean>> consumer, ErrorAction error) {
        return mApi.getGirlData(type, number, page)
                .map(girlsBeanResultArray -> {
                    List<GirlsBean> results = girlsBeanResultArray.getResults();
                    if (results.isEmpty()) {
                        throw new QiufgException(QiufgCode.CODE_DATA_EMPTY);
                    }
                    return results;
                })
                .compose(RxSchedulers.ioSchedulers()).subscribe(consumer, error);
    }

    public Disposable getBannerData(Consumer<List<String>> consumer, ErrorAction error) {
        return mApi.getBannerData(ServiceUrls.SERVER_GITHUB + ServiceUrls.API_BANNER_IMAGES, Const.CLIENT_ID, Const.CLIENT_SECRET)
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
