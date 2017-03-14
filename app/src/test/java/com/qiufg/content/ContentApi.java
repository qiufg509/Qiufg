package com.qiufg.content;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Author qiufg
 * Date 2017/3/6
 */

public interface ContentApi {
    @GET("api/data/Android/{size}/{page}")
    Observable<ResultBean> getContent(@Path("size") int size,@Path("page") int page);

}
