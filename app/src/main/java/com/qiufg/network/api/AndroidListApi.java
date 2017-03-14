package com.qiufg.network.api;

import com.qiufg.model.AndroidResult;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public interface AndroidListApi {
    @GET("data/Android/{number}/{page}")
    Observable<AndroidResult> getAndroidData(@Path("number") int number, @Path("page") int page);
}
