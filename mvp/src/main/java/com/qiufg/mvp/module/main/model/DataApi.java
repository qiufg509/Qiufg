package com.qiufg.mvp.module.main.model;

import com.qiufg.mvp.net.ServiceUrls;
import com.qiufg.mvp.net.respond.ResultObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by fengguang.qiu on 2019/08/12 17:08.
 * <p>
 * Desc：
 */
public interface DataApi {

    /**
     * http://gank.io/api/data/福利/1/2
     *
     * @param number number
     * @param page   page
     * @return result
     */
    @GET(ServiceUrls.TEST_TEST_GANK)
    Flowable<ResultObject> getData(@Path("number") int number, @Path("page") int page);
}
