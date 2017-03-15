package com.qiufg.net.api;

import com.squareup.okhttp.ResponseBody;

import retrofit.http.GET;
import retrofit.http.Streaming;
import retrofit.http.Url;
import rx.Observable;

/**
 * Description
 * Author qiufg
 * Date 2017/3/15 1:24
 */

public interface DownLoadApi {
    @Streaming //Streaming注解，该注解的作用是在下载大文件中使用。添加了该注解后，下载文件不会将所有的下载内容加载到内存
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);
}
