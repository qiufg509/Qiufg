package com.qiufg.template.module.note.model;

import com.qiufg.template.bean.GitHub;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by qiufg on 2019/10/11 9:28.
 * <p>
 * Desc：我的笔记数据接口
 */
public interface NoteApi {

    @GET
    Observable<List<GitHub>> getDirectory(@Url String url, @Query("client_id") String client_id, @Query("client_secret") String client_secret);

    @GET
    Observable<List<GitHub>> getArticle(@Url String url, @Query("client_id") String client_id, @Query("client_secret") String client_secret);
}
