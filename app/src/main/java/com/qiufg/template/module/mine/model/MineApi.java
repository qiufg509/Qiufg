package com.qiufg.template.module.mine.model;

import com.qiufg.template.bean.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by qiufg on 2019/10/26 22:03.
 * <p>
 * Desc：我的页服务器数据接口
 */
public interface MineApi {

    @GET
    Observable<User> getUserInfo(@Url String url, @Query("client_id") String client_id, @Query("client_secret") String client_secret);
}
