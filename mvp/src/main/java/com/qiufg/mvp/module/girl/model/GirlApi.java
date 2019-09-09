package com.qiufg.mvp.module.girl.model;

import com.qiufg.mvp.bean.GirlsBean;
import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultArray;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fengguang.qiu on 2019/08/12 17:08.
 * <p>
 * Desc：首页（美女们）服务器数据接口
 */
public interface GirlApi {

    /**
     * http://gank.io/api/data/福利/1/2
     *
     * @param number number
     * @param page   page
     * @return result
     */
    @GET(ServiceUrls.TEST_TEST_GANK)
    Flowable<ResultArray<GirlsBean>> getData(@Path("number") int number, @Path("page") int page);
}
