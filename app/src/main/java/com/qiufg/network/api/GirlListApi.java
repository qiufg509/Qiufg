package com.qiufg.network.api;


import com.qiufg.model.GirlResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Author qiufg
 * Date 2017/2/20
 */

public interface GirlListApi {

    @GET("data/福利/{number}/{page}")
    Observable<GirlResult> getGirls(@Path("number") int number, @Path("page") int page);
}
