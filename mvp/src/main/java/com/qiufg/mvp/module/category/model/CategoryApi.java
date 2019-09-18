package com.qiufg.mvp.module.category.model;

import com.qiufg.mvp.bean.GankBean;
import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultArray;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fengguang.qiu on 2019/09/18 10:36.
 * <p>
 * Desc：分类页数据接口
 */
public interface CategoryApi {

    @GET(ServiceUrls.TEST_TEST_GANK)
    Flowable<ResultArray<GankBean>> getGankData(@Path("type") String type, @Path("number") int number, @Path("page") int page);
}
