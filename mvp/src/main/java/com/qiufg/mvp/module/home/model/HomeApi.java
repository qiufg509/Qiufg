package com.qiufg.mvp.module.home.model;

import com.qiufg.mvp.bean.BannerBean;
import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultArray;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by fengguang.qiu on 2019/08/12 17:08.
 * <p>
 * Desc：首页（美女们）服务器数据接口
 */
public interface HomeApi {

    /**
     * http://gank.io/api/data/福利/1/2
     *
     * @param number number
     * @param page   page
     * @return result
     */
    @GET(ServiceUrls.TEST_TEST_GANK)
    Flowable<ResultArray<GirlsBean>> getGirlData(@Path("number") int number, @Path("page") int page);

    @GET
    Flowable<List<BannerBean>> getBannerData(@Url String url);
}
